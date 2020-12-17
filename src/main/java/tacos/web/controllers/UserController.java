package tacos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.data.*;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.User;
import tacos.security.AuthorizeService;
import tacos.web.forms.UserUpdateForm;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/user{id}")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final LogRecordRepository logRecordRepository;
    private final AuthorizeService authorizeService;
    private final OrderRepository orderRepository;
    private final TacoRepository tacoRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, LogRecordRepository logRecordRepository,
                          AuthorizeService authorizeService, OrderRepository orderRepository,
                          TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.logRecordRepository = logRecordRepository;
        this.authorizeService = authorizeService;
        this.orderRepository = orderRepository;
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute("form")
    public UserUpdateForm updateForm() {
        return new UserUpdateForm();
    }

    @GetMapping
    @PreAuthorize("@authorizeService.isAvailableUserPage(#userId)")
    public String load(@PathVariable("id") String userId, Model model) {
        model.addAttribute("user", userRepository.findByStringId(userId));
        model.addAttribute("orders", orderRepository.getOrdersForUser(Long.valueOf(userId)));
        model.addAttribute("tacos", orderRepository.getOrdersForUser(Long.valueOf(userId)).stream()
                .collect(Collectors.toMap(Order::getId, o -> tacoRepository.findTacosForOrder(o.getId()))));
        model.addAttribute("ingrs", tacoRepository.getAllTacos().stream()
                .collect(Collectors.toMap(Taco::getId, t -> ingredientRepository.findIngrsForTaco(t.getId()))));
//        model.addAttribute("roles", roleRepository.findRolesForUser(Long.valueOf(userId)));
        return "user-page";
    }

    @PostMapping
    public String processUserUpdate(@Valid @ModelAttribute("form") UserUpdateForm form, Errors errors) {
        User user = form.toUser(passwordEncoder);
        User existingUserWithSuchUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserWithSuchUsername != null && !existingUserWithSuchUsername.getId().equals(Long.valueOf(form.getId())))
            errors.rejectValue("username", "666", "user with this name already exists");
        if (errors.hasErrors())
            return "redirect:/user" + form.getId();
        userRepository.updateUser(Long.valueOf(form.getId()), user.getUsername());
        logRecordRepository.logAction(new Date(), "Update user: " + form.getUsername(),
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return "redirect:/user" + form.getId();
    }
}
