package bb.orzechowski.phonebookapp.repositories;

import bb.orzechowski.phonebookapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByCityAndStreet(String city, String street);
}
