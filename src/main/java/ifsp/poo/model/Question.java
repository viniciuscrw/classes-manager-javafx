package ifsp.poo.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Question {

    private int number;
    private char answer;

    public Question(int number, char answer) {
        this.number = number;
        this.answer = answer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public char getAnswer() {
        return answer;
    }

    public void setAnswer(char answer) {
        this.answer = answer;
    }

    public ObservableValue<Integer> getNumberProperty() {
        return new SimpleIntegerProperty(getNumber()).asObject();
    }

    public StringProperty getAnswerProperty() {
        return new SimpleStringProperty(String.valueOf(this.answer));
    }
}
