/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.services;

/**
 *
 * @author megapc
 */
import Elit.entities.Classroom;
import Elit.entities.Club;
import Elit.entities.Equipment;
import Elit.entities.Event;
import Elit.entities.User;
import Elit.utils.DbConnection;
import Elit.utils.Mailer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author megapc
 */
public class EventServices {

    Connection cnx2;

    public EventServices() {
        cnx2 = DbConnection.getInstance().getCnx();
    }

    public boolean ajouterEvent(Event e, List<Equipment> l, List<Integer> lq) {

        // verifier nb equip disponible sinn tt abandonner
        List<Equipment> myList = new ArrayList<Equipment>();
        try {
            String req = "Select * from equipement";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Equipment eq = new Equipment(rs.getInt("id"), rs.getFloat("qte"));

                myList.add(eq);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        long ok = 0;  //respectd nbr
        for (int i = 0; i < l.size(); i++) {
            final int id = l.get(i).getId();
            final int qte = lq.get(i);
            ok += myList.stream()
                    .filter((eqq) -> eqq.getId() == id && eqq.getQte() >= qte)
                    .count();
        }

        if (ok == l.size()) {
            try {
                //    Event z = new Event(0, title, startDate, startHour, description, logo, club, classroom);
                int id = e.getId();
                String title = e.getTitle();
                String description = e.getDescription();
                Club club = e.getClub();
                Date startDate = e.getStartDate();
                Date startHour = e.getStartHour();
                String logo = e.getLogo();
                Classroom classroom = e.getClassroom();
                String req = "INSERT INTO Event  VALUES (?,?,?,?,?,?,?,?,'no')";
                PreparedStatement pst = cnx2.prepareStatement(req);
                pst.setInt(1, id);
                pst.setString(2, title);
                pst.setDate(3, new java.sql.Date(startDate.getTime()));
                pst.setTime(4, new java.sql.Time(startHour.getTime()));
                pst.setString(5, description);
                pst.setString(6, logo);
                pst.setInt(7, club.getId());
                pst.setInt(8, classroom.getId());
                System.out.println(pst.toString());
                pst.executeUpdate();

                for (int i = 0; i < l.size(); i++) {
                    try {
                        req = "INSERT INTO events_equipements  VALUES (?,?,?)";
                        pst = cnx2.prepareStatement(req);
                        pst.setInt(1, id);
                        pst.setInt(2, l.get(i).getId());
                        pst.setInt(3, lq.get(i));
                        pst.executeUpdate();
                        System.out.println("eq ajoute");
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                        return false;
                    }
                }
                for (int i = 0; i < l.size(); i++) {
                    try {
                        req = "update equipement  set qte = qte -  ? where id = ?";
                        pst = cnx2.prepareStatement(req);
                        pst.setInt(2, l.get(i).getId());
                        pst.setInt(1, lq.get(i));
                        pst.executeUpdate();
                        System.out.println("equip updated ajoute");
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                        return false;
                    }
                }

                System.out.println("ajout event good");
                req = "select email from user";
                 cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next())
            {
               System.out.println("sending emails"); 
   /*  Mailer.send("elitjava3a17@gmail.com","elitjava",rs.getString(1),
             "Hello ! Dont miss this new Event !",e.getTitle() + " at "+ e.getStartDate() 
               +" \nfor more informations please check within your Website/Application "); */}
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }else
        {
            System.out.println("Condition qte eq non respectée ");
            return false;
        }
        return true;
    }
    
    public List<Event> listerEvent() {
        List<Event> myList = new ArrayList<Event>();
        try {
            String req = "Select e.*,c.*,cl.* from club c,event e,classrooms cl where e.idclub = c.id and e.idclassroom=cl.id";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(16));
                Club club = new Club (rs.getInt(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getDate(14),rs.getString(15),u);
                Classroom classroom = new Classroom(rs.getInt(17),rs.getString(18),rs.getInt(19),rs.getString(20));
                Event e = new Event(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5),rs.getString(6), club, classroom);

                
                
                myList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Event> listerEvent(Map<String, String> criteria, String op) {
        // Lister mes events selon des parametres specifiés, et l'operateur specifie (and/or) , recherche avancée
        List<Event> myList = new ArrayList<Event>();
        try {
            String crits = "";

            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                crits += "e." + entry.getKey() + " = ? " + op + " ";
            }
            crits = crits.substring(0, crits.length() - 4);
            System.out.println(crits);
            String req = "Select e.*,c.*,cl.* from club c,event e,classrooms cl where ("+crits+") and e.idclub = c.id and e.idclassroom=cl.id";
            PreparedStatement pst = cnx2.prepareStatement(req);

            int i = 1;
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                pst.setString(i, entry.getValue());
                i++;
            }
            //System.out.println(pst.toString());
            ResultSet rs = pst.executeQuery();//select bel execute query
         while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(16));
                Club club = new Club (rs.getInt(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getDate(14),rs.getString(15),u);
                Classroom classroom = new Classroom(rs.getInt(17),rs.getString(18),rs.getInt(19),rs.getString(20));
                Event e = new Event(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5),rs.getString(6), club, classroom);

                
                
                myList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public int supprimerEvent(Event e) {
        try {
            int id = e.getId();
            
            String req = "select * from events_equipements where event_id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();//select bel execute query
         Map<Integer,Integer> equips = new HashMap<Integer,Integer>();
            while (rs.next()) { 
         
         equips.put(rs.getInt(2),rs.getInt(3));
         }
           for (Map.Entry<Integer,Integer> entry : equips.entrySet())
           {
                        req = "update equipement  set qte = qte +  ? where id = ?";
                        pst = cnx2.prepareStatement(req);
                        pst.setInt(2, entry.getKey());
                        pst.setInt(1, entry.getValue());
                        pst.executeUpdate();
                      
           }
            
         
            req = "Delete from Event where id = ?";
             pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            System.out.println(pst.toString());
            return pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public void modifierEvent(Event e,List<Equipment> l, List<Integer> lq) {
        
         Map<String,String> criteria = new HashMap<String,String>();
        criteria.put("id", Integer.toString(e.getId()));
        Event old = this.listerEvent(criteria, "and").get(0);//recuperer l'ancien event
        List<Integer> lqold = new ArrayList<Integer>();
        String req = "select * from events_equipements where event_id = ?";
        try{
        PreparedStatement pst = cnx2.prepareStatement(req);
        pst.setInt(1, e.getId());
      ResultSet rs= pst.executeQuery();
       while(rs.next())
       {
           lqold.add(rs.getInt(3));
       }
            System.out.println(lqold);
        }catch (Exception exc)
        {
            System.out.println(exc.getMessage());
        }
        this.supprimerEvent(e);
        if(this.ajouterEvent(e, l, lq) == true)
        {
            System.out.println("Modify success");
        }
        else
        {
            this.ajouterEvent(old,l,lqold);
        }
        
        
    }

}
