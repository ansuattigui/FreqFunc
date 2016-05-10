/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template report, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ralfh
 */
@ManagedBean(name = "relatorioDivisao")
public final class RelatorioDivisao {
    
    private StreamedContent report;
    private InputStream stream;
    private String jasper;
    private ExternalContext context;
    private JRDataSource jrDataSource;
    private JasperPrint jasperPrint;
    private Divisao[] arrayDivisoes;
    
    @EJB 
    private DivisaoFacade divisaoFacade;


    /**
     * Creates a new instance of RelatorioDivisao
     */
    public RelatorioDivisao() { 
        JasperExportManager.exportReportToPdfStream(getJasperPrint(), servletOutputStream);            
//            JasperExportManager.exportReportToPdfFile(jasperPrint, destino);
        
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
     * @return the report
     */
    public StreamedContent getReport() {
        InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(getJasper());
        report = new DefaultStreamedContent(is,"application/pdf");
        return report;
    }

    /**
     * @param report the report to set
     */
    public void setReport(StreamedContent report) {
        this.report = report;
    }

    /**
     * @return the stream
     */
    public InputStream getStream() {        
        stream = getContext().getResourceAsStream(getJasper());
        return stream;
    }

    /**
     * @param stream the stream to set
     */
    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    /**
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint() {
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(), null, getJrDataSource());
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

    
}
