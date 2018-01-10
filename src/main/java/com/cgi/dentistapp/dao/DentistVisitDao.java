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
                "Keegi Veel"
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

    public void delete(Long id) {
        Query query = em.createQuery("DELETE FROM DentistVisitEntity e WHERE e.id = :id");
        query.setParameter("id", id).executeUpdate();
    }
}
