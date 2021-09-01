package quotedecor.customer_quotes_tab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import quotedecor.model.Quote;

import java.net.URL;
import java.util.ResourceBundle;

public class AcceptedQuotesListItemCtrl implements Initializable {

    @FXML
    private Labeled lblDate, lblProject, lblCompany, lblQuotePrice, lblConfirmed;
    @FXML
    private Button btnLeaveReview, btnDelete;
    @FXML
    private GridPane gpReviewOption;

    private int listNo;
    public Quote quote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Labeled getLblDate() {
        return lblDate;
    }

    public void setLblDate(Labeled lblDate) {
        this.lblDate = lblDate;
    }

    public Labeled getLblProject() {
        return lblProject;
    }

    public void setLblProject(Labeled lblProject) {
        this.lblProject = lblProject;
    }

    public Labeled getLblCompany() {
        return lblCompany;
    }

    public void setLblCompany(Labeled lblCompany) {
        this.lblCompany = lblCompany;
    }

    public Labeled getLblQuotePrice() {
        return lblQuotePrice;
    }

    public void setLblQuotePrice(Labeled lblQuotePrice) {
        this.lblQuotePrice = lblQuotePrice;
    }

    public Labeled getLblConfirmed() {
        return lblConfirmed;
    }

    public void setLblConfirmed(Labeled lblConfirmed) {
        this.lblConfirmed = lblConfirmed;
    }

    public Button getBtnLeaveReview() {
        return btnLeaveReview;
    }

    public void setBtnLeaveReview(Button btnLeaveReview) {
        this.btnLeaveReview = btnLeaveReview;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public GridPane getReviewOption() {
        return gpReviewOption;
    }

    public void setReviewOption(GridPane gpReviewOption) {
        this.gpReviewOption = gpReviewOption;
    }

    public int getListNo() {
        return listNo;
    }

    public void setListNo(int listNo) {
        this.listNo = listNo;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
