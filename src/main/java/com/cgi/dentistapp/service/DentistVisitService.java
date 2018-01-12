package com.cgi.dentistapp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public DentistVisitEntity findById(Long id) {
        return dentistVisitDao.getById(id);
    }

    public List<String> findAllDentists() {
        return dentistVisitDao.getAllDentists();
    }

    public List<String> findAllTimes() {return dentistVisitDao.getAllTimes();}

    public List<DentistVisitEntity> findAll() {
        return dentistVisitDao.getAllVisits();
    }

    public void addVisit(Long id, String dentistName, Date visitTime) {
        dentistVisitDao.create(new DentistVisitEntity(id, dentistName, visitTime));
    }

    public List<DentistVisitEntity> search(String key) {
        return dentistVisitDao.search(key);
    }

    public void delete(Long id) {
        dentistVisitDao.delete(id);
    }

    public boolean visitTimeInPast(Date date) {
        return date.before(new Date()) && new Date().getDay() != date.getDay();
    }

    public boolean visitTimeAvailable(String dentistName, Date date) {
        // visit time is not available
        return dentistVisitDao.getVisitByDateAndTime(new DentistVisitEntity(null, dentistName, date)) == null;
    }

    public Date formatDate(Date visitDate, String visitTime) {
        String[] vals = visitTime.split(":");
        Date temp = visitDate;
        temp.setHours(Integer.parseInt(vals[0]));
        temp.setMinutes(Integer.parseInt(vals[1]));
        return temp;
    }
}
