package kata.coursesmanager;

import kata.coursesmanager.dto.CourseDTO;
import kata.coursesmanager.entities.Course;
import kata.coursesmanager.repostories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class CourseControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

   @MockBean
    private KafkaTemplate<String, CourseDTO> kafkaTemplate;

    @Test
    public void testCreateCourseNominal() throws Exception {
        Course course = new Course();
        course.setNom("Course de test");
        course.setNumeroUnique(123456l);

        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(kafkaTemplate.send(anyString(), any(CourseDTO.class))).thenReturn(null);

        String jsonRequest = "{\n" +
                "  \"nom\": \"Course de test\",\n" +
                "  \"numeroUnique\": 123456,\n" +
                "  \"date\": \"2023-10-28T12:00:00Z\",\n" +
                "  \"partants\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"nom\": \"Partant 1\",\n" +
                "      \"numero\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"nom\": \"Partant 2\",\n" +
                "      \"numero\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"nom\": \"Partant 3\",\n" +
                "      \"numero\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Course de test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroUnique").value(123456));
    }

    @Test
    public void testCreateCourseException() throws Exception {
        Course course = new Course();
        course.setNom("Course KO");
        course.setNumeroUnique(123456l);

        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(kafkaTemplate.send(anyString(), any(CourseDTO.class))).thenReturn(null);
        // Course avec des partants du même numéro.
        String jsonRequest = "{\n" +
                "  \"nom\": \"Course de test\",\n" +
                "  \"numeroUnique\": 123456,\n" +
                "  \"date\": \"2023-10-28T12:00:00Z\",\n" +
                "  \"partants\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"nom\": \"Partant 1\",\n" +
                "      \"numero\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"nom\": \"Partant 2\",\n" +
                "      \"numero\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"nom\": \"Partant 3\",\n" +
                "      \"numero\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Les numéros des partants doivent être uniques.")));
    }

    //TODO tester les autres cas fontionnels
}
