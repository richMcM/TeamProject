package quotedecor.completedProjectTab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import quotedecor.Main;
import quotedecor.company_quotesTab.QuoteDetailsWindowCtrl;
import quotedecor.company_reviewsSection.CompanyReviewUICtrl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompCompletedProjectsTitlePaneCtrl implements Initializable {

    @FXML
    TitledPane Lbl_projectID;

    @FXML
    Label lbl_ConfirmedProject;

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




    public void openCustomerReviewWindow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quotedecor/company_reviewsSection/CompanySubmitReview.fxml"));
        Parent root2 =  (Parent) loader.load();

        CompanyReviewUICtrl companyReviewUICtrl = loader.getController();
        companyReviewUICtrl.setTxt_customerId(lbl_customerID.getText());

        Stage stage = new Stage();
        stage.setTitle("Leave a Review: ");
        Scene scene = new Scene(root2, 1100 , 450);
        scene.getStylesheets().add(Main.class.getResource("css/bootstrapfx.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("css/global.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

