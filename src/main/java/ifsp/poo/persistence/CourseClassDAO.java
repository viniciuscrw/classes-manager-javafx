package ifsp.poo.persistence;

import ifsp.poo.model.CourseClass;
import ifsp.poo.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CourseClassDAO {

    public void save(CourseClass courseClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(courseClass);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(CourseClass courseClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(courseClass);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
