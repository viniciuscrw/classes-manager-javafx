package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.service.CourseClassService;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

public class ClassRegisterController extends FormController {

    @FXML
    private TextField subjectField;
    @FXML
    private TextField courseField;

    private Stage mainStage;

    @FXML
    @Override
    void confirm() {
        if(validFields()) {
            try {
                createNewClass();
                dialogStage.close();
                mainStage.close();
                App.initializeProgram();
            } catch (Exception e) {
                AlertUtils.showErrorAlert("Erro ao registrar nova turma.");
                e.printStackTrace();
            }
        } else {
            AlertUtils.showWarningAlert("Ambos os campos devem ser preenchidos!");
        }
    }

    private void createNewClass() {
        CourseClassService classService = new CourseClassService();
        classService.createClassAndSave(subjectField.getText(), courseField.getText());
    }

    private boolean validFields() {
        return StringUtils.isNotEmpty(subjectField.getText())
                && StringUtils.isNotEmpty(courseField.getText());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
