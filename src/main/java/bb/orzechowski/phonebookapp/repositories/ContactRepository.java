package bb.orzechowski.phonebookapp.repositories;

import bb.orzechowski.phonebookapp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    String BY_NAME = "select * from phoneapp where name = ?1";

    //@Query(value = BY_NAME, nativeQuery = true) //alternatywnie przez zmienna BY_NAME
    @Query(value = "select * from contacts where name = ?1", nativeQuery = true)
    List<Contact> findAllByName(String name);

    Contact findAllBySurname(String surname);

    @Query(value = "select * from contacts where number = ?1", nativeQuery = true)
    Optional<Contact> findByPhoneNumber(String phone);

    @Query(value = "select contacts.id,\n" +
            "contacts.name,\n" +
            "contacts.number,\n" +
            "contacts.surname,\n" +
            "contacts.fk_category,\n" +
            "contacts.fk_address,\n" +
            "contacts.fk_ranking\n" +
            "from contacts\n" +
            "join contact_tag on contact_id = contact_tag.contact_id\n" +
            "join atags on contact_tag.tag_id = atags.id\n" +
            "where atags.id = (select id from atags where title= ?1)", nativeQuery = true)
    List<Contact> findContactByTag(String title);
}
