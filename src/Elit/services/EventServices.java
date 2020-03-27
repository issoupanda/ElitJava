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
import Elit.utils.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

    public void ajouterEvent(Event e, List<Equipment> l, List<Integer> lq) {

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
                    }
                }

                System.out.println("ajout event good");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }else
        {
            System.out.println("Condition qte eq non respectée ");
        }
    }
    /*
    public List<Club> listerClub() {
        List<Club> myList = new ArrayList<Club>();
        try {
            String req = "Select c.*, u.* from club c,user u where c.studentid = u.id";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
                Club c = new Club();

                c.setId(rs.getInt("id"));
                c.setTitle(rs.getString(2));
                c.setDescription(rs.getString(3));
                c.setCategory(rs.getString(4));
                c.setCreationDate(rs.getDate(5));
                c.setLogo(rs.getString(6));
                User u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("fistname"), rs.getString("lastname"));

                c.setPresident(u);
                myList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Club> listerClub(Map<String, String> criteria, String op) {
        // Lister mes clubs selon des parametres specifiés, et l'operateur specifie (and/or) , recherche avancée
        List<Club> myList = new ArrayList<Club>();
        try {
            String crits = "";

            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                crits += "c." + entry.getKey() + " = ? " + op + " ";
            }
            crits = crits.substring(0, crits.length() - 4);
            System.out.println(crits);
            String req = "Select c.*, u.* from club c,user u where (" + crits + ") and c.studentid = u.id";
            PreparedStatement pst = cnx2.prepareStatement(req);

            int i = 1;
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                pst.setString(i, entry.getValue());
                i++;
            }
            //System.out.println(pst.toString());
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
                Club c = new Club();
                c.setId(rs.getInt("id"));
                c.setTitle(rs.getString(2));
                c.setDescription(rs.getString(3));
                c.setCategory(rs.getString(4));
                c.setCreationDate(rs.getDate(5));
                c.setLogo(rs.getString(6));
                User u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("fistname"), rs.getString("lastname"));
                c.setPresident(u);
                myList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public int supprimerClub(Club c) {
        try {
            int id = c.getId();
            String req = "Delete from Club where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            System.out.println(pst.toString());
            return pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public void modifierClub(Club c) {
        try {
            int id = c.getId();
            String title = c.getTitle();
            String description = c.getDescription();
            String category = c.getCategory();
            Date creationDate = c.getCreationDate();
            String logo = c.getLogo();
            User president = c.getPresident();
            String req = "update club set  title = ?, description = ? , category = ?, creationdate =  ? , logo = ? , studentid = ? where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);

            pst.setString(1, title);
            pst.setString(2, description);
            pst.setString(3, category);
            pst.setDate(4, new java.sql.Date(creationDate.getTime()));
            pst.setString(5, logo);
            pst.setInt(6, president.getId());
            pst.setInt(7, id);
            System.out.println(pst.toString());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }*/

}
