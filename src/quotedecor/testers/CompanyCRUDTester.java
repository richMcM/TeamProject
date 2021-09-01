package quotedecor.testers;

import quotedecor.config.DB;
import quotedecor.model.Company;

public class CompanyCRUDTester extends DB {

    public static void main (String [] args){

        /*
         >>>>> USE CTRL+/ TO COMMENT & UNCOMMENT LINES <<<<<
         */

        DB db = new DB();

        /* Company DB testing *****************************************************************************************
        /* ===== create Company account constructor ====== */
        /* creates new Company local object and also writes the new Company object to the DB */
        // run once to create new Company | un-comment if Company already exists and use login Company constructor
//        Company c = new Company("Bart@quote-decor.com", "12345", "LYIT Donegal", "Bart", "Simpson");

        /* ===== login Company constructor ====== */
        /* retrieves the Company record from the DB using the email passed in,
        then maps that records columns (forename, surname etc..) to the objects properties. */
        // run multiple times if Company already exists | comment if Company doesn't exist and use new Company constructor
        Company c = new Company("2@quote-decor.com");
//        c.labour.setPapering(0);
        c.setCompanyName("blah blah");
        c.setContactsName("billy");
        c.update();

//        c.labour.setPainting(1.25);
//        c.labour.update();

        /* THE DB HAS BEEN DESIGNED TO REMOVE ALL ASSOCIATED USER [CUSTOMER+COMPANY] INFORMATION WHEN DELETING THAT USER */
        // Customer +> all associated projects, rooms, reviews, quotes.
        // Company  +> all above +> labour_costs .
//        db.insertUpdateDelete("DELETE FROM company WHERE email = '2@quote-decor.com';");
        db.printTableCompany();

        // TODO -> in-progress -> testing and refining company DB operations

//        // Company.toString(); prints the Company and all associated details [LaborCosts|Quotes|Reviews] which
//        // helps assess what Company associated information is being held locally during testing/debugging.
//        System.out.println(c.toString());
//        db.printTableCompany();
//        db.printTableLabourCosts();

    }
}
