package pl.sda.jdbcjpa.jpaAll.jpa;

import com.google.common.collect.Lists;
import pl.sda.jdbcjpa.jpaAll.JpaDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class JpaMain {


    public static void main(String[] args) {
        EntityManager entityManager = JpaDao.getEntityManager();
        createCustomer(entityManager);

//        System.out.println("*********************");
//
//        System.out.println(findCustomersBySurname(entityManager, "Kowal"));
//
//        System.out.println("******************");

        Customer customerByPesel = findCustomerByPesel("124", entityManager);
        System.out.println(customerByPesel);

        addNewBook("Pan Tadzio", new Ebook(), entityManager);

        System.out.println(basicNamedQuery(entityManager, "Jan"));

        entityManager.close();
        JpaDao.getEntityManagerFactory().close();
    }

    private static List<Customer> basicNamedQuery(EntityManager entityManager, String name) {
        TypedQuery<Customer> findByFirstname = entityManager.createNamedQuery("findByFirstname", Customer.class);
        findByFirstname.setParameter("fn", name);
        return findByFirstname.getResultList();
    }

    private static void addNewBook(String title, Product book, EntityManager entityManager) {
        book.setProductName("Pan Tadzio");
        JpaDao.saveEntity(book);
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
        customer.setLastname("Nowak");
        customer.setAge(37);
        customer.setCustomerStatus(CustomerStatus.ACTIVATED);
        customer.setPesel("124");
        customer.getNicknames().add("bla");

        OrderLine orderLine = new OrderLine();
        orderLine.setPrice(BigDecimal.valueOf(50.50));
        orderLine.setProductName("Book");

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setPrice(BigDecimal.valueOf(550.50));
        orderLine2.setProductName("Bike");

        Order order1 = new Order();
        order1.setCustomerName(customer.getFirstname());
        order1.setTotalCost(BigDecimal.valueOf(100));
        order1.setCustomer(customer);
        order1.setOrderLine(Lists.newArrayList(orderLine));

        Order order2 = new Order();
        order2.setCustomerName(customer.getFirstname());
        order2.setTotalCost(BigDecimal.valueOf(300));
        order2.setCustomer(customer);
        order2.setOrderLine(Lists.newArrayList(orderLine2));

        orderLine.setOrderHeader(order1);
        orderLine2.setOrderHeader(order2);

        customer.setOrders(Lists.newArrayList(order1, order2));

        Cart cart = new Cart();
        cart.setCustomer(customer);

        customer.setCart(cart);

        entityManager.getTransaction().begin();  //transakcja jest potrzebna gdy wystepuja zmiany (persistence context po drodze miedzy entity menedzerem a baza danych)
//        entityManager.persist(order1);
//        entityManager.persist(order2);
//        entityManager.persist(cart);
        entityManager.persist(customer);  //jesli jest cascade persist to zapisze tez order i cart do DB
        entityManager.getTransaction().commit(); //zapisuje z persitence context do bazy danych

        return customer;
    }

    public static Customer findCustomerByPesel(String pesel, EntityManager entityManager) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "select c from Customer c where c.pesel = :ps", Customer.class
        );
        query.setParameter("ps", pesel);
        return query.getSingleResult();
    }


}
