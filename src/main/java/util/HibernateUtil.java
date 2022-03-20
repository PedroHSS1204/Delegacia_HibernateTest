package util;

import modelos.Arma;
import modelos.Crime;
import modelos.Criminoso;
import modelos.Vitima;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static Configuration configuration;
    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    private static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration().configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(Arma .class);
            configuration.addAnnotatedClass(Crime .class);
            configuration.addAnnotatedClass(Criminoso .class);
            configuration.addAnnotatedClass(Vitima .class);
        }

        return configuration;
    }

    private static StandardServiceRegistry getServiceRegistry() {
        if (serviceRegistry == null) {
            serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(getConfiguration().getProperties())
                .build();
        }

        return serviceRegistry;
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory(getServiceRegistry());
        }

        return sessionFactory;
    }

    public static Session createSession() {
        return getSessionFactory().openSession();
    }
}
