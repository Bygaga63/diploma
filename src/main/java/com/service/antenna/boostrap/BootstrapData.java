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
            user.setFullName("admin");

            userService.saveUser(user);
        }


    }

    private void initData() {

    }
}
