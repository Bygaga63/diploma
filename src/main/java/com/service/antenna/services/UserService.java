package com.service.antenna.services;


import com.service.antenna.domain.Role;
import com.service.antenna.domain.User;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.exceptions.UsernameAlreadyExistsException;
import com.service.antenna.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User saveUser(User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            newUser.setActive(true);

            if (newUser.getRole() == null) {
                newUser.setRole(Role.USER);
            }

            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return repository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Пользователь '" + newUser.getUsername() + "' уже существует");
        }

    }

    public void checkAuthority(String name) {
        User user = findOne(name);

        if (user.getRole() == Role.USER) {
            throw new CustomException("У вас недостаточно полномочий");
        }
    }

    public void update(User user){
        User fromDb = findOneRest(user.getId());
        fromDb.setUsername(user.getUsername());
        fromDb.setRole(user.getRole());
        fromDb.setFullName(user.getFullName());

        if (StringUtils.hasText(user.getPassword())) {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                fromDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            } else {
                throw new CustomException("Пароли не совпадают");
            }
        }

        repository.save(fromDb);
    }

    public User findOne(String username) {
        return repository.findByUsername(username);
    }

    public User findOneRest(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public Set<User> findAll() {
        return repository.findAll();

    }

    public Set<User> findAll(Set<Long> id) {
        return repository.findAllById(id);

    }

    public boolean remove(Long userId) {
        repository.deleteById(userId);
        boolean isExists = repository.existsById(userId);
        return !isExists;
    }
}
