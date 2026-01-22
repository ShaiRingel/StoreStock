package project.StoreStock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.StoreStock.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/mainpage")
    public String mainScreen(Model model) {
        try {
            model.addAttribute("products", productService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main-screen";
    }

    @PostMapping("/add")
    public String goToAddScreen() {
        return "redirect:/product-form";
    }

    @PostMapping("/edit")
    public String goToEditScreen(@RequestParam(name = "selectedId", required = false) Integer id) {
        if (id == null) {
            return "redirect:/";
        }
        return "redirect:/product-form?id=" + id;
    }

    @PostMapping("/show")
    public String goToShowScreen(@RequestParam(name = "selectedId", required = false) Integer id) {
        if (id == null) {
            return "redirect:/";
        }
        return "redirect:/product-details?id=" + id;
    }

    @PostMapping("/delete")
    public String deleteSelected(@RequestParam(name = "selectedId", required = false) Integer id) {
        if (id != null) {
            try {
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }
}
