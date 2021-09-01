package quotedecor.model;

import quotedecor.quotesystem.QuoteComputer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends User {

    // Company user properties
    private String forename, surname;

    // composites
    private Project project;  // single project instance for new project creation

    // default constructor
    public Customer() {super();}
    public Customer(String email){
        super(false);
        this.email = email;
        this.TABLE = "CUSTOMER";  // for DB console logging
        retrieve();
    }
    // login constructor
    public Customer(ResultSet rs) throws SQLException{
        super(false);
        mapResultSet(rs);
        this.TABLE = "CUSTOMER";  // for DB console logging
    }
    // create account constructor
    public Customer(String email, String password, String address, String forename, String surname) {
        super(email, password, address, true);
        this.forename = forename;
        this.surname = surname;
        this.TABLE = "CUSTOMER";  // for DB console logging
        // create new customer record in customer table in DB
        create();
    }

    // getters & setters
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Project getProject() {
        return project;
    }

    /* ************************************************************************************************************** */
    // DB OPERATIONS
    // create new customer record in customer table
    @Override
    public void create(){

        String sqlStmnt = String.format(
                "INSERT INTO `customer`(" +
                        "`email`, " +
                        "`password`, " +
                        "`forename`, " +
                        "`surname`, " +
                        "`address" +
                "`) VALUES ('%s', '%s', '%s', '%s', '%s'); ",
                email,
                password,
                forename,
                surname,
                address
        );
        DB.createKnownKey(TABLE, sqlStmnt);
    }
    // read customer record from DB and map its details to this properties
    @Override
    public void retrieve() {

        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `customer` " +
                "WHERE `email` = '%s' " +
                "LIMIT 1;",
                email
        );
        DB.retrieve(this, sqlStmnt);
    }
    // update this customer properties to customer record in DB
    @Override
    public void update() {

        String sqlStmnt = String.format(
                "UPDATE customer " +
                "SET " +
                    "password = '%s'," +
                    "forename = '%s'," +
                    "surname = '%s'," +
                    "address = '%s' " +
                "WHERE email = '%s';",
                password,
                forename,
                surname,
                address,
                email
        );
        DB.update(TABLE, sqlStmnt);
    }
    // delete this customer record from customer table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM `customer` " +
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
        this.forename = rs.getString("forename");
        this.surname = rs.getString("surname");
        this.address = rs.getString("address");
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
    public void retrieveAcceptedQuotes() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM quote " +
                "WHERE customer_id = '%s';",
                email
        );
        this.quotes = DB.retrieveAcceptedQuotes(sqlStmnt);
    }
    public void retrieveProjects() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `project` " +
                "WHERE `customer_id` = '%s';",
                email
        );
        this.projects = DB.retrieveProjects(email);
    }
    public ArrayList<Company> retrieveCompanies() {
        String sqlStmnt =
                "SELECT * " +
                "FROM `company`;";
        return DB.retrieveCompanies(sqlStmnt);
    }
    public void retrieveAll() {
        retrieve();
        retrieveAcceptedQuotes();
        retrieveReviews();
    }
	// DB OPERATIONS END
	/* ************************************************************************************************************** */
    public void newProject(String projectName){
//        if (project != null) {
            // create new project
            project = new Project();
            project.setProjectName(projectName);
            // set which customer it will belongs to
            project.setCustomerID(this.getEmail());
//        }
    }
//    public void addNewRoom(String roomTitle, double width, double length, String wallPrep, boolean ceiling, String wallWork){
    public void addNewRoom(String roomTitle, double width, double length, boolean ceiling){
        if (project != null)
            project.rooms.add(new Room(
                    roomTitle,
                    width,
                    length,
                    ceiling
            ));
    }
    //    public void addNewDoor(String type, boolean paned, String currentState, String desiredFinish){
//        if (project != null)
//            project.doors.add(new Door(
//                    type,
//                    paned,
//                    currentState,
//                    desiredFinish
//            ));
//    }
    public void saveNewProject(){
//        if (project != null) {
            project.create();
            retrieveProjects();
//            project = null;
//        computeQuote();
//        }
    }
    public void cancelNewProject(){
        project = null;
    }

    public void computeQuote(){
        QuoteComputer qc = new QuoteComputer(project);
        qc.computeQuotes();
    }
    @Override
    public String toString() {
        return "Customer{" +
                "\n\temail: '" + email + '\'' +
                ",\n\tpassword: '" + password + '\'' +
                ",\n\taddress: '" + address + '\'' +
                ",\n\tforename: '" + forename + '\'' +
                ",\n\tsurname: '" + surname + '\'' +
                ",\n\tprojects: " + projects +
                "\n}";
    }
	
}
