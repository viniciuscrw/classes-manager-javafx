package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.Activity;
import ifsp.poo.model.Grade;
import ifsp.poo.service.ActivityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ActivityCorrectorController {

    @FXML
    private Label activityLabel;
    @FXML
    private TableView<Grade> gradesTable;
    @FXML
    private TableColumn<Grade, String> studentColumn;
    @FXML
    private TableColumn <Grade, Double> gradeColumn;

    private Stage dialogStage;
    private Activity activity;
    private ActivityService activityService;

    public void initializeElements(Activity activity) {
        this.activity = activity;
        activityService = new ActivityService();
        setActivityLabelText();
        initializeStudentsGradesTable();
        configureGradesTableForDoubleClickEvent();
    }

    @FXML
    public void confirm() {
        activityService.updateActivityStatus(activity);
        dialogStage.close();
        App.showClassManagerWithActivitySelected(activity.getCourseClass());
    }

    @FXML
    public void cancel() {
        dialogStage.close();
        App.showClassManagerWithActivitySelected(activity.getCourseClass());
    }

    private void initializeStudentsGradesTable() {
        if (activity.getCourseClass().getStudents() != null) {
            ObservableList<Grade> grades = FXCollections.observableList(
                    activityService.getActivityGrades(activity).stream()
                            .sorted(Comparator.comparing(g -> g.getStudent().getName()))
                            .collect(Collectors.toList()));

            gradesTable.setItems(grades);
            studentColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().getCodeProperty());
            gradeColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        }
    }

    private void configureGradesTableForDoubleClickEvent() {
        gradesTable.setRowFactory(tv -> {
            TableRow<Grade> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    App.showAddGradeForm(row.getItem(), activity);
                    dialogStage.close();
                }
            });
            return row;
        });
    }

    private void setActivityLabelText() {
        activityLabel.setText(activity.getCode());
    }

    public void setDialogStage(Stage correctorStage) {
        this.dialogStage = correctorStage;
    }
}
