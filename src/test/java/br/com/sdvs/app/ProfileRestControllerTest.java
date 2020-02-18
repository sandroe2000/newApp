package br.com.sdvs.app;

import br.com.sdvs.app.controller.ProfileRestController;
import br.com.sdvs.app.dto.ProfileDto;
import br.com.sdvs.app.model.Profile;
import br.com.sdvs.app.service.ProfileService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProfileRestController profileRestController;

    @Autowired
    private ProfileService profileService;

    private Pageable pageable = PageRequest.of(0, 5);

    /*
    * /opt/sonarqube/bin/linux-x86-64/sonar.sh console
    *
    * mvn compile
    * mvn sonar:sonar -Dsonar.projectKey=newApp -Dsonar.host.url=http://localhost:9000 -Dsonar.login=378581e0fe4100315b5b04a4010153510ea3dd57
    */

    @Test
    @Order(1)
    public void whenSetFakeData_returnOk() throws Exception {

        ResponseEntity<String> request = profileRestController.setFakeData("/home/sandro/DEV/newApp/data/data.csv");
        Assertions.assertEquals(HttpStatus.CREATED, request.getStatusCode());
    }

    @Test
    @Order(2)
    public void whenSetFakeData_returnFail() throws Exception {

        ResponseEntity<String> request = profileRestController.setFakeData("/home/sandro/DEV/newApp/data/xxx.csv");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode());
    }

    @Test
    @Order(3)
    public void whenSearch_withOutNameAndGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search("", "", pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    @Order(4)
    public void whenSearch_withOutGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search("MARIA", "", pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    @Order(5)
    public void whenSearch_withNameAndGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search("MARIA", "ADM", pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    @Order(6)
    public void whenFindById_returnProfile() throws Exception {

        ResponseEntity<Optional<Profile>> request = profileRestController.findById(1L);
        Assertions.assertEquals("0206300832630200A9", request.getBody().get().getCard());
    }

    @Test
    @Order(7)
    public void whenFindById_returnNoContent() throws Exception {

        ResponseEntity<Optional<Profile>> request = profileRestController.findById(0L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode());
    }

    @Test
    @Order(8)
    public void whenCreate_returnCreated() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setCpf("18484003817");
        dto.setCard("111111111111111");
        dto.setName("SANDRO C. DI ESPINDULA");

        ResponseEntity<Profile> request = profileRestController.create(dto);
        Assertions.assertEquals(HttpStatus.CREATED, request.getStatusCode());
    }

    @Test
    @Order(9)
    public void whenCreate_returnConflict() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setCpf("15312356468");
        dto.setName("NONONOI NONONOI");

        ResponseEntity<Profile> request = profileRestController.create(dto);
        Assertions.assertEquals(HttpStatus.CONFLICT, request.getStatusCode());
    }

    @Test
    @Order(10)
    public void whenUpdate_returnOk() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setId(13L);
        dto.setCpf("18484003817");
        dto.setCard("111111111111111");
        dto.setName("SANDRO C. DI ESPINDULA");
        dto.setGerder("M");
        dto.setImgProfile1("/img/avatar/no-image.jpg");
        dto.setContactPhone("(21) 991388372");
        dto.setEmailAddress("sandroe2000@gmail.com");
        dto.setUsername("sandroe2000");
        dto.setPassword("1111");
        dto.setLanguage("PT-BR");
        dto.setTimeZone("-03:00:00");
        dto.setBirthDate(LocalDate.of(1973, 4, 19));
        dto.setDisabled(new ArrayList<String>(Arrays.asList("True")));
        dto.setPasswordResetVerif(new ArrayList<String>(Arrays.asList("True")));
        dto.setCommunication(new ArrayList<String>(Arrays.asList("True")));
        dto.setGroupParticipant(new ArrayList<String>(Arrays.asList("True")));
        dto.setAccessSettings(new ArrayList<String>(Arrays.asList("True")));
        dto.setAccountSettings(new ArrayList<String>(Arrays.asList("True")));
        dto.setChangePassword(new ArrayList<String>(Arrays.asList("True")));
        dto.setListOfProfiles(new ArrayList<String>(Arrays.asList("True")));
        dto.setPersonalInformation(new ArrayList<String>(Arrays.asList("True")));

        ResponseEntity<Profile> request = profileRestController.update(13L, dto);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    @Order(11)
    public void whenUpdate_returnNotFound() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setId(0L);
        dto.setName("NONONOI NONONOI");

        ResponseEntity<Profile> request = profileRestController.update(0L, dto);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, request.getStatusCode());
    }

    @Test
    @Order(12)
    public void whenDelete_returnOk() throws Exception {

        ResponseEntity<Profile> request = profileRestController.delete(10L);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    @Order(13)
    public void whenDelete_returnNotFound() throws Exception {

        ResponseEntity<Profile> request = profileRestController.delete(0L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, request.getStatusCode());
    }

    @Test
    @Order(14)
    public void main() {

        Application.main(new String[] {});
        Assertions.assertNotEquals(null, profileRestController);
    }
}
