package quotedecor.model;

import quotedecor.util.InputProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Room  extends AppObject {

    // Room properties
    private int roomID, projectID;
    private String roomTitle, wallPrep,wallWork;
    private double width, length;
    private boolean ceiling;

    // default constructor
    public Room() {}

    // DB read ResultSet initialisation constructor
    public Room(ResultSet rs) throws SQLException{
        mapResultSet(rs);
        this.TABLE = "ROOM";  // for DB console logging
    }
    // read initialisation constructor
    public Room(int roomID){
        this.roomID = roomID;
        this.TABLE = "ROOM";  // for DB console logging
    }
    // V1: new room initialisation constructor
    public Room(String roomTitle, double width, double length, boolean ceiling) {
        this.roomTitle = InputProcessor.strSanitise(roomTitle);
        this.width = width;
        this.length = length;
        this.ceiling = ceiling;
        this.TABLE = "ROOM";  // for DB console logging
    }
    // V2: new room initialisation constructor
    public Room(String roomTitle, double width, double length, String wallPrep, boolean ceiling, String wallWork) {
        this.roomTitle = InputProcessor.strSanitise(roomTitle);
        this.wallPrep = wallPrep;
        this.wallWork = wallWork;
        this.width = width;
        this.length = length;
        this.ceiling = ceiling;
        this.TABLE = "ROOM";  // for DB console logging
    }

    public int getRoomID() {
        return roomID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public int getProjectID() {
        return projectID;
    }
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    public String getRoomTitle() {
        return roomTitle;
    }
    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }
    public String getWallWork() {
        return wallWork;
    }
    public void setWallWork(String wallWork) {
        this.wallWork = wallWork;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public String getWallPrep() {
        return wallPrep;
    }
    public void setWallPrep(String wallPrep) {
        this.wallPrep = wallPrep;
    }
    public boolean isCeiling() {
        return ceiling;
    }
    public void setCeiling(boolean ceiling) {
        this.ceiling = ceiling;
    }

    @Override
    public void create(){
        String sqlStmnt = String.format(
                "INSERT INTO " +
                "`room`(" +
                        "`project_id`, " +
                        "`title`, " +
                        "`width`, " +
                        "`length`, " +
                        "`wall_prep`, " +
                        "`ceiling`, " +
                        "`wall_work`" +
                ") VALUES (%s, '%s', %s, %s, '%s', %s, '%s'); ",
                projectID,
                roomTitle,
                width,
                length,
                wallPrep,
                ceiling,
                wallWork
        );
        this.setRoomID(DB.create(TABLE, sqlStmnt));
    }
    // retrieve labour_costs record from DB and map its details to this properties
    @Override
    public void retrieve() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `room` " +
                "WHERE `room_id` = %s " +
                "LIMIT 1;",
                roomID
        );
        DB.retrieve(this, sqlStmnt);
    }
    // update this labour_costs properties to labour_costs record in DB
    @Override
    public void update() {
        String sqlStmnt = String.format(
                "UPDATE `room` " +
                "SET " +
                        "`project_id`= %s," +
                        "`title`= '%s'," +
                        "`width`= %s," +
                        "`length`= %s," +
                        "`wall_prep`= '%s'," +
                        "`ceiling`= %s," +
                        "`wall_work`= '%s' " +
                "WHERE `room_id` = %s;",
                projectID,
                roomTitle,
                width,
                length,
                wallPrep,
                ceiling,
                wallWork,
                roomID
        );
        DB.update(TABLE, sqlStmnt);
    }
    // delete this labour_costs record from labour_costs table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM `room` " +
                "WHERE 'room_id' = %s;",
                roomID
        );
        DB.delete(TABLE, sqlStmnt);
    }
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException {
        this.roomID = rs.getInt("room_id");
        this.projectID = rs.getInt("project_id");
        this.roomTitle = rs.getString("title");
        this.width = rs.getInt("width");
        this.length = rs.getInt("length");
        this.wallPrep = rs.getString("wall_prep");
        this.ceiling = rs.getBoolean("ceiling");
        this.wallWork = rs.getString("wall_work");
    }
    @Override
    public String toString() {
        return "\n\t\t\troomID: '" + roomID + '\'' +
                "\n\t\t\tprojectID: '" + projectID + '\'' +
                "\n\t\t\troomTitle: '" + roomTitle + '\'' +
                ",\n\t\t\twallWork: '" + wallWork + '\'' +
                ",\n\t\t\twidth: " + width +
                ",\n\t\t\tlength: " + length +
                ",\n\t\t\tceiling: " + ceiling +
                "\n\t\t}";
    }
}
