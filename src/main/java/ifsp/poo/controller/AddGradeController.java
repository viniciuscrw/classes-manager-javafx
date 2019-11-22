package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.Activity;
import ifsp.poo.model.Grade;
import ifsp.poo.service.StudentService;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.List;

public class AddGradeController extends FormController {

    @FXML
    private Label studentCodeLabel;
    @FXML
    private ComboBox<Double> studentGradeCombo;

    private Grade grade;
    private Activity activity;
    private StudentService studentService;

    public void initializeElements(Grade grade, Activity activity) {
        this.grade = grade;
        this.activity = activity;
        studentService = new StudentService();
        studentCodeLabel.setText(grade.getStudent().getName());
        loadComboBox();

        if (grade.getValue() != null) {
            loadWithExistingGrade();
        }
    }

    @FXML
    @Override
     void confirm() {
        if (studentGradeCombo.getValue() != null) {
            studentService.updateGradeToStudent(grade, studentGradeCombo.getValue());
            dialogStage.close();
            App.showActivityCorrector(activity);
        } else {
            AlertUtils.showWarningAlert("Você deve selecionar uma nota.");
        }
    }

    @FXML
    @Override
    public void cancel() {
        dialogStage.close();
        App.showActivityCorrector(activity);
    }

    private void loadComboBox() {
        List<Double> possibleGrades = Arrays.asList(0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0,
                5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0);
        studentGradeCombo.getItems().addAll(possibleGrades);
    }

    private void loadWithExistingGrade() {
        studentGradeCombo.setValue(this.grade.getValue());
    }
}
