/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Club;
import Elit.entities.User;
import Elit.services.ClubServices;
import Elit.utils.DbConnection;
import Elit.utils.sqlexcept;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class OneClubController implements Initializable {
private Club club;
     @FXML
    private ImageView img;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField desc;

    @FXML
    private JFXTextField cat;

    @FXML
    private JFXDatePicker cdate;

    @FXML
    private JFXComboBox<String> pres;
        private List<User> L=new ArrayList<User>();
    
    
     Connection cnx2;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private JFXTextField picture;

    @FXML
    private Button picPic;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          cnx2 = DbConnection.getInstance().getCnx();           
        
        try {
            String req = "Select  * from user u where type = 'Student' ";
            PreparedStatement pst = cnx2.prepareStatement(req);
            ResultSet rs = pst.executeQuery();//select bel execute query
            while (rs.next()) {
               
                User u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("fistname"), rs.getString("lastname"));
     L.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        for ( User u : L)
        {
            pres.getItems().add(u.getFirstname()+" "+u.getLastname());
        }
        
    }    
    public void setClub(Club c)
    {
        this.club=c;
    }

    public void setPicture(String picture) {
        this.picture.setPromptText("Picture path");
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

    public void setTitle(String title) {
        System.out.println(title);
        this.title.setText(title);
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    public void setCat(String cat) {
        this.cat.setText(cat);
    }

    public void setCdate(String cdate) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  LocalDate localDate = LocalDate.parse(cdate, formatter);
        this.cdate.setValue(localDate);
    }

    public void setPres(String pres) {
        this.pres.getSelectionModel().select(pres);
    }

    @FXML
    private void editClub(ActionEvent event) {
        ClubServices cs = new ClubServices();
        
        if(title.getText().isEmpty() || desc.getText().isEmpty() || cat.getText().isEmpty()
                ||cdate.getValue()==null ||  pres.getSelectionModel().getSelectedIndex()==-1)
        {
                 Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("Verify your entries ! They cannot be empty"); 
     
        alert.showAndWait();
        }else{
        
        
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = cdate.getValue(); 
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        User student = L.get(pres.getSelectionModel().getSelectedIndex());
        if(picture.getText().length()!=0)
        {Club c = new Club (club.getId(),title.getText(),desc.getText(),cat.getText(),date,picture.getText(),student);}
        else {
            Club c = new Club (club.getId(),title.getText(),desc.getText(),cat.getText(),date,"D:\\wamp64\\www\\"
                    + "PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+club.getLogo(),student);
        System.out.println(c);
            try {
                cs.modifierClub(c);
            } catch (sqlexcept ex) {
                     Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplication error");
        alert.setHeaderText("Cannot update");
        alert.setContentText(ex.getMsg()); 
     
        alert.showAndWait();
            }}
}
    }

    @FXML
    private void deleteClub(ActionEvent event) {
           ClubServices cs = new ClubServices();
           cs.supprimerClub(club);
           Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("viewClubs.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ClubMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
       BorderPane borderPane =(BorderPane) title.getScene().lookup("#borderPane");
        borderPane.setCenter(root);
    }
    

    @FXML
    private void changePic(ActionEvent event) {
            
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if(selected !=null)
        {
            picture.setText(selected.getAbsolutePath());
            Image i;
            try {
                i = new Image(new FileInputStream(selected.getAbsolutePath()));
                this.img.setImage(i);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }else
        {
            System.out.println("invalid picture");
        }
        
        
    }
}
