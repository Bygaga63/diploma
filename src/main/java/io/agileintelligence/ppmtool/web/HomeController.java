package io.agileintelligence.ppmtool.web;

import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import io.agileintelligence.ppmtool.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.agileintelligence.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@Controller
public class HomeController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/")
    public String validateUser(Authentication oauthAuthentication, HttpServletResponse response) throws IOException {
        if (oauthAuthentication == null) {
            return "redirect:/login";
        }
        User user = (User) oauthAuthentication.getPrincipal();
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(user);
        return "redirect:http://localhost:3000/dashboard?token=" + jwt;

    }
}
