package ifsp.poo.persistence;

import ifsp.poo.model.Professor;
import ifsp.poo.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ProfessorDAO {

    public void save(Professor professor) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.save(professor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Professor getByUsernameAndPassword(String username, String password) {
        try (Session session = HibernateSessionFactory.getInstance().openSession()) {
            Query query = session.createQuery("FROM Professor WHERE username = :username" +
                    " AND password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return (Professor) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
