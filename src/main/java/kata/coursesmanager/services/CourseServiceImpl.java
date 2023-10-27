package kata.coursesmanager.services;

import kata.coursesmanager.dto.CourseDTO;
import kata.coursesmanager.entities.Course;
import kata.coursesmanager.entities.Partant;
import kata.coursesmanager.exceptions.FunctionalException;
import kata.coursesmanager.repostories.CourseRepository;
import kata.coursesmanager.repostories.PartantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final PartantRepository partantRepository;

    private final ModelMapper mapper;


    @Override
    public CourseDTO createCourse(CourseDTO course) throws FunctionalException {
        log.info("Validation d'une nouvelle course à créer : {} ", course);
        if (courseRepository.existsByDateAndNumeroUnique(course.getDate(), course.getNumeroUnique())) {
            throw new FunctionalException("Un numéro unique existe déjà pour cette date.");
        }
        if (course.getPartants() == null || course.getPartants().size() < 3) {
            throw new FunctionalException("Une course doit avoir  3 partants.");
        }

        Set<Integer> partantNumbers = new HashSet<>();

        for (Partant partant : course.getPartants()) {
            // Vérifier que le numéro du partant est entre  1 et 3
            if (partant.getNumero() < 1 || partant.getNumero() > 3) {
                throw new FunctionalException("Les numéros des partants doivent être supérieurs ou égaux à 1.");
            }
            // Vérifier que le numéro du partant est unique
            if (partantNumbers.contains(partant.getNumero())) {
                throw new FunctionalException("Les numéros des partants doivent être uniques.");
            }
            partantNumbers.add(partant.getNumero());
        }
        log.info("Course  {} valide", course);
        return mapper.map(courseRepository.save(mapper.map(course, Course.class)), CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        log.info("## Récupération de toutes les courses ##");
        return  courseRepository.findAll().stream()
                .map(b -> mapper.map(b, CourseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public void addPartantToCourse(Long courseId, Partant partant) {
        Course course = courseRepository.findById(courseId).orElse(null);

        //TODO effectuer les contôles métier ici
        if (course != null) {
            partant.setCourse(course);
            partantRepository.save(partant);
        }
    }
}