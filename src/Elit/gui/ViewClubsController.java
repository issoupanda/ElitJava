/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Club;
import Elit.services.ClubServices;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class ViewClubsController implements Initializable {

    private BorderPane borderPane;
    private TableView<Club> tableClub;
    @FXML
    private TilePane tilePane;
    @FXML
    private JFXTextField search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        tilePane.setHgap(10);
        tilePane.setVgap(10);
        for (Club cc : getClubs()) {

            FXMLLoader l = new FXMLLoader(getClass().getResource("smallClub.fxml"));
            try {
                Parent root = l.load();

                SmallClubController occ = l.getController();

                occ.setTitle(cc.getTitle());
                occ.setDescription(cc.getDescription());
                occ.setImg(cc.getLogo());
                occ.setC(cc);
                tilePane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        search.textProperty().addListener(((observable, oldValue, newValue) -> {
            tilePane.getChildren().clear();
            for (Club f : getClubs()) {
                if (f.getTitle().toLowerCase().contains(newValue) || f.getDescription().toLowerCase().contains(newValue)) {
                    FXMLLoader l = new FXMLLoader(getClass().getResource("smallClub.fxml"));
                    try {
                        Parent root = l.load();

                        SmallClubController occ = l.getController();

                        occ.setTitle(f.getTitle());
                        occ.setDescription(f.getDescription());
                        occ.setImg(f.getLogo());
                        occ.setC(f);
                        tilePane.getChildren().add(root);
                    } catch (IOException ex) {
                        Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }));
    }

    public ObservableList<Club> getClubs() {
        ObservableList<Club> clubs = FXCollections.observableArrayList();
        ClubServices cs = new ClubServices();
        List<Club> lc = cs.listerClub();
        for (Club c : lc) {
            clubs.add(c);
        }
        return clubs;
    }

    private void loadMenu(String m) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(m + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ClubMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane = (BorderPane) tableClub.getScene().lookup("#borderPane");
        borderPane.setCenter(root);
    }

}
