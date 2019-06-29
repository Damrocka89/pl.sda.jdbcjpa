package pl.sda.jdbcjpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaDao {  //dao - data access object

    //zakladamy jednowątkowość
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("configurationOfDB"); //sdajpa - tak sie nazywa konfiguracja dostepu do bazy danych, ktora jest wpisana w persistence.xml


    private static EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();


    public static <E extends BaseEntity> E saveEntity(E entity) {
        entityManager.getTransaction().begin();
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
        entityManager.getTransaction().commit();
        return entity;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
