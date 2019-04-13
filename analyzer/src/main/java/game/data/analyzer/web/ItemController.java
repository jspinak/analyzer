package game.data.analyzer.web;

import game.data.analyzer.model.Item;
import game.data.analyzer.model.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;
    private final RecipeService recipeService;

    public ItemController(ItemService itemService, RecipeService recipeService) {

        this.itemService = itemService;
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        List<Recipe> recipes = recipeService.findAll();
        Recipe firstRecipe = recipes.get(0);
        System.out.println("number of inputs = " + firstRecipe.getInputs().size());
        firstRecipe.getInputs().forEach(input -> System.out.println(input.getName()));
		recipes.forEach(recipe -> recipe.getInputs().forEach(input -> System.out.println(input.getName())));
        model.addAttribute("recipes",recipes);

        return "homepage";
    }

    @GetMapping("/addItem")
    public String add(Model model) {
        Item newItem = new Item();
        model.addAttribute("item", newItem);
        return "addItem";
    }

    @PostMapping("/addItem")
    public String added(Model model, Item item)  {
        itemService.save(item);
        return "redirect:http://localhost:8080/";
    }

}
