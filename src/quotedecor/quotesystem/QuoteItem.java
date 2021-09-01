package quotedecor.quotesystem;

import java.text.DecimalFormat;

public abstract class QuoteItem {

    // its DB_ID
    public int projectObjectID;

    // default
    public QuoteItem() {}

    public int getProjectObjectID() {
        return projectObjectID;
    }
    public void setProjectObjectID(int projectObjectID) {
        this.projectObjectID = projectObjectID;
    }
    public double round(double n){
        DecimalFormat format = new DecimalFormat("##.00");
        return Double.parseDouble(format.format(n));
    }
}
