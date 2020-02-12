package br.com.sdvs.app;

import br.com.sdvs.app.controller.ProfileRestController;
import br.com.sdvs.app.dto.ProfileDto;
import br.com.sdvs.app.model.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
;import java.net.URL;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProfileRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProfileRestController profileRestController;

    private Pageable pageable = PageRequest.of(0, 5);

    /*
    * /opt/sonarqube/bin/linux-x86-64/sonar.sh console
    *
    * mvn compile
    * mvn sonar:sonar -Dsonar.projectKey=newApp -Dsonar.host.url=http://localhost:9000 -Dsonar.login=378581e0fe4100315b5b04a4010153510ea3dd57
    */

    @Test
    public void whenSetFakeData_returnOk() throws Exception {

        ResponseEntity<String> request = profileRestController.setFakeData();
        Assertions.assertEquals(HttpStatus.CREATED, request.getStatusCode());
    }

    @Test
    public void whenSearch_withOutNameAndGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search(new String(), new String(), pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    public void whenSearch_withOutGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search("MARIA", new String(), pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    public void whenSearch_withNameAndGroupParticipant() throws Exception {

        ResponseEntity<Page<Profile>> request = profileRestController.search("MARIA", "ADM", pageable);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    public void whenFindById_returnProfile() throws Exception {

        ResponseEntity<Optional<Profile>> request = profileRestController.findById(1L);
        Assertions.assertEquals("0206300832630200A9", request.getBody().get().getCard());
    }

    @Test
    public void whenFindById_returnNoContent() throws Exception {

        ResponseEntity<Optional<Profile>> request = profileRestController.findById(0L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode());
    }

    @Test
    public void whenCreate_returnCreated() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setCpf("18484003817");
        dto.setCard("111111111111111");
        dto.setName("SANDRO C. DI ESPINDULA");

        ResponseEntity<Profile> request = profileRestController.create(dto);
        Assertions.assertEquals(HttpStatus.CREATED, request.getStatusCode());
    }

    @Test
    public void whenCreate_returnConflict() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setCpf("33798001553");
        dto.setName("NONONOI NONONOI");

        ResponseEntity<Profile> request = profileRestController.create(dto);
        Assertions.assertEquals(HttpStatus.CONFLICT, request.getStatusCode());
    }

    @Test
    public void whenUpdate_returnOk() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setId(13L);
        dto.setCpf("18484003817");
        dto.setCard("111111111111111");
        dto.setName("SANDRO C. DI ESPINDULA");

        ResponseEntity<Profile> request = profileRestController.update(13L, dto);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    public void whenUpdate_returnNotFound() throws Exception {

        ProfileDto dto = new ProfileDto();
        dto.setId(0L);
        dto.setName("NONONOI NONONOI");

        ResponseEntity<Profile> request = profileRestController.update(0L, dto);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, request.getStatusCode());
    }

    @Test
    public void whenDelete_returnOk() throws Exception {

        ResponseEntity<Profile> request = profileRestController.delete(10L);
        Assertions.assertEquals(HttpStatus.OK, request.getStatusCode());
    }

    @Test
    public void whenDelete_returnNotFound() throws Exception {

        ResponseEntity<Profile> request = profileRestController.delete(0L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, request.getStatusCode());
    }
}
