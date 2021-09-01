package quotedecor.view_companies;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyListItemCtrl implements Initializable {

    @FXML
    private Label lblCompanyName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Label getLblCompanyName() {
        return lblCompanyName;
    }
}
