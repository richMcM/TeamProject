/**
 * Sample Skeleton for 'ReviewsSubmissionDialog.fxml' Controller Class
 */

package quotedecor.review_submission;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import quotedecor.model.Quote;
import quotedecor.model.Review;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewsSubmissionDialogCtrl implements Initializable {

    @FXML // fx:id="txtUserID"
    private TextField txtUserID;

    @FXML // fx:id="cbxRating"
    private ComboBox<?> cbxRating;

    @FXML // fx:id="btnSubmit"
    private Button btnSubmit;

    @FXML // fx:id="txtaReview"
    private TextArea txtaReview;

    private Quote quote;;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUserID.setEditable(false);
    }

    public TextField getTxtUserID() {
        return txtUserID;
    }

    public Button getBtnSubmit() {
        return btnSubmit;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public void submitReview(){
        Review review = new Review(quote.getCompanyID(), quote.getCustomerID());
        review.setRating(Integer.parseInt(cbxRating.getValue().toString()));
        review.setComment(txtaReview.getText());
        review.create();
        quote.setCustomerReviewed(true);
        quote.update();
    }
}

