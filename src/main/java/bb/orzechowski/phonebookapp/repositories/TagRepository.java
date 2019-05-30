package bb.orzechowski.phonebookapp.repositories;


import bb.orzechowski.phonebookapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

   // @Query(value = "select * from atags where title =?1", nativeQuery = true)  //true = SQL, a false = JPQL
    @Query(value = "select o from Tag o where o.title = ?1", nativeQuery = false)
    Optional<Tag> findTagByTitle(String title);
}
