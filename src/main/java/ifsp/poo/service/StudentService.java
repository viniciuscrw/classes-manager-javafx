package ifsp.poo.service;

import ifsp.poo.model.Activity;
import ifsp.poo.model.CourseClass;
import ifsp.poo.model.Grade;
import ifsp.poo.model.Student;
import ifsp.poo.persistence.GradeDAO;
import ifsp.poo.persistence.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private StudentDAO studentDAO;
    private GradeDAO gradeDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
        gradeDAO = new GradeDAO();
    }

    public void createNewStudent(CourseClass courseClass, String name, String registration) {
        Student student = new Student(name, registration);
        student.setCourseClass(courseClass);
        studentDAO.save(student);

        List<Student> students = new ArrayList<>();

        if (courseClass.getStudents() != null) {
            students = courseClass.getStudents();
        }

        students.add(student);
        courseClass.setStudents(students);
    }

    public void updateExistingStudent(Student student, String name, String registration) {
        student.setName(name);
        student.setRegistration(registration);

        studentDAO.update(student);
    }

    public void createNewGradeToStudents(CourseClass courseClass, Activity activity) {
        courseClass.getStudents().forEach(
                student -> {
                    Grade grade = new Grade(activity, 0d);
                    grade.setStudent(student);
                    gradeDAO.save(grade);
                    student.getGrades().add(grade);
                }
        );
    }

    public void removeGradeToStudents(CourseClass courseClass, Activity activity) {
        courseClass.getStudents().forEach(
                student -> {
                    Grade gradeToRemove = null;
                    for (Grade grade : student.getGrades()) {
                        if (grade.getActivity().equals(activity)) {
                            gradeToRemove = grade;
                            gradeDAO.delete(grade);
                        }
                    }
                    student.getGrades().remove(gradeToRemove);
                }
        );
    }

    public void removeStudent(CourseClass courseClass, Student student) {
        studentDAO.delete(student);
        courseClass.getStudents().remove(student);
    }
}
