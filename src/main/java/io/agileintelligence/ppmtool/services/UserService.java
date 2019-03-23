package io.agileintelligence.ppmtool.services;


import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.exceptions.ActivateException;
import io.agileintelligence.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private MailSender mailSender;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, MailSender mailSender, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser (User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            newUser.setActivationCode(UUID.randomUUID().toString());
            newUser.setActive(false);
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            String message = String.format(
                    "День добрый, %s! \n" +
                            "Код подтверждения: %s\n" +
                            "для успешной регистрации пройдите по ссылку: http://localhost:8080/api/users/activate/%s",
                    newUser.getFullName(),
                    newUser.getActivationCode(),
                    newUser.getActivationCode()
            );

            mailSender.send(newUser.getUsername(), "Код подтверждения", message);
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Пользователь '"+newUser.getUsername()+"' уже существует");
        }

    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            throw new ActivateException("Код не найден");
        }
        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public User findOne(String username){
        return userRepository.findByUsername(username);
    }
}
