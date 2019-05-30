package bb.orzechowski.phonebookapp.repositories;

import bb.orzechowski.phonebookapp.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankingRepository  extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findRankingByNumber(int number);
}
