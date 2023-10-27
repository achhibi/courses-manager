package kata.coursesmanager.repostories;

import kata.coursesmanager.entities.Partant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartantRepository extends JpaRepository<Partant, Long> {
}
