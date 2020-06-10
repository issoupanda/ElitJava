/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Classroom;
import Elit.entities.Club;
import Elit.entities.Equipment;
import Elit.entities.Event;
import Elit.entities.User;
import Elit.services.ClubServices;
import Elit.services.EventServices;
import Elit.utils.DbConnection;
import Elit.utils.sqlexcept;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class AddEventController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField Desc;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXTimePicker startHour;
    @FXML
    private JFXTextField img;
    @FXML
    private JFXButton btnImg;
    @FXML
    private JFXComboBox<String> Club;
    @FXML
    private JFXComboBox<String> classroom;
    @FXML
    private Pane equiPane;
    
    private List<Club> lc;
    private List<Classroom> ls=new ArrayList<Classroom>();
    private List<Equipment> le = new ArrayList<Equipment>();
    List<JFXTextField> tabT = new ArrayList<JFXTextField>();
     Connection cnx2;
    @FXML
    private VBox vPn;
    @FXML
    private ImageView picture;
    @FXML
    private JFXButton addEvent;
    @FXML
    private JFXTextField searchkey;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add classrooms
          cnx2 = DbConnection.getInstance().getCnx();           
        try {
            String req = "Select  * from classrooms ";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
              Classroom cl = new Classroom(rs.getInt(1),rs.getString(2), rs.getInt(3),rs.getString(4));
     ls.add(cl);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        for ( Classroom c : ls)
        {
            classroom.getItems().add(c.getName()+" "+c.getBloc());
        }
        ClubServices cs = new ClubServices();
        lc=cs.listerClub();
        for (Club c : lc)
        {
            Club.getItems().add(c.getTitle());
        }
        //adding equips
         try {
            String req = "Select  * from equipement ";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
              Equipment e = new Equipment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),rs.getInt(5));
     le.add(e);
     Label equipName = new Label (e.getLabel());
     Label qte = new Label("quantity : ");
     JFXTextField tqte = new JFXTextField();
     tqte.setText("0");
     tabT.add(tqte);
     tqte.setMaxWidth(30);
     HBox h = new HBox();
     h.getChildren().add(equipName);
     h.getChildren().add(qte);
     h.getChildren().add(tqte);
     h.setSpacing(10);
     h.setAlignment(Pos.BASELINE_CENTER);

     vPn.getChildren().add(h);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }  

    @FXML
    private void addPic(ActionEvent event) {
         System.out.println("ss");
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if(selected !=null)
        {
            String extension = selected.getAbsolutePath().substring(selected.getAbsolutePath().length()-3,selected.getAbsolutePath().length());
            System.out.println(extension);
            if(!extension.equals( "jpg") && !extension.equals("png"))
            {
                              Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Picture");
        alert.setHeaderText("Invalid picture format");
        alert.setContentText("Verify your entries !png/jpg allowed"); 
     
        alert.showAndWait();
            }else{
            img.setText(selected.getAbsolutePath());
                Image i;
    try {
        i = new Image(new FileInputStream(selected.getAbsolutePath()));
         this.picture.setImage(i);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
    }}
        
    }
    }
    @FXML
    private void addEvent(ActionEvent event) {
      
        if(title.getText().isEmpty()|| startHour.getValue()==null || startDate.getValue()==null || Desc.getText().isEmpty() || img.getText().isEmpty()
                || searchkey.getText().isEmpty() || classroom.getSelectionModel().getSelectedIndex()==-1 || Club.getSelectionModel().getSelectedIndex()==-1 )
        {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("Verify your entries ! They cannot be empty"); 
     
        alert.showAndWait();
        }else{
        
        Classroom cls = ls.get(classroom.getSelectionModel().getSelectedIndex());
        Club c = lc.get(Club.getSelectionModel().getSelectedIndex());
        LocalTime lt =startHour.getValue();
       Calendar calendar= Calendar.getInstance();
       Date startHour = new Date();
       calendar.set(0, 0, 0, lt.getHour(), lt.getMinute(),0);
       startHour=calendar.getTime();
       LocalDate sd = startDate.getValue();
       calendar.set(sd.getYear(),sd.getMonthValue()-1,sd.getDayOfMonth(),0,0,0);
       Date startDate = new Date();
       startDate=calendar.getTime();
      
       Event e = new Event(0, title.getText(), startDate, startHour, Desc.getText(), img.getText(), c, cls,searchkey.getText());
        EventServices es = new EventServices();
try{          
List<Equipment> le1 = new ArrayList<Equipment>();
List<Integer> lq1 = new ArrayList<Integer>();

int i =0;
        for (JFXTextField j : tabT)
        {
            System.out.println(j.getText());
         
            Integer qte = Integer.parseInt(j.getText());
             if (qte > 0)
            {
                le1.add(le.get(i));
                lq1.add(qte);
            }
            
           
            i++;
            
        }
        System.out.println(le1);
            System.out.println(lq1);
            try {
                es.ajouterEvent(e, le1, lq1);
            } catch (sqlexcept ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("The quantities are unavailable"); 
     
        alert.showAndWait();
            }
} catch(NumberFormatException n)
            {
                System.out.println(n.getMessage());
                     Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("The quantities must be numbers"); 
     
        alert.showAndWait();
            }
      //  System.out.println("ajout event + "+e);
        
    }}

    
    
}
