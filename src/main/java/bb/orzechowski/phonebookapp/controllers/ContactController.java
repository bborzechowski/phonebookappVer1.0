package bb.orzechowski.phonebookapp.controllers;

import bb.orzechowski.phonebookapp.model.Contact;
import bb.orzechowski.phonebookapp.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public List<Contact> getAllContacts(){
        return contactService.getContacts();
    }

    @PostMapping("/add")
    public void addContact(@RequestBody Contact contact){ //dodajemy jsona (@RequestBody
        contactService.addContact(contact);
    }
}
