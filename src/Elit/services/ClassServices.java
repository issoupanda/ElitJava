/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.services;

import Elit.entities.Classe;
import Elit.utils.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author kinga
 */
public class ClassServices {

    Connection c;

    public ClassServices() {
        c = DbConnection.getInstance().getCnx();
    }

    public void addClass(Classe cl) {
        try {
            String requete = "insert into classes (name,level) values(?,?)";
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1, cl.getName());
            pst.setString(2, cl.getLevel());
            pst.executeUpdate();
            System.out.println("Class added !!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Classe> ListClasse() {
        List<Classe> Mylist = new ArrayList<>();
        try {
            String requete = "select * from classes";
            PreparedStatement pst = c.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Classe p = new Classe();
                p.setName(rs.getString("name"));
                p.setLevel(rs.getString("level"));
                p.setId(rs.getInt("id"));
                Mylist.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }

    public void UpdateClasse(Classe cl) {
        try {
            String requete = "update classes set (name,level) values(?,?) where ? = id";
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1, cl.getName());
            pst.setString(2, cl.getLevel());
            pst.setInt(3, cl.getId());
            pst.executeUpdate();
            System.out.println("Classe Updated !!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void DeleteClasse(Classe cl) {
        try {
            String requete = "delete from classes where ? = id";
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setInt(1, cl.getId());
            pst.executeUpdate();
            System.out.println("Classe Deleted !!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
