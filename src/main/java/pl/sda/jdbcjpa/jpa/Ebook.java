package pl.sda.jdbcjpa.jpa;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class Ebook extends Product{



}
