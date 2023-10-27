package kata.coursesmanager.prometheus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerParams {
    private String name;
    private String service;
    private String operation;
}