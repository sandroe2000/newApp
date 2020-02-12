package br.com.sdvs.app;

import br.com.sdvs.app.controller.ProfileRestController;
import br.com.sdvs.app.model.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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

    /*
    * /opt/sonarqube/bin/linux-x86-64/sonar.sh console
    *
    * mvn compile
    * mvn sonar:sonar
    *   -Dsonar.projectKey=newApp
    *   -Dsonar.host.url=http://localhost:9000
    *   -Dsonar.login=378581e0fe4100315b5b04a4010153510ea3dd57
    */

    @Test
    public void whenFindById_returnProfile() throws Exception {

        //ResponseEntity<Profile> request = restTemplate.getForEntity(new URL("http://localhost:" + port + "/profiles/1").toString(), Profile.class);
        ResponseEntity<Optional<Profile>> request = profileRestController.findById(1L);
        Assertions.assertEquals("0206300832630200A9", request.getBody().get().getCard());
    }

    @Test
    public void whenFindById_returnNoContent() throws Exception {

        //ResponseEntity<Profile> request = restTemplate.getForEntity(new URL("http://localhost:" + port + "/profiles/1").toString(), Profile.class);
        ResponseEntity<Optional<Profile>> request = profileRestController.findById(0L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode());
    }
}
