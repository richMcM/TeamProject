package quotedecor.quotesystem;

import quotedecor.model.Company;

public class RoomQuoteItem extends QuoteItem {

    // surface areas
    private double ceilingAreaMtr2, wallsAreaMtr2;;
    // required paint
    private double ceilingPaintRequiredLtr, wallPaintRequiredLtr;
    // paint costs
    private double ceilingPaintCost, wallPaintCost;
    // labour cost
    private double ceilingLabourCost, wallsLabourCost;
    // total cost for this room
    private double totalLabourCost;

    private String roomTitle;
    private Company company;

    public RoomQuoteItem(String roomTitle, Double totalLabourCost) {
        super();
        this.roomTitle = roomTitle;
        this.company = null;
        this.ceilingAreaMtr2 = 0;
        this.wallsAreaMtr2 = 0;
        this.ceilingPaintRequiredLtr = 0;
        this.wallPaintRequiredLtr = 0;
        this.ceilingPaintCost = 0;
        this.wallPaintCost = 0;
        this.ceilingLabourCost = 0;
        this.wallsLabourCost = 0;
        this.totalLabourCost = totalLabourCost;
    }
    public RoomQuoteItem(String roomTitle, Company company) {
        super();
        this.roomTitle = roomTitle;
        this.company = company;
    }

    public double getCeilingAreaMtr2() {
        return ceilingAreaMtr2;
    }
    public void setCeilingAreaMtr2(double ceilingAreaMtr2) {
        this.ceilingAreaMtr2 = round(ceilingAreaMtr2);
    }
    public double getCeilingLabourCost() {
        return ceilingLabourCost;
    }
    public void setCeilingLabourCost(double ceilingLabourCost) {
        this.ceilingLabourCost = round(ceilingLabourCost);
    }
    public double getWallsAreaMtr2() {
        return wallsAreaMtr2;
    }
    public void setWallsAreaMtr2(double wallsAreaMtr2) {
        this.wallsAreaMtr2 = wallsAreaMtr2;
    }
    public double getCeilingPaintCost() {
        return ceilingPaintCost;
    }
    public void setCeilingPaintCost(double ceilingPaintCost) {
        this.ceilingPaintCost = ceilingPaintCost;
    }
    public double getWallPaintCost() {
        return wallPaintCost;
    }
    public void setWallPaintCost(double wallPaintCost) {
        this.wallPaintCost = wallPaintCost;
    }
    public double getWallsLabourCost() {
        return wallsLabourCost;
    }
    public void setWallsLabourCost(double wallsLabourCost) {
        this.wallsLabourCost = wallsLabourCost;
    }
    public double getCeilingPaintRequiredLtr() {
        return ceilingPaintRequiredLtr;
    }
    public void setCeilingPaintRequiredLtr(double ceilingPaintRequiredLtr) {
        this.ceilingPaintRequiredLtr = round(ceilingPaintRequiredLtr);
    }
    public double getWallPaintRequiredLtr() {
        return wallPaintRequiredLtr;
    }
    public void setWallPaintRequiredLtr(double wallPaintRequiredLtr) {
        this.wallPaintRequiredLtr = round(wallPaintRequiredLtr);
    }
    public double getTotalLabourCost() {
        return totalLabourCost;
    }
    public void setTotalLabourCost(double totalLabourCost) {
        this.totalLabourCost = totalLabourCost;
    }
    public String getRoomTitle() {
        return roomTitle;
    }
    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // compute the ceiling paint required, its costs and labour cost to apply it
    public void computeCeilingDetails(double roomWidth, double roomLength){
        KnownVariables knownVar = new KnownVariables();
        setCeilingAreaMtr2(roomWidth * roomLength);
        setCeilingLabourCost(getCeilingAreaMtr2() * company.labour.getPainting());
        setCeilingPaintRequiredLtr(getCeilingAreaMtr2() / knownVar.coverage1LtrPaintMtr2);
    }
    // compute the walls paint required, its costs and labour cost to apply it
    public void computeWallsDetails(double roomWidth, double roomLength){
        KnownVariables knownVar = new KnownVariables();
        setWallsAreaMtr2(((roomWidth * knownVar.aveCeilingHeightM)*2) + ((roomLength * knownVar.aveCeilingHeightM)*2));
        setWallsLabourCost(getWallsAreaMtr2() * company.labour.getPainting());
        setWallPaintRequiredLtr(getWallsAreaMtr2() / knownVar.coverage1LtrPaintMtr2);
    }
    // compute total labour cost
    public double computeTotalLabourCost() {
        totalLabourCost = (
//                  ceilingPaintCost
                wallsLabourCost
                        + ceilingLabourCost
//                + wallsLabourCost
        );
        return round(totalLabourCost);
    }
    @Override
    public String toString() {
        return "\n\t\t"+roomTitle+"{" +
                "\n\t\t\tCeiling Area: " + ceilingAreaMtr2 +" mtr2"+
                "\n\t\t\tWalls Area: " + wallsAreaMtr2 +" mtr2"+
                "\n\t\t\tCeiling Paint: " + ceilingPaintRequiredLtr +" ltrs"+
                "\n\t\t\tWall Paint: " + wallPaintRequiredLtr +" ltrs"+
                "\n\t\t\tCeiling Paint Cost: €" + ceilingPaintCost +
                "\n\t\t\tWall Paint Cost: €" + wallPaintCost +
                "\n\t\t\tCeiling Labour Cost: €" + ceilingLabourCost +
                "\n\t\t\tWalls Labour Cost: €" + wallsLabourCost +
                "\n\t\t\tTotal Cost: €" + totalLabourCost +
                "\n\t\t}";
    }
}
