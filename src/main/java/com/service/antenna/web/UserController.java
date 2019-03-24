package com.service.antenna.web;

import com.service.antenna.exceptions.UsernameAlreadyExistsException;
import com.service.antenna.payload.JWTLoginSucessReponse;
import com.service.antenna.payload.LoginRequest;
import com.service.antenna.security.JwtTokenProvider;
import com.service.antenna.security.SecurityConstants;
import com.service.antenna.services.MapValidationErrorService;
import com.service.antenna.services.UserService;
import com.service.antenna.validator.UserValidator;
import com.service.antenna.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final MapValidationErrorService mapValidationErrorService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(user);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User existUser = userService.findOne(user.getUsername());

        if (existUser != null) {
            if (existUser.isActive()) {
                throw new UsernameAlreadyExistsException("Username '" + user.getUsername() + "' already exists");
            } else {
                user.setId(existUser.getId());
            }
        }
        userService.saveUser(user);

        return new ResponseEntity(HttpStatus.OK);
    }


}
