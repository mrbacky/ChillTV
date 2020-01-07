package chilltv.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML
    private Button btn_library;
    @FXML
    private Button btn_previous;
    @FXML
    private Button btn_play;
    @FXML
    private Button btn_pause;
    @FXML
    private Button btn_next;
    @FXML
    private ImageView img_minVolume;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressSlider;
    @FXML
    private ImageView img_maxVolume;
    @FXML
    private Label lbl_startTime;
    @FXML
    private Label lbl_endTime;
    @FXML
    private MediaView mediaView;
    @FXML
    private VBox buttonBar;
    @FXML
    private Button btn_stop;

    private String filePath;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mediaView.toBack();
        buttonBar.setOpacity(0.3);

        //buttonBar.setAlignment(Pos.TOP_CENTER);
    }

    @FXML
    private void handle_openLibrary(ActionEvent event) throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/LibraryScene.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<LibraryController>getController().setContr(this);

        Stage libraryStage = new Stage();
        Scene libraryScene = new Scene(root1);

        //songStage.initStyle(StageStyle.UNDECORATED);
        libraryStage.setScene(libraryScene);
        libraryStage.show();
    }

    private void handle_openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp4 Files", "*.mp4"),
                new FileChooser.ExtensionFilter("mpeg4 Files", "*.mpeg4")
        );

        File movieFile = fileChooser.showOpenDialog(null);
        filePath = movieFile.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            mediaPlayer.play();

        }

    }

    @FXML
    private void handle_play(ActionEvent event) {
        mediaPlayer.play();
    }

    @FXML
    private void handle_pause(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void handle_stop(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void handle_next(ActionEvent event) {

    }

    @FXML
    private void handle_previous(ActionEvent event) {
    }

    @FXML
    private void handle_showBar(MouseEvent event) {
        buttonBar.setOpacity(1);
    }

    @FXML
    private void handle_hideBar(MouseEvent event) {
        buttonBar.setOpacity(0.3);
    }

}