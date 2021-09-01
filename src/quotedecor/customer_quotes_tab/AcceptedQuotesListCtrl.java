package quotedecor.customer_quotes_tab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quotedecor.model.Quote;
import quotedecor.review_submission.ReviewsSubmissionDialogCtrl;
import quotedecor.sessions.Session;
import quotedecor.util.Console;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AcceptedQuotesListCtrl implements Initializable {

    @FXML
    private VBox vbAcceptedQuotesList;

    private int vBoxListNo = 0;

    private ArrayList<AcceptedQuotesListItemCtrl>  quoteListItemCtrls = new ArrayList<>();

    private quotedecor.config.DB DB = new quotedecor.config.DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBoxListNo = 0;
        Session.customer.retrieveAcceptedQuotes();

        for (Quote quote : Session.customer.quotes){
            try {
                // for each customer related quote => add quote list item to vbAcceptedQuotesList
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.CUSTOMER_ACCEPTED_QUOTES_LIST_ITEM));
                GridPane quoteListItem = (GridPane) loader.load();

                // get each quotes (GridPane) controller
                AcceptedQuotesListItemCtrl quoteListItemCtrl = loader.<AcceptedQuotesListItemCtrl>getController();
                quote.setCompanyName(getCompanyName(quote.getCompanyID()));
                quoteListItemCtrl.setQuote(quote);
                quoteListItemCtrl.getLblDate().setText(""+quote.getTimestamp());
                quoteListItemCtrl.getLblProject().setText(getProjectName(quote.getProjectID()));
                quoteListItemCtrl.getLblCompany().setText(quote.getCompanyName());
                quoteListItemCtrl.getLblQuotePrice().setText("€"+quote.getTotal());

                if(quote.isCompanyConfirmed()){
                    quoteListItemCtrl.getReviewOption().setVisible(true);
                    quoteListItemCtrl.getLblConfirmed().setVisible(false);
                }
                if(quote.isCustomerReviewed())
                    setStatusLabelComplete(quoteListItemCtrl);

                quoteListItemCtrl.setListNo(vBoxListNo);

                // set leave review button action
                setLeaveReviewButtonAction(quoteListItemCtrl);

                // set delete quote button action
                setDeleteButtonAction(quoteListItemCtrl);

                // add quote list item controller to quoteListItemCtrls ArrayList
                quoteListItemCtrls.add(quoteListItemCtrl);

                // add title pane to ArrayList
                vbAcceptedQuotesList.getChildren().add(quoteListItem);

                // inc Vbox list number
                vBoxListNo++;

                Console.print(quote.toString());
            }
            catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String getProjectName(int companyID) throws SQLException {

        ResultSet rs = DB.query("SELECT * FROM project WHERE project_id = " + companyID + " LIMIT 1;");
        rs.next();
        return rs.getString("project_name");
    }
    private String getCompanyName(String companyID) throws SQLException {

        ResultSet rs = DB.query("SELECT * FROM company WHERE email = '" + companyID + "' LIMIT 1;");
        rs.next();
        return rs.getString("company_name");
    }
    private void setLeaveReviewButtonAction(AcceptedQuotesListItemCtrl quoteListItemCtrl){

        quoteListItemCtrl.getBtnLeaveReview().setOnAction(e -> {
            // display review submission dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.REVIEW_SUBMISSION_UI));
            Parent parent = null;
            try {
                parent = (Parent) loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ReviewsSubmissionDialogCtrl reviewDialogCtrl = loader.getController();
            Console.print(reviewDialogCtrl.toString());
            reviewDialogCtrl.setQuote(quoteListItemCtrl.getQuote());
            reviewDialogCtrl.getTxtUserID().setText(quoteListItemCtrl.getLblCompany().getText());
            reviewDialogCtrl.getBtnSubmit().setOnAction(e2 -> {
                reviewDialogCtrl.submitReview();
            });


            Scene scene = new Scene(parent, 760, 525);
            Stage stage = new Stage();
    //        stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
            stage.setTitle("Submit Review");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            setStatusLabelComplete(quoteListItemCtrl);
        });
    }
    private void setDeleteButtonAction(AcceptedQuotesListItemCtrl ctrl){

        ctrl.getBtnDelete().setOnAction(e -> {

            // remove title pane item from vbox quotes list
            vbAcceptedQuotesList.getChildren().remove(ctrl.getListNo());

            // remove ctrl from quoteListItemCtrls ArrayList
            quoteListItemCtrls.remove(ctrl.getListNo());

            // dec list number item counter
            vBoxListNo--;

            // remove quote fromm DB
            ctrl.getQuote().delete();

            // for the remaining vbox quote list item children
            for (int i = 0; i< vBoxListNo; i++) {
                // update list accordion list number
                quoteListItemCtrls.get(i).setListNo(i);
                Console.print(""+i);
            }
            DB.printTableQuote();
        });
    }
    private void setStatusLabelComplete(AcceptedQuotesListItemCtrl quoteListItemCtrl){
        quoteListItemCtrl.getReviewOption().setVisible(false);
        quoteListItemCtrl.getLblConfirmed().setVisible(true);
        quoteListItemCtrl.getLblConfirmed().setStyle("-fx-text-fill: #248ef8;");;
        quoteListItemCtrl.getLblConfirmed().setText("SERVICE COMPLETE");
    }
}
