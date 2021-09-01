package quotedecor.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Company extends User {

    // Company user properties
    private String companyName, contactsName;
    boolean available;

    // composites
    public LabourCosts labour;

    // default constructor
    public Company() {super();}

    public Company(String email){
        super(true);
        this.email = email;
        this.available = false;
        this.TABLE = "COMPANY";  // for DB console logging
        retrieveAll();
    }

    // login constructor
    public Company(ResultSet rs) throws SQLException{
        super(true);
        mapResultSet(rs);
        this.TABLE = "COMPANY";  // for DB-OPS console logging
        retrieveLabourCosts();
    }
    // create account constructor
    public Company(String email, String password, String address, String companyName, String contactsName) {
        super(email, password, address, true);
        this.companyName = companyName;
        this.contactsName = contactsName;
        this.available = false;
        this.TABLE = "COMPANY";  // for DB console logging
// create new company record in DB
        create();
// create new labour_costs record in DB
        labour = new LabourCosts(email);
    }
    // getters & setters
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getContactsName() {
        return contactsName;
    }
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /* ************************************************************************************************************** */
// DB OPERATIONS
// create new company record in company table
    @Override
    public void create(){

        String sqlStmnt = String.format(
                "INSERT INTO `company`(" +
                        "`email`, " +
                        "`password`, " +
                        "`company_name`, " +
                        "`contacts_name`, " +
                        "`address" +
                        "`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s'); ",
                email,
                password,
                companyName,
                contactsName,
                address,
                available
        );
        DB.createKnownKey(TABLE, sqlStmnt);
    }
    // read company record from DB and propagate its details to this properties
    @Override
    public void retrieve() {

        String sqlStmnt = String.format(
                "SELECT * " +
                        "FROM `company` " +
                        "WHERE `email` = '%s' " +
                        "LIMIT 1;",
                email
        );
        DB.retrieve(this, sqlStmnt);
    }
    // write this company properties to company record in DB
    @Override
    public void update() {

        String sqlStmnt = String.format(
                "UPDATE company " +
                        "SET " +
                        "password = '%s', " +
                        "company_name = '%s', " +
                        "contacts_name = '%s', " +
                        "address = '%s', " +
                        "available = %s " +
                        "WHERE email = '%s';",
                password,
                companyName,
                contactsName,
                address,
                available,
                email
        );
        DB.update(TABLE, sqlStmnt);
        System.out.println(sqlStmnt);
    }
    // delete this company record from company table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM `company` " +
                "WHERE 'email' = '%s';",
                email
        );
        DB.delete(TABLE, sqlStmnt);
    }
    // map the ResultSet properties to this properties
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException{
        this.email = rs.getString("email");
        this.password = rs.getString("password");
        this.companyName = rs.getString("company_name");
        this.contactsName = rs.getString("contacts_name");
        this.address = rs.getString("address");
        this.available = rs.getBoolean("available");
    }
    @Override
    public void retrieveReviews() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `review` " +
                "WHERE `user_id` = '%s' " +
                "ORDER BY date DESC;",
                email
        );
        this.reviews = DB.retrieveReviews(sqlStmnt);
    }
    @Override
    public void retrieveProjects() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `quote` " +
                "WHERE `company_id` = '%s';",
                email
        );
    }
    @Override
    public void retrieveAcceptedQuotes() {
        String sqlStmnt = String.format(
                "SELECT * " +
                        "FROM `quote` " +
                        "WHERE `company_id` = '%s';",
                email
        );
        this.quotes = DB.retrieveAcceptedQuotes(sqlStmnt);
    }
    public void retrieveLabourCosts() {
        String sqlStmnt = String.format(
                "SELECT * " +
                        "FROM `labour_costs` " +
                        "WHERE `company_id` = '%s';",
                email
        );
        DB.retrieveLabourCosts(this, sqlStmnt);
    }
    public void retrieveAll() {
        retrieve();
        retrieveLabourCosts();
        retrieveAcceptedQuotes();
        retrieveReviews();
    }
// DB OPERATIONS END
    /* ************************************************************************************************************** */

    @Override
    public String toString() {
        return "Company{" +
                " \n\temail: '" + email + '\'' +
                ", \n\tpassword: '" + password + '\'' +
                ", \n\taddress: '" + address + '\'' +
                " \n\tcompanyName: '" + companyName + '\'' +
                ", \n\tcontactsName: '" + contactsName + '\'' +
                ", \n\tisCompany: " + isCompany +
                ", \n\tlabour: " + labour +
                ", \n\tquotes: " + quotes +
                "\n}";
    }

}
