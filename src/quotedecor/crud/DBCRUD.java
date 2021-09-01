/**
 * This class contains all database CREATE|RETRIEVE|UPDATE|DELETE operations.
 *
 * All database operations have been compiled into this class for 2 reasons:
 *
 *      1: to reduce code redundancy:
 *          The SQL statements are created in the system classes through-out and passed into the statement execution
 *          op methods of this class, reusing similar statement execution structures where
 *          possible also reducing the amount of try-catch blocks through-out the system.
 *
 *      2: for an added element of system modularity:
 *          compiling the DB operations into their own class provides
 *          cleaner|less-congested|readable code through-out and a more modularized system.
 *
 * Some methods have object parameters where objects can be passed in and their properties processed locally.
 *
 * The abstract class AppObject contains an instance of this class [ DBCRUD DB = new DBCRUD() ]
 * most if not all classes of this system inherit from AppObject an therefore posses the DBCRUD obj
 * which is used to execute their DB operations.
 */

package quotedecor.crud;

import quotedecor.config.DB;
import quotedecor.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBCRUD extends DB {

    /* NON-UNIQUE DB-OPs ************************************************************************************************************** */
    /**
     * Creates a DB record in any table where the primary key is known,
     * eg. user|labour_costs tables -> know key => email,
     * the primary key is assigned by the the user (user-generated key)
     * @param TABLE: the name of the table for console logging purposes
     * @param sqlStmnt: the INSERT SQL statement to be executed
     */
    public void createKnownKey(String TABLE, String sqlStmnt){

        System.out.println("CREATING "+TABLE+": ");
        // execute INSERT SQL statement
        insertUpdateDelete(sqlStmnt);
        System.out.println(TABLE+" CREATE END!\n---");
    }

    /**
     * Creates a DB record in any table where the primary is an auto-increment int (auto-generated key),
     * @param TABLE: the name of the table for console logging purposes
     * @param sqlStmnt: the INSERT SQL statement to be executed
     * @return generatedID: the primary key generated when inserting the record to the DB,
     *                      used to set the calling objects ID property to the generatedID,
     *                      eg. setTableID(DB.create(TABLE, sqlStmnt);
     */
    public int create(String TABLE, String sqlStmnt){

        System.out.println("CREATING "+TABLE+": ");
        // execute INSERT SQL statement and retrieve the PK of the inserted record
        int generatedID = insert(sqlStmnt);
        System.out.println(TABLE+" CREATE END!\n---");
        // return the PK of the inserted record to the calling object to set it ID property
        return generatedID;
    }

    /**
     * Retrieves a DB record from any table and uses the calling objects mapResultSet(rs) method
     * to map the ResultSet returned by the query(sqlStmnt) method to the properties of that object.
     * @param appObj: the calling AppObject object => any object who extends the AppObject class,
     *                object passed in to process the ResultSet [ appObj.mapResultSet(rs); ]
     *                while the DB connection is open and within this try-catch block, reducing code redundancy.
     * @param sqlStmnt: the SELECT SQL statement to be executed
     */
    public void retrieve(AppObject appObj, String sqlStmnt) {
        ResultSet rs;
        System.out.println("READING "+appObj.getTABLE()+": ");
        // execute SELECT SQL statement
        rs = query(sqlStmnt);
        try {
            // if record returned:
            if (rs.next()) {
                // map that record to the properties of the calling object
                appObj.mapResultSet(rs);
                System.out.println(appObj.getTABLE()+" READ COMPLETE!\n---");
            }
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: appObj.mapResultSet(rs);\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
    }

    /**
     * Updates a DB record in any table.
     * @param TABLE: the name of the table for console logging purposes
     * @param sqlStmnt: the UPDATE SQL statement to be executed
     */
    public void update(String TABLE, String sqlStmnt){

        System.out.println("UPDATING "+TABLE+": ");
        // execute UPDATE SQL statement
        insertUpdateDelete(sqlStmnt);
        System.out.println(TABLE+" UPDATE END!\n---");
    }
    /**
     * Deletes a DB record from any table.
     * @param TABLE: the name of the table for console logging purposes
     * @param sqlStmnt: the DELETE SQL statement to be executed
     */
    public void delete(String TABLE, String sqlStmnt){

        System.out.println("DELETING "+TABLE+": ");
        // execute DELETE SQL statement
        insertUpdateDelete(sqlStmnt);
        System.out.println(TABLE+" DELETE END!\n---");
    }

    /**
     * Retrieves User DB review records,
     * Can be used to retrieve a users own reviews or other users reviews,
     * eg. Company+Customer own reviews | Company reviews for Customer | Customer reviews for Company
     * @param sqlStmnt : the SELECT SQL statement to be executed
     * @return -> an ArrayList of reviews which are stored in the calling User objects reviews ArrayList.
     */
    public ArrayList<Review> retrieveReviews(String sqlStmnt) {
        ResultSet rs;
        ArrayList<Review> reviews = new ArrayList<>();
        System.out.print("READING REVIEWS.. ");
        // execute SELECT SQL statement
        rs = query(sqlStmnt);
        try {
            // for each record returned:
            while (rs.next()) {
                // add that record to the reviews ArrayList of the calling User object
                reviews.add(new Review(rs));
            }
            System.out.println("REVIEWS READ COMPLETE! ");
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: while (rs.next())\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
        return reviews;
    }

    /**
     * Retrieves User DB quote records,
     * Used to retrieve a users accepted quotes.
     * @param sqlStmnt : the SELECT SQL statement to be executed
     * @return -> an ArrayList of quotes which are stored in the calling User objects quotes ArrayList.
     */
    public ArrayList<Quote> retrieveAcceptedQuotes(String sqlStmnt) {
        ResultSet rs;
        ArrayList<Quote> quotes = new ArrayList<>();
        System.out.print("READING QUOTES.. ");
        rs = query(sqlStmnt);
        try {
            while (rs.next()) {
                quotes.add(new Quote(rs));
            }
            System.out.println("QUOTES READ COMPLETE! ");
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: while (rs.next())\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
        return quotes;
    }
    /**
     * Retrieves Customer DB project records,
     * Used to retrieve a customer projects for both the customer and company to view.
     * @param customerID : the projects customer ID
     * @return -> an ArrayList of projects which are stored in the calling User objects projects ArrayList.
     */
    public ArrayList<Project> retrieveProjects(String customerID) {
        ResultSet rsProjects, rsRooms, rsDoors;
        String sqlProject, sqlRoom, sqlDoor;
        ArrayList<Project> projects = new ArrayList<>();
        sqlProject = String.format(
                "SELECT * " +
                "FROM `project` " +
                "WHERE `customer_id` = '%s';",
                customerID
        );
        System.out.print("READING PROJECTS.. ");
        rsProjects = query(sqlProject);
        try {
            int i = 0;
            while (rsProjects.next()) {
                projects.add(new Project(rsProjects));

                // read associated rooms from DB
                System.out.print("READING ROOMS.. ");
                sqlRoom = String.format(
                        "SELECT * " +
                        "FROM `room` " +
                        "WHERE `project_id` = %s;",
                        projects.get(i).getProjectID()
                );
                rsRooms = query(sqlRoom);
                while (rsRooms.next())
                    projects.get(i).rooms.add(new Room(rsRooms));
                System.out.println("SUCCESS! ");
                // read associated doors from DB
//                System.out.print("READING DOORS.. ");
//                sqlDoor = String.format(
//                        "SELECT * " +
//                        "FROM `door` " +
//                        "WHERE `project_id` = %s;",
//                        projects.get(i).getProjectID()
//                );
//                rsDoors = query(sqlDoor);
//                while (rs.next())
//                    projects.get(i).doors.add(new Door(rsDoors));
//                System.out.println("SUCCESS! ");
                i++;
            }
            System.out.println("ROOM READ COMPLETE! ");
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: while (rs.next())\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
        return projects;
    }
    /* UNIQUE DB-OPs ************************************************************************************************ */
    /**
     * Retrieves company associated DB labour_costs records -> set the results to calling Company objects labour property.
     * @param company: the calling Company object:
     *                 object passed in to process the ResultSet [ company.labour = new LabourCosts(rs); ]
     *                 while the DB connection is open and within this try-catch block, reducing code redundancy.
     * @param sqlStmnt: the SELECT SQL statement to be executed
     */
    public void retrieveLabourCosts(Company company, String sqlStmnt) {
        ResultSet rs;
        System.out.print("READING LABOUR-COSTS.. ");
        rs = query(sqlStmnt);
        try {
            if (rs.next()) {
                company.labour = new LabourCosts(rs);
            }
            System.out.println("LABOUR-COSTS READ COMPLETE! ");
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: while (rs.next())\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
    }

    public ArrayList<Company> retrieveCompanies(String sqlStmnt) {
        ResultSet rs;
        ArrayList<Company> companies = new ArrayList<>();
        System.out.print("READING COMPANIES.. ");
        rs = query(sqlStmnt);
        try {
            while (rs.next()) {
                Company c = new Company(rs);
//                c.retrieveReviews();
                companies.add(c);
            }
            System.out.println("COMPANIES READ COMPLETE! ");
        }catch (SQLException e){
            System.out.println("FAILED! => Problem with SQL: while (rs.next())\n" + e.getMessage()+"\n---");
        }finally {
            closeConnection();
        }
        return companies;
    }
}
