package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.*;
import ifsp.poo.service.ActivityService;
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

    public void initalizeElements(CourseClass courseClass) {
        this.courseClass = courseClass;
        this.professor = (Professor) App.getSessionUser();
        studentService = new StudentService();
        activityService = new ActivityService();
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
                courseClass.getStudents().remove(studentToBeRemoved);
                updateClasses(this.professor, this.courseClass);
                save(this.professor);
                App.showClassManagerWithStudentSelected(this.courseClass);
                dialogStage.close();
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showErrorAlert("Erro ao remover aluno(s).");
            }
        } else {
            AlertUtils.showWarningAlert("Nenhum aluno selecionado.");
        }
    }

    @FXML
    private void closeClass() throws Exception {
        boolean existsActivityToBeCorrected = courseClass.getActivities()
                .stream()
                .anyMatch(actv -> actv.getStatus().equals(ActivityStatus.WAITING_CORRECTION.getValue()));

        if (!existsActivityToBeCorrected) {
            this.courseClass.setActive(false);
            setStatusLabel();
            //TODO persist
            updateClasses(this.professor, this.courseClass);
            save(this.professor);
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
//        int index = this.courseClass.getActivities().in(correctedActivity);
//        correctedActivity.setStatus(ActivityStatus.CORRECTED.getValue());
//        courseClass.getActivities().set(index, correctedActivity);
//
        try {
//            updateClasses(professor, this.courseClass);
//            //TODO persist
//            save(professor);
//            App.showClassManagerWithActivitySelected(this.courseClass);
//            this.dialogStage.close();
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

    private void removeFromStudentsGrade(Activity activity) {
        courseClass.getStudents().forEach(student ->
                student.getGrades().removeIf(grade -> grade.getActivity().equals(activity))
        );
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

    public void computeGradeToStudent(Activity activity, String studentId, BigDecimal score) {
        Student student = getStudentById(studentId);
        ArrayList<Grade> grades = new ArrayList<>();
        Grade grade = new Grade(activity, score.doubleValue());

        if (CollectionUtils.isEmpty(student.getGrades())) {
            grades.add(grade);
            student.setGrades(grades);
        } else {
            boolean found = false;
            for (Grade grd : student.getGrades()) {
                if (grd.getActivity().equals(activity)) {
                    int index = student.getGrades().indexOf(grd);
                    student.getGrades().set(index, grade);
                    found = true;
                }
            }

            if (!found) {
                student.getGrades().add(grade);
            }
        }

        try {
            persistToProfessorData(student);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showErrorAlert("Ocorreu um erro ao computar a nota da atividade para o aluno " + student.getName());
        }
    }

    private Student getStudentById(String studentId) {
        return this.courseClass.getStudents().stream()
                .filter(student -> student.getRegistration().equals(studentId))
                .findAny()
                .orElse(new Student());
    }

    private void persistToProfessorData(Student student) throws Exception {
        int index = this.courseClass.getStudents().indexOf(student);
        this.courseClass.getStudents().set(index, student);
        updateClasses(professor, courseClass);
        save(professor);
    }

    private void updateClasses(Professor professor, CourseClass updatedClass) {
        int index = professor.getClasses().indexOf(courseClass);
        professor.getClasses().set(index, updatedClass);
    }

    private void save(Professor professor) throws Exception {

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
