package bb.orzechowski.phonebookapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;


    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Contact> contacts = new HashSet<>();


    public Tag(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) { //tworzymy hashCode i equals tylko z title ponieważ tagi bedą w secie i by się zapętlały
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return getTitle().equals(tag.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}