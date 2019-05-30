package bb.orzechowski.phonebookapp.commons.xlsCreator;

import bb.orzechowski.phonebookapp.model.Contact;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CreatorXLS<Person> creatorXLS = new CreatorXLS<>(Person.class);

        List<Person> persons = Arrays.asList(
                new Person("Stefan", "Nowak", 24),
                new Person("Darek", "Kowalski", 54)
        );

        try {
            creatorXLS.createFile(persons, "src/main/resources/", "persons");
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
