package com.service.antenna.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "customer")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"phone", "full_name"})})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(nullable = false)
    private String street;
    private String house;
    @Column(name = "flat_number")
    private String flatNumber;
    private String phone;

}
