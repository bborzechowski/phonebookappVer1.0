package bb.orzechowski.phonebookapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    //Category

    @ManyToOne(cascade = {
            CascadeType.PERSIST, //jeśli zapytamy o kontakt to automatycznie zrobi się zapytanie join do kategorii (powiązania)
            CascadeType.MERGE, //łączenie
            CascadeType.DETACH, //odłączanie obiektów od sesji
            CascadeType.REFRESH //odświerzanie
    }) //jeśli usumiemy kategorie to usuna się wszystkie osoby które był w tej kat.
    @JoinColumn(name = "fk_Category") //działa tak samo jak Column
    private Category category; // jeden kontakt może mieć jedną kategorie, a kategoria może mieć wiele kontaktów


    //Address
    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "fk_address")
    private Address address;


    //Ranking
    @ManyToOne(cascade = {
            CascadeType.PERSIST, //jeśli zapytamy o kontakt to automatycznie zrobi się zapytanie join do kategorii (powiązania)
            CascadeType.MERGE, //łączenie
            CascadeType.DETACH, //odłączanie obiektów od sesji
            CascadeType.REFRESH //odświerzanie
    })
    @JoinColumn(name = "fk_ranking")
    private Ranking ranking;


    //Tag
    @ManyToMany(cascade = {
            CascadeType.PERSIST, //jeśli zapytamy o kontakt to automatycznie zrobi się zapytanie join do kategorii (powiązania)
            CascadeType.MERGE, //łączenie
            CascadeType.DETACH, //odłączanie obiektów od sesji
            CascadeType.REFRESH //odświerzanie
    })
    @JoinTable(name = "contact_tag",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

}
