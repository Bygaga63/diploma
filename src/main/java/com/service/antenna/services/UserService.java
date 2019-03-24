package com.service.antenna.services;


import com.service.antenna.domain.Role;
import com.service.antenna.domain.User;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.exceptions.UsernameAlreadyExistsException;
import com.service.antenna.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User findOne(String username) {
        return repository.findByUsername(username);
    }

    public Set<User> findAll() {
        return repository.findAll();

    }

    public boolean remove(Long userId) {
        repository.deleteById(userId);
        boolean isExists = repository.existsById(userId);
        return !isExists;
    }
}