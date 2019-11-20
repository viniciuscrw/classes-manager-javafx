package ifsp.poo.service;

import ifsp.poo.model.Activity;
import ifsp.poo.model.CourseClass;
import ifsp.poo.persistence.ActivityDAO;

import java.time.LocalDate;
import java.util.HashSet;
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
        activity.setTest(isTest);
        activity.setDescription(description);

        activityDAO.update(activity);
    }

    public void removeActivity(CourseClass courseClass, Activity activity) {
        activityDAO.delete(activity);
        courseClass.getActivities().remove(activity);
        studentService.removeGradeToStudents(courseClass, activity);
    }
}
