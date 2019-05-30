package bb.orzechowski.phonebookapp.dtos.model;

import bb.orzechowski.phonebookapp.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private String name;
    private String surname;
    private String number;
    private String category;
    private String addressCity;
    private String addressStreet;
    private int ranking;
    private List<String> tags;
}
