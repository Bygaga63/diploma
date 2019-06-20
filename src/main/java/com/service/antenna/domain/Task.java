package com.service.antenna.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
//import java.util.Date;
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
    @NotBlank
    private Status status;
    @NotBlank
    private Integer priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_At")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_At")
    @Temporal(TemporalType.DATE)
    private Date updateAt;

    private boolean isClosed;

    @ManyToMany
    @JoinTable(name = "task_breakdown",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "breakdown_id") })
    private Set<BreakdownType> breakdownType;

    @OneToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "user_task",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;


    @PrePersist
    protected void onCreate(){
        this.createAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = new Date();
    }

}
