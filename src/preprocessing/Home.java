/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import preprocessing.preprocesses.Skeletonize;
import preprocessing.preprocesses.OtsuBinarize;
import preprocessing.preprocesses.MedianFilter;
import preprocessing.database.Signatory;
import preprocessing.database.DataClass;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.StrokeTransition;
import javafx.animation.StrokeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;


/**
 *
 * @author olyjosh
 */
public class Home implements Initializable {

    @FXML private TitledPane tp1, tp2;
    @FXML private TextField biField;
    @FXML private Slider biSlider;
    @FXML private Button save,bin, grey, med, skel;
    @FXML private ListView list,imgList;
    @FXML private Label name,sex,age;
    @FXML private ImageView imgView;
    @FXML private VBox processBox;
//    @FXML private ContextMenu conMenu;
    private Stage primaryStage;
    private DataClass dc;
    private String path;
    OtsuBinarize oBin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        primaryStage = Usman.getStage();
        tp1.setCollapsible(false);
        tp2.setCollapsible(false);
        biSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            try {
                if(oBin==null)oBin=new OtsuBinarize();
                biField.setText(String.valueOf(newValue));
                //imgView.setImage(SwingFXUtils.toFXImage(oBin.binarize(SwingFXUtils.fromFXImage(imgView.getImage(), null),true,newValue.intValue()), null));
                BufferedImage bimg=ImageIO.read(new File(imgList.getSelectionModel().getSelectedItem().toString()));
                imgView.setImage(SwingFXUtils.toFXImage(oBin.binarize(bimg, true, newValue.intValue()), null));
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        biField.addEventHandler(KeyEvent.ANY, numeric_Validation(3));
        listWahala();

    }

    @FXML private void populateList(ActionEvent e){
        if(dc==null)dc=new DataClass();
        List<Signatory> signatories = dc.getSignatories();
        list.setItems(FXCollections.observableList(signatories));
    }
    
    
    final char SEP=File.separatorChar;
    private void listWahala(){
        populateList(null);
        if (list.getItems().size() > 0) {
            list.getSelectionModel().selectFirst();
        }
//        list.setCellFactory(new Callback() {
//
//            
//            public Object call(Object lv) {
//                ListCell<Signatory> cell = new ListCell<>();
//                
//                ContextMenu contextMenu = new ContextMenu();
//                MenuItem deleteItem = new MenuItem();
//                deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
//                deleteItem.setOnAction(event -> {
//                    list.getItems().remove(cell.getItem());
//                });
//                contextMenu.getItems().add(deleteItem);
//                //cell.textProperty().bind(cell.);
//                //cell.textProperty().bind(cell.itemProperty());
//                
//                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
//                    if (isNowEmpty) {
//                        cell.setContextMenu(null);
//                    } else {
//                        cell.setContextMenu(contextMenu);
//                    }
//                });
//                return cell ;
//            }
//        });

        MenuItem it = new MenuItem("Delete");
        list.getContextMenu().getItems().add(it);
        it.setOnAction((ActionEvent event) -> {
            Signatory si = (Signatory) list.getSelectionModel().getSelectedItem();
            if (si != null) {
                dc.deleteSig(si.getId());
                path = Utili.PATH + si.getName() + si.getTime();
                System.out.println(path);
                File folder=new File(path + si.getName() + si.getTime());
                if (folder.exists()) {
                    String[] entries = folder.list();
                    for (String s : entries) {
                        File currentFile = new File(folder.getPath(), s);
                        currentFile.delete();
                    }
                    System.out.println("DELETED: "+folder.delete());
                }
                list.getItems().remove(si);
            }
        });

        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            Signatory si = (Signatory) list.getSelectionModel().getSelectedItem();
            if (si != null) {
                name.setText(si.getName());
                sex.setText(si.getSex());
                age.setText(String.valueOf(si.getAge()));
                path = Utili.PATH + si.getName() + si.getTime() + SEP;
                File[] listFiles = new File(path).listFiles();
                ObservableList list = FXCollections.observableArrayList();
                if (listFiles.length > 0) {
                    for (int i = 0; i < listFiles.length; i++) {
                        File listFile = listFiles[i];
                        list.add(listFiles[i].getAbsolutePath());
                    }
                }
                imgList.setItems(list);
                if (list.size() > 0) {
                    imgList.getSelectionModel().selectFirst();
                    save.setDisable(false);
                }
            }
        });

        imgList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        
        imgList.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            try {
                imgView.setImage(new Image(new FileInputStream(newValue.toString())));
                processBox.setDisable(false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
        return (KeyEvent e) -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= max_Lengh) {
                e.consume();
            }
            if (e.getCharacter().matches("[0-9.]")) {
                if (txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
                    e.consume();
                } else if (txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
                    e.consume();
                }
                biSlider.setValue(Double.parseDouble(txt_TextField.getText()));
            } else {
                e.consume();
            }
        };
    }

    private void showDilogStage(String title, String fxml, Modality m, StageStyle s) throws IOException {
        // Load the fxml file and create a new stage for the popup dialog.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/usman/" + fxml));
        AnchorPane page = (AnchorPane) loader.load();
        strokeTransitionEffect(page);
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(m);
        dialogStage.initStyle(s);
        dialogStage.setResizable(false);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

    }

    @FXML
    public void showNewDialog(ActionEvent e) throws IOException {
        showDilogStage("New Signatory", "New.fxml", Modality.APPLICATION_MODAL, StageStyle.UNDECORATED);
    }

    private void strokeTransitionEffect(AnchorPane root) {
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

    
    private File saveFile(String intitDir,String initFiName) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save processed image as");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pre-processed bitmap", "*.bmp"));
        fc.setInitialFileName(initFiName);
        fc.setInitialDirectory(new File(intitDir));
        return fc.showSaveDialog(primaryStage);
    }


    
    @FXML private void binarize(ActionEvent e) throws IOException {
        if(oBin==null)oBin = new OtsuBinarize();
        //imgView.setImage(SwingFXUtils.toFXImage(oBin.binarize(SwingFXUtils.fromFXImage(imgView.getImage(), null),false,0), null));
        BufferedImage bimg=ImageIO.read(new File(imgList.getSelectionModel().getSelectedItem().toString()));
        imgView.setImage(SwingFXUtils.toFXImage(oBin.binarize(bimg, false, 0), null));
        biSlider.setValue(oBin.getThreshold());
    }

    @FXML private void greyScale(ActionEvent e) {
        if(oBin==null)oBin = new OtsuBinarize();
        imgView.setImage(SwingFXUtils.toFXImage(oBin.toGray(SwingFXUtils.fromFXImage(imgView.getImage(), null)), null));
    }
    
    @FXML private void medianFilter(ActionEvent e) {
        imgView.setImage(SwingFXUtils.toFXImage(MedianFilter.medianFilter(SwingFXUtils.fromFXImage(imgView.getImage(), null)), null));
    }
    
    @FXML private void skeleton(ActionEvent e) throws IOException {
//        BufferedImage fromFXImage = SwingFXUtils.fromFXImage(imgView.getImage(), null);
//        ByteProcessor by =new ByteProcessor(fromFXImage);
//        by.skeletonize();
//        imgView.setImage(SwingFXUtils.toFXImage(by.getBufferedImage(), null));
                
        if(oBin==null)oBin = new OtsuBinarize();
        String toString = imgList.getSelectionModel().getSelectedItem().toString();
        File temp = File.createTempFile("binarized", ".bmp"); 
        System.out.println(toString);
        BufferedImage binarize = oBin.binarize(ImageIO.read(new File(toString)), false, 0);
        
        boolean write = ImageIO.write(binarize, "bmp", temp);
        BufferedImage skel1 = Skeletonize.skel(temp.getAbsolutePath());
    	imgView.setImage(SwingFXUtils.toFXImage(skel1, null));
 
    }
    
    
    @FXML private void save(ActionEvent e) throws IOException{
        BufferedImage fromFXImage = SwingFXUtils.fromFXImage(imgView.getImage(), null);
        String s = (String)imgList.getSelectionModel().getSelectedItem();
        int in = s.lastIndexOf(SEP);
        File saveFile = saveFile(s.substring(0,in+1),s.substring(in+2));
        if(saveFile!=null)ImageIO.write(fromFXImage, "bmp", saveFile);
    } 
   
//    @FXML private void contextCall(ActionEvent e){
//        if(list.getContextMenu()){}
//    }
//    

    static class XCell extends ListCell<String> {
        VBox hbox = new VBox();
        Label label = new Label("(empty)");
        
        //Pane pane = new Pane();
        //Button v = new Button("(>)");
        ImageView v = new ImageView();
        String lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label, v);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item!=null ? item : "<null>");
                label.setMaxWidth(100);
                label.setPrefWidth(100);
                try {
                    v.setPreserveRatio(true);
                    v.setFitHeight(100);
                    v.setFitWidth(100);
                    v.setImage(new Image(new FileInputStream(item)));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
                setGraphic(hbox);
            }
        }
    }

    
}
