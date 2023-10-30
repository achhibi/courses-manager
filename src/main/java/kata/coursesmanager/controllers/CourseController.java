package kata.coursesmanager.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kata.coursesmanager.dto.CourseDTO;
import kata.coursesmanager.exceptions.FunctionalException;
import kata.coursesmanager.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Tag(name = "Course ", description = "Courses API")
public class CourseController {

    private final CourseService courseService;
    private final KafkaTemplate<String, CourseDTO> kafkaTemplate;


    @Value("${kata.coursesmanager.kafka.topic}")
    private String kafkaTopic;


    @PostMapping
    @Operation(summary = "Create Course ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created | OK"),
            @ApiResponse(responseCode = "400", description = "Bad request!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO course) throws FunctionalException {
        CourseDTO createdCourse = courseService.createCourse(course);
        log.info("Publication d'une nouvelle course dans Kafka: (Nom: {}, Numéro: {}, Date: {})", course.getNom(), course.getNumeroUnique(), course.getDate());
        kafkaTemplate.send(kafkaTopic, createdCourse);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Course by id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized!"),
            @ApiResponse(responseCode = "403", description = "Forbidden!"),
            @ApiResponse(responseCode = "404", description = "Not Found!")})
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> course(@Parameter(description = "Course ID. Cannot be empty.", required = true) @PathVariable(value = "id") long id) {
        try {
            //TODO find course By Id
            throw new UnsupportedOperationException("Not yet implemented !");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Course ")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO updateCourse(
            @Parameter(description = "Id of the Course to be update. Cannot be empty.", required = true) @PathVariable("id") final String id,
            @Parameter(description = "Course to update.", required = true, schema = @Schema(implementation = CourseDTO.class)) @RequestBody final CourseDTO course) {
        //TODO mise a jour de la course
        return course;
    }


}