/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.tests;

import Elit.entities.Classroom;
import Elit.entities.Club;
import Elit.entities.Equipment;
import Elit.entities.Event;
import Elit.entities.User;
import Elit.services.ClubServices;
import Elit.services.EventServices;
import Elit.utils.DbConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kinga
 */
public class ElitMain {
    public static void main(String[] args) {
        System.out.println("Test");
        
        
        User e1 = new User (169,"issam.benmoussa@esprit.tn","issam","Ben moussa");
        User e2 = new User (888,"issam.benmoussa96@gmail.com","wassim","Brahem");
        

               Calendar c= Calendar.getInstance();
       Date dd = new Date();
       c.set(2019,3, 16);
       
       
      dd=c.getTime();
        Club C = new Club(1566, "syphp2", "javades", "javacat", dd, "ddddde", e1);
        String p = "bokhlic9de0d3b-4c87-43af-92a5-f836ff338bdd.jpg";
        Club C1 = new Club(1569, "Heni", "Nheb", "50 Euro", dd,p/* "C:\\Users\\megapc\\Desktop\\bokhli.jpg"*/, e2);
        ClubServices cs = new ClubServices();
        System.out.println(C1.getCreationDate().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                String strDate = dateFormat.format(C1.getCreationDate()); 
                System.out.println(strDate);
                
               
    //    cs.ajouterClub(C1);
  //      cs.supprimerClub(C1);
     /*   List<Club> l = cs.listerClub();
        cs.ajouterClub(C);
        System.out.println(l);
        Map<String,String> criteria = new HashMap<String,String>();
        criteria.put("id", "777");
       criteria.put("creationdate", "2020-04-16");
       List<Club> l1 = cs.listerClub(criteria,"and");
        System.out.println(l1);*/
       // System.out.println(cs.supprimerClub(C));
  //  cs.ajouterClub(C);
 //   cs.modifierClub(C);
// 
// Date dd1 = new Date();
//       c.set(2019,3, 16,13,54,00);
//      dd1=c.getTime();
// 
//        Equipment eq1 = new Equipment(1, "chaise","logistic", 10, 10);
//         Equipment eq2 = new Equipment(2, "kes","bologistic", 9,9);
//          Equipment eq3 = new Equipment(3, "table","khilogistic", 8,8);
//          Classroom c1 = new Classroom(1, "A1", 50, "A");
//          Classroom c2 = new Classroom(2, "B1", 150, "B");
//          Classroom c3 = new Classroom(3, "C1", 550, "C");
//        Event ev1 = new Event(11, "ev11", dd1, dd1, "fun","chem", C, c1);
//        Event ev2 = new Event(211, "ev211", dd1, dd1, "fuun","cheem", C, c2);
//        Event ev3 = new Event(311, "ev311", dd1, dd1, "fuun","cheem", C, c3);
//        EventServices es = new EventServices();
//        List<Equipment> le1 = new ArrayList<Equipment>();
//        List<Equipment> le2 = new ArrayList<Equipment>();
//        List<Integer> lq1 = new ArrayList<Integer>();
//        le1.add(eq1);
//        le1.add(eq2);
//        le1.add(eq3);
//        le2.add(eq2);
//        lq1.add(1);
//        lq1.add(2);
//        lq1.add(10);
//        System.out.println(
//        es.listerEvent());
//   //   es.ajouterEvent(ev3, le1, lq1);
//      /*        Map<String,String> criteria = new HashMap<String,String>();
//        criteria.put("id", "11");
//       criteria.put("startdate", "2019-04-15");
//        System.out.println(es.listerEvent(criteria,"and"));*/
// //    es.supprimerEvent(ev3);
//     es.modifierEvent(ev2, le1, lq1);
    }
  
}
