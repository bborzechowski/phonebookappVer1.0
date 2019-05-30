package bb.orzechowski.phonebookapp.service;

import bb.orzechowski.phonebookapp.dtos.mappers.ContactMapper;
import bb.orzechowski.phonebookapp.dtos.model.ContactDto;
import bb.orzechowski.phonebookapp.model.*;
import bb.orzechowski.phonebookapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private ContactMapper contactMapper;
    private AddressRepository addressRepository;
    private CategoryRepository categoryRepository;
    private RankingRepository rankingRepository;
    private TagRepository tagRepository;

    public ContactService(ContactRepository contactRepository,
                          ContactMapper mapper,
                          AddressRepository addressRepository,
                          CategoryRepository categoryRepository,
                          RankingRepository rankingRepository,
                          TagRepository tagRepository) {
        this.contactRepository = contactRepository;
        this.contactMapper = mapper;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.rankingRepository = rankingRepository;
        this.tagRepository = tagRepository;
    }

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }

    //DTO
    public List<ContactDto> getContactsDto(){
        List<ContactDto> contactsDtos = new ArrayList<>();
        for(Contact c : contactRepository.findAll()){
            contactsDtos.add(contactMapper.map(c));
        }
        return contactsDtos;
    }


    public void addContact(Contact contact){
        contactRepository.save(contact);
    }


    public List<Contact> getContactsByName(String name){
        return contactRepository.findAllByName(name);
    }

     //DTO
    public List<ContactDto> getContactsDtoByName(String name){
        List<ContactDto> contactDtos = new ArrayList<>();
        for(Contact c : contactRepository.findAllByName(name)){
            contactDtos.add(contactMapper.map(c));
        }
        return contactDtos;
    }

    // DTO
    public boolean deleteContactByPhone(String phone){
        Optional<Contact> contact = contactRepository.findByPhoneNumber(phone);
        if(contact.isPresent()){
            contactRepository.deleteById(contact.get().getId());
            return true;
        }
        return false;
    }

    public void addNewContactDto(ContactDto contactDto){

        Contact contact = new Contact();

        //ADDRESS
        Optional<Address> address = addressRepository.findAddressByCityAndStreet(contactDto.getAddressCity(), contactDto.getAddressStreet());
        if(address.isPresent()){
            contact.setAddress(address.get());
        } else {
            Address adr = new Address();
            adr.setCity(contactDto.getAddressCity());
            adr.setStreet(contactDto.getAddressStreet());
            contact.setAddress(addressRepository.save(adr));
        }

        //RANKING
        Optional<Ranking> ranking = rankingRepository.findRankingByNumber(contactDto.getRanking());
        if(ranking.isPresent()){
            contact.setRanking(ranking.get());
        } else {
            Ranking rank = new Ranking();
            rank.setNumber(contactDto.getRanking());
            contact.setRanking(rankingRepository.save(rank));
        }

        //CATEGORY
        Optional<Category> category = categoryRepository.findCategoryByTitle(contactDto.getCategory());
        if(category.isPresent()){
            contact.setCategory(category.get());
        } else {
            Category cat = new Category();
            cat.setTitle(contactDto.getCategory());
            contact.setCategory(categoryRepository.save(cat));
        }

        //TAG
        Set<Tag> emptyTags = new HashSet<>();
        contact.setTags(emptyTags);

        //FIELDS
        contact.setName(contactDto.getName());
        contact.setSurname(contactDto.getSurname());
        contact.setNumber(contactDto.getNumber());

        //SAVE
        contactRepository.save(contact);
    }

    //DTO aktualizacja kontaktu
    public boolean updateAddressByPhone(String phone, String city, String street){

        //1 - znajduje kontakt po numerze telefonu
        Optional<Contact> contactOptional = contactRepository.findByPhoneNumber(phone);

        //2 - jeśli kontakt istnieje, sprawdzamy czy juz istnieje podany adres do aktualizacji w bazie danych
        if(contactOptional.isPresent()){
            Optional<Address> adr = addressRepository.findAddressByCityAndStreet(city, street);

            //3 - jezeli taki adres już istnieje, aktualizuje podany wczesniej kontakt
            if(adr.isPresent()){
                contactOptional.get().setAddress(adr.get());
                contactRepository.save(contactOptional.get());

                //4 - jeśli podany adres nie istnieje to tworzy sie nowy obiekt adresu i zapisuje nowy adres w bazie danych
            } else {
                Address address = new Address();
                address.setCity(city);
                address.setStreet(street);
                contactOptional.get().setAddress(addressRepository.save(address));
                //zapisuje zaktualizowany obiekt spowrotem do bazy danych
                contactRepository.save(contactOptional.get());
            }
            return true;
        }
        return false;
    }

    //DTO
    public boolean addTagsToContact(String phone, List<String> tagList){
        //szukam kontaktu w bazie danych po numerze telefonu
        Optional<Contact> contactOptional = contactRepository.findByPhoneNumber(phone);

        //jeśli taki kontakt istnieje
        if(contactOptional.isPresent()){

            //iteruje po liście tagów
            for(String tag : tagList){

                //przy każdej iteracji pętli sprawdzam, czy taki tag istnieje w bzi danych
                Optional<Tag> tagOptional = tagRepository.findTagByTitle(tag);

                //jeśli tag nie istnieje w bazie, to zapisje go do bazy danych jako nowy tag oraz dodaje ten tag do kontaktu
                if(!tagOptional.isPresent()){
                    contactOptional.get().getTags().add(tagRepository.save(new Tag(tag)));

                    //jeśli istnieje w bazie dany tag, to tylko dodaje ten tag do kontaktu
                } else {
                    contactOptional.get().getTags().add(tagOptional.get());
                }
            }
            //zapisuje zaktualizowany kontakt z powrotem do bazy danych
            contactRepository.save(contactOptional.get());
            return true;
        }
        return false;
    }

    //DTO
    public List<ContactDto> getContactDtoByTag(String title){
        List<ContactDto> contactDtos = new ArrayList<>();

        for(Contact c : contactRepository.findContactByTag(title)){
            contactDtos.add(contactMapper.map(c));
        }
        return contactDtos;
    }

}
