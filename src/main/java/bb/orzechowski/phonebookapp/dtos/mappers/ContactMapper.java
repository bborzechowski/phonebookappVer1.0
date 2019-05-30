package bb.orzechowski.phonebookapp.dtos.mappers;

import bb.orzechowski.phonebookapp.commons.mapper.Mapper;
import bb.orzechowski.phonebookapp.dtos.model.ContactDto;
import bb.orzechowski.phonebookapp.model.Contact;
import bb.orzechowski.phonebookapp.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component //kontener springa stworzy nam beana z tej klasy - czyli obiekt
public class ContactMapper  implements Mapper<Contact, ContactDto> {

    @Override
    public ContactDto map(Contact from) {

        List<String> tags = from.getTags()
                .stream()
                .map(TagsToStringsList.INSTANCE)
                .collect(Collectors.toList());





        return new ContactDto(
                from.getName(),
                from.getSurname(),
                from.getNumber(),
                from.getCategory().getTitle(),
                from.getAddress().getCity(),
                from.getAddress().getStreet(),
                from.getRanking().getNumber(),
                tags );
    }
    //pomocniczy enum do wywoływania metody na obiekcie Tag z listy
    private enum TagsToStringsList implements Function<Tag, String> {
        INSTANCE;

        @Override
        public String apply(Tag tag) {
            return tag.getTitle();
        }
    }
}
