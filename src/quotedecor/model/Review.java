package quotedecor.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Review extends AppObject {

    // Review properties
    private int reviewID, rating;
    private String userID, reviewerID, comment;
    private Timestamp timestamp;

    // default constructor
    public Review() {
        this.TABLE = "REVIEW";
    }
    // new review initialisation constructor
    public Review(String userID, String reviewerID) {
        this.rating = 0;
        this.userID = userID;
        this.reviewerID = reviewerID;
        this.comment = null;
        this.TABLE = "REVIEW";
    }
    // new review initialisation constructor
    public Review(String userID, String reviewerID, int rating, String comment) {
        this.rating = rating;
        this.userID = userID;
        this.reviewerID = reviewerID;
        this.comment = comment;
        this.TABLE = "REVIEW";
    }
    // read initialisation constructor
    public Review(ResultSet rs) throws SQLException {
        mapResultSet(rs);
        this.TABLE = "REVIEW";
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void create(){

        String sqlStmnt = String.format(
                "INSERT INTO review (" +
                        "`user_id`, " +
                        "`reviewer_id`, " +
                        "`rating`, " +
                        "`comment`" +
                        ") VALUES ('%s','%s', %s, '%s'); ",
                userID,
                reviewerID,
                rating,
                comment
        );
        this.setReviewID(DB.create(TABLE, sqlStmnt));
    }
    // read company record from DB and propagate its details to this properties
    @Override
    public void retrieve() throws SQLException {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM review " +
                "WHERE `review_id` = %s " +
                "LIMIT 1;",
                reviewID
        );
        DB.retrieve(this, sqlStmnt);
    }
    // write this company properties to company record in DB
    @Override
    public void update() {
//        String sqlStmnt = String.format(
//                "UPDATE `review` " +
//                "SET " +
//                        "`rating`= %s ," +
//                        "`contacts_name`= '%s' ," +
//                        "`comment`= '%s' " +
//                "WHERE 'review_id'= %s;",
//                rating,
//                comment,
//                reviewID
//        );
        // TODO implement review update???
    }
    // delete this company record from company table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM `review` " +
                "WHERE 'review_id' = '%s';",
                reviewID
        );
        DB.delete(TABLE, sqlStmnt);
    }
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException{
        this.reviewID = rs.getInt("review_id");
        this.userID = rs.getString("user_id");
        this.reviewerID = rs.getString("reviewer_id");
        this.rating = rs.getInt("rating");
        this.comment = rs.getString("comment");
        this.timestamp = rs.getTimestamp("date");
    }
    @Override
    public String toString() {
        return "Review{" +
                "\n\treviewID: " + reviewID +
                ",\n\trating: " + rating +
                ",\n\tuserID: '" + userID + '\'' +
                ",\n\treviewerID: '" + reviewerID + '\'' +
                ",\n\tcomment: '" + comment + '\'' +
                ",\n\tdate: '" + timestamp + '\'' +
                "\n}";
    }
}
