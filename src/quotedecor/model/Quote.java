package quotedecor.model;

import quotedecor.quotesystem.KnownVariables;
import quotedecor.quotesystem.RoomQuoteItem;
import quotedecor.sessions.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Quote extends AppObject {

    // Quote properties
    private int quoteID, projectID;
    private String companyID, companyName, customerID;
    private double totalCeilingPaintLtr, totalWallPaintLtr;
    private double totalCeilingPaintCost, totalWallPaintCost, totalMaterialsCost;
    private double totalCeilingLabour, totalWallLabour, totalLabourCost, total;
    private boolean customerAccepted, companyConfirmed, customerReviewed, companyReviewed;
    private Timestamp timestamp;

    // composites
    private ArrayList<RoomQuoteItem> roomQuoteItems;
    private KnownVariables knownVar;

    public Quote() {
        this.quoteID = 0;
        this.projectID = 0;
        this.companyID = null;
        this.companyName = null;
        this.customerID = null;
        this.totalCeilingPaintLtr = 0;
        this.totalWallPaintLtr = 0;
        this.totalCeilingPaintCost = 0;
        this.totalWallPaintCost = 0;
        this.totalMaterialsCost = 0;
        this.totalCeilingLabour = 0;
        this.totalWallLabour = 0;
        this.totalLabourCost = 0;
        this.total = 0;
        this.customerAccepted = false;
        this.companyConfirmed = false;
        this.customerReviewed = false;
        this.companyReviewed = false;
        this.TABLE = "QUOTE";  // for DB-OPS console logging
        this.roomQuoteItems = new ArrayList<>();
        this.knownVar = new KnownVariables();
    }

    // read initialisation constructor
    public Quote(ResultSet rs) throws SQLException {
        mapResultSet(rs);
        this.TABLE = "QUOTE";  // for DB-OPS console logging
        this.roomQuoteItems = new ArrayList<>();
    }

    public Quote(int quoteID, int projectID, String companyID, String customerID, double total, boolean customerAccepted, boolean companyConfirmed, boolean customerReviewed, boolean companyReviewed) {
        this.quoteID = quoteID;
        this.projectID = projectID;
        this.companyID = companyID;
        this.customerID = customerID;
        this.total = total;
        this.customerAccepted = customerAccepted;
        this.companyConfirmed = companyConfirmed;
        this.customerReviewed = customerReviewed;
        this.companyReviewed = companyReviewed;
        this.roomQuoteItems = new ArrayList<>();
    }

    public int getQuoteID() {
        return quoteID;
    }
    public void setQuoteID(int quoteID) {
        this.quoteID = quoteID;
    }
    public int getProjectID() {
        return projectID;
    }
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    public String getCompanyID() {
        return companyID;
    }
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
    public String getCompanyName() {

        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public double getTotal() {
        return total;
    }
    public void incPrice(double price) {
        this.total += price;
    }
    public double getTotalCeilingPaintLtr() {
        return totalCeilingPaintLtr;
    }
    public void incTotalCeilingPaintRequiredLtr(double totalCeilingPaintRequiredLtr) {
        this.totalCeilingPaintLtr += totalCeilingPaintRequiredLtr;
    }
    public double getTotalCeilingPaintCost() {
        return totalCeilingPaintCost;
    }
    public double getTotalWallPaintCost() {
        return totalWallPaintCost;
    }
    public double getTotalMaterialsCost() {
        return totalMaterialsCost;
    }
    public double getTotalLabourCost() {
        return totalLabourCost;
    }
    public double getTotalCeilingLabour() {
        return totalCeilingLabour;
    }
    public void incTotalCeilingLabour(double totalCeilingLabour) {
        this.totalCeilingLabour += totalCeilingLabour;
    }
    public void incTotalCeilingPaintCost(double totalCeilingPaintCost) {
        this.totalCeilingLabour += totalCeilingPaintCost;
    }
    public double getTotalWallPaintLtr() {
        return totalWallPaintLtr;
    }
    public void incTotalWallPaintRequiredLtr(double totalWallPaintRequiredLtr) {
        this.totalWallPaintLtr += totalWallPaintRequiredLtr;
    }
    public double getTotalWallLabour() {
        return totalWallLabour;
    }
    public void incTotalWallLabour(double totalWallLabour) {
        this.totalWallLabour += totalWallLabour;
    }
    public boolean isCustomerAccepted() {
        return customerAccepted;
    }
    public void setCustomerAccepted(boolean customerAccepted) {
        this.customerAccepted = customerAccepted;
    }
    public boolean isCompanyConfirmed() {
        return companyConfirmed;
    }
    public void setCompanyConfirmed(boolean companyConfirmed) {
        this.companyConfirmed = companyConfirmed;
    }
    public boolean isCustomerReviewed() {
        return customerReviewed;
    }
    public void setCustomerReviewed(boolean customerReviewed) {
        this.customerReviewed = customerReviewed;
    }
    public boolean isCompanyReviewed() {
        return companyReviewed;
    }
    public void setCompanyReviewed(boolean companyReviewed) {
        this.companyReviewed = companyReviewed;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ArrayList<RoomQuoteItem> getRoomQuoteItems() {
        return roomQuoteItems;
    }
    public void addQuoteItem(RoomQuoteItem item) {
        this.roomQuoteItems.add(item);
    }
    public void computeTotalPaintCosts(){
        // total ceiling paint cost
        if(totalCeilingPaintLtr <= 35)
            totalCeilingPaintCost = knownVar.costWhite5ltr;
        else if(totalCeilingPaintLtr > 35 && totalCeilingPaintLtr <= 70)
            totalCeilingPaintCost = knownVar.costWhite10ltr;
        else if(totalCeilingPaintLtr > 70 && totalCeilingPaintLtr <= 105)
            totalCeilingPaintCost = knownVar.costWhite10ltr + knownVar.costWhite5ltr;
        else if(totalCeilingPaintLtr > 105 && totalCeilingPaintLtr <= 140)
            totalCeilingPaintCost = knownVar.costWhite10ltr * 2;
        else if(totalCeilingPaintLtr > 140 && totalCeilingPaintLtr <= 175)
            totalCeilingPaintCost = ((knownVar.costWhite10ltr * 2) + knownVar.costWhite5ltr);
        else if(totalCeilingPaintLtr > 175 && totalCeilingPaintLtr <= 210)
            totalCeilingPaintCost = knownVar.costWhite10ltr * 4;
        totalCeilingPaintCost = round(totalCeilingPaintCost);
        totalCeilingPaintLtr = round(totalCeilingPaintLtr);
        /* ---------------------------------------------------------------------------------------------------------- */
        // total wall paint cost
        if(totalWallPaintLtr <= 35)
            totalWallPaintCost = knownVar.costColor5ltr;
        else if(totalWallPaintLtr > 35 && totalWallPaintLtr <= 70)
            totalWallPaintCost = knownVar.costColor10ltr;
        else if(totalWallPaintLtr > 70 && totalWallPaintLtr <= 105)
            totalWallPaintCost = knownVar.costColor10ltr + knownVar.costColor5ltr;
        else if(totalWallPaintLtr > 105 && totalWallPaintLtr <= 140)
            totalWallPaintCost = knownVar.costColor10ltr * 2;
        else if(totalWallPaintLtr > 140 && totalWallPaintLtr <= 175)
            totalWallPaintCost = ((knownVar.costColor10ltr * 2) + knownVar.costColor5ltr);
        else if(totalWallPaintLtr > 175 && totalWallPaintLtr <= 210)
            totalWallPaintCost = knownVar.costColor10ltr * 4;
        totalWallPaintCost = round(totalWallPaintCost);
        totalWallPaintLtr = round(totalWallPaintLtr);
    }
    public void computeTotalQuotePrice(){
        computeTotalPaintCosts();
        totalMaterialsCost = totalCeilingPaintCost + totalWallPaintCost;
        // compute total labour costs
        for (RoomQuoteItem q: roomQuoteItems) {
            incTotalCeilingLabour(q.getCeilingLabourCost());
            incTotalWallLabour(q.getWallsLabourCost());
        }
        totalLabourCost = totalCeilingLabour + totalWallLabour;
        total = totalMaterialsCost + totalLabourCost;
    }
    public double round(double n){
        DecimalFormat format = new DecimalFormat("##.00");
        return Double.parseDouble(format.format(n));
    }

//    @Override
//    public void create() {
//        String sqlStmnt = String.format(
//                "INSERT INTO `quote`(" +
//                        "`project_id`, " +
//                        "`company_id`, " +
//                        "`company_name`, " +
//                        "`customer_id`, " +
//                        "`price`, " +
//                        "`customer_accepted`, " +
//                        "`company_confirmed`, " +
//                        "`customer_reviewed`, " +
//                        "`company_reviewed`" +
//                ") VALUES (%s, '%s', '%s', '%s', %s, %s, %s, %s, %s); ",
//                projectID,
//                companyID,
//                companyName,
//                customerID,
//                total,
//                customerAccepted,
//                companyConfirmed,
//                customerReviewed,
//                companyReviewed
//        );
//        this.setQuoteID(DB.create(TABLE, sqlStmnt));
//    }

    @Override
    public void create() {
        String sqlStmnt = String.format(
                "INSERT INTO `quote`(" +
                        "`project_id`, " +
                        "`company_id`, " +
//                        "`company_name`, " +
                        "`customer_id`, " +
                        "`price`, " +
                        "`customer_accepted`, " +
                        "`company_confirmed`, " +
                        "`customer_reviewed`, " +
                        "`company_reviewed`" +
                        ") VALUES (%s, '%s', '%s', %s, %s, %s, %s, %s); ",
                projectID,
                companyID,
                customerID,
                total,
                true,
                companyConfirmed,
                customerReviewed,
                companyReviewed
        );
        this.setQuoteID(DB.create(TABLE, sqlStmnt));
    }

    @Override
    public void retrieve() {

        String sqlStmnt = String.format(
                "SELECT * FROM quote " +
                "WHERE quote_id = %s " +
                "LIMIT 1;",
                quoteID
        );
        DB.retrieve(this, sqlStmnt);

    }
    @Override
    public void update() {
        String sqlStmnt = String.format(
                "UPDATE `quote` SET " +
                    "`price`= %s, " +
                    "`customer_accepted`= %s, " +
                    "`company_confirmed` = %s, " +
                    "`customer_reviewed` = %s, " +
                    "`company_reviewed` = %s " +
                "WHERE quote_id = %s;",
                total,
                customerAccepted,
                companyConfirmed,
                customerReviewed,
                companyReviewed,
                quoteID
        );
        DB.update(TABLE, sqlStmnt);
    }
    @Override
    public void delete() {

        String sqlStmnt = String.format(
            "DELETE FROM quote " +
            "WHERE quote_id = %s;",
            quoteID
        );
        System.out.println(sqlStmnt);
        DB.delete(TABLE, sqlStmnt);
    }
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException {
        this.quoteID = rs.getInt("quote_id");
        this.projectID = rs.getInt("project_id");
        this.companyID = rs.getString("company_id");
//        this.companyName = rs.getString("company_name");
        this.customerID = rs.getString("customer_id");
        this.total = rs.getDouble("price");
        this.customerAccepted = rs.getBoolean("customer_accepted");
        this.companyConfirmed = rs.getBoolean("company_confirmed");
        this.customerReviewed = rs.getBoolean("customer_reviewed");
        this.companyReviewed = rs.getBoolean("company_reviewed");
        this.timestamp = rs.getTimestamp("date");
    }

    public void mapResultSet(ResultSet rs, ResultSet labCostRs) throws SQLException {
        this.quoteID = rs.getInt("quote_id");
        this.projectID = rs.getInt("project_id");
        this.companyID = rs.getString("company_id");
//        this.companyName = rs.getString("company_name");
        this.customerID = rs.getString("customer_id");
        this.total = rs.getDouble("price");
        this.customerAccepted = rs.getBoolean("customer_accepted");
        this.companyConfirmed = rs.getBoolean("company_confirmed");
        this.customerReviewed = rs.getBoolean("customer_reviewed");
        this.companyReviewed = rs.getBoolean("company_reviewed");
    }

    public String getQuoteBreakdown() {
        return "Quote{" +
                "\nDate: " + timestamp +
                "\nCompany: " + companyName +
                "\nCustomer: " + Session.customer.getForename()+" "+Session.customer.getSurname() +
                "\n==========================================" +
                "\nCeiling Paint: " + totalCeilingPaintLtr +" ltrs"+
                "\nWall Paint: " + totalWallPaintLtr +" ltrs"+
                "\nCeiling Paint Cost: €" + totalCeilingPaintCost +
                "\nWall Paint Cost: €" + totalWallPaintCost +
                "\n------------------------------------------" +
                "\nTotal Materials Costs: €" + totalMaterialsCost +
                "\n==========================================" +
                "\nCeiling Labour: €" + totalCeilingLabour +
                "\nWalls Labour: €" + totalWallLabour +
                "\n------------------------------------------" +
                "\nTotal Labour Costs: €" + totalLabourCost +
                "\n__________________________________________" +
                "\nQUOTE: €" + total +
                "\n==========================================" +
                "\nBreakdown: " + roomQuoteItems +
                "\n}";
    }

    @Override
    public String toString() {
        return "Quote{" +
                "\n\tquoteID: " + quoteID +
                "\n\tprojectID:" + projectID +
                "\n\tcompanyID:'" + companyID + '\'' +
                "\n\tcompanyName: '" + companyName + '\'' +
                "\n\tcustomerID: '" + customerID + '\'' +
                "\n\tcustomerAccepted: " + customerAccepted +
                "\n\tcompanyConfirmed: " + companyConfirmed +
                "\n\tcustomerReviewed: " + customerReviewed +
                "\n\tcompanyReviewed: " + companyReviewed +
                "\n\t=========================================" +
                "\n\tCeiling Paint: " + totalCeilingPaintLtr +" ltrs"+
                "\n\tWall Paint: " + totalWallPaintLtr +" ltrs"+
                "\n\tCeiling Paint Cost: €" + totalCeilingPaintCost +
                "\n\tWall Paint Cost: €" + totalWallPaintCost +
                "\n\t-----------------------------------------" +
                "\n\tTotal Materials Costs: €" + totalMaterialsCost +
                "\n\t=========================================" +
                "\n\tCeiling Labour: €" + totalCeilingLabour +
                "\n\tWalls Labour: €" + totalWallLabour +
                "\n\t-----------------------------------------" +
                "\n\tTotal Labour Costs: €" + totalLabourCost +
                "\n\t_________________________________________" +
                "\n\tQUOTE: €" + total +
                "\n\t=========================================" +
                "\n\tbreakdown: " + roomQuoteItems +
                "\n}";
    }
}
