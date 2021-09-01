package quotedecor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quotedecor.config.LAYOUT;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerHomeCtrl implements Initializable {

    @FXML
    private Label lblWelcome;

    @FXML
    private Tab tabProfile, tabViewCompanies, tabQuotes, tabProjects, tabBuildProject, tabReviews;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if(Session.statusCompany() != null){
            lblWelcome.setText("Welcome "+ Session.customer.getForename()+"!");
//        }
    }

    public void setViewProfileContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.CUSTOMER_PROFILE));
        AnchorPane paneProfile = (AnchorPane) loader.load();
        tabProfile.setContent(paneProfile);
    }
    public void setViewCompaniesContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.VIEW_COMPANIES_LIST));
        AnchorPane paneViewCompanies = (AnchorPane) loader.load();
        tabViewCompanies.setContent(paneViewCompanies);
    }
    public void setAcceptedQuotesContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.CUSTOMER_ACCEPTED_QUOTES_LIST));
        ScrollPane paneQuotes = (ScrollPane) loader.load();
        tabQuotes.setContent(paneQuotes);
    }
    public void setProjectsContent() throws IOException {
        Session.customer.retrieveProjects();
//        if(Session.customer.projects.size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.CUSTOMER_PROJECTS_LIST));
            ScrollPane paneProjects = (ScrollPane) loader.load();
            paneProjects.setPrefViewportWidth(LAYOUT.WIDTH);
            paneProjects.setPrefViewportHeight(LAYOUT.HEIGHT);
            tabProjects.setContent(paneProjects);
//        }
//        else{
//            System.out.println("in set project else");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.NO_PROJECTS_CREATED_MESSAGE));
//            AnchorPane ap = (AnchorPane) loader.load();
//            tabProjects.setContent(ap);
//        }
    }
    public void setBuildProjectContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.BUILD_PROJECT));
        AnchorPane paneBuildProject = (AnchorPane) loader.load();
        tabBuildProject.setContent(paneBuildProject);
    }
    public void setReviewsContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.REVIEWS_LIST));
        ScrollPane paneReviews = (ScrollPane) loader.load();
        tabReviews.setContent(paneReviews);
    }

    public void doLogout(ActionEvent actionEvent) {
        try {
            Parent parent =  FXMLLoader.load(getClass().getResource(URLS.LOGIN));
            Scene scene2 = new Scene(parent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(scene2);
            Session.customerDestroy();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

}
