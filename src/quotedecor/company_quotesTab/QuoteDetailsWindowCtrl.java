package quotedecor.company_quotesTab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import quotedecor.company_projects.CompanyProjectTitlePaneCtrl;
import quotedecor.config.DB;
import quotedecor.model.Company;
import quotedecor.model.Review;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class QuoteDetailsWindowCtrl implements Initializable {


    @FXML
    TextField txt_customerId;

    @FXML
    TextArea txtArea_QuoteDetails;

    @FXML
    ComboBox comRating;
    @FXML
    Button btn_submitReview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTxt_customerId(String cid) {
        txt_customerId.setText(cid);
    }

    public void setTxt_txtArea_QuoteDetails(String str) {
        txtArea_QuoteDetails.setText(str);
    }

}
