/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template report, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import com.ctex.cport.modelo.Movimento;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

/**
 *
 * @author ralfh
 */
@ManagedBean(name = "relatorioMovimento")
@SessionScoped
public class RelatorioMovimento implements Serializable {
    
    private String jasper;
    private String relatorio;
    private ExternalContext context;
    private JRDataSource jrDataSource;
    private JasperPrint jasperPrint;
    private Movimento[] arrayMovimento;
    private Object modelo;
        
    @EJB 
    private MovimentoFacade movimentoFacade;

 
    /**
     * Creates a new instance of RelatorioDivisao
     */
    public RelatorioMovimento() { 
    }    

    /**
     * @return the jasper
     */
    public String getJasper() {
        jasper = getContext().getRealPath("WEB-INF/relatorios/movimento/movimento.jasper");
        return jasper;
    }

    /**
     * @param jasper the jasper to set
     */
    public void setJasper(String jasper) {
        this.jasper = jasper;
    }

    /**
     * @return the context
     */
    public ExternalContext getContext() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ExternalContext context) {
        this.context = context;
    }

    public JRDataSource getJrDataSource() {
        jrDataSource = new JRBeanArrayDataSource(getArrayMovimento());
        return jrDataSource;
    }   
    
    /**
     * @param filtro
     * @return the jrDataSource
     */
    public JRDataSource getJrDataSource(Object filtro) {
        jrDataSource = new JRBeanArrayDataSource(getArrayMovimento(filtro));
        return jrDataSource;
    }

    /**
     * @param jrDataSource the jrDataSource to set
     */
    public void setJrDataSource(JRDataSource jrDataSource) {
        this.jrDataSource = jrDataSource;
    }

    public Movimento[] getArrayMovimento() {
        List<Movimento> lista;
        lista = movimentoFacade.findAll();        
        arrayMovimento = lista.toArray(new Movimento[lista.size()]);        
        return arrayMovimento;
    }
    

    public Movimento[] getArrayMovimento(Object filtro) {
        List<Movimento> lista = null;
        if (filtro instanceof Divisao) {
            lista = movimentoFacade.findAll((Divisao) filtro);        
        }
        arrayMovimento = lista.toArray(new Movimento[lista.size()]);        
        return arrayMovimento;
    }

    public void setArrayMovimento(Movimento[] arrayMovimento) {
        this.arrayMovimento = arrayMovimento;
    }

    
    public JasperPrint getJasperPrint() {        
        ImageIcon logotipo = new ImageIcon(getContext().getRealPath("/resources/img/logo-ctex.png"));                
        HashMap hm = new HashMap<>();
        hm.put("par_logotipo",logotipo.getImage());
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(),hm, getJrDataSource());
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jasperPrint;
    }
    
    
    /**
     * @param filtro
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint(Object filtro) {        
        ImageIcon logotipo = new ImageIcon(getContext().getRealPath("/resources/img/logo-ctex.png"));                
        HashMap hm = new HashMap<>();
        hm.put("par_logotipo",logotipo.getImage());
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(),hm, getJrDataSource(filtro));
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jasperPrint;
    }

    /**
     * @param jasperPrint the jasperPrint to set
     */
    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void setReport() {        
         relatorio = "/reports/movimento.pdf";
        try { 
            JasperExportManager.exportReportToPdfFile(getJasperPrint(), getContext().getRealPath(relatorio));
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * @param filtro
     */
    public void setReport(Object filtro) {        
         relatorio = "/reports/movimento.pdf";
        try { 
            if (filtro instanceof Divisao) {
                JasperExportManager.exportReportToPdfFile(getJasperPrint(filtro), getContext().getRealPath(relatorio));
            }
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getRelatorio() {
        return relatorio;
    }

    /**
     * @param relatorio the relatorio to set
     */
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    /**
     * @return the modelo
     */
    public Object getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     * @return 
     */
    public String setModelo(Object modelo) {
        this.modelo = modelo;
        if (modelo instanceof Divisao) {
            setReport(modelo);
        } else if (modelo == null) {
            setReport();
        }
        return "/relatorios/movimento/movimento";
    }

}
