package quotedecor.quotesystem;

import quotedecor.crud.DBCRUD;
import quotedecor.model.*;
import quotedecor.sessions.Session;

import java.util.ArrayList;

public class QuoteComputer {

    private Project project;
    private KnownVariables knownVar;
    private ArrayList<Company> companies;
    private ArrayList<Quote> quotes;
    private DBCRUD DB;


    public QuoteComputer(Project project) {

        this.project = project;
        DB = new DBCRUD();
        knownVar = new KnownVariables();
        companies = new ArrayList<>();
        quotes = new ArrayList<>();
        // get all companies from DB
        companies = DB.retrieveCompanies("SELECT * FROM company;");
    }

    public ArrayList<Quote> computeQuotes(){
        // for each company
        for (Company company: companies) {
            // retrieve their labour costs
          company.retrieveLabourCosts();
          // if labour costs present:
          if(company.labour != null && company.isAvailable()) {
              // and if painting price specified
              if (company.labour.getPainting() > 0) {
                  // compute quote
                  Quote quote = new Quote();
                  quote.setProjectID(project.getProjectID());
                  quote.setCompanyID(company.getEmail());
                  quote.setCompanyName(company.getCompanyName());
                  quote.setCustomerID(Session.customer.getEmail());
                  for (Room room : project.rooms) {
                      quote.addQuoteItem(computeRoomPrice(quote, room, company));
                  }
                  quote.computeTotalQuotePrice();
                  quotes.add(quote);
              }
          }
        }
        return quotes;
    }

    private RoomQuoteItem computeRoomPrice(Quote quote, Room room, Company company){

        RoomQuoteItem roomQuoteItem = new RoomQuoteItem(room.getRoomTitle(), company);
        roomQuoteItem.projectObjectID = room.getProjectID();

        if(room.isCeiling()) {
            // compute the ceiling paint required, its costs and labour cost to apply it
            roomQuoteItem.computeCeilingDetails(room.getWidth(), room.getLength());
            // increment the total amount of white ceiling paint (Ltrs) required for this project
            quote.incTotalCeilingPaintRequiredLtr(roomQuoteItem.getCeilingPaintRequiredLtr());
        }
        if(room.getWidth() > 0 && room.getLength() >0){
            // compute the walls paint required, its costs and labour cost to apply it
            roomQuoteItem.computeWallsDetails(room.getWidth(), room.getLength());
            // increment the total amount of wall paint (Ltrs) required for this project
            quote.incTotalWallPaintRequiredLtr(roomQuoteItem.getWallPaintRequiredLtr());
        }
        // increment the total cost of labour for this room
        quote.incPrice(roomQuoteItem.computeTotalLabourCost());
        return roomQuoteItem;
    }


}
