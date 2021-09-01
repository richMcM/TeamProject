package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyHomeCtrl implements Initializable {

    @FXML
    private Tab tabProfile, tabLabour, tabProjects, tabQuotes, tabReviews, tabCompletedProjects;

    @FXML
    private Label lblWelcome;
    @FXML
    private Button btnAvailable, btnBusy;
    @FXML
    private TextField companyNametf, contactsNametf, apartmentNotf, addresstf, towntf, countytf, emailtf, passwordtf;

    @FXML
    private Button btnEdit;
    @FXML
    private Button updateSaveBtn;

    int selectionChangeCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Session.statusCompany() != null){
            lblWelcome.setText("Welcome "+Session.company.getCompanyName()+"!");
            if(Session.company.isAvailable()){
                btnAvailable.setStyle("-fx-background-color: 'green';");
                btnAvailable.setTextFill(Paint.valueOf("white"));
                btnBusy.setStyle("-fx-background-color: 'white';");
                btnBusy.setTextFill(Paint.valueOf("black"));
            }
            else{
                btnAvailable.setStyle("-fx-background-color: 'white';");
                btnAvailable.setTextFill(Paint.valueOf("black"));
                btnBusy.setStyle("-fx-background-color: 'orange';");
                btnBusy.setTextFill(Paint.valueOf("white"));
            }
        }
    }
    public void setViewProfileContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPANY_PROFILE));
        AnchorPane paneProfile = (AnchorPane) loader.load();
        tabProfile.setContent(paneProfile);
    }
    public void setLabourContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.LABOUR_COSTS));
        AnchorPane paneProfile = (AnchorPane) loader.load();
        tabLabour.setContent(paneProfile);
    }
    public void setTabQuotesContent() throws IOException {

//        if (selectionChangeCount % 2 == 0) {
//            selectionChangeCount++;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPANY_QUOTES_TAB_ACCORDION));
            ScrollPane paneQuotes = (ScrollPane) loader.load();
//        AnchorPane paneQuotes = (AnchorPane) loader.load();
            System.out.println(selectionChangeCount);
            tabQuotes.setContent(paneQuotes);

//        }
    }
    public void setProjectsContent() throws IOException {
//        if (selectionChangeCount % 2 == 0) {
        selectionChangeCount++;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPANY_PROJECT_TAB_ACCORDION));
        ScrollPane paneProjects = (ScrollPane) loader.load();
        //        paneBuildProject.setId(String.valueOf(paneBuildProject));
        System.out.println(selectionChangeCount);
        tabProjects.setContent(paneProjects);
//        RoomCtrl roomCtrl = loader.<RoomCtrl>getController();
//        roomCtrl.setLblRoom("ROOM: "+roomNo++);
//        }
    }
    public void setReviewsContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.REVIEWS_LIST));
        ScrollPane paneReviews = (ScrollPane) loader.load();
        tabReviews.setContent(paneReviews);
    }

    public void setCompletedProjectsContent(Event event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.COMPLETED_PROJECT_TAB_ACCORDION));
        ScrollPane paneCompletedProjects = (ScrollPane) loader.load();
        tabCompletedProjects.setContent(paneCompletedProjects);
    }

    public void doLogout(ActionEvent actionEvent) {
        try {
            Parent parent =  FXMLLoader.load(getClass().getResource(URLS.LOGIN));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
            Session.companyDestroy();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void markAvailable(){
        Session.company.setAvailable(true);
        Session.company.update();
        btnAvailable.setStyle("-fx-background-color: 'green';");
        btnAvailable.setTextFill(Paint.valueOf("white"));
        btnBusy.setStyle("-fx-background-color: 'white';");
        btnBusy.setTextFill(Paint.valueOf("black"));
    }
    public void markBusy(){
        Session.company.setAvailable(false);
        Session.company.update();
        btnAvailable.setStyle("-fx-background-color: 'white';");
        btnAvailable.setTextFill(Paint.valueOf("black"));
        btnBusy.setStyle("-fx-background-color: 'orange';");
        btnBusy.setTextFill(Paint.valueOf("white"));
    }

}
