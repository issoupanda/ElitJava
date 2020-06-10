/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class AccueilElitController implements Initializable {

    @FXML
    private Button btnClub;
    @FXML
    private Button btnEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    

    @FXML
    private void goToClub(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clubMenu.fxml"));
        try {
            Parent root = loader.load();
            btnClub.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AccueilElitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void gotToEvent(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("eventMenu.fxml"));
        try {
            Parent root = loader.load();
            btnClub.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AccueilElitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


}
