package io.agileintelligence.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;

    @ManyToMany
    @JoinTable(name = "area_user",
            joinColumns = { @JoinColumn(name = "area_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users;
}
