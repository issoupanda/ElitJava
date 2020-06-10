/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kinga
 */
public class DbConnection {

    String url = "jdbc:mysql://localhost:3306/pidevelit2";
    String login = "root";
    String mdp = "";
    Connection cnx;
    public static DbConnection instance; 
    
    private DbConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, mdp);
            System.out.println("Connection établie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static DbConnection getInstance(){
        if (instance == null){
            instance = new DbConnection();
        }
        return instance;
    }
    public Connection getCnx(){
        return cnx;
    }
            
}

