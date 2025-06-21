package io.github.library.libraryapi.security;

import io.github.library.libraryapi.model.User;
import io.github.library.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return userService.findByLogin(login);
    }
}
