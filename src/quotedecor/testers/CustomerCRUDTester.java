package quotedecor.testers;

import quotedecor.config.DB;
import quotedecor.model.Customer;

public class CustomerCRUDTester extends DB {

    public static void main (String [] args){

        /*
         >>>>> USE CTRL+/ TO COMMENT & UNCOMMENT LINES <<<<<
         */

        DB db = new DB();

        /* Customer DB testing *****************************************************************************************
        /* ===== create Customer account constructor ====== */
        /* creates new Customer local object and also writes the new Customer object to the DB */
        // run once to create new Customer | un-comment if customer already exists and use login customer constructor
//        Customer c = new Customer("Homer@quote-decor.com", "12345", "LYIT Donegal", "Homer", "Simpson");

        /* ===== login Customer constructor ====== */
        /* retrieves the customer record from the DB using the email passed in,
        then maps that records columns (forename, surname etc..) to the objects properties. */
        // run multiple times if customer already exists | comment if customer doesn't exist and use new customer constructor
        Customer c = new Customer("1@.com");

        // newProject() -> will be called when create project button/tab selected in customer account UI
        c.newProject("TEst Project 1");

        // addRoom(String roomTitle, double width, double length, String wallPrep, boolean ceiling, String wallWork)
        // -> will be called when the customer selects save project, the details contained in each UI input component
        // will be passed into the Customer.addRoom(String, double, double, String, boolean, String ) method.
        // * SUBJECT TO CHANGE THOUGH *
        // as another option would be to call an addRoom() method with no params which creates a new room with default
        // properties when the add room button is clicked and then use the Room setters to set the room properties
        // as the user enters them project construction UI.
        // Same concepts will apply to Doors if we are permitted to add them down the line.
        c.addNewRoom(
                "kitchen",
                15,
                10,
//                "general",
                true
//                "4 x painted"
        );
//        c.addNewRoom(
//                "lounge",
//                10,
//                8,
//                "general",
//                true,
//                "3 x painted"
//        );

        // saveNewProject() writes the project and its contents [Rooms and Doors] to the DB and then retrieves them to
        // hold a copy of each inserted record locally with the ID that was generated for each when they were inserted
        // into the DB. This is done encase the user wishes to edit the project.. the ID for each room and door would
        // be required locally to update that record when the user concludes the edit.
        c.saveNewProject();

        // Customer.toString(); prints the customer and all associated details [Projects|Rooms|Doors at present] which
        // helps assess what customer associated information is being held locally during testing/debugging.
        System.out.println(c.toString());

        // db contains a range of DB table printing to help helps assess what information is being held in the database
        // during testing/debugging. All the available print methods can be found in the quote_decor.config.DB class.
        // db.printTableCustomer(), db.printTableProject(), & db.printTableRoom() used for this test to compare what
        // information is in the DB to the local information displayed with toString() printed invoked above.
        // after creating the new Project and Rooms.
        db.printTableCustomer();
        db.printTableProject();
        db.printTableRoom();

        /* ===== delete a customer ===== */
        /* THE DB HAS BEEN DESIGNED TO REMOVE ALL ASSOCIATED USER [CUSTOMER+COMPANY] INFORMATION WHEN DELETING THAT USER */
        // Customer +> all associated projects, rooms, reviews, quotes.
        // Company  +> all above +> labour_costs .
//        db.insertUpdateDelete("DELETE FROM customer WHERE email = 'Homer@quote-decor.com';");
//        db.printTableCustomer();


    }
}
