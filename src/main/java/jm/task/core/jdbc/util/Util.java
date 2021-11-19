package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    // реализуйте настройку соеденения с БД
    private static Util instance;

    private static final String URL = "jdbc:postgresql://localhost:5432/bd_users";
    private static final String USER = "postgres";
    private static final String PASSWORD = "ab1756404";

    private Util() {
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //подключение к БД через JDBC
    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //подключение к БД через Hibernate
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("hibernate.connection.username", USER);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("Dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

        StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(serviceRegistry.build());
    }
}
