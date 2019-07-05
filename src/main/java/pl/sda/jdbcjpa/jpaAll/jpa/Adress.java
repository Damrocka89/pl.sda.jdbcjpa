package pl.sda.jdbcjpa.jpaAll.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;


@Getter
@Setter
@ToString
@Embeddable
public class Adress {

   private String country;
   private String city;
   private String streetNo;
   private String postalCode;
}
