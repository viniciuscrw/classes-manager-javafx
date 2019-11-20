package ifsp.poo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;
    @Column
    private String status;
    @Column
    private LocalDate date;
    @Column
    private boolean test;
    @ManyToOne(fetch = FetchType.LAZY)
    private CourseClass courseClass;

    public Activity() {}

    public Activity(String description, LocalDate date, boolean test) {
        this.description = description;
        this.date = date;
        this.test = test;
        this.status = ActivityStatus.WAITING_CORRECTION.getValue();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public Long getId() {
        return id;
    }

    public StringProperty getDescriptionProperty() {
        return new SimpleStringProperty(this.description);
    }

    public StringProperty getStatusProperty() {
        return new SimpleStringProperty(this.status);
    }

    @Override
    public String toString() {
        return this.description;
    }
}
