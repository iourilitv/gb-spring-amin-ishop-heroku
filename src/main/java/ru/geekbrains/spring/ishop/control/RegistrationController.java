package ru.geekbrains.spring.ishop.control;

import ru.geekbrains.spring.ishop.utils.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showForm")
    public String showRegistrationFormPage(Model theModel) {
        theModel.addAttribute("systemUser", new SystemUser());
        return "amin/registration-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/process")
    public String processRegistrationForm(
            @Valid @ModelAttribute("systemUser") SystemUser theSystemUser,
            BindingResult theBindingResult, Model theModel) {

        String userName = theSystemUser.getUserName();
        logger.debug("Processing registration form for: " + userName);
        if (theBindingResult.hasErrors()) {
            return "amin/registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            // theSystemUser.setUserName(null);
            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("registrationError", "User with current username already exists");
            logger.debug("User name already exists.");
            return "amin/registration-form";
        }
        userService.save(theSystemUser);
        logger.debug("Successfully created user: " + userName);
        theModel.addAttribute("confirmationTitle", "Amin | Registration Confirmation Page");
        theModel.addAttribute("confirmationMessage", "You have been registered successfully!");
        theModel.addAttribute("confirmationAHref", "/login");
        theModel.addAttribute("confirmationAText", "Login with new user");
        return "amin/confirmation";
    }

}
