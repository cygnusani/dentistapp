package com.cgi.dentistapp.service;

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
}
