package com.service.antenna.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, name = "caption")
    private String caption;

    @ManyToMany
    @JoinTable(name = "area_user",
            joinColumns = { @JoinColumn(name = "area_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users;

    public Area(String caption) {
        this.caption = caption;
    }
}
