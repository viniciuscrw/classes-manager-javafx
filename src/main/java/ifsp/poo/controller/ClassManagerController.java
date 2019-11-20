package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.*;
import ifsp.poo.service.ActivityService;
import ifsp.poo.service.CourseClassService;
import ifsp.poo.service.StudentService;
import ifsp.poo.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ClassManagerController {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentColumn;

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> descriptionColumn;
    @FXML
    private TableColumn<Activity, String> statusColumn;

    @FXML
    private Label studentClassLabel;
    @FXML
    private Label studentStatusLabel;
    @FXML
    private Label activityClassLabel;
    @FXML
    private Label activityStatusLabel;

    private Stage dialogStage;
    private CourseClass courseClass;
    private Professor professor;

    private StudentService studentService;
    private ActivityService activityService;
    private CourseClassService courseClassService;

    public void initalizeElements(CourseClass courseClass) {
        this.courseClass = courseClass;
        this.professor = (Professor) App.getSessionUser();
        studentService = new StudentService();
        activityService = new ActivityService();
        courseClassService = new CourseClassService();
        initializeStudentsTable();
        initializeActivitiesTable();
        setClassLabel();
        setStatusLabel();
        configureStudentTableForDoubleClickEvent();
    }

    @FXML
    private void createNewStudent() {
        App.showStudentRegisterForm(dialogStage, courseClass, new Student());
    }

    @FXML
    private void editStudent() {
        Student studentToBeEditted = studentTable.getSelectionModel().getSelectedItem();

        if (studentToBeEditted != null) {
            App.showStudentRegisterForm(dialogStage, courseClass, studentToBeEditted);
        } else {
            AlertUtils.showWarningAlert("Você deve selecionar um aluno para editar");
        }
    }

    @FXML
    private void removeStudent() {
        Student studentToBeRemoved = studentTable.getSelectionModel().getSelectedItem();

        if (studentToBeRemoved != null) {
            try {
                studentService.removeStudent(courseClass, studentToBeRemoved);
                dialogStage.close();
                App.showClassManagerWithStudentSelected(this.courseClass);
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showErrorAlert("Erro ao remover aluno(s).");
            }
        } else {
            AlertUtils.showWarningAlert("Nenhum aluno selecionado.");
        }
    }

    @FXML
    private void closeClass() {
        boolean existsActivityToBeCorrected = courseClass.getActivities()
                .stream()
                .anyMatch(actv -> actv.getStatus().equals(ActivityStatus.WAITING_CORRECTION.getValue()));

        if (!existsActivityToBeCorrected) {
            courseClassService.updateClassStatus(courseClass, false);
            setStatusLabel();
        } else {
            AlertUtils.showWarningAlert("Ainda existem atividades a serem corrigidas.");
        }
    }

    private void initializeStudentsTable() {
        if (courseClass.getStudents() != null) {
            ObservableList<Student> students = FXCollections.observableArrayList(
                    courseClass.getStudents().stream()
                            .sorted(Comparator.comparing(Student::getName))
                            .collect(Collectors.toList()));

            studentTable.setItems(students);
            studentColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
        }
    }

    @FXML
    private void createActivity() {
        App.showActivityRegisterForm(dialogStage, courseClass, new Activity(), this);
    }

    @FXML
    private void editActivity() {
        Activity activityToBeEditted = activityTable.getSelectionModel().getSelectedItem();

        if (activityToBeEditted != null) {
            App.showActivityRegisterForm(dialogStage, courseClass, activityToBeEditted, this);
        } else {
            AlertUtils.showWarningAlert("Você deve selecionar uma atividade para editar.");
        }
    }

    @FXML
    private void correctActivity() {
        if (this.courseClass.getStudents() == null) {
            AlertUtils.showWarningAlert("Não existem alunos matriculados nesta disciplina.");
            return;
        }

        Activity activityToBeCorrected = activityTable.getSelectionModel().getSelectedItem();

        if (activityToBeCorrected != null) {
            if (activityToBeCorrected.getStatus().equals(ActivityStatus.CORRECTED.getValue())) {
                AlertUtils.showWarningAlert("Atividade já está corrigida.");
                return;
            }

            //TODO
            
        } else {
            AlertUtils.showWarningAlert("Nenhuma atividade selecionada.");
        }
    }

    private void updateTestStatus(Activity correctedActivity) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showErrorAlert("Erro ao atualizar status da atividade.");
        }
    }

    @FXML
    private void removeActivity() {
        Activity activityToBeRemoved = activityTable.getSelectionModel().getSelectedItem();

        if (activityToBeRemoved != null) {
            try {
                activityService.removeActivity(courseClass, activityToBeRemoved);
                dialogStage.close();
                App.showClassManagerWithActivitySelected(courseClass);
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showErrorAlert("Erro ao remover atividade.");
            }
        } else {
            AlertUtils.showWarningAlert("Nenhuma atividade selecionada.");
        }
    }

    private void initializeActivitiesTable() {
        if (courseClass.getActivities() != null) {
            ObservableList<Activity> activities = FXCollections.observableArrayList(
                    courseClass.getActivities().stream()
                            .sorted(Comparator.comparing(Activity::getStatus)
                                    .thenComparing(Activity::getDescription))
                            .collect(Collectors.toList()));

            activityTable.setItems(activities);
            descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        }
    }

    private void setClassLabel() {
        String code = courseClass.getCode().length() < 30 ?
                courseClass.getCode() :
                courseClass.getCode().substring(0, 26) + "...";

        studentClassLabel.setText(code);
        activityClassLabel.setText(code);
    }

    private void setStatusLabel() {
        String status = courseClass.isActive() ? "Em Andamento" : "Finalizada";
        studentStatusLabel.setText(status);
        activityStatusLabel.setText(status);
    }

    private void configureStudentTableForDoubleClickEvent() {
        studentTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    App.showStudentGrades(row.getItem());
                }
            });
            return row;
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
