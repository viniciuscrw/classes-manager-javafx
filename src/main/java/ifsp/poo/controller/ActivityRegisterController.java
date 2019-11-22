package ifsp.poo.controller;

import ifsp.poo.App;
import ifsp.poo.model.Activity;
import ifsp.poo.model.CourseClass;
import ifsp.poo.service.ActivityService;
import ifsp.poo.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

public class ActivityRegisterController extends FormController {

    @FXML
    private TextField descriptionField;
    @FXML
    public CheckBox testCheckBox;
    @FXML
    public DatePicker activityDate;

    private Stage classStage;
    private CourseClass courseClass;
    private Activity activity;

    public void initializeElements(Activity activity) {
        this.activity = activity;

        if (activity.getId() != null) {
            loadWithTestData();
        }
    }

    private void loadWithTestData() {
        descriptionField.setText(activity.getDescription());
        activityDate.setValue(activity.getDate());
        testCheckBox.setSelected(activity.isTest());
    }

    @FXML
    @Override
    void confirm() {
        if(validFields()) {
            try {
                saveActivity();
                dialogStage.close();
                classStage.close();
                App.showClassManagerWithActivitySelected(courseClass);
            } catch (Exception e) {
                AlertUtils.showErrorAlert("Erro ao registrar nova atividade.");
                e.printStackTrace();
            }
        } else {
            AlertUtils.showWarningAlert("Ambos os campos devem ser preenchidos!");
        }
    }

    private void saveActivity() {
        ActivityService activityService = new ActivityService();

        if (activity.getId() != null) {
            activityService.updateExistingActivity(activity, descriptionField.getText(),
                    activityDate.getValue(), testCheckBox.isSelected());
        } else {
            activityService.createNewActivity(courseClass, descriptionField.getText(),
                    activityDate.getValue(), testCheckBox.isSelected());
        }
    }

    private boolean validFields() {
        return StringUtils.isNotEmpty(descriptionField.getText())
                && activityDate.getValue() != null;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public void setClassStage(Stage classStage) {
        this.classStage = classStage;
    }
}
