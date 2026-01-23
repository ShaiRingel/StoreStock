package project.StoreStock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;
import project.StoreStock.service.ProductService;
import project.StoreStock.service.SupplierService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Controller
public class EntityController {

    private final ProductService productService;
    private final SupplierService supplierService;

    @Autowired
    public EntityController(ProductService productService,
                            SupplierService supplierService) {
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping("/products")
    public String productsScreen(Model model) {
        model.addAttribute("page", "products");

        List<String> fields = Arrays.stream(Product.class.getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("counter"))
                .toList();

        try {
            model.addAttribute("fields", fields);
            model.addAttribute("entities", productService.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "main-screen";
    }

    @GetMapping("/suppliers")
    public String suppliersScreen(Model model) {
        model.addAttribute("page", "suppliers");

        List<String> fields = Arrays.stream(Supplier.class.getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("counter"))
                .toList();

        try {
            model.addAttribute("fields", fields);
            model.addAttribute("entities", supplierService.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "main-screen";
    }

    @PostMapping("/add")
    public String goToAddScreen(@RequestParam(name = "page") String page) {
        return "redirect:/" + page + "-form";
    }

    @PostMapping("/edit")
    public String goToEditScreen(
            @RequestParam(name = "selectedId", required = false) Integer id,
            @RequestParam(name = "page") String page) {

        if (id == null) {
            return "redirect:/" + page + "?error=noSelection";
        }

        return "redirect:/" + page + "-form?id=" + id;
    }

    @PostMapping("/show")
    public String goToShowScreen(
            @RequestParam(name = "selectedId", required = false) Integer id,
            @RequestParam(name = "page") String page) {

        if (id == null) {
            return "redirect:/" + page + "?error=noSelection";
        }

        return "redirect:/" + page + "-details?id=" + id;
    }

    @PostMapping("/delete")
    public String deleteSelected(
            @RequestParam(name = "selectedId", required = false) Integer id,
            @RequestParam(name = "page") String page) {

        if (id == null) {
            return "redirect:/" + page + "?error=noSelection";
        }

        try {
            if (page.equals("products")) {
                productService.delete(id);
            } else if (page.equals("suppliers")) {
                supplierService.delete(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/" + page;
    }

}
