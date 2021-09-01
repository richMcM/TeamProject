package quotedecor.company_projects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quotedecor.Main;
import quotedecor.company_reviewsSection.CompanyReviewUICtrl;
import quotedecor.config.DB;
import quotedecor.util.URLS;
import quotedecor.view_companies.CompanyListItemCtrl;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class  CompanyProjectTitlePaneCtrl implements Initializable {

    @FXML
    TitledPane Lbl_projectID;

    @FXML
    Label lbl_ConfirmedProject;
//    @FXML
//    Label Lbl_projectID;
    @FXML
    Label lbl_customerID;
    @FXML
    Label lbl_projectName;
    @FXML
    Label lbl_Price;
    @FXML
    Label lbl_Address;
    @FXML
    Label lbl_NoOfRooms;
    @FXML
    Label lbl_NoProjectsMessage;
    @FXML
    Button btn_LeaveCustomerReview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLbl_projectID(String pID) {
        this.Lbl_projectID.setText(pID);
    }

    public void setLbl_customerID(String cID) {
        this.lbl_customerID.setText(cID);
    }

    public String getLbl_customerID() {
        return lbl_customerID.getText();
    }

    public void setLbl_projectName(String pN) {
        this.lbl_projectName.setText(pN);
    }

    public void setLbl_Price(String pR) {
        this.lbl_Price.setText(pR);
    }

    public void setLbl_Address(String ad) {
        this.lbl_Address.setText(ad);
    }

    public void setLbl_NoOfRooms(String NoR) {
        this.lbl_NoOfRooms.setText(NoR);
    }

    public void setLbl_NoProjectsMessage(String noProjectsMessage) {
        this.lbl_NoProjectsMessage.setText(noProjectsMessage);
    }

    public void removeLbl_ConfirmedProject() {
        this.lbl_ConfirmedProject.setText("");
    }

    public void markProjectAsCompleted(ActionEvent actionEvent) {

        String str = Lbl_projectID.getText();
        // Used to parse integers and ignore any
        int projectID = Integer.parseInt(str.replaceAll("[\\D]", ""));

        DB db = new DB();
        System.out.println("Before!!!!!!!!!!!!!!!!!!!!!!" + projectID);
        db.printTable("project");
        // Update Project record to mark project_status boolean column as completed
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(350);
        alert.setHeaderText("Mark Project as Completed?");
        alert.setContentText("Would you like to mark this Project as Finished and Completed?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("OK Button Clicked!!!!!!!!!!");
            db.insertUpdateDelete("UPDATE `project` " +
                    "SET `project_status` = " + true +
                    " WHERE `project_id`  = " + projectID);

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Complete");
            alertConfirmPriceChange.setHeaderText("Project Information Updated!");
            alertConfirmPriceChange.setContentText("Project marked as Finished and Complete!");
            alertConfirmPriceChange.showAndWait();

        } else if(result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button Clicked!!!!!!!!!!");
        }

        System.out.println("After!!!!!!!!!!!!!!!!");

    }
}
