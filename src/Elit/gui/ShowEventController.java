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
import Elit.utils.DbConnection;
import Elit.utils.Elem;
import Elit.utils.WeatherApi;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class ShowEventController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField Desc;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXTimePicker startHour;
    @FXML
    private JFXComboBox<String> Club;
    @FXML
    private JFXComboBox<String> classroom;
    @FXML
    private Pane equiPane;
    @FXML
    private VBox vPn;
    @FXML
    private ImageView picture;
    @FXML
    private Label temp;
    @FXML
    private Label descT;
    @FXML
    private ImageView icon;

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
public void setWeather()
{
    
        WeatherApi w = new WeatherApi();
       Elem e= w.get(LocalDateTime.of(startDate.getValue(),startHour.getValue()));
       temp.setText(e.getTemp());
       descT.setText(e.getDesc());
        URL url;   
        try {
            url = new URL("http://openweathermap.org/img/wn/"+e.getImage()+"@2x.png");
                Image image =  SwingFXUtils.toFXImage( ImageIO.read(url), null);
       icon.setImage(image);
        } catch (Exception ex) {
            Logger.getLogger(ShowEventController.class.getName()).log(Level.SEVERE, null, ex);
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
      
    } catch (FileNotFoundException ex) {
        Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
      public void setClub(String Club) {
        this.Club.getSelectionModel().select(Club);
    }

    public void setClassroom(String classroom) {
        this.classroom.getSelectionModel().select(classroom);
    }
    
}
