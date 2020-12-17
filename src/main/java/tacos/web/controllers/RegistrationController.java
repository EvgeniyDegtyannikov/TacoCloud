package tacos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.LogRecordRepository;
import tacos.data.RoleRepository;
import tacos.data.UserRepository;
import tacos.domain.User;
import tacos.web.forms.RegistrationForm;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private final LogRecordRepository logRecordRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder, LogRecordRepository logRecordRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.logRecordRepository = logRecordRepository;
    }

    @ModelAttribute("form")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("form") RegistrationForm form, Errors errors) {
        User user = form.toUser(passwordEncoder);
        if (userRepository.findByUsername(user.getUsername()) != null)
            errors.rejectValue("username", "666", "user with this name already exists");
        if (errors.hasErrors())
            return "registration";
        String role = userRepository.getAllUsers().isEmpty() ? "admin" : "user";
        userRepository.saveUser(user.getUsername(), user.getPassword());
        User saved = userRepository.findByUsername(user.getUsername());
        roleRepository.addRole(roleRepository.findByName(role).getId(), saved.getId());
        logRecordRepository.logAction(new Date(), "Register user: " + saved.getUsername(),
                saved.getUsername());
        return "redirect:/login";
    }
}
