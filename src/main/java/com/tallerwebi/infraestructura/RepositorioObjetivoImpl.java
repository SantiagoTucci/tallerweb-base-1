package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.objetivo.RepositorioObjetivo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioObjetivo")
public class RepositorioObjetivoImpl implements RepositorioObjetivo {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioObjetivoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Objetivo> listarObjetivos() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Objetivo", Objetivo.class)
                .getResultList();
    }


    }
