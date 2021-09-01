package quotedecor.company_quotesTab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import quotedecor.config.DB;
import quotedecor.controllers.CompanyHomeCtrl;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CompanyQuotesTabAccordionCtrl implements Initializable {

    @FXML
    Accordion accordion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DB db = new DB();
        String companyID = Session.company.getEmail();
        System.out.println("In Quotes Tab Acc Cttrl");
        ResultSet rs_customer2 = db.query("SELECT * " +
                                                    "FROM `customer` " +
                                                    "LEFT JOIN `project` " +
                                                    "ON project.customer_id=customer.email " +
                                                    "RIGHT JOIN `quote` " +
                                                    "ON project.project_id=quote.project_id ");

        try {
            // Filling Label in each accordion for each project
            while (rs_customer2.next()) {

                String companyIdrs = rs_customer2.getString("company_id");

                if(companyIdrs.equals(companyID)) {

                    int projectId = rs_customer2.getInt("project_id");
                    String projectName = rs_customer2.getString("project_name");
                    String customerId = rs_customer2.getString("customer_id");
                    //                System.out.println(projectId + "\t" + projectName + "\t" + customerId);
                    String customerAddress = rs_customer2.getString("address");
                    double price = rs_customer2.getDouble("price");
                    int quoteId = rs_customer2.getInt("quote_id");
                    boolean customerAccepted = rs_customer2.getBoolean("customer_accepted");
                    boolean companyAccepted = rs_customer2.getBoolean("company_confirmed");

                    // Counting Number of ROOMS for the projects
                    ResultSet roomsRS = db.query("SELECT * " +
                            "FROM `room` " +
                            "WHERE `project_id` = " + projectId);
                    int roomCount = 0;
                    while (roomsRS.next()) {
//                    System.out.println(roomsRS + "Num of Rooms!!!!!!!!!!!!!!!!!!!");
                        roomCount++;
                    }

//                    String customer_id = rs_customer2.getString("customer_id");

                    if (companyIdrs.equals(companyID) && customerAccepted && !companyAccepted) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPANY_QUOTES_TITLE_PANE));
                        TitledPane quotesTitlePane = (TitledPane) loader.load();
                        CompanyQuotesTabTitlePaneCtrl companyQuotesTabTitlePaneCtrl = loader.getController();
                        quotesTitlePane.setText("Project ID: " + projectId);
//                        companyQuotesTabTitlePaneCtrl.setLbl_projectID(String.valueOf(projectId));
                        companyQuotesTabTitlePaneCtrl.setLbl_QuoteId(String.valueOf(quoteId));
                        companyQuotesTabTitlePaneCtrl.setLbl_projectName(projectName);
                        companyQuotesTabTitlePaneCtrl.setLbl_customerID(customerId);
                        companyQuotesTabTitlePaneCtrl.setLbl_Address(customerAddress);
                        companyQuotesTabTitlePaneCtrl.setLbl_NoOfRooms(String.valueOf(roomCount));
                        companyQuotesTabTitlePaneCtrl.setLbl_Price(String.valueOf(price));

                        accordion.getPanes().add(quotesTitlePane);
                    }

                }

            }

            if (accordion.getPanes().size() == 0) {
                System.out.println("Accordion size is equal to zero!!!!!!!!!!!!!!!!!!!!!!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPANY_QUOTES_TITLE_PANE));
                TitledPane quotesTitlePane = (TitledPane) loader.load();
                CompanyQuotesTabTitlePaneCtrl companyQuotesTabTitlePaneCtrl = loader.getController();
                quotesTitlePane.setText("");
                quotesTitlePane.setStyle("-fx-text-fill: red");
                quotesTitlePane.setMaxWidth(1145);
                companyQuotesTabTitlePaneCtrl.removeLbl_ConfirmedProject();
                companyQuotesTabTitlePaneCtrl.setLbl_NoQuotesMessage("CURRENTLY NO QUOTES TO REVIEW....");

                accordion.getPanes().add(quotesTitlePane);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

}
