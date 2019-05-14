package com.service.antenna.boostrap;

import com.service.antenna.domain.Role;
import com.service.antenna.domain.User;
import com.service.antenna.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
        User admin = userService.findOne("admin");
        if (admin == null){
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
        }


    }

    private void initData() {

    }
}
