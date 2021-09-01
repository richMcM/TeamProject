package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import quotedecor.config.DB;
import quotedecor.sessions.Session;
import quotedecor.util.InputProcessor;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerProfileCtrl implements Initializable {

    @FXML
    GridPane gridProfile;

    @FXML
    private TextField txtForename, txtSurname, txtAptNo, txtStreet, txtTown, txtCounty, txtEmail, txtPassword;

    @FXML
    private Button btnEdit, btnSave;

    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProfileGridPane();
    }
    
    public void setProfileGridPane(){
        txtEmail.setText(Session.customer.getEmail());
        txtPassword.setText(Session.customer.getPassword());
        txtForename.setText(Session.customer.getForename());
        txtSurname.setText(Session.customer.getSurname());

        String address = Session.customer.getAddress();
        System.out.println(address);
        // Splitting ADDRESS String and setting COMPANY PROFILE fields
        String str[] = address.split(",", 4);
        List<String> addressArray = new ArrayList<String>();
        addressArray = Arrays.asList(str);
        for(String s: addressArray){
            System.out.println(s);
        }
        if(addressArray.size() >=4 ) {
            txtAptNo.setText(addressArray.get(0));
            txtStreet.setText(addressArray.get(1));
            txtTown.setText(addressArray.get(2));
            txtCounty.setText(addressArray.get(3));

        }
    }
    public void setEditable(){
        btnEdit.setVisible(false);
        gridProfile.setDisable(false);
        txtPassword.setEditable(true);
        txtForename.setEditable(true);
        txtSurname.setEditable(true);
        txtAptNo.setEditable(true);
        txtStreet.setEditable(true);
        txtTown.setEditable(true);
        txtCounty.setEditable(true);
        btnSave.setVisible(true);
    }

    public void updateCustomerDetails(ActionEvent actionEvent) {

        // Getting input for company detail fields and assign to Session.company
        String forname = InputProcessor.strSanitise(txtForename.getText());
        Session.customer.setForename(forname);
        System.out.println(forname);

        String surname = InputProcessor.strSanitise(txtSurname.getText());
        Session.customer.setSurname(surname);
        System.out.println(surname);

        String address = InputProcessor.strSanitise(txtAptNo.getText()) + ", " +
                InputProcessor.strSanitise(txtStreet.getText()) + ", " +
                InputProcessor.strSanitise(txtTown.getText()) + ", " +
                InputProcessor.strSanitise(txtCounty.getText());

        Session.customer.setAddress(address);
        System.out.println(address);

        String email = InputProcessor.strSanitise(txtEmail.getText());
        Session.customer.setEmail(email);
        System.out.println(email);

        String password = InputProcessor.strSanitise(txtPassword.getText());
        Session.customer.setPassword(password);
        System.out.println(password);

        // Performs the sql update
        Session.customer.update();

        DB.printTableCustomer();
        System.out.println(Session.customer);
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
        gridProfile.setDisable(true);
        setProfileGridPane();
    }
}
