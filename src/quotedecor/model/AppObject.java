package quotedecor.model;

import quotedecor.crud.DBCRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AppObject {

    protected DBCRUD DB = new DBCRUD();
    protected String TABLE;  // for DB-OPS console logging

    public abstract void create();
    public abstract void retrieve() throws SQLException;
    public abstract void update();
    public abstract void delete();
    public abstract void mapResultSet(ResultSet rs) throws SQLException;

    public String getTABLE() {return TABLE;}
}
