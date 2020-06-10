/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Club;
import Elit.entities.Event;
import Elit.services.ClubServices;
import Elit.services.EventServices;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class ViewEventsController implements Initializable {

    @FXML
    private TilePane tpane;
    @FXML
    private JFXTextField search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tpane.setHgap(10);
        tpane.setVgap(10);
        EventServices cs = new EventServices();
        List<Event> liste = cs.listerEvent();
        for (Event e : liste) {
            FXMLLoader l = new FXMLLoader(getClass().getResource("smallEvent.fxml"));
            try {
                Parent root = l.load();

                SmallEventController sec = l.getController();

                sec.setEvent(e);
                sec.setTitle(e.getTitle());
                sec.setDesc(e.getDescription());
                sec.setDate(e.getStartDate());
                sec.setClassroom(e.getClassroom().getName());
                sec.setTime(e.getStartHour());
                sec.setImg(e.getLogo());

                tpane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        search.textProperty().addListener(((observable, oldValue, newValue) -> {
            tpane.getChildren().clear();
            for (Event e : liste) {
                if (e.getTitle().toLowerCase().contains(newValue) || e.getDescription().toLowerCase().contains(newValue) || e.getStartDate().toString().contains(newValue)) {
                    FXMLLoader l = new FXMLLoader(getClass().getResource("smallEvent.fxml"));
                    try {
                        Parent root = l.load();

                        SmallEventController sec = l.getController();

                        sec.setEvent(e);
                        sec.setTitle(e.getTitle());
                        sec.setDesc(e.getDescription());
                        sec.setDate(e.getStartDate());
                        sec.setClassroom(e.getClassroom().getName() + " " + e.getClassroom().getBloc());
                        sec.setTime(e.getStartHour());
                        sec.setImg(e.getLogo());
                        
                       

                        tpane.getChildren().add(root);
                    } catch (IOException ex) {
                        Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // TODO
            }

        }));
    }
}
