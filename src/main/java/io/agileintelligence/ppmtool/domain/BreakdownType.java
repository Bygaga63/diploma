package io.agileintelligence.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BreakdownType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String type;
}
