/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ralfh
 */
@Stateless
public class DivisaoFacade extends AbstractFacade<Divisao> {
    @PersistenceContext(unitName = "controlePortaria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DivisaoFacade() {
        super(Divisao.class);
    }
    
    
    @Override
    public List<Divisao> findAll() {        
        TypedQuery<Divisao> tq = getEntityManager().createNamedQuery("Divisao.findAll", Divisao.class);
        List<Divisao> lista = tq.getResultList();
        return lista;
    }
}
