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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
            customer.setFullName("Семеном Григорий");
            customer.setPhone("89033260131");

            task.setCustomer(customer);
            task.setClosed(false);
            task.setUsers(Collections.singleton(user2));
            task.setPriority(1);
            Date date = new Date();
            date.setMonth(Calendar.OCTOBER);
            task.setDueDate(date);
            taskService.save(task);



            Task task1 = new Task();
            task1.setStatus(Status.IN_PROGRESS);
            task1.setArea(areaService.findOne(1L));
            task1.setBreakdownType(Collections.singleton(breakdownTypeService.findOne(1L)));


            Address address1 = new Address();
            address1.setFlatNumber("51");
            address1.setHouse("53а");
            address1.setStreet("Лебедева");

            Customer customer1 = new Customer();
            customer1.setAddress(address1);
            customer1.setFullName("Иванов Иван");
            customer1.setPhone("636363");

            task1.setCustomer(customer1);
            task1.setClosed(false);
            task1.setUsers(Collections.singleton(user2));
            task1.setPriority(1);
            Date date1 = new Date();
            date1.setMonth(Calendar.OCTOBER);
            task1.setDueDate(date1);
//
//
//
//
            taskService.save(task1);


            Task task2 = new Task();
            task2.setStatus(Status.IN_PROGRESS);
            task2.setArea(areaService.findOne(1L));
            task2.setBreakdownType(Collections.singleton(breakdownTypeService.findOne(1L)));


            Address address2 = new Address();
            address2.setFlatNumber("67");
            address2.setHouse("15");
            address2.setStreet("Ивана Кырли");

            Customer customer2 = new Customer();
            customer2.setAddress(address2);
            customer2.setFullName("Иванов Иван");
            customer2.setPhone("252525");

            task2.setCustomer(customer2);
            task2.setClosed(false);
            task2.setUsers(Collections.singleton(user));
            task2.setPriority(1);
            Date date2 = new Date();
            date.setMonth(Calendar.OCTOBER);
            task2.setDueDate(date2);




            taskService.save(task2);
        }
    }

    private void initData() {

    }
}
