package bb.orzechowski.phonebookapp.commons.mapper;

public interface Mapper<F,T> {

    //F - obiekt DAO, czysta encja
    //T - obiekt zwracany(zmapowany), czyli DTO(w tym przypadku to klasa Contact)
    T map(F from);
}
