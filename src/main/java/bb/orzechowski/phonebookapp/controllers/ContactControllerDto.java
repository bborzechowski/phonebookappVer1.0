package bb.orzechowski.phonebookapp.controllers;

import bb.orzechowski.phonebookapp.dtos.mappers.ContactMapper;
import bb.orzechowski.phonebookapp.dtos.model.ContactDto;
import bb.orzechowski.phonebookapp.model.Contact;
import bb.orzechowski.phonebookapp.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin //żeby back-end puścił do angulara
@RestController
@RequestMapping("/api/dto")
public class ContactControllerDto {

    private ContactService contactService;

    public ContactControllerDto(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public List<ContactDto> getAllContacts(){
        return contactService.getContactsDto();
    }

    @GetMapping("contacts/{name}")
    public List<ContactDto> getAllContactsByName(@PathVariable String name){
        return contactService.getContactsDtoByName(name);
    }

    @DeleteMapping("/contacts")
    public ResponseEntity<?> deleteContact(@RequestParam(name = "phone") String phone){

        if(contactService.deleteContactByPhone(phone)){
            return new ResponseEntity<>(HttpStatus.OK); //status 200
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); //status 400
    }
    @PostMapping("/contacts")
    public void addNewContact(@RequestBody ContactDto contactDto){
        contactService.addNewContactDto(contactDto);
    }

    @PutMapping("/contacts")
    public ResponseEntity<?> updateContactAddress(
            @RequestParam("phone") String phone,
            @RequestParam("city") String city,
            @RequestParam("street") String street
            ){
        if(contactService.updateAddressByPhone(phone, city, street)){
            return new ResponseEntity<>("Contact updated!!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: CONTACT NOT FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contacts/tag")
    public List<ContactDto> getContactsByTag(@RequestParam String title){
        return contactService.getContactDtoByTag(title);
    }
}
