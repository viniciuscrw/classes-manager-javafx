package ifsp.poo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column(unique = true)
    private String registration;
    @Column
    private Double gradesAverage;
    @ManyToOne(fetch = FetchType.LAZY)
    private CourseClass courseClass;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Grade> grades;

    public Student(String name, String registration) {
        this.name = name;
        this.registration = registration;
        this.grades = new ArrayList<>();
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public Long getId() {
        return id;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public Double getGradesAverage() {
        return gradesAverage;
    }

    public void setGradesAverage(Double gradesAverage) {
        this.gradesAverage = gradesAverage;
    }

    public StringProperty getCodeProperty() {
        return new SimpleStringProperty(this.name + " [" + this.registration + "]");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student student = (Student) obj;

            return this.registration.equals(student.registration) &&
                    this.name.equals(student.name);
        }

        return false;
    }
}
