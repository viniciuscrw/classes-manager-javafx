package ifsp.poo.model;

import ifsp.poo.util.DateUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course_class")
public class CourseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;
    @Column
    private String course;
    @Column
    private String code;
    @Column
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor professor;

    @OneToMany(
            mappedBy = "courseClass",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Student> students;

    @OneToMany(
            mappedBy = "courseClass",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<Activity> activities;

    public CourseClass(String subject, String course) {
        this.subject = subject;
        this.course = course;
        this.active = true;
        this.code = generateClassCode();
    }

    public CourseClass() {}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    private String generateClassCode() {
        return subject + " [" + course + "]" + " - " + DateUtils.getCurrentPeriod();
    }

    @Override
    public String toString() {
        return this.code;
    }
}
