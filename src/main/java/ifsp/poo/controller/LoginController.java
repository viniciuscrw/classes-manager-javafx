package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.Professor;
import ifsp.poo.service.AuthenticationService;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;

    private Stage dialogStage;

    @FXML
    private void login() {
        Professor professor = findFromLogin();
        if (professor != null) {
            App.setSessionUser(professor);
            dialogStage.close();
            App.initializeProgram();
        } else {
            AlertUtils.showAlert("Login falhou", "Usuário e/ou senha inválidos.");
        }
    }

    private Professor findFromLogin() {
        AuthenticationService authenticationService = new AuthenticationService();
        return authenticationService.authenticateAndFindProfessor(user.getText(), password.getText());
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void openSignupForm() {
        App.showSignupForm(dialogStage);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
