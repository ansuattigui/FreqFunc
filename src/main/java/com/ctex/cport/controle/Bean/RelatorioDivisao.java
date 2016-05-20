/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template report, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
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
@ManagedBean(name = "relatorioDivisao")
@SessionScoped
public class RelatorioDivisao implements Serializable {
    
    private String jasper;
    private String relatorio;
    private ExternalContext context;
    private JRDataSource jrDataSource;
    private JasperPrint jasperPrint;
    private Divisao[] arrayDivisoes;
    private String contentType;
        
    @EJB 
    private DivisaoFacade divisaoFacade;

 
    /**
     * Creates a new instance of RelatorioDivisao
     */
    public RelatorioDivisao() { 
//        relatorio = getContext().getRealPath("reports")+ "\"  +"divisao.pdf";
    }    

    /**
     * @return the jasper
     */
    public String getJasper() {
        jasper = getContext().getRealPath("WEB-INF/relatorios/divisao/divisao.jasper");
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

    /**
     * @return the jrDataSource
     */
    public JRDataSource getJrDataSource() {
        jrDataSource = new JRBeanArrayDataSource(getArrayDivisoes());
        return jrDataSource;
    }

    /**
     * @param jrDataSource the jrDataSource to set
     */
    public void setJrDataSource(JRDataSource jrDataSource) {
        this.jrDataSource = jrDataSource;
    }

    /**
     * @return the arrayDivisoes
     */
    public Divisao[] getArrayDivisoes() {
        List<Divisao> lista = divisaoFacade.findAll();        
        arrayDivisoes = lista.toArray(new Divisao[lista.size()]);        
        return arrayDivisoes;
    }

    /**
     * @param arrayDivisoes the arrayDivisoes to set
     */
    public void setArrayDivisoes(Divisao[] arrayDivisoes) {
        this.arrayDivisoes = arrayDivisoes;
    }

    /**
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint() {
        
        ImageIcon logotipo = new ImageIcon(getContext().getRealPath("/resources/img/logo-ctex.png"));        
        
        HashMap hm = new HashMap<>();
        hm.put("par_logotipo",logotipo.getImage());
        
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(),hm, getJrDataSource());
        } catch (JRException ex) {
            Logger.getLogger(RelatorioDivisao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jasperPrint;
    }

    /**
     * @param jasperPrint the jasperPrint to set
     */
    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    /**
     * @return the relatorio
     */
    public String getRelatorio() {        
        relatorio = "/reports/divisao.pdf";
        try {                
            JasperExportManager.exportReportToPdfFile(getJasperPrint(), getContext().getRealPath(relatorio));
        } catch (JRException ex) {
            Logger.getLogger(RelatorioDivisao.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(relatorio);
        return relatorio;
    }

    /**
     * @param relatorio the relatorio to set
     */
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

}
