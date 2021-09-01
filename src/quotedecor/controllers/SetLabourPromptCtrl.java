package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetLabourPromptCtrl implements Initializable {

    @FXML
    private Label lblWelcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Session.statusCompany() != null){
            lblWelcome.setText("Welcome "+Session.company.getCompanyName()+"!\n Please set your labour costs to become part of the quotation system.");
        }
    }

    @FXML
    private void showCompanyHomeUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.COMPANY_HOME));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
