package tacos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.data.RoleRepository;
import tacos.data.UserRepository;
import tacos.domain.User;
import tacos.web.forms.UserUpdateForm;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user{id}")
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("form")
    public UserUpdateForm updateForm() {
        return new UserUpdateForm();
    }

    @GetMapping
    public String load(@PathVariable("id") String userId, Model model) {
        System.out.println("2 " + userId);
        model.addAttribute("user", userRepository.findById(Long.valueOf(userId)).get());
        return "user-page";
    }

    @PostMapping
    public String processUserUpdate(@Valid @ModelAttribute("form") UserUpdateForm form, Errors errors) {
        User user = form.toUser(passwordEncoder);
        User existingUserWithSuchUsername = userRepository.findByUsername(user.getUsername());
        System.out.println("1 " + form.getId());
        if (existingUserWithSuchUsername != null && !existingUserWithSuchUsername.getId().equals(Long.valueOf(form.getId())))
            errors.rejectValue("username", "666", "user with this name already exists");
        if (errors.hasErrors())
            return "redirect:/user" + form.getId();
        userRepository.updateUser(Long.valueOf(form.getId()), user.getUsername(), user.getPassword(), user.getFullname(), user.getStreet(),
                user.getCity(), user.getState(), user.getZip(), user.getPhoneNumber());
        return "redirect:/user" + form.getId();
    }
}
