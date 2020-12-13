package tacos.web.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private UserRepository userRepository;

    @Autowired
    public AdminPanelController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String loadPanel(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-panel";
    }

}
