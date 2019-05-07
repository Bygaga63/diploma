package com.service.antenna.domain;

import lombok.Data;

import javax.persistence.*;


@Entity(name = "address")
@Data
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"street", "house", "flat_number"})})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String street;
    private String house;
    @Column(name = "flat_number")
    private String flatNumber;
}
