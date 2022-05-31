package org.example.Web2.service;

import org.example.Web2.controllers.Response;
import org.example.Web2.domain.User;
import org.example.Web2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User tempUser = userRepo.findByUsername(userName);
        if (tempUser == null)
            throw new UsernameNotFoundException("Пользователь не найден");
        return tempUser;
    }

    private void send(String email, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        mailSender.send(mailMessage);
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
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String text = String.format(
                    "Здравствуйте, %s!\n" +
                            "Благодарим Вас за регистрацию на сайте \"ЖКХ\"\n" +
                            "Пожалуйста, для активации аккаунта перейдите по следующей ссылке: http://localhost:8081/activate/%s\n" +
                            "Если Вы не регистрировались, игнорируйте это сообщение",
                    user.getUsername(),
                    user.getActivationCode()
            );

            send(user.getEmail(), "Активация профиля на сайте \"ЖКХ\"", text);
        }

        response.setTarget("login");

        return response;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }
}