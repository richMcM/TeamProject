package quotedecor.quotesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class QuoteListItemHeaderCtrl {
    @FXML
    private Button btnAccept;
    @FXML
    private TextField tfCompany, tfPrice;

    private int listNo;

    public Button getBtnAccept() {
        return btnAccept;
    }

    public TextField getTfCompany() {
        return tfCompany;
    }

    public TextField getTfPrice() {
        return tfPrice;
    }

    public int getListNo() {
        return listNo;
    }

    public void setListNo(int listNo) {
        this.listNo = listNo;
    }
}
