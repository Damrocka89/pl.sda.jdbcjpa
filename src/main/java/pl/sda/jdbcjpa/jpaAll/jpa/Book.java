package pl.sda.jdbcjpa.jpaAll.jpa;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends Product {



}
