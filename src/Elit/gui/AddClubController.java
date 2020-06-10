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
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class AddClubController implements Initializable {

    @FXML
    private Button logoBtn;
    @FXML
    private Button addClub;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField category;
    @FXML
    private JFXTextField picture;
    @FXML
    private JFXTextField description;
    @FXML
    private JFXDatePicker creationDate;
    @FXML
    private JFXComboBox<String> president;
    
    private List<User> L=new ArrayList<User>();
    
    
     Connection cnx2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator rq = new RequiredFieldValidator();
       NumberValidator n = new NumberValidator();
       
        rq.setMessage("Cannot be empty !");
        title.getValidators().add(rq);
        
        
        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) title.validate();
        
        });
        
        description.getValidators().add(rq);
        description.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if(!newValue) description.validate();
        });
        
        category.getValidators().add(rq);
        category.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if(!newValue) category.validate();
        });
        
        
        
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
            president.getItems().add(u.getFirstname()+" "+u.getLastname());
        }
    }

    @FXML
    private void picturePic(ActionEvent event) {
        System.out.println("ss");
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if(selected !=null )
        {
            String extension = selected.getAbsolutePath().substring(selected.getAbsolutePath().length()-3,selected.getAbsolutePath().length());
            System.out.println(extension);
            if(!extension.equals( "jpg") && !extension.equals("png"))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid picture");
        
        alert.setContentText("Invalid picture format (only jgp/png available)"); 
     
        alert.showAndWait();
        picture.setText("");
            }else
            picture.setText(selected.getAbsolutePath());
        }
        
    }

    @FXML
    private void addClubbtn(ActionEvent event) {
	ZoneId defaultZoneId = ZoneId.systemDefault();
        
    //    if(creationDate.getValue()==null || president.getSelectionModel().getSelectedIndex())
        if(title.getText().isEmpty() || description.getText().isEmpty() || category.getText().isEmpty()
                ||creationDate.getValue()==null || picture.getText().isEmpty() || president.getSelectionModel().getSelectedIndex()==-1)
        {
                 Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid data");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText("Verify your entries ! They cannot be empty"); 
     
        alert.showAndWait();
        }
        else{
            LocalDate localDate = creationDate.getValue(); 
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        User student = L.get(president.getSelectionModel().getSelectedIndex());
        Club c = new Club (111,title.getText(),description.getText(),category.getText(),date,picture.getText(),student);
        ClubServices cs = new ClubServices();
            try {
                cs.ajouterClub(c);
            } catch (sqlexcept ex) {
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplication error");
        alert.setHeaderText("Cannot add a new entry");
        alert.setContentText(ex.getMsg()); 
     
        alert.showAndWait();
            }
    }}
        
    
    
}
