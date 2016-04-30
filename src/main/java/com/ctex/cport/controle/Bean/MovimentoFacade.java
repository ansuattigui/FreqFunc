/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import com.ctex.cport.modelo.Funcionario;
import com.ctex.cport.modelo.Movimento;
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
public class MovimentoFacade extends AbstractFacade<Movimento> {
    @PersistenceContext(unitName = "controlePortaria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimentoFacade() {
        super(Movimento.class);
    }
    
    public List<Movimento> findAll(Divisao div) {       
        TypedQuery<Movimento> tq;
        if (div!=null) {
            tq = getEntityManager().createNamedQuery("Movimento.FindAllByDivisao", Movimento.class);
            tq.setParameter("pdivisao", div);
        } else {
            tq = getEntityManager().createNamedQuery("Movimento.FindAll", Movimento.class);
        }        
        List<Movimento> lista = tq.getResultList();
        return lista;
    }
    
    public List<Movimento> findAll(Funcionario func) {       
        TypedQuery<Movimento> tq;
        if (func!=null) {
            tq = getEntityManager().createNamedQuery("Movimento.FindAllByFuncionario", Movimento.class);
            tq.setParameter("pfuncionario", func);
        } else {
            tq = getEntityManager().createNamedQuery("Movimento.FindAll", Movimento.class);
        }        
        List<Movimento> lista = tq.getResultList();
        return lista;
    }
    
}
