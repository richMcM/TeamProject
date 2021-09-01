package quotedecor.customer_projects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import quotedecor.config.DB;
import quotedecor.model.Project;
import quotedecor.sessions.Session;
import quotedecor.util.URLS;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerProjectsListCtrl implements Initializable {

    @FXML
    Accordion accordion;

    private int accordionListNo = 0;

    private ArrayList<TitledPane> titlePanes = new ArrayList<>();
    private ArrayList<CustomerProjectsListItemCtrl>  titlePaneCtrls = new ArrayList<>();

    DB DB = new DB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        accordionListNo = 0;

        for (Project project : Session.customer.getProjects()){
            try {
                // project title pane UI
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLS.CUSTOMER_PROJECTS_LIST_ITEM));
                TitledPane titlePane = (TitledPane) loader.load();

                titlePane.setText(project.getProjectName());

                // project title pane controller
                CustomerProjectsListItemCtrl titlePaneCtrl = loader.<CustomerProjectsListItemCtrl>getController();
                titlePaneCtrl.setProject(project);
                titlePaneCtrl.setListNo(accordionListNo);

                // set delete project button action
                setDeleteButtonAction(titlePaneCtrl);
                // add title pane to ArrayList
                titlePanes.add(titlePane);

                // add ctrl to ArrayList
                titlePaneCtrls.add(titlePaneCtrl);

                // add complete project title pane to accordion pane
                accordion.getPanes().add(titlePane);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setDeleteButtonAction(CustomerProjectsListItemCtrl ctrl){
        ctrl.getBtnX().setOnAction(e -> {

            // remove title pane item from accordion
            accordion.getPanes().remove(titlePanes.get(ctrl.getListNo()));

            // remove title pane from title panes ArrayList
            titlePanes.remove(ctrl.getListNo());

            // remove ctrl from ctrls ArrayList
            titlePaneCtrls.remove(ctrl.getListNo());

            // dec list number item counter
            accordionListNo--;

            // remove project fromm DB
            ctrl.getProject().delete();

            // for the remaining accordion title pane children
            for (int i = 0; i< accordionListNo; i++) {
                // update list accordion list number
                titlePaneCtrls.get(i).setListNo(i);
            }
            // DB feedback/confirmation
            DB.printTableCustomer();
            DB.printTableProject();
            DB.printTableRoom();
        });
    }
}
