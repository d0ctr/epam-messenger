import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {

    @FXML
    private TextField userName;
    @FXML
    private TextField port;
    @FXML
    private Button buttonConnect;

    @FXML
    void initialize() {
        buttonConnect.setOnAction(event -> {
                    if (!(userName.getText().trim().isEmpty()) && !(port.getText().trim().isEmpty())) {
                        try {
                            MessengerWindow.userName = userName.getText();
                            MessengerWindow.port = Integer.parseInt(port.getText());
                            handleButtonAction();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            );
    }

    private void handleButtonAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MessengerWind.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Epam messenger team#4");
            stage.setScene(new Scene(root, 650, 343));
            stage.setResizable(false);
            stage.show();
            Stage primaryStage = (Stage) buttonConnect.getScene().getWindow();
            primaryStage.hide();
        }catch (IOException e) {e.printStackTrace();}
    }
}