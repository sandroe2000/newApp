package br.com.sdvs.app.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProfileDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String cpf;
    private String card;
    private String name;
    private String gerder;
    private String imgProfile1;
    private String contactPhone;
    private String emailAddress;
    private String username;
    private String password;
    private String language;
    private String timeZone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    private List<String> disabled;
    private List<String> passwordResetVerif;
    private List<String> communication;
    private List<String> groupParticipant;
    private List<String> accessSettings;
    private List<String> accountSettings;
    private List<String> changePassword;
    private List<String> listOfProfiles;
    private List<String> personalInformation;
}