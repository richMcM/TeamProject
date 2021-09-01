package quotedecor.company_reviewsSection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import quotedecor.company_projects.CompanyProjectTitlePaneCtrl;
import quotedecor.config.DB;
import quotedecor.model.Company;
import quotedecor.model.Review;
import quotedecor.sessions.Session;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyReviewUICtrl implements Initializable {


    @FXML
    TextField txt_customerId;

    @FXML
    TextArea txt_UserReview;

    @FXML
    ComboBox comRating;
    @FXML
    Button btn_submitReview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTxt_customerId(String cid) {
        txt_customerId.setText(cid);
//        this.txf_customerId =
//        CompanyProjectTitlePaneCtrl companyProjectTitlePaneCtrl = new loader.getController();
//        System.out.println(companyProjectTitlePaneCtrl.getLbl_customerID());
//        String tf_cid = companyProjectTitlePaneCtrl.getLbl_customerID().getText();
//        System.out.println("TextField Customer ID" + tf_cid);
//        this.txf_customerId.setText(tf_cid);
//
//        System.out.println("TextField Customer ID" + tf_cid);
    }

    public void setTxt_UserReview(String str) {
        txt_UserReview.setText(str);
    }

    public void submitReview(ActionEvent actionEvent) {
        String custID = txt_customerId.getText();
        String comment = txt_UserReview.getText();
        int rating = Integer.parseInt(comRating.getValue().toString());
        System.out.println("Cust id " + custID);
        System.out.println("Comment " + comment);
        System.out.println("Rating " + rating);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(350);
        alert.setHeaderText("Confirm Review!");
        alert.setContentText("Are you sure you would Submit this Review?");



        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {

            Review review = new Review();
            review.setUserID(custID);
            review.setReviewerID(Session.company.getEmail());
            System.out.println("Company Email       ! " + Session.company.getEmail());
            review.setComment(comment);
            review.setRating(rating);
            review.create();

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Complete");
            alertConfirmPriceChange.setHeaderText("Review Submitted!");
            alertConfirmPriceChange.setContentText("Your Review for Customer " + custID + " has been Submitted Successfully!");
            alertConfirmPriceChange.showAndWait();

        } else if(result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button Clicked!!!!!!!!!!");
        }



//        String sql = "INSERT INTO Review VALUES (" + 0 + ",'"+ custID + "'" + "," + rating + ",'" +  comment + "')";
//        String sql = "INSERT INTO Review (review_id, user_id, rating, comment) VALUES (Null" + ",'"+ custID + "'" + "," + rating + ",'" +  comment + "')";
//        System.out.println(sql);

//        DB db = new DB();
//        db.insertUpdateDelete(sql);

    }
}
