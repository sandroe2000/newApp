package br.com.sdvs.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Entity
@Data
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String cpf;
    private String card;
    private String name;
    private String gerder;
    private String imgProfile1;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String contactPhone;
    private String emailAddress;
    private String username;
    private String password;
    private String language;
    private String timeZone;

    @ElementCollection
    @CollectionTable(name ="disabled")
    private List<String> disabled = new ArrayList<String>();
    
    @ElementCollection
    @CollectionTable(name ="password_reset_verif")
    private List<String> passwordResetVerif = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="communication")
    private List<String> communication = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="group_participant")
    private List<String> groupParticipant = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="access_settings")
    private List<String> accessSettings = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="account_settings")
    private List<String> accountSettings = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="change_password")
    private List<String> changePassword = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="list_of_profiles")
    private List<String> listOfProfiles = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name ="personal_information")
    private List<String> personalInformation = new ArrayList<String>();
}