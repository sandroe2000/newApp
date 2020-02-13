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

@Entity
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
    private List<String> disabled = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name ="password_reset_verif")
    private List<String> passwordResetVerif = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="communication")
    private List<String> communication = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="group_participant")
    private List<String> groupParticipant = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="access_settings")
    private List<String> accessSettings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="account_settings")
    private List<String> accountSettings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="change_password")
    private List<String> changePassword = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="list_of_profiles")
    private List<String> listOfProfiles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="personal_information")
    private List<String> personalInformation = new ArrayList<>();

    public Profile() {
        //An empty constructor is needed to create a new instance via reflection by your persistence framework
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGerder() {
        return gerder;
    }

    public void setGerder(String gerder) {
        this.gerder = gerder;
    }

    public String getImgProfile1() {
        return imgProfile1;
    }

    public void setImgProfile1(String imgProfile1) {
        this.imgProfile1 = imgProfile1;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<String> getDisabled() {
        return disabled;
    }

    public void setDisabled(List<String> disabled) {
        this.disabled = disabled;
    }

    public List<String> getPasswordResetVerif() {
        return passwordResetVerif;
    }

    public void setPasswordResetVerif(List<String> passwordResetVerif) {
        this.passwordResetVerif = passwordResetVerif;
    }

    public List<String> getCommunication() {
        return communication;
    }

    public void setCommunication(List<String> communication) {
        this.communication = communication;
    }

    public List<String> getGroupParticipant() {
        return groupParticipant;
    }

    public void setGroupParticipant(List<String> groupParticipant) {
        this.groupParticipant = groupParticipant;
    }

    public List<String> getAccessSettings() {
        return accessSettings;
    }

    public void setAccessSettings(List<String> accessSettings) {
        this.accessSettings = accessSettings;
    }

    public List<String> getAccountSettings() {
        return accountSettings;
    }

    public void setAccountSettings(List<String> accountSettings) {
        this.accountSettings = accountSettings;
    }

    public List<String> getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(List<String> changePassword) {
        this.changePassword = changePassword;
    }

    public List<String> getListOfProfiles() {
        return listOfProfiles;
    }

    public void setListOfProfiles(List<String> listOfProfiles) {
        this.listOfProfiles = listOfProfiles;
    }

    public List<String> getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(List<String> personalInformation) {
        this.personalInformation = personalInformation;
    }
}