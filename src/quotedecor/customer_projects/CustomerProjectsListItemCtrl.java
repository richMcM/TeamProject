package quotedecor.customer_projects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quotedecor.model.Project;
import quotedecor.model.Room;
import quotedecor.quotesystem.QuoteComputer;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerProjectsListItemCtrl implements Initializable {

    @FXML
    private Button btnGetQuote, btnX;

    /* ===== room table view ===== */
    @FXML private TableView<Room> roomTableView;

    /* ===== room table view columns ===== */
    @FXML private TableColumn<Room, String> colTitle;
    @FXML private TableColumn<Room, Double> colWidth;
    @FXML private TableColumn<Room, Double> colLength;
    @FXML private TableColumn<Room, Boolean> colCeiling;
//    @FXML private TableColumn<Project, Integer> swapsCol;
//    @FXML private TableColumn<Project, Long> timeCol;

    /* ===== room table view contents list ===== */
    private ObservableList<Room> roomTableList = FXCollections.observableArrayList();

    private int listNo;

    public Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* ===== roomTableView setup ===== */
//        roomTableView.
        colTitle.setCellValueFactory(new PropertyValueFactory<>("roomTitle"));
        colWidth.setCellValueFactory(new PropertyValueFactory<>("width"));
        colLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        colCeiling.setCellValueFactory(new PropertyValueFactory<>("Ceiling"));
//        swapsCol.setCellValueFactory(new PropertyValueFactory<>("swaps"));
//        timeCol.setCellValueFactory(new PropertyValueFactory<>("elapsedTime"));
        roomTableView.setItems(roomTableList);
    }

    public Button getBtnGetQuote() {
        return btnGetQuote;
    }

    public Button getBtnX() {
        return btnX;
    }

    public int getListNo() {
        return listNo;
    }

    public void setListNo(int listNo) {
        this.listNo = listNo;
    }

    public Project getProject() {
        return project;
    }
    // set the project object of this Title pane for access to its DB update and delete operations
    public void setProject(Project project) {
        this.project = project;
        setRoomTableContents();
    }
    // add rooms to room table list
    private void setRoomTableContents(){
        roomTableList.addAll(project.rooms);

    }

    // display pop up dialog containing a list of quotes for this project
    @FXML private void getQuote() throws IOException {
        // generate list of quotes and store in globally accessible customer quotes ArrayList
        Session.customer.quotes = new QuoteComputer(project).computeQuotes();
        System.out.println(Session.customer.quotes);
        // load the quotes list dialog scroll pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.QUOTE_LIST_DIALOG));
        // cast to Parent
        Parent parent = (Parent) loader.load();
//        QuotesListModalCtrl quotesListModalCtrl = loader.getController();
//        QuotesListModalCtrl.addNewRoomTableViewItem();

        Scene scene = new Scene(parent, 760, 525);
        Stage stage = new Stage();
//        stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
        stage.setTitle("Quotes");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
//        System.out.println(Session.customer.quotes);

    }
}
