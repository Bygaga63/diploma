package com.service.antenna.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;
    private Integer priority;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date create_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date update_At;

    @ManyToMany
    @JoinTable(name = "task_breakdown",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "breakdown_id") })
    private Set<BreakdownType> breakdownType;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "user_task",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> user = new HashSet<>();

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;


    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

}
