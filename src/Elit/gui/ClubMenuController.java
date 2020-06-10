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
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class ClubMenuController implements Initializable {

    @FXML
    private Button welcome;
    @FXML
    private Button add;
    @FXML
    private Button view;
    @FXML
    private Button back;
    @FXML
    private BorderPane borderPane;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clear(ActionEvent event) {
        borderPane.setCenter(null);
    }

    @FXML
    private void goToAdd(ActionEvent event) {
        loadMenu("addClub");
    }

    @FXML
    private void goToView(ActionEvent event) {
    
        loadMenu("viewClubs");
    }

    @FXML
    private void goToAcc(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilElit.fxml"));
        try {
            Parent root = loader.load();
            back.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AccueilElitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadMenu(String m)
    {
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(m+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ClubMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }
    
}
