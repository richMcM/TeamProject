package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectUserUICtrl implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void showLoginUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.LOGIN));
            setScene(actionEvent, parent);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    @FXML
    public void showCustomerCreateAccUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.CUSTOMER_CREATE));
            setScene(actionEvent, parent);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    @FXML
    public void showCompanyCreateAccUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.COMPANY_CREATE));
            setScene(actionEvent, parent);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    private void setScene(ActionEvent actionEvent, Parent parent){
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
    }
}
