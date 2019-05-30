package bb.orzechowski.phonebookapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rankings")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  int number;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ranking", cascade = {
            CascadeType.PERSIST, //włączenie nowej encji do kontaktu
            CascadeType.MERGE, //aktualizacja encji
            CascadeType.DETACH, //odłączanie encji
            CascadeType.REFRESH //odświerzanie stanu encji
          //  CascadeType.REMOVE  //usuwanie encji
    }) //jeden ranking do wieku kontaktów

    private Set<Contact> contacts = new HashSet<>();
}
