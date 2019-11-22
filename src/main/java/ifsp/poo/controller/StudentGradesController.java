package ifsp.poo.controller;

import ifsp.poo.model.Activity;
import ifsp.poo.model.Grade;
import ifsp.poo.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Optional;

public class StudentGradesController extends FormController {

    @FXML
    private TableView<Grade> gradesTable;
    @FXML
    private TableColumn<Grade, Activity> activityColumn;
    @FXML
    private TableColumn<Grade, Double> gradeColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField registrationField;
    @FXML
    private Label averageLabel;

    private Student student;

    public void initializeElements(Student student) {
        this.student = student;
        setNameField();
        setRegistrationField();
        initializeGradesTable();
        setAverageLabel();
    }

    @FXML
    @Override
    void confirm() {
        dialogStage.close();
    }

    public void setNameField() {
        nameField.setText(this.student.getName());
    }

    public void setRegistrationField() {
        registrationField.setText(this.student.getRegistration());
    }

    private void initializeGradesTable() {
        if (this.student.getGrades() != null) {
            ObservableList<Grade> grades = FXCollections.observableArrayList(student.getGrades());

            gradesTable.setItems(grades);
            activityColumn.setCellValueFactory(cellData -> cellData.getValue().getActivityProperty());
            gradeColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        }
    }

    private void setAverageLabel() {
        averageLabel.setText(String.format("%.2f",
                Optional.ofNullable(student.getGradesAverage())
                        .orElse(0d)));
    }
}
