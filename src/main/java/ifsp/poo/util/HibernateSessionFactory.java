package ifsp.poo.util;

import ifsp.poo.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateSessionFactory {

    private static SessionFactory instance;

    private HibernateSessionFactory() {}

    public static SessionFactory getInstance() {
        if (instance == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/classmanagerdb?createDatabaseIfNotExist=true" +
                        "&useUnicode=yes&characterEncoding=UTF-8&useSSL=false&useTimezone=true&serverTimezone=UTC");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "password");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Professor.class);
                configuration.addAnnotatedClass(CourseClass.class);
                configuration.addAnnotatedClass(Activity.class);
                configuration.addAnnotatedClass(Student.class);
                configuration.addAnnotatedClass(Grade.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                instance = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
