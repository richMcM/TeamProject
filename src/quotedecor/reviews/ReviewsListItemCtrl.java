package quotedecor.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewsListItemCtrl implements Initializable {

    @FXML
    private Labeled lblDate, lblUsersName, lblRating;

    @FXML
    private TextArea txtaFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Labeled getLblDate() {
        return lblDate;
    }

    public void setLblDate(Labeled lblDate) {
        this.lblDate = lblDate;
    }

    public Labeled getLblUsersName() {
        return lblUsersName;
    }

    public void setLblUsersName(Labeled lblUsersName) {
        this.lblUsersName = lblUsersName;
    }

    public TextArea getTxtaFeedback() {
        return txtaFeedback;
    }

    public void setTxtaFeedback(TextArea txtaFeedback) {
        this.txtaFeedback = txtaFeedback;
    }

    public Labeled getLblRating() {
        return lblRating;
    }

    public void setLblRating(Labeled lblRating) {
        this.lblRating = lblRating;
    }



}
