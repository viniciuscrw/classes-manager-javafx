package ifsp.poo.service;

import ifsp.poo.App;
import ifsp.poo.model.CourseClass;
import ifsp.poo.model.Professor;
import ifsp.poo.persistence.CourseClassDAO;

import java.util.ArrayList;
import java.util.List;

public class CourseClassService {

    private CourseClassDAO classDAO;

    public CourseClassService() {
        classDAO = new CourseClassDAO();
    }

    public void createClassAndSave(String subject, String course) {
        CourseClass courseClass = new CourseClass(subject, course);
        Professor professor = (Professor) App.getSessionUser();
        List<CourseClass> classes = new ArrayList<>();

        if (professor.getClasses() != null) {
            classes = professor.getClasses();
        }
        classes.add(courseClass);
        professor.setClasses(classes);
        courseClass.setProfessor(professor);
        classDAO.save(courseClass);
    }
}
