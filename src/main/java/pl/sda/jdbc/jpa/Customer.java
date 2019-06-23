package pl.sda.jdbc.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity //must have
public class Customer {

    @Id //must have
    @GeneratedValue(strategy = GenerationType.AUTO) //must have
    private Integer id;

    private String firstname;
    private String lastname;
    private Integer age;
    private String city;
    private String postalCode;

    @Transient //to nie będzie się zapisywać
    private String string;
}
