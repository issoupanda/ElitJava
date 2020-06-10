/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Club;
import Elit.entities.Event;
import Elit.utils.Elem;
import Elit.utils.WikipediaApi;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class SmallEventController implements Initializable {
BorderPane borderPane ;
    @FXML
    private Label date;
    @FXML
    private ImageView eyeBtn;
    @FXML
    private Label title;
    @FXML
    private Label time;
    @FXML
    private Label desc;
    @FXML
    private Label classroom;
private Event event;
    @FXML
    private ImageView img;
    @FXML
    private ImageView goToedit;
    @FXML
    private ImageView wikiBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setImg(String img) {
               Image i;
    try {
        i = new Image(new FileInputStream("D:\\wamp64\\www\\PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+img));
         this.img.setImage(i);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void goToDetail(MouseEvent event) {
                  FXMLLoader l = new FXMLLoader(getClass().getResource("showEvent.fxml"));
      try {
          Parent root = l.load();
      Event e = this.event;
    ShowEventController oec = l.getController();
    oec.setDesc(e.getDescription());
    oec.setEvent(this.event);
    oec.setPicture(e.getLogo());
    oec.setStartDate(e.getStartDate().toString());
    oec.setStartHour(e.getStartHour().toString());
    oec.setTitle(e.getTitle());
    oec.setEquips();
    oec.setClub(e.getClub().getTitle());
    oec.setClassroom(e.getClassroom().getName());
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
         DateFormat timeFormat = new SimpleDateFormat("HH:mm");  
String strH = timeFormat.format(e.getStartHour());  
    String strD = dateFormat.format(e.getStartDate());  

String str=strD+" "+strH;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
    LocalDateTime eventTime = LocalDateTime.parse(str, formatter);

    if (Math.abs(LocalDateTime.now().until(eventTime, ChronoUnit.SECONDS)) < 432000)
    
    oec.setWeather();
   
    this.borderPane =(BorderPane) title.getScene().lookup("#borderPane2");
        borderPane.setCenter(root);

    } catch (IOException ex) {
          Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    public void setDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    String strDate = dateFormat.format(date);  
        this.date.setText(strDate);
    }


    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTime(Date time) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");  
String strDate = dateFormat.format(time);  
this.time.setText(strDate);
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    public void setClassroom(String classroom) {
        this.classroom.setText(classroom);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    private void small(MouseEvent event) {
        this.eyeBtn.setScaleX(1);
        this.eyeBtn.setScaleY(1);
    }

    @FXML
    private void big(MouseEvent event) {
        this.eyeBtn.setScaleX(2);
        this.eyeBtn.setScaleY(2);
    }

    @FXML
    private void small2(MouseEvent event) {
          this.goToedit.setScaleX(1);
        this.goToedit.setScaleY(1);
    }

    @FXML
    private void big2(MouseEvent event) {
           this.goToedit.setScaleX(2);
        this.goToedit.setScaleY(2);
    }

    @FXML
    private void gotoedit(MouseEvent event) {
                 FXMLLoader l = new FXMLLoader(getClass().getResource("oneEvent.fxml"));
      try {
          Parent root = l.load();
      Event e = this.event;
    OneEventController oec = l.getController();
    oec.setDesc(e.getDescription());
    oec.setEvent(this.event);
    oec.setPicture(e.getLogo());
    oec.setStartDate(e.getStartDate().toString());
    oec.setStartHour(e.getStartHour().toString());
    oec.setTitle(e.getTitle());
    oec.setEquips();
    oec.setClub(e.getClub().getTitle());
    oec.setClassroom(e.getClassroom().getName());
    oec.setSeacrh(e.getSearchKey());
    
   
    this.borderPane =(BorderPane) title.getScene().lookup("#borderPane2");
        borderPane.setCenter(root);

    } catch (IOException ex) {
          Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    @FXML
    private void small3(MouseEvent event) {
               this.wikiBtn.setScaleX(1);
        this.wikiBtn.setScaleY(1);
    }

    @FXML
    private void big3(MouseEvent event) {
        this.wikiBtn.setScaleX(2);
        this.wikiBtn.setScaleY(2);
    }

    @FXML
    private void gotoWiki(MouseEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
dialog.setTitle("Wikipedia");
dialog.setHeaderText("Wikipedia result for the term : \n"+this.event.getSearchKey());
        WikipediaApi n = new WikipediaApi();
      
String wiki =   n.getRes(this.event.getSearchKey());
dialog.setContentText(wiki);
ImageView i =  new ImageView("/IssamFiles/wikilogo.png");
i.setFitHeight(150);
i.setFitWidth(200);
i.setScaleZ(0.1);
i.setScaleX(0.5);
i.setScaleY(0.5);
dialog.setGraphic(i);
dialog.getDialogPane().getButtonTypes().add(new ButtonType("Thanks !", ButtonData.CANCEL_CLOSE));

       dialog.show();
        
        
    }
    
    
}
