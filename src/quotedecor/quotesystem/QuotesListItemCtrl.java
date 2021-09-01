package quotedecor.quotesystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import quotedecor.model.Quote;

import java.net.URL;
import java.util.ResourceBundle;

public class QuotesListItemCtrl implements Initializable {
    @FXML private VBox vbTreeTableContainer;
    @FXML private TitledPane tpQuoteListItem;
//    @FXML private Button btnAcceptQuote;
    // materials details
    @FXML private TextField tfCeilingPaint, tfCeilingPaintCost, tfWallPaint, tfWallPaintCost, tfMaterialsTotalCost;
    // labour details
    @FXML private TextField tfCeilingLabourCost, tfWallLabourCost, tfLabourTotalCost;
    // quote breakdown
    @FXML private TextArea taBreakQuoteDown;

    private int listNo;

    private Quote quote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    // set the project object of this Title pane for access to its DB update and delete operations
    public void setQuote(Quote quote) {
        this.quote = quote;
        setQuoteItemDetails();
    }
    public void setQuoteItemDetails(){
        tfCeilingPaint.setText(quote.getTotalCeilingPaintLtr()+"ltrs");
        tfCeilingPaintCost.setText("€"+ quote.getTotalCeilingPaintCost());
        tfWallPaint.setText(quote.getTotalWallPaintLtr()+"ltrs");
        tfWallPaintCost.setText("€"+ quote.getTotalWallPaintCost());
        tfMaterialsTotalCost.setText("€"+ quote.getTotalMaterialsCost());
        tfCeilingLabourCost.setText("€"+ quote.getTotalCeilingLabour());
        tfWallLabourCost.setText("€"+ quote.getTotalWallPaintCost());
        tfLabourTotalCost.setText("€"+ quote.getTotalLabourCost());
        taBreakQuoteDown.setText(quote.getQuoteBreakdown());
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

}
