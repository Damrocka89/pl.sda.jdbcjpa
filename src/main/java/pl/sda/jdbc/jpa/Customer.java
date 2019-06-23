package pl.sda.jdbc.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany
    private List<Order> orders;

    @Transient //to nie będzie się zapisywać
    private String string;
}
