package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import quotedecor.config.DB;
import quotedecor.model.Customer;
import quotedecor.sessions.Session;
import quotedecor.util.InputProcessor;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerCreateAccCtrl implements Initializable {

    //All of the inputs for a new customer account
    @FXML
    private TextField txfEmail, txfForename, txfSurname, txfAptHouseNo, txfStreet, txfTown, txfCounty;
    @FXML
    private PasswordField psfPassword;
    //Labels in case inputs are incorrect
    @FXML
    private Label lblEmailError, lblForenameError, lblSurnameError, lblPasswordError, lblAptHouseNoError, lblStreetError, lblTownError, lblCountyError;
    //Next declare all labels alongside the input fields in the GUI
    @FXML
    private Label lblEmail, lblForename, lblPassword, lblAptHouseNo, lblStreet, lblTown, lblCountry;
    //Next The create account button
    @FXML
    Button btnJoin;
    @FXML
    private Pane testPane;

    //Database Object composites
    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    @FXML
    public void showLoginUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.LOGIN));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void showHomeUI(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URLS.CUSTOMER_HOME));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //Create Account method
    @FXML
    public void doCreateAccount(ActionEvent actionEvent) {

        String customerEmail = txfEmail.getText().trim().toLowerCase();
        String forename = InputProcessor.strSanitise(txfForename.getText());
        String surname = InputProcessor.strSanitise(txfSurname.getText());
        String password = psfPassword.getText();
        String apartmentNo = InputProcessor.strSanitise(txfAptHouseNo.getText());
        String addressLine1 = InputProcessor.strSanitise(txfStreet.getText());
        String town = InputProcessor.strSanitise(txfTown.getText());
        String county = InputProcessor.strSanitise(txfCounty.getText());

        boolean inputsValid = true;
        if (forename.length() == 0) {
            lblForenameError.setTextFill(Paint.valueOf("red"));
            lblForenameError.setText("Required Field!");
            inputsValid = false;
        }
        if (surname.length() == 0) {
            lblSurnameError.setTextFill(Paint.valueOf("red"));
            lblSurnameError.setText("Required Field!");
            inputsValid = false;
        }
        if (apartmentNo.length() == 0) {
            lblAptHouseNoError.setTextFill(Paint.valueOf("red"));
            lblAptHouseNoError.setText("Required Field!");
            inputsValid = false;
        }
        if (addressLine1.length() == 0) {
            lblStreetError.setTextFill(Paint.valueOf("red"));
            lblStreetError.setText("Required Field!");
            inputsValid = false;
        }
        if (town.length() == 0) {
            lblTownError.setTextFill(Paint.valueOf("red"));
            lblTownError.setText("Required Field!");
            inputsValid = false;
        }
        if (county.length() == 0) {
            lblCountyError.setTextFill(Paint.valueOf("red"));
            lblCountyError.setText("Required Field!");
            inputsValid = false;
        }
        if (!InputProcessor.validEmail(customerEmail)) {
            lblEmailError.setTextFill(Paint.valueOf("red"));
            lblEmailError.setText("Invalid Email!");
            inputsValid = false;
        }
        if (customerEmail.length() == 0) {
            lblEmailError.setTextFill(Paint.valueOf("red"));
            lblEmailError.setText("Required Field!");
            inputsValid = false;
        }
        if (password.length() == 0) {
            lblPasswordError.setTextFill(Paint.valueOf("red"));
            lblPasswordError.setText("Required Field!");
            inputsValid = false;
        }
        if (inputsValid) {
            try {
                ResultSet rs = null;
                String sqlStmnt = String.format(
                        "SELECT * " +
                                "FROM `customer` " +
                                "WHERE `email` = '%s' " +
                                "LIMIT 1;",
                        customerEmail
                );
                System.out.println("READING USER: " + customerEmail);
                rs = DB.query(sqlStmnt);
                if (rs.next()) {
                    lblEmailError.setTextFill(Paint.valueOf("orange"));
                    lblEmailError.setText("Email already exists!");
                    btnJoin.setText("Join?");
                } else {
                    String address = String.format("%s, %s, %s, %s",
                            apartmentNo,
                            addressLine1,
                            town,
                            county
                    );
                    Customer c = new Customer(customerEmail, password, address, forename, surname);

                    sqlStmnt = String.format(
                            "SELECT * " +
                                    "FROM `customer` " +
                                    "WHERE `email` = '%s' " +
                                    "LIMIT 1;",
                            customerEmail
                    );
                    System.out.println("READING USER: " + customerEmail);
                    rs = DB.query(sqlStmnt);
                    if (rs.next()) {
                        Session.createCustomer(rs);
                        showHomeUI(actionEvent);
                        System.out.println("CUSTOMER CREATED " + c.toString());
                    } else {
                        System.out.println(">>>> ERROR! - NEW CUSTOMER NOT FOUND <<<<");
                    }

                }
            } catch (SQLException e) {
                System.out.println("FAILED!\n" + e.getMessage() + "\n---");
            }
        }
    }

    public void clearLblCompanyNameError() {
        this.lblForenameError.setText("");
        btnJoin.setText("");
    }

    public void clearLblContactNameError() {
        this.lblSurnameError.setText("");
        btnJoin.setText("");
    }

    public void clearLblHouseNoError() {
        this.lblAptHouseNoError.setText("");
        btnJoin.setText("");
    }

    public void clearLblStreetError() {
        this.lblStreetError.setText("");
        btnJoin.setText("");
    }

    public void clearLblTownError() {
        this.lblTownError.setText("");
        btnJoin.setText("");
    }

    public void clearLblCountyError() {
        this.lblCountyError.setText("");
        btnJoin.setText("");
    }

    public void clearLblEmailError() {
        this.lblEmailError.setText("");
        btnJoin.setText("");
    }

    public void clearLblPasswordError() {
        this.lblPasswordError.setText("");
        btnJoin.setText("");
    }
}
