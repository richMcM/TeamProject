package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import quotedecor.config.DB;
import quotedecor.sessions.Session;
import quotedecor.util.InputProcessor;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginCtrl implements Initializable {
    @FXML private Label lblEmailError, lblPasswordError;
    @FXML private TextField txfEmail;
    @FXML private PasswordField psfPassword;
    @FXML private Pane testPane;
    @FXML private Button btnLogin;

    // composites
    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEnterKeyPressedAction(txfEmail);
        setEnterKeyPressedAction(psfPassword);
        setEnterKeyPressedAction(btnLogin);
    }
    private void setEnterKeyPressedAction(Button control) {
        control.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doLogin(event);
            }
        });
    }
    private void setEnterKeyPressedAction(TextField control) {
        control.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doLogin(event);
            }
        });
    }
    @FXML
    public void doLogin(ActionEvent actionEvent){
        String email = txfEmail.getText().trim().toLowerCase();
        String password = psfPassword.getText();
        boolean userRetrieved = false;
        if (!InputProcessor.validEmail(email)) {
            lblEmailError.setTextFill(Paint.valueOf("red"));
            lblEmailError.setText("Invalid Email!");
        }
        else {
            try {
                ResultSet rs;
                String sqlStmnt = String.format(
                        "SELECT * " +
                        "FROM `company` " +
                        "WHERE `email` = '%s' " +
                        "LIMIT 1;",
                        email
                );
                System.out.println("READING USER: "+email);
                rs = DB.query(sqlStmnt);
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        userRetrieved = true;
                        Session.createCompany(rs);
                        showHomeUI(actionEvent, URLS.COMPANY_HOME);
                    } else {
                        lblPasswordError.setTextFill(Paint.valueOf("red"));
                        lblPasswordError.setText("Incorrect password!");
                    }
                }
                if(!userRetrieved){
                    sqlStmnt = String.format(
                            "SELECT * " +
                            "FROM customer " +
                            "WHERE email = '%s' " +
                            "LIMIT 1;",
                            email
                    );
                    rs = DB.query(sqlStmnt);
                    if (rs.next()) {
                        System.out.println("in");
                        System.out.println(rs.getString("email"));
                        if (password.equals(rs.getString("password"))) {
                            Session.createCustomer(rs);
                            showHomeUI(actionEvent, URLS.CUSTOMER_HOME);
                        } else{
                            lblPasswordError.setTextFill(Paint.valueOf("red"));
                            lblPasswordError.setText("Incorrect password!");
                        }
                    }else{
                        lblEmailError.setTextFill(Paint.valueOf("red"));
                        lblEmailError.setText("No user found! Create account?");
                    }
                }
            }catch (SQLException e){
                System.out.println("FAILED!\n" + e.getMessage()+"\n---");
            }
        }
    }
    @FXML
    public void clearEmailError(){
        lblEmailError.setText("");
    }
    @FXML
    public void clearPasswordError(){
        lblPasswordError.setText("");
    }

    @FXML
    public void showSelectUserUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.SELECT_USER));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void showHomeUI(ActionEvent actionEvent, String url) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(url));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
