package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.codegym.model.Product;
import vn.codegym.service.IProductService;
import vn.codegym.service.IProductTypeService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    @ModelAttribute("productTypes")
    public Iterable<vn.codegym.model.ProductType> productTypes() {
        return productTypeService.findAll();
    }

    @GetMapping("")
    public String showList(Model model,
                           @RequestParam(name = "nameSearch", defaultValue = "") String nameSearch,
                           @RequestParam(name = "priceSearch", required = false) Double priceSearch,
                           @RequestParam(name = "typeSearch", required = false) Integer typeSearch,
                           @PageableDefault(size = 5) Pageable pageable) {

        model.addAttribute("products", productService.findAll(nameSearch, priceSearch, typeSearch, pageable));
        model.addAttribute("nameSearch", nameSearch);
        model.addAttribute("priceSearch", priceSearch);
        model.addAttribute("typeSearch", typeSearch);

        return "list";
    }

    @PostMapping("/delete")
    public String deleteProducts(@RequestParam("productIds") int[] productIds, RedirectAttributes redirectAttributes) {
        productService.removeMultiple(productIds);
        redirectAttributes.addFlashAttribute("message", "Đã xóa các sản phẩm được chọn!");
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String deleteOneProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        productService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm thành công!");
        return "redirect:/product";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Thêm mới sản phẩm thành công!");
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "edit";
        }
        return "redirect:/product";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
        return "redirect:/product";
    }
}