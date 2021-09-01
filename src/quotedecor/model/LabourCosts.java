package quotedecor.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabourCosts extends AppObject {

    // Company LabourCosts properties
    String companyID;
    double wallPrep, painting, papering, woodPrepBareVar2Paint, woodPrepBareVar2Varnish, woodPrepPaint2Paint;

    // default constructor
    public LabourCosts() {}

    // DB retrieve/login constructor
    public LabourCosts(ResultSet rs) throws SQLException {
        mapResultSet(rs);
    }
    // create account constructor
    public LabourCosts(String companyEmail) {
        this.companyID = companyEmail;
        create();
    }

    public String getCompanyID() {
        return companyID;
    }
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
    public double getWallPrep() {
        return wallPrep;
    }
    public void setWallPrep(double wallPrep) {
        this.wallPrep = wallPrep;
    }
    public double getPainting() {
        return painting;
    }
    public void setPainting(double painting) {
        this.painting = painting;
    }
    public double getPapering() {
        return papering;
    }
    public void setPapering(double papering) {
        this.papering = papering;
    }
    public double getWoodPrepBareVar2Paint() {
        return woodPrepBareVar2Paint;
    }
    public void setWoodPrepBareVar2Paint(double woodPrepBareVar2Paint) {
        this.woodPrepBareVar2Paint = woodPrepBareVar2Paint;
    }
    public double getWoodPrepBareVar2Varnish() {
        return woodPrepBareVar2Varnish;
    }
    public void setWoodPrepBareVar2Varnish(double woodPrepBareVar2Varnish) {
        this.woodPrepBareVar2Varnish = woodPrepBareVar2Varnish;
    }
    public double getWoodPrepPaint2Paint() {
        return woodPrepPaint2Paint;
    }
    public void setWoodPrepPaint2Paint(double woodPrepPaint2Paint) {
        this.woodPrepPaint2Paint = woodPrepPaint2Paint;
    }

    /* ************************************************************************************************************** */
    // DB OPERATIONS
    // create new labour_costs record in labour_costs table
    @Override
    public void create(){
        String sqlStmnt = String.format(
                "REPLACE INTO " +
                "`labour_costs`(" +
                        "`company_id`, " +
                        "`painting`, " +
                        "`papering`, " +
                        "`wall_prep`, " +
                        "`wood_prep_bv2_paint`, " +
                        "`wood_prep_bv2_varnish`, " +
                        "`wood_prep_paint2_paint`" +
                ") VALUES ('%s', %s, %s, %s, %s, %s, %s);",
                companyID,
                painting,
                papering,
                wallPrep,
                woodPrepBareVar2Paint,
                woodPrepBareVar2Varnish,
                woodPrepPaint2Paint
        );
        DB.createKnownKey(TABLE, sqlStmnt);
    }
    // retrieve labour_costs record from DB and map its details to this properties
    @Override
    public void retrieve() {
        String sqlStmnt = String.format(
                "SELECT * " +
                "FROM `labour_costs` " +
                "WHERE `company_id` = '%s' " +
                "LIMIT 1;",
                companyID
        );
        DB.retrieve(this, sqlStmnt);
    }
    // update this labour_costs properties to labour_costs record in DB
    @Override
    public void update() {
        String sqlStmnt = String.format(
                "UPDATE `labour_costs` " +
                "SET " +
                    "`painting` = %s," +
                    "`papering` = %s," +
                    "`wall_prep` = %s," +
                    "`wood_prep_bv2_paint` = %s," +
                    "`wood_prep_bv2_varnish` = %s," +
                    "`wood_prep_paint2_paint` = %s " +
                    "WHERE `company_id` = '%s';",
                painting,
                papering,
                wallPrep,
                woodPrepBareVar2Paint,
                woodPrepBareVar2Varnish,
                woodPrepPaint2Paint,
                companyID

        );
        DB.update(TABLE, sqlStmnt);
    }
    // delete this labour_costs record from labour_costs table in DB
    @Override
    public void delete() {
        String sqlStmnt = String.format(
                "DELETE FROM `labour_costs` " +
                "WHERE 'company_id' = '%s';",
                companyID
        );
        DB.delete(TABLE, sqlStmnt);
    }
    @Override
    public void mapResultSet(ResultSet rs) throws SQLException {
        companyID = rs.getString("company_id");
        painting = rs.getDouble("painting");
        papering = rs.getDouble("papering");
        wallPrep = rs.getDouble("wall_prep");
        woodPrepBareVar2Paint = rs.getDouble("wood_prep_bv2_paint");
        woodPrepBareVar2Varnish = rs.getDouble("wood_prep_bv2_varnish");
        woodPrepPaint2Paint = rs.getDouble("wood_prep_paint2_paint");
    }
    @Override
    public String toString() {
        return "{" +
                " \n\t\tcompanyID: " + companyID +
                ", \n\t\twallPrep: " + wallPrep +
                ", \n\t\tpainting: " + painting +
                ", \n\t\tpapering: " + papering +
                ", \n\t\twoodPrepBareVar2Paint: " + woodPrepBareVar2Paint +
                ", \n\t\twoodPrepBareVar2Varnish: " + woodPrepBareVar2Varnish +
                ", \n\t\twoodPrepPaint2Paint: " + woodPrepPaint2Paint +
                "\n\t}";
    }
}
