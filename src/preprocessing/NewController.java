package preprocessing;

import preprocessing.database.DataClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.StrokeTransition;
import javafx.animation.StrokeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author OLYJOSH
 */
public class NewController implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private TextField name;
    @FXML private ScrollPane scrollPane;
    @FXML private ChoiceBox sex,age;
    @FXML private VBox imgBox;
    @FXML private Hyperlink attach;
    @FXML Label s,f,close;
    @FXML ProgressIndicator prog;
    
    
    private DataClass dc;
    private static NewController instance;
    private Stage stage;
    
    
    public static NewController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        close.setGraphic(new ImageView(new Image("/usman/close.png")));
        close.setOnMouseClicked((MouseEvent event) -> {
            ((Stage)close.getScene().getWindow()).close();
        });
        attach.addEventHandler(MouseEvent.MOUSE_CLICKED, doPress());
        sex.getItems().addAll("Male","Female");
        for (int i = 5; i <=100; i++) {
            age.getItems().add(i);
        }
//        scrollPane.setOnMouseClicked(doPress());
//        imgBox.setOnMouseClicked(doPress());
        
    }
//
    private EventHandler doPress(){
        return (EventHandler<MouseEvent>) (MouseEvent event) -> {
            if (stage == null) {
                stage = (Stage) (anchorPane.getScene().getWindow());
            }
            imgBox.getChildren().removeAll(imgBox.getChildren());
            List<File> filechoose = filechoose(new FileChooser.ExtensionFilter("Images files", "*.bmp","*.jpg"/*,"*.png"*/));
            if (filechoose != null) {
                for (int i = 0; i < filechoose.size(); i++) {
                    imgBox.getChildren().add(new Hyperlink(filechoose.get(i).getAbsolutePath()));
                }
            }
        };
    }
    
    
    private List<File> filechoose(FileChooser.ExtensionFilter... ext) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose images");
        if (ext != null) {
            fc.getExtensionFilters().addAll(ext);
        }
        return fc.showOpenMultipleDialog(stage);
    }
//    public HomeController showCustomerDialog() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/budget/tracker/resource/Home.fxml"));
//        loader.load();
//        HomeController hc = loader.<HomeController>getController();
//        return hc;
//    }

    @FXML private void createNew(ActionEvent event) throws IOException {
        String nameText = name.getText();
        boolean valid=true;
        if(nameText.isEmpty()){
            name.setStyle("-fx-border-color:red;");
            valid=false;
        }
        if(age.getSelectionModel().isEmpty()){
            age.setStyle("-fx-border-color:red;");
            valid=false;
        }
        if(sex.getSelectionModel().isEmpty()){
            sex.setStyle("-fx-border-color:red;");
            valid=false;
        }
        if(imgBox.getChildren().isEmpty()){
            scrollPane.setStyle("-fx-border-color:red;");
            valid=false;
        }
        
        if(valid){
            // Registerations stuffs here 
            prog.setVisible(true);
            if(dc==null)dc = new DataClass();
            long time=System.currentTimeMillis();
            dc.createSignatory(nameText, 
                    (String)sex.getSelectionModel().getSelectedItem(), 
                    Short.parseShort(String.valueOf(age.getSelectionModel().getSelectedItem())),time);
            copyImages(nameText+time);
            name.clear();
            age.getSelectionModel().select(null);
            sex.getSelectionModel().select(null);
            name.setStyle("");
            age.setStyle("");
            sex.setStyle("");
            
            s.setVisible(true);
            f.setVisible(false);
        }else{
            f.setVisible(true);
            s.setVisible(false);
        }
    }
    
    private void copyImages(String folderName){
        final ObservableList<Node> children = imgBox.getChildren();
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                final int max = children.size();
                String path;
                final char SEP=File.separatorChar;
                final String HOME_PATH=Utili.PATH+folderName+SEP;
                new File(HOME_PATH).mkdirs();
                try {
                    for (int i = 1; i <= max; i++) {

                        path = ((Hyperlink) children.get(i - 1)).getText();
                        new File(Utili.PATH + folderName + SEP + path.substring(path.lastIndexOf(SEP) + 1)).createNewFile();
                        Utili.copyFileUsingStream(new File(path),
                                new File(Utili.PATH + folderName + SEP + path.substring(path.lastIndexOf(SEP) + 1)));
                        
                        if (isCancelled()) {
                            break;
                        }
                        updateProgress(i, max);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Reached here");
                imgBox.getChildren().removeAll(imgBox.getChildren());

                return null;
            }
        };
        prog.progressProperty ().bind(task.progressProperty());
        new Thread(task).start();
    }
    
    @FXML private void openSelectDialog(ActionEvent e){
        if(stage==null)stage=(Stage)(anchorPane.getScene().getWindow());
        filechoose(new FileChooser.ExtensionFilter("Images", "bmp"/*,"jpeg","png"*/));
    }
    
    private void strokeTransitionEffect(VBox root) {
        Rectangle rect = new Rectangle(0, 0, root.getPrefWidth() + 1, root.getPrefHeight() + 1);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(null);
        rect.setStroke(Color.GREEN);
        rect.setStrokeWidth(8);
        root.getChildren().add(rect);

        StrokeTransition strokeTransition = StrokeTransitionBuilder.create()
                .duration(Duration.seconds(3))
                .shape(rect)
                .fromValue(Color.GREEN)
                .toValue(Color.DODGERBLUE)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
        strokeTransition.play();
    }
    
}