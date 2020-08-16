package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.entity.ProductImage;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ImageSaverService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductFilter productFilter;
    private final ImageSaverService imageSaverService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService,
                             ProductFilter productFilter, ImageSaverService imageSaverService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productFilter = productFilter;
        this.imageSaverService = imageSaverService;
    }

    @GetMapping
    public String indexPage() {
        return "redirect:/admin/product/all";
    }

    @GetMapping("/all")
    public String showAll(@RequestParam Map<String, String> params, Model model,
                          HttpSession session) {
        session.removeAttribute("product");
        session.removeAttribute("productErrors");
        //инициируем настройки фильтра
        productFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(productFilter, "id");//TODO id -> константы
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));//TODO title -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", productFilter.getFilterDefinition());
        //коллекцию категорий
        model.addAttribute("categories", categories);
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Products");
        return "amin/admin/products";
    }

    @GetMapping("/create")
    public RedirectView createNew(Model model, HttpSession session) {
        if(session.getAttribute("product") == null) {
            Product product = new Product();
            session.setAttribute("product", product);
        }
        return new RedirectView("/amin/admin/product/edit/0/prod_id");
    }

    @GetMapping("/edit/{prod_id}/prod_id")
    public String edit(@PathVariable Long prod_id, Model model,
                              HttpSession session) {
        Product product;
        if(prod_id != 0) {
            product = productService.findById(prod_id);
            session.setAttribute("product", product);
            //TODO replace List with Map
            MultipartFile file = imageSaverService.getFile(
                    product.getImages().get(0).getPath());
            model.addAttribute("imageFile", file);
        } else {
            //новый продукт созданный заранее
            product = (Product) session.getAttribute("product");
        }
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("productErrors", session.getAttribute("productErrors"));

        return "amin/admin/product-form";
    }

    @GetMapping("/delete/{prod_id}/prod_id")
    public String delete(@PathVariable Long prod_id) {
        Product product = productService.findById(prod_id);
        productService.delete(product);
        return "redirect:/admin/product/all";
    }

    @PostMapping("/process/edit")
    @Transactional
    public RedirectView processUpdate(@ModelAttribute @Valid Product product,
                                      BindingResult bindingResult, HttpSession session,
                                      @RequestParam("file") MultipartFile file) {
        //in order to save filled information in the form
        session.setAttribute("product", product);
        Long prod_id = product.getId();
        //for new product creating
        if (prod_id == null) {
            if (productService.isProductWithTitleExists(product.getTitle())) {
                bindingResult.addError(new ObjectError("product.title", "Товар с таким названием уже существует"));
            }

            if (productService.isProductWithVendorCodeExists(product.getVendorCode())) {
                bindingResult.addError(new ObjectError("product.vendorCode", "Товар с таким vendor code уже существует"));
            }

            if(file.isEmpty()) {
                bindingResult.addError(new ObjectError("product.images", "No image chosen!"));
            }
        }

        if(bindingResult.hasErrors()){
            session.setAttribute("productErrors", bindingResult.getAllErrors());
            if(prod_id == null) {
                return new RedirectView("/amin/admin/product/edit/0/prod_id");
            } else {
                return new RedirectView("/amin/admin/product/edit/" + prod_id + "/prod_id");
            }
        }

        ProductImage productImage;
        if (!file.isEmpty()) {
            //for exist product
            if(prod_id != null) {
                //удаляем старый файл картинки
                //TODO заменить список картинок на Map<"keyName", ProductImage>
                imageSaverService.deleteFile(productService.findById(prod_id)
                        .getImages().get(0).getPath());
                //TODO is it required to delete all images?
                //удаляем все предыдущие объекты картинки из БД
                productService.deleteAllProductImagesByProduct(product);
            }
            //сохраняем новый файл картинки
            String pathToSavedImage = imageSaverService.saveFile(file, "products");
            productImage = new ProductImage();
            productImage.setPath(pathToSavedImage);
            productImage.setProduct(product);
        //for exist product only
        } else {
            //TODO replace List with Map
            productImage = productService.findById(product.getId()).getImages().get(0);
        }
        productService.addImage(product, productImage);
        productService.save(product);
        return new RedirectView("/amin/admin/product/all");
    }

}
