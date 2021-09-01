package quotedecor.model;

import quotedecor.util.InputProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Project extends AppObject {

    // Project properties
    private int projectID;
    private String projectName, customerID;
    private boolean projectComplete;

    // composites
    public ArrayList<Room>rooms;
//    public ArrayList<Door>doors;

    // default constructor
    public Project() {
        this.TABLE = "PROJECT";  // for DB console logging
        this.projectComplete = false;
        rooms = new ArrayList<>();
//        doors = new ArrayList<>();
    }

    // read initialisation constructor
    public Project(ResultSet rs) throws SQLException{
        mapResultSet(rs);
        this.TABLE = "PROJECT";
        this.projectComplete = false;
        rooms = new ArrayList<>();
//        doors = new ArrayList<>();
    }
    // new project initialisation constructor
    public Project(String customerID, ArrayList<Room> rooms) {
        this.customerID = customerID;
        this.rooms = rooms;
        this.projectComplete = false;
        this.TABLE = "PROJECT";  // for DB console logging
    }
//    // new project initialisation constructor
//    public Project(String customerID, ArrayList<Room> rooms, ArrayList<Door> doors) {
//        this.customerID = customerID;
//        this.rooms = rooms;
//        this.doors = doors;
//    }

    public int getProjectID() {
        return projectID;
    }
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = InputProcessor.strSanitise(projectName);
    }
    public String getCustomerID() {
        return customerID;
    }
    public boolean isProjectComplete() {
        return projectComplete;
    }
    public void setProjectComplete() {
        this.projectComplete = true;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

//    public ArrayList<Door> getDoors() {
//        return doors;
//    }

//    public void setDoors(ArrayList<Door> doors) {
//        this.doors = doors;
//    }
    // create new project record in project table
    @Override
    public void create(){

        String sqlStmnt = String.format(
                "INSERT INTO " +
                "project (project_name, customer_id, project_status) " +
                "VALUES ('%s', '%s', %s);",
                projectName,
                customerID,
                isProjectComplete()
        );
        this.setProjectID(DB.create(TABLE, sqlStmnt));
        // for each room
        for (Room room: rooms) {
            // set which project it belongs to => this project
            room.setProjectID(projectID);
            // create a new record of it in room table
            room.create();
        }
        // for each door
//        for (Door door: doors) {
//            // set which project it belongs to => this project
//            door.setProjectID(projectID);
//            // create a new record of it in door table
//            door.create();
//        }
    }
    // retrieve project record from DB and map its details to this properties
    @Override
    public void retrieve() {

        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `project` " +
                "WHERE `project_id` = %s " +
                "LIMIT 1;",
                projectID
        );
        DB.retrieve(this, sqlStmnt);

    }
    // update this customer properties to customer record in DB
    @Override
    public void update() {

        String sqlStmnt = String.format(
                "UPDATE project\n" +
                "SET\n" +
                    "project_name = '%s', " +
                    "customer_id = '%s'\n" +
                    "project_status = '%s'\n" +
                    "WHERE '%s';",
                projectName,
                customerID,
                isProjectComplete(),
                projectID
        );
        DB.update(TABLE, sqlStmnt);
    }
    // delete this customer record from customer table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM\n" +
                "  project\n" +
                "WHERE\n" +
                "  project_id = %s;",
                projectID
        );
        System.out.println(sqlStmnt);
        DB.delete(TABLE, sqlStmnt);
    }
    // map the ResultSet properties to this properties
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException {
        this.projectID = rs.getInt("project_id");
        this.projectName = rs.getString("project_name");
        this.customerID = rs.getString("customer_id");
        this.projectComplete = rs.getBoolean("project_status");
    }

    @Override
    public String toString() {
        return  "\n\t\tprojectID: " + projectID +
                "\n\t\tprojectName: " + projectName +
                ",\n\t\tcustomerID: '" + customerID + '\'' +
                ",\n\t\trooms: " + rooms +
//                ",\n\t\tdoors: " + doors +
                "\n\t}";
    }
}
