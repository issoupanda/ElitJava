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
import Elit.entities.User;
import Elit.entities.Club;
import Elit.utils.Copy;
import Elit.utils.DbConnection;
import Elit.utils.sqlexcept;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author megapc
 */
public class ClubServices {

    Connection cnx2;

    public ClubServices() {
        cnx2 = DbConnection.getInstance().getCnx();
    }

    public void ajouterClub(Club c) throws sqlexcept {
        try {
            int id = c.getId();
            String title = c.getTitle();
            String description = c.getDescription();
            String category = c.getCategory();
            Date creationDate = c.getCreationDate();
            String logo = c.getLogo();
            User president = c.getPresident();
            String req = "INSERT INTO Club (title,description,category,creationDate,logo,studentid)  VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cnx2.prepareStatement(req);
      //      pst.setInt(1, id);
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setString(3, category);
            pst.setDate(4, new java.sql.Date(creationDate.getTime()));
      // ajout image, avec un id unique    
            UUID u = UUID.randomUUID();
            String old = logo;
            String extension = logo.substring(logo.lastIndexOf("."));
           logo = logo.substring(logo.lastIndexOf("\\")+1,logo.lastIndexOf("."));
           logo = logo + u.toString() + extension;
          // fin ajout image
            pst.setString(5, logo);
            pst.setInt(6, president.getId());
            System.out.println(pst.toString());
            pst.executeUpdate();
          //deplacement vers le dossier du serveur web
            File source = new File(old);
            File dest = new File("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+logo);
         
        Copy.copyFileUsingStream(source,dest);
        //fin deplacement
            System.out.println("ajout club good");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new sqlexcept(e.getMessage());
        }
        
    }

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
            int result = -1;
            String req = "Delete from Club where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            System.out.println(pst.toString());
            result = pst.executeUpdate();
            String logo = c.getLogo();
            File f = new File("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+logo);
            f.delete();
            
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public void modifierClub(Club c) throws sqlexcept {
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
            
                // ajout image, avec un id unique    
            UUID u = UUID.randomUUID();
            String old = logo;
            String extension = logo.substring(logo.lastIndexOf("."));
           logo = logo.substring(logo.lastIndexOf("\\")+1,logo.lastIndexOf("."));
           logo = logo + u.toString() + extension;
          // fin ajout image
            pst.setString(5, logo);
            
            pst.setInt(6, president.getId());
            System.out.println(president.getId());
            pst.setInt(7,id);
            System.out.println(pst.toString());
            pst.executeUpdate();
    //deplacement vers le dossier du serveur web
            File source = new File(old);
            File dest = new File("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+logo);
         
        Copy.copyFileUsingStream(source,dest);
        //fin deplacement
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
             throw new sqlexcept(ex.getMessage());
        }
       
    }

}
