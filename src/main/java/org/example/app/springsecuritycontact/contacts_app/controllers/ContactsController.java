package org.example.app.springsecuritycontact.contacts_app.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springsecuritycontact.apps_dto.ResponseDataDTO;
import org.example.app.springsecuritycontact.contacts_app.entity.Contact;
import org.example.app.springsecuritycontact.contacts_app.service.ContactsService;
import org.example.app.springsecuritycontact.contacts_app.service.DTO.ContactDTO;
import org.example.app.springsecuritycontact.validate.contacts_form.FormDataForValidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ContactsController {
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @Autowired
    private ContactsService contactsService;


    @GetMapping("contacts")
    public String fetchAllCustomers(Model model, HttpSession session) {
        ResponseDataDTO<ContactDTO> allCustomers = contactsService.getAll("contacts_list", session);
        model.addAttribute("listCustomers", allCustomers.getData());
        model.addAttribute("fragmentName", "contacts_list");
        return "index";
    }

    @GetMapping("contacts/create")
    public String createCustomer(Model model) {
        model.addAttribute("fragmentName", "contact_add");
        return "index";
    }

    @PostMapping("contacts/create")
    public RedirectView addCustomer(@ModelAttribute FormDataForValidateDTO input, RedirectAttributes attributes, HttpSession session) {
        ResponseDataDTO<Contact> createResponse = contactsService.create(input, "contact_add", session);
        RedirectView successRedirectView = new RedirectView("/contacts");
        attributes.addFlashAttribute("successMsg", createResponse.getMsg());
        CONSOLE_LOGGER.info("Contacts created successfully.");
        return successRedirectView;
    }

    @GetMapping("contacts/update")
    public String updateCustomer(Model model, @RequestParam(name = "id") @Valid @NonNull Long id) {
        ResponseDataDTO<Contact> getById = contactsService.getById(id, "contact_update");
        model.addAttribute("fragmentName", "contact_update");
        model.addAttribute("contact", getById.getData().getFirst());
        return "index";
        }

    @PostMapping("contacts/update/processing")
    public RedirectView updateCustomer(@ModelAttribute FormDataForValidateDTO input, RedirectAttributes attributes) {
        ResponseDataDTO<Contact> updateResponse = contactsService.update(input, "contact_update");
        RedirectView successRedirectView = new RedirectView("/contacts");
        attributes.addFlashAttribute("successMsg", updateResponse.getMsg());
        CONSOLE_LOGGER.info("Contact update successfully.");
        return successRedirectView;
    }

    @PostMapping("contacts/delete")
    public RedirectView deleteCustomer(@ModelAttribute("id") @Valid @NonNull Long id, RedirectAttributes attributes) {
        ResponseDataDTO<Contact> deleteResponse = contactsService.delete(id, "contacts_list");
            RedirectView successRedirectView = new RedirectView("/contacts");
            attributes.addFlashAttribute("successMsg", deleteResponse.getMsg());
            CONSOLE_LOGGER.info("Contact deleted successfully.");
            return successRedirectView;
    }
}
