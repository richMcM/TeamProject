package quotedecor.completedProjectTab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import quotedecor.config.DB;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CompCompletedProjectsTabAccordionCtrl implements Initializable {

    @FXML
    Accordion accordion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DB db = new DB();
        String companyID = Session.company.getEmail();

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
                boolean isConfirmed = rs_customer2.getBoolean("company_confirmed");
                boolean projectStatus = rs_customer2.getBoolean("project_status");
                System.out.println("Project COMPLETE Status !!!!!!!!!!!!!!!!  =  " + projectStatus);

                if (companyIdrs.equals(companyID) && isConfirmed == true && projectStatus == true) {

                    int projectId = rs_customer2.getInt("project_id");
                    String projectName = rs_customer2.getString("project_name");
                    String customerId = rs_customer2.getString("customer_id");
//                  System.out.println(projectId + "\t" + projectName + "\t" + customerId);
                    String customerAddress = rs_customer2.getString("address");
                    double price = rs_customer2.getDouble("price");

                    ResultSet roomsRS = db.query("SELECT * " +
                            "FROM `room` " +
                            "WHERE `project_id` = " + projectId);
                    int roomCount = 0;

                    while (roomsRS.next()) {
//                    System.out.println(roomsRS + "Num of Rooms!!!!!!!!!!!!!!!!!!!");
                        roomCount++;
                    }

                    if (companyIdrs.equals(companyID) && isConfirmed == true && projectStatus == true) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPLETED_PROJECT_TITLE_PANE));
                        TitledPane projectTitlePane = (TitledPane) loader.load();
                        CompCompletedProjectsTitlePaneCtrl CompCompletedProjectsTitlePaneCtrl = loader.getController();
                        projectTitlePane.setText("Project ID: " + projectId);
                        //CompanyProjectTitlePaneCtrl.setLbl_projectID(String.valueOf(projectId));
                        CompCompletedProjectsTitlePaneCtrl.setLbl_customerID(customerId);
                        CompCompletedProjectsTitlePaneCtrl.setLbl_projectName(projectName);
                        CompCompletedProjectsTitlePaneCtrl.setLbl_Address(customerAddress);
                        CompCompletedProjectsTitlePaneCtrl.setLbl_NoOfRooms(String.valueOf(roomCount));
                        CompCompletedProjectsTitlePaneCtrl.setLbl_Price(String.valueOf(price));

                        accordion.getPanes().add(projectTitlePane);
                    }

                }

            }

            if (accordion.getPanes().size() == 0) {
                System.out.println("Accordion size is equal to zero!!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPLETED_PROJECT_TITLE_PANE));
                TitledPane projectTitlePane = (TitledPane) loader.load();
                CompCompletedProjectsTitlePaneCtrl CompCompletedProjectsTitlePaneCtrl = loader.getController();
                projectTitlePane.setText("");
                CompCompletedProjectsTitlePaneCtrl.removeLbl_ConfirmedProject();
                CompCompletedProjectsTitlePaneCtrl.setLbl_NoProjectsMessage("CURRENTLY NO COMPLETED PROJECTS....");
                projectTitlePane.setStyle("-fx-text-fill: red");

                accordion.getPanes().add(projectTitlePane);
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
