package kata.coursesmanager.repostories;

import kata.coursesmanager.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByDateAndNumeroUnique(Date date, Long numeroUnique);
}
