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
import quotedecor.model.Company;
import quotedecor.sessions.Session;
import quotedecor.util.InputProcessor;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

    public class CompanyCreateAccCtrl implements Initializable {

        @FXML
        private TextField tfCompanyName, tfContactName, txfApartmentNo, txfAddress1, txfTown, txfCounty, txfEmail;
        @FXML
        private Label lblCompanyNameError, lblContactNameError, lblHouseNoError, lblStreetError,lblTownError, lblCountyError, lblEmailError, lblPasswordError;
        @FXML
        Button btnLogin;
        @FXML
        PasswordField psfPassword;
        @FXML
        private Pane testPane;

        // composites
        DB DB = new DB();

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        @FXML
        public void showSelectUserUI(ActionEvent actionEvent) {
            try {
                Parent parent =  FXMLLoader.load(getClass().getResource(URLS.SELECT_USER));
                Scene scene2 = new Scene(parent);
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setScene(scene2);
            }catch (IOException io){
                io.printStackTrace();
            }
        }
        @FXML
        public void showLoginUI(ActionEvent actionEvent){
            try {
                Parent parent =  FXMLLoader.load(getClass().getResource(URLS.LOGIN));
                Scene scene2 = new Scene(parent);
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setScene(scene2);
            }catch (IOException io){
                io.printStackTrace();
            }
        }
        private void showLabourPrompt(ActionEvent actionEvent) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource(URLS.COMPANY_HOME));
                Scene scene2 = new Scene(parent);
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setScene(scene2);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        @FXML
        public void doCreateAccount(ActionEvent actionEvent) {

            String companyName = InputProcessor.strSanitise(tfCompanyName.getText());
            String contactName = InputProcessor.strSanitise(tfContactName.getText());
            String email = txfEmail.getText().trim().toLowerCase();
            String password = psfPassword.getText();
            String apartmentNo = InputProcessor.strSanitise(txfApartmentNo.getText());
            String addressLine1 = InputProcessor.strSanitise(txfAddress1.getText());
            String county = InputProcessor.strSanitise(txfCounty.getText());
            String town = InputProcessor.strSanitise(txfTown.getText());

            boolean inputsValid = true;
            if (companyName.length() == 0) {
                lblCompanyNameError.setTextFill(Paint.valueOf("red"));
                lblCompanyNameError.setText("Required Field!");
                inputsValid = false;
            }
            if (contactName.length() == 0) {
                lblContactNameError.setTextFill(Paint.valueOf("red"));
                lblContactNameError.setText("Required Field!");
                inputsValid = false;
            }
            if (apartmentNo.length() == 0) {
                lblHouseNoError.setTextFill(Paint.valueOf("red"));
                lblHouseNoError.setText("Required Field!");
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
            if (!InputProcessor.validEmail(email)) {
                lblEmailError.setTextFill(Paint.valueOf("red"));
                lblEmailError.setText("Invalid Email!");
                inputsValid = false;
            }
            if (email.length()==0) {
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
                                    "FROM `company` " +
                                    "WHERE `email` = '%s' " +
                                    "LIMIT 1;",
                            email
                    );
                    System.out.println("READING USER: "+email);
                    rs = DB.query(sqlStmnt);
                    if (rs.next()) {
                        lblEmailError.setTextFill(Paint.valueOf("orange"));
                        lblEmailError.setText("Email already exists!");
                        btnLogin.setText("Login?");
                    }else{
                        String address = String.format("%s, %s, %s, %s",
                                apartmentNo,
                                addressLine1,
                                town,
                                county
                        );
                        Company c = new Company(email, password, address, companyName, contactName);

                        sqlStmnt = String.format(
                                "SELECT * " +
                                        "FROM `company` " +
                                        "WHERE `email` = '%s' " +
                                        "LIMIT 1;",
                                email
                        );
                        System.out.println("READING USER: "+email);
                        rs = DB.query(sqlStmnt);
                        if (rs.next()) {
                            Session.createCompany(rs);
                            showLabourPrompt(actionEvent);
                            System.out.println("COMPANY CREATED " + c.toString());
                        }else{
                            System.out.println(">>>> ERROR! - NEW COMPANY NOT FOUND <<<<");
                        }

                    }
                }catch (SQLException e){
                    System.out.println("FAILED!\n" + e.getMessage()+"\n---");
                }
            }




//            System.out.println("Value for customer company name:" + companyName);
//            System.out.println("Value for customer contacts name:" + contactName);
//            System.out.println("Value for customer Apt/house No:" + apartmentNo);
//            System.out.println("Value for customer Address Line 1:" + addressLine1);
//            System.out.println("Value for customer Town:" + town);
//            System.out.println("Value for customer County:" + county);
//            System.out.println("Value for customer email:" + email);
//            System.out.println("Value for customer password:" + password);
        }

        public void clearLblCompanyNameError() {
            this.lblCompanyNameError.setText("");
            btnLogin.setText("");
        }

        public void clearLblContactNameError() {
            this.lblContactNameError.setText("");
            btnLogin.setText("");
        }

        public void clearLblHouseNoError() {
            this.lblHouseNoError.setText("");
            btnLogin.setText("");
        }

        public void clearLblStreetError() {
            this.lblStreetError.setText("");
            btnLogin.setText("");
        }

        public void clearLblTownError() {
            this.lblTownError.setText("");
            btnLogin.setText("");
        }

        public void clearLblCountyError() {
            this.lblCountyError.setText("");
            btnLogin.setText("");
        }

        public void clearLblEmailError() {
            this.lblEmailError.setText("");
            btnLogin.setText("");
        }

        public void clearLblPasswordError() {
            this.lblPasswordError.setText("");
            btnLogin.setText("");
        }
    }
