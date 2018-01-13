package com.cgi.dentistapp.dao.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dentist_visit_id")
    private Long id;

    @NotNull
    @Column(name = "dentist_name")
    private String dentistName;

    @NotNull
    @Column(name = "visit_time")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date visitTime;

    public DentistVisitEntity() {}

    public DentistVisitEntity(Long id, String dentistName, Date visitTime) {
        this.setId(id);
        this.setDentistName(dentistName);
        this.setVisitTime(visitTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }
}
