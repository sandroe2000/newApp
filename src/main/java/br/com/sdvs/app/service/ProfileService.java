package br.com.sdvs.app.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.app.model.Profile;
import br.com.sdvs.app.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private static Logger logger = LoggerFactory.getLogger(ProfileService.class);

    public String setFakeData(String fileName) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String result = "OK";
        int i = 0;

        Scanner scanner = null;
        
        try (InputStream inputStream = new FileInputStream(fileName)){

            scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());


            List<Profile> listProfiles = new ArrayList<>();

            while (scanner.hasNext()) {
                
                List<String> line = Arrays.asList(scanner.nextLine().split("\\|"));

                if(line.size()<55){
                    continue;
                }

                Profile profile = new Profile();

                LocalDate birthDate = LocalDate.parse(line.get(6).trim(), formatter);

                profile.setCpf(line.get(26).trim());
                profile.setCard(line.get(3).trim());
                profile.setName(line.get(4).trim());
                profile.setGerder(line.get(5).trim());
                profile.setImgProfile1("/img/avatar/no-image.jpg");
                profile.setBirthDate(birthDate);

                if(line.get(51).trim().length()>0 && !line.get(51).trim().equals("00")){
                    profile.setContactPhone("("+line.get(51).trim()+") "+ line.get(52).trim());
                }

                listProfiles.add(profile);

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(listProfiles);
                    listProfiles.clear();
                }

                i++;
            }

            if (!listProfiles.isEmpty()) {
                repository.saveAll(listProfiles);
                listProfiles.clear();
            }

        } catch (IOException e) {
            result = "FAIL";
            logger.error("Error log message", e);
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }

        return result;
    }
}
