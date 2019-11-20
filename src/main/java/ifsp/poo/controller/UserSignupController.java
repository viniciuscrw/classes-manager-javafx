package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.Professor;
import ifsp.poo.persistence.ProfessorDAO;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class UserSignupController extends FormController {
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordRepeatField;
    @FXML
    private TextField emailField;

    private Stage loginStage;

    @FXML
    @Override
    void confirm() {
        if(isInfoValid()) {
            try {
                Professor professor = createUser();
                loginStage.close();
                dialogStage.close();
                App.setSessionUser(professor);
                App.initializeProgram();
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showErrorAlert("Erro ao completar o cadastro.");
            }
        }
    }

    private boolean isInfoValid() {
        String errorMsg = "";
        if (StringUtils.isEmpty(fullNameField.getText())) {
            errorMsg = "O campo Nome Completo é obrigatório.";
        } else if (StringUtils.isEmpty(usernameField.getText())) {
            errorMsg = "O campo Nome de Usuário é obrigatório.";
        } else if (StringUtils.isEmpty(emailField.getText())) {
            errorMsg = "O campo Email é obrigatório.";
        } else if (StringUtils.isEmpty(passwordField.getText())) {
            errorMsg = "Voce deve preencher a sua Senha.";
        } else if (!isValidPassword()) {
            errorMsg = "Senhas divergentes. Confirme sua novamente.";
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            showInvalidInputAlert(errorMsg);
            return false;
        }
        return true;
    }

    private void showInvalidInputAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Inválidos");
        alert.setHeaderText("Não foi possível confirmar o seu cadastro.");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    private boolean isValidPassword() {
        return passwordField.getText().equals(passwordRepeatField.getText());
    }

    private Professor createUser() throws Exception {
        Professor professor = new Professor();
        professor.setName(fullNameField.getText());
        professor.setUsername(usernameField.getText());
        professor.setPassword(passwordField.getText());
        professor.setEmail(emailField.getText());
        professor.setClasses(new ArrayList<>());

        //TODO persist
        persistProfessor(professor);
        return professor;
    }

    private void persistProfessor(Professor professor) throws Exception {
        ProfessorDAO professorDAO = new ProfessorDAO();
        professorDAO.save(professor);
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
}
