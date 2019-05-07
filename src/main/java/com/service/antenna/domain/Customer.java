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

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    private Address address;
    private String phone;

}
