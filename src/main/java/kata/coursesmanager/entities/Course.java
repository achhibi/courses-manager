package kata.coursesmanager.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String nom;
    private Long numeroUnique;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Partant> partants;
}
