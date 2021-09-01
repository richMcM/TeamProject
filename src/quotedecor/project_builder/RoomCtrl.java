package quotedecor.project_builder;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomCtrl implements Initializable {
    @FXML
    private Label lblRoom, lblTitle, lblWidth, lblLength;
    @FXML
    private TextField txtRoomTitle, txtRoomWidth, txtRoomLength;
    @FXML
    private CheckBox ckbCeilings;
    @FXML
    private Button btnDeleteRoom;

    private int listNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtRoomTitle.setOnKeyTyped(e -> {
            txtRoomTitle.setStyle("-fx-border-color: #cccccc;");
        });
        txtRoomWidth.setOnKeyTyped(e -> {
            txtRoomWidth.setStyle("-fx-border-color: #cccccc;");
        });
        txtRoomLength.setOnKeyTyped(e -> {
            txtRoomLength.setStyle("-fx-border-color: #cccccc;");
        });
    }

    public Label getLblRoom() {
        return lblRoom;
    }

    public TextField getTxtRoomTitle() {
        return txtRoomTitle;
    }

    public TextField getTxtRoomWidth() {
        return txtRoomWidth;
    }

    public TextField getTxtRoomLength() {
        return txtRoomLength;
    }

    public CheckBox getCkbCeilings() {
        return ckbCeilings;
    }

    public Button getBtnDeleteRoom() {
        return btnDeleteRoom;
    }
    public int getListNo() {return listNo;}

    public void setListNo(int listNo) {
        this.listNo = listNo;
    }

    public void setBorderDefault(Event e){

    }



}


