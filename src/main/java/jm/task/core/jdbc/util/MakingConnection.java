package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class MakingConnection {

    //принимает строку, создает объект jdbc или hibernate, и его возвращает
    public static UserDao returnSpecificDao() {
        UserDao userDao = null;
        String stringFromFile = ReadFile.readLineInFile();
        switch (stringFromFile.toLowerCase()) {
            case "jdbc" -> userDao = new UserDaoJDBCImpl();
            case "hibernate" -> userDao = new UserDaoHibernateImpl();
        }
        return userDao;
    }
}
