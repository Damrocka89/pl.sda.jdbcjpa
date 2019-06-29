package pl.sda.jdbc.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString(exclude = "transientField")
@Getter //opcjonalne
@Setter
@Entity //must have
@Table(name = "Customers")  //opcjonalne
public class Customer extends BaseEntity {



    @Column(name = "first_name")  //opcjonalne
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private Integer age;
    private String pesel;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Order> orders;

    @Embedded
    private Adress adress;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    @ElementCollection
    private Set<String> nicknames = new HashSet<String>();

    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Cart cart;

    @Transient //to nie będzie się zapisywać
    private String transientField;


}
