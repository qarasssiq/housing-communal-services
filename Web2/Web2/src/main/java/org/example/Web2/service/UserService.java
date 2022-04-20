package org.example.Web2.service;

import org.example.Web2.controllers.Response;
import org.example.Web2.domain.User;
import org.example.Web2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User tempUser = userRepo.findByUsername(userName);
        if (tempUser == null)
            throw new UsernameNotFoundException("Пользователь не найден");
        return tempUser;
    }

    public Response createUser(String username,
                           String password,
                           String firstname,
                           String lastname,
                           String patronymic,
                           String phoneNumber,
                           String email){
        Response response = new Response();

        if (userRepo.existsByUsername(username)) {
            response.setMessage("User exists!");
            response.setTarget("registration");
            return response;
        }
        User user = new User(username, password, "ADMIN", firstname, lastname, patronymic, phoneNumber, email);
        user.setActive(true);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepo.save(user);

        response.setTarget("redirect:/login");

        return response;
    }
}