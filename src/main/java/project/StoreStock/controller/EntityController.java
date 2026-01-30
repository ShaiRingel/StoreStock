package project.StoreStock.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    private final ProductService productService;
    @Autowired
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
                .filter(name -> !name.equals("counter")  && !name.equals("serialVersionUID"))
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
                .filter(name -> !name.equals("counter") && !name.equals("serialVersionUID"))
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

    @GetMapping("/products-form")
    public String showProductForm(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Product product;

        if(id == null){
            product = new Product("", "", 0, null);
            product.setSupplier(new Supplier());
        }
        else {
            try {
                product = productService.get(id);
                if(product == null)
                    return "redirect:/products?error=notfound";
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("product",product);
        try {
            model.addAttribute("suppliers", supplierService.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "products-form";
    }

    @PostMapping("/save-product")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result, Model model) {
            if (result.hasErrors()) {
                try {
                    model.addAttribute("suppliers", supplierService.getAll());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return "products-form";
            }
            try {
                Supplier supplier = supplierService.get(product.getSupplier().getId());
                product.setSupplier(supplier);
                try{
                    if (productService.get(product.getId()) == null)
                        productService.save(product);
                    else
                        productService.update(product);
                }catch(Exception er){
                        throw new RuntimeException(er);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "redirect:/mainScreen";
        }

    @GetMapping("/suppliers-form")
    public String showSupplierForm(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Supplier supplier;
        if(id == null)
            supplier = new Supplier("","");
        else {
            try{
                supplier = supplierService.get(id);
                if(supplier == null)
                    return "redirect:/suppliers?error=notfound";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("supplier",supplier);
        return "suppliers-form";
    }

    @PostMapping("/save-supplier")
    public String saveSupplier(@Valid @ModelAttribute("supplier") Supplier supplier,
                              BindingResult result, Model model) {
        if (result.hasErrors())
            return "suppliers-form";
        try {
            if (supplierService.get(supplier.getId()) == null)
                supplierService.save(supplier);
            else
                supplierService.update(supplier);
        } catch (Exception er) {
            throw new RuntimeException(er);
        }
        return "redirect:/mainScreen";
    }


}
