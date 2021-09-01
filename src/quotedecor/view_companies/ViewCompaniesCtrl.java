package quotedecor.view_companies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import quotedecor.config.DB;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewCompaniesCtrl implements Initializable {

    quotedecor.config.DB DB = new DB();

    @FXML
    private VBox vbCompanyItemPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String sqlStmnt = "SELECT * FROM company;";
        ResultSet rs = DB.query(sqlStmnt);
        try{
            while(rs.next()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.VIEW_COMPANIES_LIST_ITEM));
                try {
                    AnchorPane companyListItem = (AnchorPane) loader.load();
                    CompanyListItemCtrl listItemCtrl = loader.getController();
                    listItemCtrl.getLblCompanyName().setText(rs.getString("company_name"));
                    vbCompanyItemPane.getChildren().add(companyListItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println("FAILED!\n" + e.getMessage()+"\n---");
        }
    }
}
