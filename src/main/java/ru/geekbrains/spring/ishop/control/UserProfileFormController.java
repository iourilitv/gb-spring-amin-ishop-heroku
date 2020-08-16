package ru.geekbrains.spring.ishop.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.SystemUser;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile/form")
public class UserProfileFormController {
    private final IUserService userService;
    private final ShoppingCartService cartService;

    @Autowired
    public UserProfileFormController(IUserService userService, ShoppingCartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    private final Logger logger = LoggerFactory.getLogger(UserProfileFormController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/show")
    public String showProfileFormPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "amin/profile-form";
    }

    @GetMapping("/change/password/showForm")
    public String showPasswordChangingPage(HttpSession session, Model theModel) {
        User theUser = (User) session.getAttribute("user");
        theModel.addAttribute("systemUser", new SystemUser(theUser));
        return "amin/password-changing-form";
    }

    @PostMapping("/process/change/password")
    public String processPasswordChangingForm(
            @Valid @ModelAttribute("systemUser") SystemUser theSystemUser,
            BindingResult theBindingResult, Model theModel) {

        String userName = theSystemUser.getUserName();
        logger.debug("Processing password changing form for: " + userName);
        if (theBindingResult.hasErrors()) {
            return "amin/password-changing-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing == null) {
            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("registrationError", "There is no user with current username!");
            logger.debug("There is no user with current username.");
            return "amin/password-changing-form";
        }
        userService.updatePassword(userName, theSystemUser.getPassword());
        logger.debug("Successfully updated user password: " + userName);
        theModel.addAttribute("confirmationTitle", "Amin | Password Changing Confirmation Page");
        theModel.addAttribute("confirmationMessage", "The password has been changed successfully!");
        theModel.addAttribute("confirmationAHref", "/");
        theModel.addAttribute("confirmationAText", "Go to home page");
        return "amin/confirmation";
    }

    @PostMapping("/process/update/deliveryAddress")
    public RedirectView processUpdateDeliveryAddress(
            @Valid @ModelAttribute("deliveryAddress") Address deliveryAddress,
            BindingResult theBindingResult, HttpSession session) {

        logger.debug("Processing user profile deliveryAddress updating: ");
        logger.info(deliveryAddress.toString());

        if (!theBindingResult.hasErrors()) {
            User user = (User)session.getAttribute("user");
            userService.updateDeliveryAddress(user, deliveryAddress);
            session.setAttribute("user", userService.findById(user.getId()));
        }
        return new RedirectView("/amin/profile/form/show");
    }

    @PostMapping("/process/update/first_name")
    public RedirectView processUpdateFirstName(
            @RequestParam("firstName") String first_name,
            HttpSession session) {

        logger.debug("Processing user profile first_name updating: ");
        logger.info(first_name);

        User user = (User)session.getAttribute("user");
        userService.updateFirstName(user, first_name);
        session.setAttribute("user", userService.findById(user.getId()));
        return new RedirectView("/amin/profile/form/show");
    }

    @PostMapping("/process/update/last_name")
    public RedirectView processUpdateLastName(
            @RequestParam("lastName") String last_name,
            HttpSession session) {

        logger.debug("Processing user profile last_name updating: ");
        logger.info(last_name);

        User user = (User)session.getAttribute("user");
        userService.updateLastName(user, last_name);
        session.setAttribute("user", userService.findById(user.getId()));
        return new RedirectView("/amin/profile/form/show");
    }

}
