package com.tallerwebi.dominio.objetivo;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioObjetivoImpl implements ServicioObjetivo {

    private RepositorioObjetivo repositorioObjetivo;

    public ServicioObjetivoImpl(RepositorioObjetivo repositorioObjetivo){
        this.repositorioObjetivo = repositorioObjetivo;
    }

    @Override
    public List<Objetivo> listarObjetivos() {
        return repositorioObjetivo.listarObjetivos();
    }




}
