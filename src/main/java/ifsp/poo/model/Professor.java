package ifsp.poo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor extends User {

    @Column
    private String name;
    @Column
    private String email;

    @OneToMany(
            mappedBy = "professor",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<CourseClass> classes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CourseClass> getClasses() {
        return classes;
    }

    public void setClasses(List<CourseClass> classes) {
        this.classes = classes;
    }
}
