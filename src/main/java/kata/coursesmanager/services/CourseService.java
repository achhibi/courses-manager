package kata.coursesmanager.services;

import kata.coursesmanager.dto.CourseDTO;
import kata.coursesmanager.entities.Partant;
import kata.coursesmanager.exceptions.FunctionalException;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO course) throws FunctionalException;
    List<CourseDTO> getAllCourses();
    void addPartantToCourse(Long courseId, Partant partant);
}