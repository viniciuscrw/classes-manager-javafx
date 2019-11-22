package ifsp.poo;

import ifsp.poo.controller.*;
import ifsp.poo.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Stage primaryStage;
    private static User sessionUser;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Gerenciador de Turmas");
        initializeLogin();
    }

    private void initializeLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/Login.fxml"));
            AnchorPane login = loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(login));
            loginStage.setTitle("Gerenciador de Turmas - Login");
            loginStage.show();
            LoginController controller = loader.getController();
            controller.setDialogStage(loginStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeProgram() {
        initializeMainLayout();
    }

    private static void initializeMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/MainLayout.fxml"));

            AnchorPane main = loader.load();
            primaryStage.setScene(new Scene(main));
            primaryStage.setTitle("Gerenciador de Turmas");
            primaryStage.show();

            MainLayoutController controller = loader.getController();
            controller.setDialogStage(primaryStage);
            controller.initializeElements();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSignupForm(Stage loginStage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/UserSignup.fxml"));
            AnchorPane signup = loader.load();
            Stage signupStage = new Stage();
            signupStage.setScene(new Scene(signup));
            signupStage.setTitle("Cadastro de Usuário");
            signupStage.show();

            UserSignupController controller = loader.getController();
            controller.setDialogStage(signupStage);
            controller.setLoginStage(loginStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showClassRegisterForm(Stage welcomeStage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ClassRegister.fxml"));
            AnchorPane register = loader.load();
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(register));
            registerStage.setTitle("Nova Turma");
            registerStage.show();

            ClassRegisterController controller = loader.getController();
            controller.setDialogStage(registerStage);
            controller.setMainStage(welcomeStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showClassManagerWithStudentSelected(CourseClass courseClass) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ClassManager.fxml"));
            TabPane manager = loader.load();
            SingleSelectionModel<Tab> selectionModel = manager.getSelectionModel();
            selectionModel.select(0);
            loadClassManagerDefault(manager, loader, courseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showClassManagerWithActivitySelected(CourseClass courseClass) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ClassManager.fxml"));
            TabPane manager = loader.load();
            SingleSelectionModel<Tab> selectionModel = manager.getSelectionModel();
            selectionModel.select(1);
            loadClassManagerDefault(manager, loader, courseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadClassManagerDefault(TabPane manager, FXMLLoader loader, CourseClass courseClass) {
        Stage managerStage = new Stage();
        managerStage.setScene(new Scene(manager));
        managerStage.setTitle(courseClass.getCode());
        managerStage.show();

        ClassManagerController controller = loader.getController();
        controller.setDialogStage(managerStage);
        controller.initalizeElements(courseClass);
    }

    public static void showStudentRegisterForm(Stage classStage, CourseClass courseClass, Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/StudentRegister.fxml"));
            AnchorPane register = loader.load();
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(register));
            registerStage.setTitle(student.getName() == null ? "Novo Aluno" : "Editar Aluno");
            registerStage.show();

            StudentRegisterController controller = loader.getController();
            controller.setDialogStage(registerStage);
            controller.setClassStage(classStage);
            controller.setCourseClass(courseClass);
            controller.initializeElements(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showActivityRegisterForm(Stage classStage, CourseClass courseClass, Activity activity) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ActivityRegister.fxml"));
            AnchorPane register = loader.load();
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(register));
            registerStage.setTitle(activity.getDescription() == null ? "Nova Atividade" : "Editar Atividade");
            registerStage.show();

            ActivityRegisterController controller = loader.getController();
            controller.setDialogStage(registerStage);
            controller.setClassStage(classStage);
            controller.setCourseClass(courseClass);
            controller.initializeElements(activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showStudentGrades(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/StudentGrades.fxml"));
            AnchorPane register = loader.load();
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(register));
            registerStage.setTitle(student.getName() + " - Desempenho do Aluno");
            registerStage.show();

            StudentGradesController controller = loader.getController();
            controller.setDialogStage(registerStage);
            controller.initializeElements(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showActivityCorrector(Activity activity) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ActivityCorrector.fxml"));
            AnchorPane register = loader.load();
            Stage correctorStage = new Stage();
            correctorStage.setScene(new Scene(register));
            correctorStage.setTitle("Correção de Atividade");
            correctorStage.show();

            ActivityCorrectorController controller = loader.getController();
            controller.setDialogStage(correctorStage);
            controller.initializeElements(activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAddGradeForm(Grade grade, Activity activity) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/AddGrade.fxml"));
            AnchorPane register = loader.load();
            Stage gradeStage = new Stage();
            gradeStage.setScene(new Scene(register));
            gradeStage.setTitle("Adicionar Nota - " + activity.getCode());
            gradeStage.show();

            AddGradeController controller = loader.getController();
            controller.initializeElements(grade, activity);
            controller.setDialogStage(gradeStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(User sessionUser) {
        App.sessionUser = sessionUser;
    }

    public static void main(String[] args) {
        launch();
    }

}