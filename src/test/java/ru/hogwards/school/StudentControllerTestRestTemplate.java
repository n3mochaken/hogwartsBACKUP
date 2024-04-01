package ru.hogwards.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwards.school.controllers.StudentController;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.StudentRepository;

import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StudentControllerTestRestTemplate {
    List<Student> addedStudents;
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        Student student = new Student (1L,"asd",123);
        Student student2 = new Student (2L,"qwe",123);
        List<Student> studentslist = List.of(student, student2);

        addedStudents = studentRepository.saveAll(studentslist);
    }

    @AfterEach
    void afterEach(){
        studentRepository.deleteAll();
    }

    @Test
    public void contextLoads(){
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() throws JSONException, JsonProcessingException {
        String expected = mapper.writeValueAsString(addedStudents.get(0));

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:"+port+"/students/"+addedStudents.get(0).getId(),String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }

    @Test
    void testGetListOfStudents (){
        ResponseEntity<List<Student >> response = restTemplate.exchange("http://localhost:" + port + "/students/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Student> actualStudents = response.getBody().stream().collect(Collectors.toList());
        assertTrue(addedStudents.containsAll(actualStudents));
//        assertEquals(addedStudents,actualStudents);
    }

    @Test
    void createStudent()throws JsonProcessingException,JSONException{
        Student student = new Student (3L,"sa",222);
        String expected = mapper.writeValueAsString(student);
        ResponseEntity<String> response = restTemplate.postForEntity("/students",student,String.class);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);

    }

    @Test
    void edit (){
        Student student = new Student (1L,"saaa",22222);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        student.setId(addedStudents.get(0).getId());

        ResponseEntity<Student> response= this.restTemplate.
                exchange("/students/",HttpMethod.PUT,entity,Student.class);

                assertEquals(response.getStatusCode(),HttpStatus.OK);
                assertEquals(student,response.getBody());
    }


    @Test
    void   deleteStud  (){
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity <String> response =
                restTemplate
                        .exchange("/students/"+addedStudents.get(0).getId(),HttpMethod.DELETE,entity,String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

}
