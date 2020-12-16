package tacos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public AdminPanelController(UserRepository userRepository, OrderRepository orderRepository,
                                IngredientRepository ingredientRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String loadPanel(Model model) {
        model.addAttribute("users", userRepository.getAllUsers());
        model.addAttribute("orders", orderRepository.getAllOrders());
        model.addAttribute("ingrs", ingredientRepository.findAllIngredients());
        return "admin-panel";
    }
}
