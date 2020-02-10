package br.com.sdvs.app.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository repository;

    private static final char DEFAULT_SEPARATOR = '|';
    private static final char DEFAULT_QUOTE = '"';

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public String setFakeData() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "/home/sandro/DEV/newApp/data/Bkp_Usu_2019-05-30_04-00-03";
        String result = "OK";
        int i = 0;
        
        try {
            InputStream inputStream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());


            List<Profile> listProfiles = new ArrayList<Profile>();

            while (scanner.hasNext()) {
                List<String> line = parseLine(scanner.nextLine());

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
                    //logger.info("registros lidos: "+i);
                    listProfiles.clear();
                }

                i++;
            }

            if (listProfiles.size() > 0) {
                repository.saveAll(listProfiles);
                listProfiles.clear();
            }

            scanner.close();
        } catch (Exception e) {
            result = "FAIL";
        }

        return result;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        // if empty, return!
        if (cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    // Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    // Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    // double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    // ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    // the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
