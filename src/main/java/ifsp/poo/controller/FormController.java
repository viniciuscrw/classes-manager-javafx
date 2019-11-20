package ifsp.poo.controller;

import ifsp.poo.App;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class FormController {

    protected Stage dialogStage;

    abstract void confirm();

    @FXML
    protected void cancel() {
        this.dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
