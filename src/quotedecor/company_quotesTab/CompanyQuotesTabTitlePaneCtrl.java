package quotedecor.company_quotesTab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quotedecor.Main;
import quotedecor.company_reviewsSection.CompanyReviewUICtrl;
import quotedecor.config.DB;
import quotedecor.controllers.CompanyHomeCtrl;
import quotedecor.model.Quote;
import quotedecor.sessions.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyQuotesTabTitlePaneCtrl implements Initializable {

    @FXML
    Label lbl_customerID;
    @FXML
    Label lbl_QuoteId, lbl_projectName;
    @FXML
    TextField lbl_Price;
    @FXML
    Label lbl_Address;
    @FXML
    Label lbl_NoOfRooms;
    @FXML
    Label lbl_priceFieldError;
    @FXML
    Label lbl_ConfirmedProject;
    @FXML
    Label Lbl_NoQuotesMessage;

    @FXML
    Button btn_editPrice, btn_cancelQuote, btn_confirmQuote;

    int projectId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLbl_QuoteId(String qid) {
        this.lbl_QuoteId.setText(qid);
    }

    public void setLbl_customerID(String cID) {
        this.lbl_customerID.setText(cID);
    }

    public void setLbl_projectName(String pN) {
        this.lbl_projectName.setText(pN);
    }

    public void setLbl_Price(String pR) {
        this.lbl_Price.setText(pR);
    }

    public void setLbl_Address(String ad) {
        this.lbl_Address.setText(ad);
    }

    public void setLbl_NoOfRooms(String valueOf) {
        this.lbl_NoOfRooms.setText(valueOf);
    }

    public void removeLbl_ConfirmedProject() {
        this.lbl_ConfirmedProject.setText("");
    }
    public void setLbl_NoQuotesMessage(String noQuotesMessage) {
        this.Lbl_NoQuotesMessage.setText(noQuotesMessage);
    }



    public void editQuotePrice(ActionEvent actionEvent) {
        DB db = new DB();
        double newPrice = 0;
        try {
            newPrice = Double.parseDouble(lbl_Price.getText());
            lbl_Price.setStyle("-fx-border-color: white;");

            System.out.println("New Price: " + newPrice);
            String companyId = Session.company.getEmail();
            System.out.println("Company ID: " + companyId);
            int quoteId = Integer.parseInt(lbl_QuoteId.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setResizable(true);
            alert.getDialogPane().setPrefWidth(350);
            alert.setHeaderText("Update Price Confirmation!");
            alert.setContentText("Are you sure you would like to change price to: "+ newPrice);

//            alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                System.out.println("OK Button Clicked!!!!!!!!!!");
                db.insertUpdateDelete("UPDATE `quote` " +
                        "SET `price` = " + newPrice +
                        " WHERE `quote_id`  = " + quoteId);

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Complete");
            alertConfirmPriceChange.setHeaderText("Price Updated!");
            alertConfirmPriceChange.setContentText("New Price for this Project set to: " + newPrice);
            alertConfirmPriceChange.showAndWait();

            } else if(result.get() == ButtonType.CANCEL) {
                System.out.println("Cancel Button Clicked!!!!!!!!!!");
            }

        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
            System.out.println("Invalid input, try again.");
            lbl_Price.setStyle("-fx-border-color: red;");
            lbl_priceFieldError.setText("Invalid input in price field.");
        }

    }

    public void setConfirmQuote(ActionEvent actionEvent) {
        int quoteId = Integer.parseInt(lbl_QuoteId.getText());
        System.out.println("Quote ID: " + quoteId);
        DB db = new DB();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(350);
        alert.setHeaderText("Confirm Project Confirmation!");
        alert.setContentText("Are you sure you would Confirm this Project");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("OK Button Clicked!!!!!!!!!!");
            db.insertUpdateDelete("UPDATE `quote` " +
                    "SET `company_confirmed` = " + 1 +
                    " WHERE `quote_id`  = " + quoteId);

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Complete");
            alertConfirmPriceChange.setHeaderText("Quote Confirmed!");
            alertConfirmPriceChange.setContentText("You can now see this PROJECT under the CONFIRMED PROJECTS tab.");
            alertConfirmPriceChange.showAndWait();

        } else if(result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button Clicked!!!!!!!!!!");
        }

    }

    public void cancelQuote(ActionEvent actionEvent) {

        int quoteId = Integer.parseInt(lbl_QuoteId.getText());
        System.out.println("Quote ID: " + quoteId);
        DB db = new DB();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(350);
        alert.setHeaderText("Confirm Quote Deletion!");
        alert.setContentText("Are you sure you would Delete this QUOTE?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("OK Button Clicked!!!!!!!!!!");
            db.insertUpdateDelete("DELETE FROM `quote` " +
                    " WHERE `quote_id`  = " + quoteId);

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Deletion Complete");
            alertConfirmPriceChange.setHeaderText("Quote Deletion Confirmed!");
            alertConfirmPriceChange.setContentText("This Quote has now been DELETED.");
            alertConfirmPriceChange.showAndWait();

        } else if(result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button Clicked!!!!!!!!!!");
        }

    }

    public void openQuoteDetailsWindow(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quotedecor/company_quotesTab/QuoteDetailsWindow.fxml"));
            Parent root2 =  (Parent) loader.load();

            QuoteDetailsWindowCtrl quoteDetailsWindowCtrl = loader.getController();
            System.out.println(lbl_QuoteId.getText());
            quoteDetailsWindowCtrl.setTxt_customerId(lbl_QuoteId.getText());



            DB db = new DB();

            ResultSet quoteTableRS = db.query("SELECT * " +
                    "FROM `quote` " +
                    "WHERE `quote_id` = " + lbl_QuoteId.getText() + " LIMIT 1");


            while (quoteTableRS.next()) {
                projectId = (quoteTableRS.getInt("project_id"));
                System.out.println("Project ID !! " + projectId);

                Quote quote = new Quote();
                quote.mapResultSet(quoteTableRS);
                System.out.println(quote.toString());

                quoteDetailsWindowCtrl.setTxt_txtArea_QuoteDetails(quote.toString());
            }




            ResultSet roomTableRS = db.query("SELECT * " +
                    "FROM `room` " +
                    "WHERE `project_id` = " + projectId);

            int roomCount = 0;
            while (roomTableRS.next()) {
                roomCount++;
                System.out.println("Num of Rooms!!!!!!!!!!!!!!!!!!!      " + roomCount);
            }



//            ResultSet quoteAndRoomTablesRS = db.query("SELECT * " +
//                    "FROM `quote` " +
//                    "WHERE `quote_id` = " + lbl_QuoteId.getText() +
//                    "JOIN `room` " +
//                    "ON quote.project_id=room.project_id ");

//            while (quoteAndRoomTablesRS.next()) {
//
//            }

            Stage stage = new Stage();
            stage.setTitle("Leave a Review: ");
            Scene scene = new Scene(root2, 1100 , 450);
            scene.getStylesheets().add(Main.class.getResource("css/bootstrapfx.css").toExternalForm());
            scene.getStylesheets().add(Main.class.getResource("css/global.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
