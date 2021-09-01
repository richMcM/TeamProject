package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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

public class CompanyProfileCtrl implements Initializable {

    @FXML
    private GridPane gridProfile;
    @FXML
    private TextField txfEmail, tfCompanyName, tfContactName, txfApartmentNo, txfAddress1, txfTown, txfCounty;
    @FXML
    private PasswordField psfPassword;
    @FXML
    private Button btnEdit, btnSave;

    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProfileGridPane();
    }

    public void setProfileGridPane(){
        txfEmail.setText(Session.company.getEmail());
        psfPassword.setText(Session.company.getPassword());
        tfCompanyName.setText(Session.company.getCompanyName());
        tfContactName.setText(Session.company.getContactsName());
        String address = Session.company.getAddress();
        System.out.println(address);
        // Splitting ADDRESS String and setting COMPANY PROFILE fields
        String str[] = address.split(",", 4);
        List<String> addressArray = new ArrayList<String>();
        addressArray = Arrays.asList(str);
//        for(String s: addressArray){
//            System.out.println(s);
//        }
        if(addressArray.size() >=4 ) {
            txfApartmentNo.setText(addressArray.get(0));
            txfAddress1.setText(addressArray.get(1));
            txfTown.setText(addressArray.get(2));
            txfCounty.setText(addressArray.get(3));

        }

    }
    public void setEditable(){
        btnEdit.setVisible(false);
        gridProfile.setDisable(false);
        psfPassword.setEditable(true);
        tfCompanyName.setEditable(true);
        tfContactName.setEditable(true);
        txfApartmentNo.setEditable(true);
        txfAddress1.setEditable(true);
        txfTown.setEditable(true);
        txfCounty.setEditable(true);
        btnSave.setVisible(true);
    }
    @FXML
    public void updateCompanyDetails(ActionEvent actionEvent) {

        // Getting input for company detail fields and assign to Session.company
        Session.company.setCompanyName(InputProcessor.strSanitise(tfCompanyName.getText()));

        Session.company.setContactsName(InputProcessor.strSanitise(tfContactName.getText()));

        String address = InputProcessor.strSanitise(txfApartmentNo.getText()) + ", " +
                InputProcessor.strSanitise(txfAddress1.getText()) + ", " +
                InputProcessor.strSanitise(txfTown.getText()) + ", " +
                InputProcessor.strSanitise(txfCounty.getText());

        Session.company.setAddress(address);

        Session.company.setPassword(psfPassword.getText());

        // Performs the sql update
        Session.company.update();

        DB.printTableCompany();
        System.out.println(Session.company);
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
        gridProfile.setDisable(true);
        setProfileGridPane();
    }

}
