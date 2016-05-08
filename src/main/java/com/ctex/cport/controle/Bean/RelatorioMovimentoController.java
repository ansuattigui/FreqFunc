/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Movimento;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author ralfh
 */
@ManagedBean(name = "relatorioMovimentoController")
@SessionScoped
public class RelatorioMovimentoController implements Serializable {

    /**
     * Creates a new instance of RelatorioMovimentoController
     */
    public RelatorioMovimentoController() {
    }
    
    private static List<Movimento> listaMovimentos;
 
    @EJB 
    private static MovimentoFacade movimentoFacade;
     
    public static List<Movimento> getListaMovimentos() {
        listaMovimentos=movimentoFacade.findAll();
        return listaMovimentos;
    }
 
    public void setListaMovimentos(List<Movimento> listaMovimentos) {
        RelatorioMovimentoController.listaMovimentos = listaMovimentos;
    }
    
    JasperPrint jasperPrint;
    public void init() throws JRException{        
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(getListaMovimentos());
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/relatorios/movimento/Movimento.jasper");
        jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
    }
     
    public void geraRelatorio() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String arquivo = context.getRealPath("/resources/relatorios/movimento/Movimento.jasper");
 
        JRDataSource jrds = new JRBeanCollectionDataSource(getListaMovimentos());   
        gerarRelatorioWeb(jrds, null, arquivo);
    }
    
    private void gerarRelatorioWeb(JRDataSource jrds, Map<String, Object> parametros, String arquivo) {
        ServletOutputStream servletOutputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
 
        try {
            servletOutputStream = response.getOutputStream();            
            JasperRunManager.runReportToPdfStream(new FileInputStream(new File(arquivo)), servletOutputStream, parametros, jrds);            
            response.setContentType("application/pdf");
            servletOutputStream.flush();
            servletOutputStream.close();
            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    public void pdf() throws JRException, IOException{
         init();
         HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
         httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
         ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
         JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
         FacesContext.getCurrentInstance().responseComplete();
    }            
}
