package ru.geekbrains.spring.ishop.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @GetMapping
    public String contactsMenuIndex() {
        return "redirect:/catalog/all";//TODO
    }

    @GetMapping("/how_to_pay")
    public String contactsMenuHowToPay() {
        return "redirect:/catalog/all";//TODO
    }

    @GetMapping("/delivery")
    public String contactsMenuDelivery() {
        return "redirect:/catalog/all";//TODO
    }

    @GetMapping("/contact")
    public String contactsMenuContact() {
        return "redirect:/catalog/all";//TODO
    }

}
