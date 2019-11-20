package ifsp.poo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Activity activity;
    @Column
    private Double value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public Grade(Activity activity, Double value) {
        this.activity = activity;
        this.value = value;
    }

    public Grade(){}

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ObjectProperty<Activity> getActivityProperty() {
        return new SimpleObjectProperty<>(this.activity);
    }

    public ObservableValue<Double> getValueProperty() {
        return new SimpleDoubleProperty(this.value).asObject();
    }
}
