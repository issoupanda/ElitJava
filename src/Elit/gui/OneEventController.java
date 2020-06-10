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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
public class OneEventController implements Initializable {

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
    private JFXTextField searchkey;

    public void setClub(String Club) {
        this.Club.getSelectionModel().select(Club);
    }

    public void setClassroom(String classroom) {
        this.classroom.getSelectionModel().select(classroom);
    }
    @FXML
    private JFXComboBox<String> classroom;
    @FXML
    private Pane equiPane;
    @FXML
    private VBox vPn;
    @FXML
    private JFXButton edit;
    @FXML
    private ImageView picture;
    @FXML
    private Button delete;
    private Event event;
     private List<Club> lc;
    private List<Classroom> ls=new ArrayList<Classroom>();
    private List<Equipment> le = new ArrayList<Equipment>();
    List<JFXTextField> tabT = new ArrayList<JFXTextField>();
     Connection cnx2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
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
            classroom.getItems().add(c.getName());
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

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDesc(String Desc) {
        this.Desc.setText(Desc);
    }

    public void setStartDate(String date) {
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  LocalDate localDate = LocalDate.parse(date, formatter);
        this.startDate.setValue(localDate);
    }
    
    public void setSeacrh(String s)
    {
        this.searchkey.setText(s);
   }

    public void setStartHour(String startHour) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

  LocalTime localDate = LocalTime.parse(startHour);
        this.startHour.setValue(localDate);
        
    }

  public void setEquips()
  {
            try {
              String req = "Select  * from events_equipements where event_id = ? ";
            PreparedStatement pst;
      
            pst = cnx2.prepareStatement(req);
            pst.setInt(1, this.event.getId());
             ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
                int x = -1;
                int i=0;
                for (Equipment e : le)
                {
                    if(e.getId()==rs.getInt("equipement_id")){ 
                     tabT.get(i).setText(rs.getString("qte"));
                    
                    }
                    i++;
                }
               
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(OneEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

    public void setPicture(String picture) {
                     Image i;
    try {
        i = new Image(new FileInputStream("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+picture));
         this.picture.setImage(i);
         this.img.setText("");
         
    } catch (FileNotFoundException ex) {
        Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    private void addPic(ActionEvent event) {
                 System.out.println("ss");
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if(selected !=null)
        {
            img.setText(selected.getAbsolutePath());
                Image i;
    try {
        i = new Image(new FileInputStream(selected.getAbsolutePath()));
         this.picture.setImage(i);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
    }
        }else
        {
            System.out.println("invalid picture");
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        
           if(title.getText().isEmpty()|| startHour.getValue()==null || startDate.getValue()==null || Desc.getText().isEmpty() || img.getText().isEmpty()
                || searchkey.getText().isEmpty() || classroom.getSelectionModel().getSelectedIndex()==-1 || Club.getSelectionModel().getSelectedIndex()==-1 )
        {
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("Verify your entries ! They cannot be empty"); 
     
        alert.showAndWait();
        }else{
        
        
        
               try {
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
                   
                   Event e = new Event(this.event.getId(), title.getText(), startDate, startHour, Desc.getText(), img.getText(), c, cls,searchkey.getText());
                   EventServices es = new EventServices();
                   
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
                   if (e.getLogo().isEmpty()) e.setLogo("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+this.event.getLogo());
                   es.modifierEvent(e, le1, lq1);
                   //  System.out.println("ajout event + "+e);
               } catch (sqlexcept ex) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("The quantities are unavailable"); 
     
        alert.showAndWait();
               }
        
    
    }}

    @FXML
    private void delete(ActionEvent event) {
        EventServices es = new EventServices();
        es.supprimerEvent(this.event);
    }
    
}
