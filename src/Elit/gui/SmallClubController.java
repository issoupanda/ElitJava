/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.gui;

import Elit.entities.Club;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author megapc
 */
public class SmallClubController implements Initializable {
private Club c;
private BorderPane borderPane;
    @FXML
    private Pane cPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private ImageView img;
    
public void setImg(String img)
{
    Image i;
            try {
                i = new Image(new FileInputStream("D:\\wamp64\\www\\"
                    + "PIDEV2020_ELIT_3A17\\web\\uploads\\issam_pic\\"+img));
                this.img.setImage(i);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OneClubController.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    public void setC(Club c) {
        this.c = c;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        title.setWrapText(true);
        title.setMaxWidth(100);
        description.setWrapText(true);
                description.setMaxWidth(100);
                description.setMaxHeight(40);

        // TODO
    }    

    @FXML
    private void goTo(MouseEvent event) {
          FXMLLoader l = new FXMLLoader(getClass().getResource("oneClub.fxml"));
      try {
          Parent root = l.load();
      Club cc = c;
    OneClubController occ = l.getController();
    
    occ.setTitle(cc.getTitle());
    occ.setCat(cc.getCategory());
    occ.setDesc(cc.getDescription());
   
    occ.setCdate(cc.getCreationDate().toString());
    occ.setImg(cc.getLogo());
    occ.setPicture(cc.getLogo());
    occ.setPres(cc.getPresident().toString());
    occ.setClub(cc);
    borderPane =(BorderPane) description.getScene().lookup("#borderPane");
        borderPane.setCenter(root);

    } catch (IOException ex) {
          Logger.getLogger(ViewClubsController.class.getName()).log(Level.SEVERE, null, ex);
      }
        
        
    }

    @FXML
    private void noHover(MouseEvent event) {
        Pane p =cPane;
        p.setStyle(null);
        p.setStyle("-fx-border-color:black;"
                + "-fx-border-width:2px;"
                + "-fx-border-radius:30px;");
    }

    @FXML
    private void hover(MouseEvent event) {
         Pane p =cPane;
        p.setStyle("-fx-background-color: #8fbc8f;");
    }
    
}
