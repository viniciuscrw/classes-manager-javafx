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
        createGradesToNewStudent(courseClass, student);

        List<Student> students = new ArrayList<>();

        if (courseClass.getStudents() != null) {
            students = courseClass.getStudents();
        }

        students.add(student);
        courseClass.setStudents(students);
    }

    private void createGradesToNewStudent(CourseClass courseClass, Student student) {
        courseClass.getActivities().forEach(
                activity -> {
                    Grade grade = new Grade(activity, 0d);
                    grade.setStudent(student);
                    gradeDAO.save(grade);
                    student.getGrades().add(grade);
                }
        );
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
                    calculateGradesAverage(student);
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
                    calculateGradesAverage(student);
                }
        );
    }

    public void removeStudent(CourseClass courseClass, Student student) {
        studentDAO.delete(student);
        courseClass.getStudents().remove(student);
    }

    public void updateGradeToStudent(Grade grade, Double newValue) {
        grade.setValue(newValue);
        gradeDAO.update(grade);
        calculateGradesAverage(grade.getStudent());
    }

    public void updateStudentsGrades(CourseClass courseClass) {
        courseClass.getStudents()
                .forEach(this::calculateGradesAverage);
    }

    private void calculateGradesAverage(Student student) {
        WeightedGradeCalculator gradeCalculator = new WeightedGradeCalculator();
        List<Double> weightedGrades = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        student.getGrades().forEach(grade -> {
            CalculationStrategy strategy;

            if (grade.getActivity().isTest()) {
                strategy = new TestCalculation();
                weightedGrades.add(gradeCalculator.calculateWeighted(grade.getValue(), strategy));
                weights.add(gradeCalculator.getGradeWeight(strategy));
            } else {
                strategy = new ActivityCalculation();
                weightedGrades.add(gradeCalculator.calculateWeighted(grade.getValue(), strategy));
                weights.add(gradeCalculator.getGradeWeight(strategy));
            }
        });

        Double weightedGradesSum = weightedGrades.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        Integer weightsSum = weights.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (weightsSum > 0) {
            Double gradesAverage = weightedGradesSum / weightsSum;
            student.setGradesAverage(gradesAverage);
        } else {
            student.setGradesAverage(0D);
        }
    }
}
