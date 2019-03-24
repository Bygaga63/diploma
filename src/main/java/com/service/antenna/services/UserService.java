package com.service.antenna.services;


import com.service.antenna.domain.Role;
import com.service.antenna.exceptions.UsernameAlreadyExistsException;
import com.service.antenna.repositories.UserRepository;
import com.service.antenna.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User saveUser (User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            newUser.setActive(true);
            newUser.setRole(Role.USER);
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Пользователь '"+newUser.getUsername()+"' уже существует");
        }

    }

    public User findOne(String username){
        return userRepository.findByUsername(username);
    }
}
