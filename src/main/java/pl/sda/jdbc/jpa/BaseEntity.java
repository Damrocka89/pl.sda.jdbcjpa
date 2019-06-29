package pl.sda.jdbc.jpa;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass //encje dziedziczące dostaną te pola
public abstract class BaseEntity {

    @Id //must have
    @GeneratedValue(strategy = GenerationType.AUTO) //must have
    private Integer id;
}
