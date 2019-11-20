package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.CourseClass;
import ifsp.poo.model.Professor;
import ifsp.poo.model.Student;
import ifsp.poo.service.StudentService;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

public class StudentRegisterController extends FormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField registrationField;

    private Stage classStage;
    private CourseClass courseClass;
    private Student student;

    public void initializeElements(Student student) {
        this.student = student;

        if (student.getId() != null) {
            loadWithStudentData();
        }

        ensureNumericValues(registrationField);
    }

    @FXML
    @Override
    void confirm() {
        if(validFields()) {
            try {
                saveStudent();
                dialogStage.close();
                classStage.close();
                App.showClassManagerWithStudentSelected(courseClass);
            } catch (Exception e) {
                AlertUtils.showErrorAlert("Erro ao registrar novo aluno.");
                e.printStackTrace();
            }
        } else {
            AlertUtils.showWarningAlert("Ambos os campos devem ser preenchidos!");
        }
    }

    private void saveStudent() throws Exception {
        StudentService studentService = new StudentService();

        if (student.getId() != null) {
            studentService.updateExistingStudent(student, nameField.getText(), registrationField.getText());
        } else {
            studentService.createNewStudent(courseClass, nameField.getText(), registrationField.getText());
        }
    }

    private void replaceIfStudentExists() {
        if (this.student != null && this.student.getName() != null) {
            this.courseClass.getStudents().remove(this.student);
        }
    }

    private void save(Professor professor) throws Exception {
//        ProfessorPersistence persistence = new ProfessorPersistence();
//        persistence.update(professor);
    }

    private void updateClasses(Professor professor, CourseClass updatedClass) {
        int index = professor.getClasses().indexOf(courseClass);
        professor.getClasses().set(index, updatedClass);
    }

    private boolean validFields() {
        return StringUtils.isNotEmpty(nameField.getText())
                && StringUtils.isNotEmpty(registrationField.getText());
    }

    private void loadWithStudentData() {
        nameField.setText(student.getName());
        registrationField.setText(student.getRegistration());
    }

    private void ensureNumericValues(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*|\\d+,\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public void setClassStage(Stage classStage) {
        this.classStage = classStage;
    }
}
