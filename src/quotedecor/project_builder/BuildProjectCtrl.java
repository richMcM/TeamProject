package quotedecor.project_builder;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import quotedecor.config.DB;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuildProjectCtrl implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField txtProjectName;
    @FXML
    private VBox vbProjectItemPane;
    @FXML
    private Button btnAddRoom, btnSaveProject;

    private int roomListNo = 0;

    DB DB = new DB();

    private ArrayList<AnchorPane> rooms = new ArrayList<>();
    private ArrayList<RoomCtrl>  roomCtrls= new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vbProjectItemPane.setId("ProjectItemList");
        roomListNo = 0;
        txtProjectName.setOnKeyTyped(e -> {
            txtProjectName.setStyle("-fx-border-color: #cccccc;");
        });
    }
    public void addRoom(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.ROOM));
        AnchorPane roomPane = (AnchorPane) loader.load();
        RoomCtrl roomCtrl = loader.<RoomCtrl>getController();
        // list item number for vbox list removal
        roomCtrl.setListNo(roomListNo);
        // list item number for display purpose
        roomCtrl.getLblRoom().setText("Room: "+(roomListNo +1));
        // when room list item X button clicked:
        roomCtrl.getBtnDeleteRoom().setOnAction(e -> {
            // remove room list item from vbox
            vbProjectItemPane.getChildren().remove(rooms.get(roomCtrl.getListNo()));
            // remove room anchor pane from rooms ArrayList
            rooms.remove(roomCtrl.getListNo());
            // remove room ctrl from roomCtrls ArrayList
            roomCtrls.remove(roomCtrl.getListNo());
            // dec roomNo list item counter
            roomListNo--;
            // for the remaining room list items
            for (int i = 0; i< roomListNo; i++) {
                // update it list item location number
                roomCtrls.get(i).setListNo(i);
                // update the display list item number
                roomCtrls.get(i).getLblRoom().setText("Room: "+(i+1));
            }
            if(roomListNo ==0)
                btnSaveProject.setVisible(false);
        });
        rooms.add(roomPane);
        roomCtrls.add(roomCtrl);
        roomListNo++;
        vbProjectItemPane.getChildren().add(roomPane);
        slowScrollToBottom(scrollPane);
        btnSaveProject.setVisible(true);
    }
    public void saveProject(){

        boolean errorsExist = false;
        StringBuilder errorMessage = new StringBuilder();

        if(txtProjectName.getText().length() == 0) {
            txtProjectName.setStyle("-fx-border-color: 'red';");
            errorMessage.append("PROJECT NAME REQUIRED\n");
            errorsExist = true;
        }
        for (RoomCtrl rc : roomCtrls) {
            errorsExist = false;
            String err = "ROOM: "+(rc.getListNo()+1)+" ";
            if(rc.getTxtRoomTitle().getText().length() == 0){
                rc.getTxtRoomTitle().setStyle("-fx-border-color: 'red';");
                err += "\n\t- TITLE REQUIRED ";
                errorsExist = true;
            }
            if(rc.getTxtRoomWidth().getText().length() == 0){
                rc.getTxtRoomWidth().setStyle("-fx-border-color: 'red';");
                err += "\n\t- WIDTH REQUIRED ";
                errorsExist = true;
            }
            else{
                try{
                    Double.parseDouble(rc.getTxtRoomWidth().getText());
                }catch (Exception e){
                    rc.getTxtRoomWidth().setStyle("-fx-border-color: 'red';");
                    err += "\n\t- WIDTH NONE NUMERIC ";
                    errorsExist = true;
                }
            }
            if(rc.getTxtRoomLength().getText().length() == 0){
                rc.getTxtRoomLength().setStyle("-fx-border-color: 'red';");
                err += "\n\t- LENGTH REQUIRED ";
                errorsExist = true;
            }
            else{
                try{
                    Double.parseDouble(rc.getTxtRoomLength().getText());
                }catch (Exception e){
                    rc.getTxtRoomLength().setStyle("-fx-border-color: 'red';");
                    err += "\n\t- LENGTH NON NUMERIC ";
                    errorsExist = true;
                }
            }
            if(!errorsExist)
                err = "";
            else{
                err += "\n";
                errorMessage.append(err);
            }
        }
        if(errorsExist){

            // create error icon
            ImageView errorIcon = new ImageView("quotedecor/img/error_icon.png");
            errorIcon.setFitHeight(48); // Set size to API recommendation.
            errorIcon.setFitWidth(48);

            // create error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.getDialogPane().setPrefWidth(250);
            alert.getDialogPane().setGraphic(errorIcon);
            alert.setTitle("Error");
            alert.setHeaderText("Please review the following errors");
            alert.setContentText(errorMessage.toString());
//            alert.setWidth(1600);
            alert.showAndWait();
        }
        else {
            Session.customer.newProject(txtProjectName.getText());
            for (RoomCtrl rc : roomCtrls) {
                Session.customer.addNewRoom(
                        rc.getTxtRoomTitle().getText(),
                        Double.parseDouble(rc.getTxtRoomWidth().getText()),
                        Double.parseDouble(rc.getTxtRoomLength().getText()),
                        rc.getCkbCeilings().isSelected()
                        //                    rc.getDrpCeilingsToDo().getSelectionModel()
                );
            }
            Session.customer.saveNewProject();
            Session.customer.getProject().toString();
            vbProjectItemPane.getChildren().removeAll();
//            for (int i =0; i<roomNo; i++)
//                vbProjectItemPane.getChildren().remove(i);
            txtProjectName.setText("");
            btnSaveProject.setVisible(false);
            DB.printTableCustomer();
            DB.printTableProject();
            DB.printTableRoom();
            System.out.println(Session.customer.toString());

            // create success icon
            ImageView errorIcon = new ImageView("quotedecor/img/success_icon.png");
            errorIcon.setFitHeight(48); // Set size to API recommendation.
            errorIcon.setFitWidth(48);

            // create success dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(true);
            alert.getDialogPane().setPrefWidth(350);
            alert.getDialogPane().setGraphic(errorIcon);
            alert.setTitle("Complete");
            alert.setHeaderText("Project created!");
            alert.setContentText("You can view and obtain quotes for this project in the projects section under the PROJECTS tab.");
//            alert.setWidth(1600);
            alert.showAndWait();
        }
    }
    public static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(10),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }
}
