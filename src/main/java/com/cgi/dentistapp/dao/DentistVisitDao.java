package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager em;


    public void create(DentistVisitEntity visit) {
        if (visit.getId() == null) {
            em.persist(visit);
        } else {
            em.merge(visit);
        }
    }

    public List<String> getAllDentists() {
        return Arrays.asList(
                "Jane Doe",
                "John Doe",
                "Keegi Veel",
                "Keegi Kolmas",
                "Martin Luik"
        );
    }

    public List<String> getAllTimes() {
        return Arrays.asList(
                "08:00",
                "08:30",
                "09:00",
                "09:30",
                "10:00",
                "10:30",
                "11:00",
                "11:30"
        );
    }

    public DentistVisitEntity getById(Long id) {
        TypedQuery<DentistVisitEntity> query = em.createQuery("SELECT e FROM DentistVisitEntity e WHERE e.id = :id", DentistVisitEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public List<DentistVisitEntity> getAllVisits() {
        return em.createQuery("SELECT e FROM DentistVisitEntity e", DentistVisitEntity.class).getResultList();
    }

    public List<DentistVisitEntity> search(String key) {
        List<DentistVisitEntity> result = new ArrayList<>();
        TypedQuery<DentistVisitEntity> query = em.createQuery("SELECT e FROM DentistVisitEntity e", DentistVisitEntity.class);
        for (DentistVisitEntity e : query.getResultList()) {
            if (e.getDentistName().toLowerCase().contains(key.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public DentistVisitEntity getVisitByDateAndTime(DentistVisitEntity visit) {
        TypedQuery<DentistVisitEntity> query = em.createQuery("SELECT e FROM DentistVisitEntity e", DentistVisitEntity.class);
        for (DentistVisitEntity e : query.getResultList()) {
            if (e.getDentistName().equals(visit.getDentistName()) &&
                    e.getVisitTime().getTime() == visit.getVisitTime().getTime() &&
                    !e.getId().equals(visit.getId())) {
                return e;
            }
        }
        return null;
    }

    public void delete(Long id) {
        Query query = em.createQuery("DELETE FROM DentistVisitEntity e WHERE e.id = :id");
        query.setParameter("id", id).executeUpdate();
    }
}
