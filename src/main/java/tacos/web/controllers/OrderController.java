package tacos.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.domain.Order;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(path = "/orders")
public class OrderController {

    @GetMapping(path = "/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping(path = "/current")
    public String processOrder(@Valid Order order, Errors errors) {
        if (errors.hasErrors())
            return "orderForm";
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
}
