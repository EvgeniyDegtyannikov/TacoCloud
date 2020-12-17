package tacos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.*;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.User;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminPanelController(UserRepository userRepository, OrderRepository orderRepository,
                                IngredientRepository ingredientRepository, RoleRepository roleRepository,
                                TacoRepository tacoRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String loadPanel(Model model) {
        model.addAttribute("users", userRepository.getAllUsers());
        model.addAttribute("roles", userRepository.getAllUsers().stream()
                .collect(Collectors.toMap(User::getId, u -> roleRepository.findRolesForUser(u.getId()))));
        model.addAttribute("orders", orderRepository.getAllOrders());
        model.addAttribute("tacos", orderRepository.getAllOrders().stream()
                .collect(Collectors.toMap(Order::getId, o -> tacoRepository.findTacosForOrder(o.getId()))));
        model.addAttribute("ingrs", tacoRepository.getAllTacos().stream()
                .collect(Collectors.toMap(Taco::getId, t -> ingredientRepository.findIngrsForTaco(t.getId()))));
        return "admin-panel";
    }
}
