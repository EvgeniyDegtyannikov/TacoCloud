package tacos.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Order;
import tacos.domain.Taco;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(path = "/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredientList::add);
        for (Ingredient.Type type : Ingredient.Type.values()
        ) {
            model.addAttribute(type.toString().toLowerCase(), ingredientList.stream()
                    .filter(t -> t.getType().equals(type))
                    .collect(Collectors.toList())
            );
        }
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("taco") Taco taco, Errors errors, @ModelAttribute("order") Order order) {
        if (errors.hasErrors())
            return "design";
        Taco saved = tacoRepository.save(taco);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
}
