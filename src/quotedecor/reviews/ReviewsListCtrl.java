package quotedecor.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import quotedecor.model.Review;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReviewsListCtrl implements Initializable {

    @FXML
    private VBox vbReviewList;

    private quotedecor.config.DB DB = new quotedecor.config.DB();

    private ArrayList<Review> reviews = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if (Session.isCompanyUser()) {
            Session.company.retrieveReviews();
            reviews = Session.company.getReviews();
        } else if (Session.isCustomerUser()) {
            Session.customer.retrieveReviews();
            reviews = Session.customer.getReviews();
        }

        for (Review review : reviews) {
            try {
                // for each customer related review => add review list item to vbReviewList
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.REVIEWS_LIST_ITEM));
                GridPane reviewListItem = (GridPane) loader.load();

                // get each review (GridPane) controller
                ReviewsListItemCtrl reviewListItemCtrl = loader.<ReviewsListItemCtrl>getController();
                reviewListItemCtrl.getLblDate().setText(""+review.getTimestamp());
                if (Session.isCompanyUser()) {
                    reviewListItemCtrl.getLblUsersName().setText(getCustomersName(review.getReviewerID()));
                } else if (Session.isCustomerUser()) {
                    reviewListItemCtrl.getLblUsersName().setText(getCompanyName(review.getReviewerID()));
                }
                reviewListItemCtrl.getTxtaFeedback().setText(review.getComment());
                reviewListItemCtrl.getLblRating().setText("" + review.getRating());

                if (review.getRating() < 2) {
                    reviewListItemCtrl.getLblRating().setStyle("-fx-background-color: red;");
                } else if (review.getRating() >= 2 && review.getRating() < 4) {
                    reviewListItemCtrl.getLblRating().setStyle("-fx-background-color: orange;");
                }
                else if (review.getRating() >= 4) {
                    reviewListItemCtrl.getLblRating().setStyle("-fx-background-color: green;");
                }

                // add review to vbReviewList
                vbReviewList.getChildren().add(reviewListItem);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String getCustomersName(String userID) throws SQLException {

        ResultSet rs = DB.query("SELECT * FROM customer WHERE email = '" + userID + "' LIMIT 1;");
        if(rs.next())
            return rs.getString("forename") +" "+ rs.getString("surname");
        else return "not found";
    }
    private String getCompanyName(String userID) throws SQLException {

        ResultSet rs = DB.query("SELECT * FROM company WHERE email = '" + userID + "' LIMIT 1;");
        rs.next();
        return rs.getString("company_name");
    }
}
