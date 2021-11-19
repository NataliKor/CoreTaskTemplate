package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override //создаем таблицу User
    public void createUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS public.\"users\" (id serial NOT NULL PRIMARY KEY, " +
                    "name character varying(30) NOT NULL, lastName character varying(30) NOT NULL, age smallint NOT NULL)";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override //удаляем таблицу User
    public void dropUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS public.\"users\"";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override//поиск user-а в таблице
    public User findUserInTable(String name1, String lastName1, byte age1) {
        User resultFind = null;
        String sql = "SELECT user FROM User user WHERE user.name = ?1 AND user.lastName = ?2 AND user.age = ?3";
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(sql);
            query.setParameter(1, name1);
            query.setParameter(2, lastName1);
            query.setParameter(3, age1);
            try {
                resultFind = (User) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return resultFind;
    }

    @Override //сохраняем User
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override //удаляем User по его Id
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override //возвращаем всех Users
    public List<User> getAllUsers() {
        List<User> list = null;
        String sql = "SELECT user FROM User user";
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            list = session.createQuery(sql).list();
            for (User user : list) {
                System.out.println(user.toString());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override //очищаем таблицу User
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getInstance().getSessionFactory(); Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE public.\"users\"";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
