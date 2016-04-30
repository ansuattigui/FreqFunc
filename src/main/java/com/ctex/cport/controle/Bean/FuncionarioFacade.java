/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import com.ctex.cport.modelo.Funcionario;
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
public class FuncionarioFacade extends AbstractFacade<Funcionario> {
    @PersistenceContext(unitName = "controlePortaria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionarioFacade() {
        super(Funcionario.class);
    }
    
    public List<Funcionario> findAll(Divisao div) {       
        TypedQuery<Funcionario> tq;
        if (div!=null) {
            tq = getEntityManager().createNamedQuery("Funcionario.FindAllByDivisao", Funcionario.class);
            tq.setParameter("pdivisao", div);
        } else {
            tq = getEntityManager().createNamedQuery("Funcionario.FindAll", Funcionario.class);
        }
        
        List<Funcionario> lista = tq.getResultList();
        return lista;
    }
    
    
}
