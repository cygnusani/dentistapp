package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by serkp on 2.03.2017.
 */
public class DentistVisitDTO {

    Long id;

    @NotNull
    @Size(min = 1, max = 50)
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd") //yyyy-MM-dd'T'HH:mm //"dd.MM.yyyy"
    Date visitTime;

    @NotNull
    String time;

    public DentistVisitDTO() { }

    public DentistVisitDTO(Long id, String dentistName, Date visitTime, String time) {
        this.id = id;
        this.dentistName = dentistName;
        this.visitTime = visitTime;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public void setTime(String time) {this.time = time;}

    public String getTime() {return this.time;}
}
