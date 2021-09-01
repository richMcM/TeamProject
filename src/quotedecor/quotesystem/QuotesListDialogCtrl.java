package quotedecor.quotesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import quotedecor.config.DB;
import quotedecor.model.Quote;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuotesListDialogCtrl implements Initializable {

    @FXML private Accordion accordionQuoteList;

    private int accordionListNo;

    private ArrayList<TitledPane> quoteListItems = new ArrayList<>();
    private ArrayList<QuotesListItemCtrl> quotesListItemCtrls = new ArrayList<>();

    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordionListNo = 0;
        for (Quote quote : Session.customer.quotes){

            try {
                // quote title pane UI header
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.QUOTE_LIST_ITEM_HEADER));
                GridPane quoteListItemHeader = loader.load();
                QuoteListItemHeaderCtrl quoteListItemHeaderCtrl = loader.getController();
                quoteListItemHeaderCtrl.getTfCompany().setText(quote.getCompanyName());
                quoteListItemHeaderCtrl.getTfPrice().setText(String.valueOf(quote.getTotal()));
                quoteListItemHeaderCtrl.setListNo(accordionListNo);

                // quote title pane UI
                loader = new FXMLLoader(getClass().getResource(URLS.QUOTE_LIST_ITEM));
                TitledPane quoteListItem = loader.load();
                quoteListItem.setGraphic(quoteListItemHeader);

                // quote title pane controller
                QuotesListItemCtrl quotesListItemCtrl = loader.<QuotesListItemCtrl>getController();
                quotesListItemCtrl.setQuote(quote);
                quotesListItemCtrl.setListNo(accordionListNo);

                // set accept button action
                quoteListItemHeaderCtrl.getBtnAccept().setOnAction(event -> {
                    setAcceptQuoteButtonAction(quoteListItemHeaderCtrl.getListNo());
                });

                // add title pane to ArrayList
                quoteListItems.add(quoteListItem);

                // add ctrl to ArrayList
                quotesListItemCtrls.add(quotesListItemCtrl);

                // add complete project title pane to accordion pane
                accordionQuoteList.getPanes().add(quoteListItem);
                accordionListNo++;
                System.out.println(quote);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setAcceptQuoteButtonAction(int quoteListNo){

        Quote acceptedQuote = quotesListItemCtrls.get(quoteListNo).getQuote();

        // create confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(false);
        alert.getDialogPane().setPrefWidth(350);
//        alert.getDialogPane().setGraphic(errorIcon);
        alert.setTitle("QUOTE");
        alert.setHeaderText("Accept quote of €"+acceptedQuote.getTotal()+" with "+acceptedQuote.getCompanyName()+"?");
//            alert.setContentText(errorMessage.toString());
//            alert.setWidth(1600);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            acceptedQuote.setCustomerAccepted(true);
            acceptedQuote.create();
            DB.printTableQuote();

            // create error icon
            ImageView successIcon = new ImageView("quotedecor/img/success_icon.png");
            successIcon.setFitHeight(48); // Set size to API recommendation.
            successIcon.setFitWidth(48);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(false);
            alert.getDialogPane().setPrefWidth(350);
            alert.getDialogPane().setGraphic(successIcon);
            alert.setTitle("QUOTE");
            alert.setHeaderText("Quote Accepted!");
            alert.setContentText(
                    acceptedQuote.getCompanyName()+" will be in contact with you to confirm " +
                            "the specified workload before confirming the quote.");
//            alert.setWidth(1600);
            alert.showAndWait();
        }
    }
}
