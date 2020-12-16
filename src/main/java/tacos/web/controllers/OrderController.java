package tacos.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.data.LogRecordRepository;
import tacos.data.OrderRepository;
import tacos.domain.Order;
import tacos.domain.User;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepository;
    private final LogRecordRepository logRecordRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, LogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping(path = "/current")
    public String processOrder(@Valid @ModelAttribute("order") Order order, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors())
            return "orderForm";
        order.setUser(user);
        order.placedAt();
        Order saved = orderRepository.saveOrder(order.getPlacedAt(), order.getCcNumber(), order.getCcExpiration(),
                order.getCcCVV(), order.getUser().getId());
        order.getTacos().forEach(taco -> orderRepository.saveOrderTacos(taco.getId(), saved.getId()));
        logRecordRepository.logAction(new Date(), "Save order: " + saved.getId(),
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
