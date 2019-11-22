package ifsp.poo.service;

import ifsp.poo.model.Activity;
import ifsp.poo.model.ActivityStatus;
import ifsp.poo.model.CourseClass;
import ifsp.poo.model.Grade;
import ifsp.poo.persistence.ActivityDAO;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityService {

    private ActivityDAO activityDAO;
    private StudentService studentService;

    public ActivityService() {
        activityDAO = new ActivityDAO();
        studentService = new StudentService();
    }

    public void createNewActivity(CourseClass courseClass, String description, LocalDate date, boolean isTest) {
        Activity activity = new Activity(description, date, isTest);
        activity.setCourseClass(courseClass);
        activityDAO.save(activity);

        Set<Activity> activities = new HashSet<>();

        if (courseClass.getActivities() != null) {
            activities = courseClass.getActivities();
        }

        activities.add(activity);
        courseClass.setActivities(activities);
        studentService.createNewGradeToStudents(courseClass, activity);
    }

    public void updateExistingActivity(Activity activity, String description, LocalDate date, boolean isTest) {
        activity.setDate(date);
        activity.setDescription(description);

        if (activity.isTest() != isTest) {
            activity.setTest(isTest);
            studentService.updateStudentsGrades(activity.getCourseClass());
        }

        activityDAO.update(activity);
    }

    public void removeActivity(CourseClass courseClass, Activity activity) {
        activityDAO.delete(activity);
        courseClass.getActivities().remove(activity);
        studentService.removeGradeToStudents(courseClass, activity);
    }

    public List<Grade> getActivityGrades(Activity activity) {
        List<Grade> activityGrades = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(activity.getCourseClass().getStudents())) {
            activity.getCourseClass().getStudents().forEach(
                    student -> student.getGrades().forEach(
                            grade -> {
                                if (grade.getActivity().equals(activity)) {
                                    activityGrades.add(grade);
                                }
                            }
                    )
            );
        }
        return activityGrades;
    }

    public void updateActivityStatus(Activity activity) {
        if (!ActivityStatus.CORRECTED.getValue().equals(activity.getStatus())) {
            activity.setStatus(ActivityStatus.CORRECTED.getValue());
            activityDAO.update(activity);
        }
    }
}
