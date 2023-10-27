package kata.coursesmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kata.coursesmanager.entities.Partant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CourseDTO {

    @Schema(required = true)
    private String nom;
    @Schema(required = true)
    private Long numeroUnique;

    @NotNull
    private Date date;

    private List<Partant> partants;
}
