package quotedecor.testers;

import quotedecor.model.Review;
import quotedecor.util.Console;

import java.sql.SQLException;

public class ReviewCRUDTester {


    public static void main(String [] args) throws SQLException {
        // simplest way of creating reviews in your code is to use the review class
        // in the model package as follows:
        // create new review
        Review review = new Review();
        // specify the user id of the user your are reviewing
        review.setUserID("customer@.com");
        // specify the user id of the user submitting the review
        review.setReviewerID("company@.com");
        // set / get the comment from the textfield and pass it to the setComment method
        review.setComment("Great customer.. swift payment and had rooms prepared for us to work! A+!");
        // set rating 1-5 => obtained from dropdown or whatever UI component
        review.setRating(5);
        // write the review to the DB
        review.create();
        review.retrieve();
        Console.print(review.toString());


    }
}
