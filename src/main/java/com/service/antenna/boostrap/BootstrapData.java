package com.service.antenna.boostrap;

import com.service.antenna.domain.*;
import com.service.antenna.services.AreaService;
import com.service.antenna.services.BreakdownTypeService;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final AreaService areaService;
    private final BreakdownTypeService breakdownTypeService;
    private final TaskService taskService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
        User admin = userService.findOne("admin");
        if (admin == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("Bygaga63");
            user.setRole(Role.OWNER);
            user.setActive(true);
            user.setFullName("Дима");

            userService.saveUser(user);


            User user2 = new User();
            user2.setUsername("master");
            user2.setPassword("Bygaga63");
            user2.setRole(Role.USER);
            user2.setActive(true);
            user2.setFullName("Катя");

            userService.saveUser(user2);


            Task task = new Task();
            task.setStatus(Status.IN_PROGRESS);
            task.setArea(areaService.findOne(1L));
            task.setBreakdownType(Collections.singleton(breakdownTypeService.findOne(1L)));


            Address address = new Address();
            address.setFlatNumber("1");
            address.setHouse("1");
            address.setStreet("Зарубина");

            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setFullName("Foo Bar");
            customer.setPhone("89033260131");

            task.setCustomer(customer);
            task.setClosed(false);
            task.setUsers(Collections.singleton(user));
            task.setPriority(1);
            Date date = new Date();
            date.setMonth(Calendar.OCTOBER);
            task.setDueDate(date);
            taskService.save(task);
        }
    }

    private void initData() {

    }
}
