/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author olyjosh
 */
public class Usman extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Usman.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        //dialogStage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        
        new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Usman.getStage().close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    
                    Scene scene = new Scene(root);
                    Stage st =new Stage();
                    st.setTitle("Offline Signature Verification System Pre-processing");
                    Usman.stage=st;
                    st.setScene(scene);
                    st.show();
                } catch (IOException ex) {
                    Logger.getLogger(Usman.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, null)).play();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
