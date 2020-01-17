package chilltv.gui.controller;

import chilltv.be.Movie;
import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public class PlayerController implements Initializable {

    @FXML
    private BorderPane topPane;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button btn_play;
    @FXML
    private Button btn_pause;
    @FXML
    private Button btn_stop;
    @FXML
    private Label lbl_startTime;
    @FXML
    private Label lbl_endTime;
    @FXML
    private ImageView img_minVolume;
    @FXML
    private ImageView img_maxVolume;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressSlider;
    @FXML
    private VBox vBox_buttonBar;
    @FXML
    private HBox hBox_buttonBar;

    private MediaPlayer mediaPlayer;
    private LibraryController libraryController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vBox_buttonBar.setOpacity(0.3);
        // to test with a movie
        //handle_openFile(null);

    }

    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    private void bindPlayerToGUI(StringProperty sp, boolean end) {
        // Binds the currentTimeProperty to a StringProperty on the label
        // The computeValue() calculates minutes and seconds from the
        // CurrentTimeProperty, which is a javafx Duration type.
        sp.bind(new StringBinding() {
            // Initialization block 
            // Somewhat like a constructor without arguments
            {
                super.bind(mediaPlayer.currentTimeProperty());
            }

            @Override

            protected String computeValue() {
                long timeMillis = (long) mediaPlayer.getCurrentTime().toMillis();
                if (end) {
                    timeMillis = -timeMillis + (long) mediaPlayer.getMedia().getDuration().toMillis();
                }

                String form = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(timeMillis),
                        TimeUnit.MILLISECONDS.toMinutes(timeMillis)
                        - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(
                                        timeMillis
                                )
                        ),
                        TimeUnit.MILLISECONDS.toSeconds(timeMillis)
                        - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                        timeMillis
                                )
                        )
                );

                return form;
            }
        });

    }

    void playFile(Movie movieToPlay, Stage stage) {

        File movieFile = new File(movieToPlay.getFileLink());
        if (movieFile != null) {

            Media media = new Media(movieFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            stage.setOnCloseRequest((event) -> {
                mediaPlayer.dispose();
            });
            mediaView.setMediaPlayer(mediaPlayer);
            bindPlayerToGUI(lbl_endTime.textProperty(), true);
            bindPlayerToGUI(lbl_startTime.textProperty(), false);

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
    private void handle_volumeSlider(MouseEvent event) {

        if (mediaPlayer != null) {
            System.out.println(volumeSlider.getValue());
            mediaPlayer.setVolume(volumeSlider.getValue() * 100);
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
        }
    }

    @FXML
    private void hande_progressSlider(MouseEvent event) {
        //getting duration from file in seconds
        Double time = mediaPlayer.getTotalDuration().toMillis();
        /*
        Get the currentTime and add change listner, which will be notified whenever 
        the value of the ObservableValue changes and that we get the new value and set it to the slider
         */
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                progressSlider.setValue(newValue.toMillis());
            }
        });
        progressSlider.maxProperty().bind(Bindings.createDoubleBinding(()
                -> mediaPlayer.getTotalDuration().toMillis(),
                mediaPlayer.totalDurationProperty()));

        progressSlider.setOnMouseClicked((MouseEvent mouseEvent) -> { //This Method shows the progress of the progress bar
            mediaPlayer.seek(Duration.millis(progressSlider.getValue())); //It seeks the duration in seconds ofc. 
        });
    }

    @FXML
    private void handle_showBar(MouseEvent event) {
        vBox_buttonBar.setOpacity(1);
    }

    @FXML
    private void handle_hideBar(MouseEvent event) {
        vBox_buttonBar.setOpacity(0.5);
    }
}
