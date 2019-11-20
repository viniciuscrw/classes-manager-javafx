package ifsp.poo.controller;

import ifsp.poo.model.Question;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class AddQuestionController extends FormController {

    @FXML
    private TextField questionNbrField;
    @FXML
    private ComboBox<Character> questionAnswerCombo;

    private int counter;
    private ActivityRegisterController testController;
    private Question question;

    public void initializeElements(Question question) {
        questionNbrField.setText("" + counter);
        this.question = question;
        loadComboBox();

        if (question != null) {
            loadWithExistingData();
        }
    }

    @FXML
     void confirm() {
        if (questionAnswerCombo.getValue() != null) {
            replaceQuestionIfExistsOrCreateNew();
            dialogStage.close();
        } else {
            AlertUtils.showWarningAlert("Você deve selecionar uma resposta.");
        }
    }

    private void loadComboBox() {
        List<Character> possibleAnswers = Arrays.asList('A', 'B', 'C', 'D', 'E');
        questionAnswerCombo.getItems().addAll(possibleAnswers);
    }

    private void loadWithExistingData() {
        questionNbrField.setText(String.valueOf(this.question.getNumber()));
        questionAnswerCombo.setValue(this.question.getAnswer());
    }

    private void replaceQuestionIfExistsOrCreateNew() {
        if (this.question != null) {
            this.question.setAnswer(questionAnswerCombo.getValue());
        } else {
            this.question = new Question(this.counter, questionAnswerCombo.getValue());
        }
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setTestController(ActivityRegisterController testController) {
        this.testController = testController;
    }
}
