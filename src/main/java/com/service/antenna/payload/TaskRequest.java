package com.service.antenna.payload;

import com.service.antenna.domain.*;
import com.service.antenna.services.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class TaskRequest {
    private Long id;
    private Status status;
    private Integer priority;
    private Set<BreakdownType> breakdownType;
    private Area area;
    private Customer customer;
    private Set<Long> users = new HashSet<>();
    private Date dueDate;

//    public Task toTask(UserService userService){
//        Task task = new Task();
//
//        this.getU
//        userService.findOneRest();
//        return task;
//    }
}
