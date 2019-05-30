package bb.orzechowski.phonebookapp.repositories;

import bb.orzechowski.phonebookapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByTitle(String title);
}
