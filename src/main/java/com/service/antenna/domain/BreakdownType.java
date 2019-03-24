package com.service.antenna.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "breakdown_type")
@NoArgsConstructor
public class BreakdownType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private  String type;

    public BreakdownType(String type) {
        this.type = type;
    }
}
