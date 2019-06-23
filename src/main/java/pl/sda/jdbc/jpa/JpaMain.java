package pl.sda.jdbc.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaMain {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("configurationOfDB"); //sdajpa - tak sie nazywa konfiguracja dostepu do bazy danych, ktora jest wpisana w persistence.xml

    public static void main(String[] args) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        createCustomer(entityManager);

        System.out.println("*********************");

        System.out.println(findCustomersBySurname(entityManager, "Kowal"));

    }

    private static List<Customer> findCustomersBySurname(EntityManager entityManager, String surname) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "select c from Customer c where c.lastname = :ln",
                Customer.class
        );  //typowane query extends query
        query.setParameter("ln", surname);
        return query.getResultList();
    }

    public static Customer createCustomer(EntityManager entityManager) {
        Customer customer = new Customer();
        customer.setFirstname("Jan");
        customer.setLastname("Kowal");
        customer.setAge(33);
        customer.setCity("Warszawa");
        customer.setPostalCode("89-123");

        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();

        return customer;
    }


}
