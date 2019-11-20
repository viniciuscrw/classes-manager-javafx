package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.CourseClass;
import ifsp.poo.model.Professor;
import ifsp.poo.util.AlertUtils;
import ifsp.poo.util.DateUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.stream.Collectors;

public class MainLayoutController {

    @FXML
    private Label greetingLabel;
    @FXML
    private Label currentPeriodLabel;
    @FXML
    private ComboBox<CourseClass> classesCombo;

    private Stage dialogStage;

    public void initializeElements() {
        loadClassesCombo();
        setGreetingLabel();
        setCurrentPeriodLabel();
    }

    @FXML
    private void registerNewClass() {
        App.showClassRegisterForm(dialogStage);
    }

    @FXML
    private void manageClass() {
        if(classesCombo.getValue() != null) {
            App.showClassManagerWithStudentSelected(classesCombo.getValue());
        } else {
            AlertUtils.showAlert("Nenhuma turma selecionada", "Você deve selecionar uma turma.");
        }
    }

    private void loadClassesCombo() {
        Professor professor = (Professor) App.getSessionUser();
        classesCombo.setItems(FXCollections.observableArrayList(
                professor.getClasses().stream()
                        .filter(CourseClass::isActive)
                        .sorted(Comparator.comparing(CourseClass::getCode))
                        .collect(Collectors.toList())));

        classesCombo.setVisibleRowCount(professor.getClasses().size());
    }

    private void setGreetingLabel() {
        Professor professor = (Professor) App.getSessionUser();
        String[] splittedName = professor.getName().split(" ");
        greetingLabel.setText("Olá, " + splittedName[0] + "!");
    }

    private void setCurrentPeriodLabel() {
        currentPeriodLabel.setText("Período Atual: "  + DateUtils.getCurrentPeriod());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
