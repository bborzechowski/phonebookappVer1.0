package bb.orzechowski.phonebookapp.controllers;

import bb.orzechowski.phonebookapp.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dto")
public class TagControllerDto {

    private ContactService contactService;

    public TagControllerDto(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/tags")
    public ResponseEntity<?> addTagsToContact(
            @RequestParam String phone,
            @RequestBody List<String> tags){
        if(contactService.addTagsToContact(phone,tags)){
            return new ResponseEntity<>("Tags are added to contact", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);
    }
}
