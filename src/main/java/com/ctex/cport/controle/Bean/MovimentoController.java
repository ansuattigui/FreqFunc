package com.ctex.cport.controle.Bean;

import com.ctex.cport.controle.Bean.util.JsfUtil;
import com.ctex.cport.controle.Bean.util.JsfUtil.PersistAction;
import com.ctex.cport.modelo.Divisao;
import com.ctex.cport.modelo.Funcionario;
import com.ctex.cport.modelo.Movimento;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("movimentoController")
@SessionScoped
public class MovimentoController implements Serializable {

    @EJB
    private com.ctex.cport.controle.Bean.MovimentoFacade ejbFacade;
    private List<Movimento> items = null;
    private Movimento selected;
    private Object filtro;
    private String tipoFiltro;    

    public MovimentoController() {
    }

    public Movimento getSelected() {
        return selected;
    }

    public void setSelected(Movimento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MovimentoFacade getFacade() {
        return ejbFacade;
    }

    public Movimento prepareCreate() {
        selected = new Movimento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovimentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovimentoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MovimentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Movimento> getItems() {
        if (items == null) {            
            items = getFacade().findAll();
        } else if ("DIVISAO".equals(tipoFiltro)) {
            items = getFacade().findAll((Divisao)filtro);
        } else if ("FUNCIONARIO".equals(tipoFiltro)) {
            items = getFacade().findAll((Funcionario)filtro);
        } else {
            items = getFacade().findAll();
        }
        return items;
    }
    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Movimento getMovimento(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Movimento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Movimento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Object getFiltro() {
        return filtro;
    }
    
    public void setFiltro(Object filtro) {        
        if (Divisao.class.isInstance(filtro)) {
            tipoFiltro = "DIVISAO";
        } else if (Funcionario.class.isInstance(filtro)) {
            tipoFiltro = "FUNCIONARIO";
        } else {
            tipoFiltro = "";
        }
        this.filtro = filtro;
    }
    
    
    // Manipulador de Evento para o Calendário da Data do Movimento
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }    
    
    public void onTimeSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hora Selected", format.format(event.getObject())));
    }            
    
    @FacesConverter(forClass = Movimento.class)
    public static class MovimentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimentoController controller = (MovimentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimentoController");
            return controller.getMovimento(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Movimento) {
                Movimento o = (Movimento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Movimento.class.getName()});
                return null;
            }
        }

    }

}
