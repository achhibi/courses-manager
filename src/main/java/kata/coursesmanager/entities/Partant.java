package kata.coursesmanager.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
public class Partant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    //@Enumerated(EnumType.ORDINAL) !! valeurs possibles 0,1,2
    private int numero;

    @ManyToOne
    private Course course;

}
