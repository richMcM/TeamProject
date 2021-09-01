package quotedecor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import quotedecor.config.DB;
import quotedecor.sessions.Session;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetLabourCostsController implements Initializable {
    @FXML
    private TextField WPGprice;
    @FXML
    private TextField WPPGprice;
    @FXML
    private TextField WPby2Vprice;
    @FXML
    private TextField WPPlasprice;
    @FXML
    private TextField WPby2price;
    @FXML
    private TextField WPP2P;
    @FXML
    private Label WPpaint2price;
    @FXML
    private Button LabourSave;
    @FXML
    private Button LabourEdit;

    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabourGridPane();

    }

    public void setLabourGridPane(){
        WPGprice.setText(String.valueOf(Session.company.labour.getWallPrep()));
        WPPGprice.setText(String.valueOf(Session.company.labour.getPainting()));
        WPby2price.setText(String.valueOf(Session.company.labour.getWoodPrepPaint2Paint()));
        WPby2Vprice.setText(String.valueOf(Session.company.labour.getWoodPrepBareVar2Varnish()));
        WPP2P.setText(String.valueOf(Session.company.labour.getWoodPrepPaint2Paint()));
        WPPlasprice.setText(String.valueOf(Session.company.labour.getPapering()));
    }


    public void saveLabour(javafx.event.ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(350);
        alert.setHeaderText("Confirm Labour Costs Update?");
        alert.setContentText("Are you sure you would like to continue with these Changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("OK Button Clicked!!!!!!!!!!");

            Session.company.labour.setWallPrep(Double.parseDouble(WPGprice.getText()));
            Session.company.labour.setPainting(Double.parseDouble(WPPGprice.getText()));
            Session.company.labour.setPapering(Double.parseDouble(WPP2P.getText()));
            Session.company.labour.setWoodPrepBareVar2Paint(Double.parseDouble(WPby2price.getText()));
            Session.company.labour.setWoodPrepBareVar2Varnish(Double.parseDouble(WPby2Vprice.getText()));
            Session.company.labour.setWoodPrepPaint2Paint(Double.parseDouble(WPPlasprice.getText()));

            Session.company.labour.update();
            setLabourGridPane();

            DB.printTableLabourCosts();
            Session.company.toString();

            Alert alertConfirmPriceChange = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmPriceChange.setResizable(true);
            alertConfirmPriceChange.getDialogPane().setPrefWidth(350);
            alertConfirmPriceChange.setTitle("Update Complete");
            alertConfirmPriceChange.setHeaderText("Labour costs Update Successful!");
            alertConfirmPriceChange.setContentText("Your Labour Costs Have Now been Updated!");
            alertConfirmPriceChange.showAndWait();

        } else if(result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button Clicked!!!!!!!!!!");
        }

    }
}
