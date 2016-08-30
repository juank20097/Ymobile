package ymobile.controller.acceso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ymobile.controller.acceso.Menu;
import ymobile.model.generico.Mensaje;
import ymobile.model.manager.ManagerAcceso;

@SessionScoped
@ManagedBean
public class SesionBean implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 4440643807067915096L;
	
	@EJB
	private ManagerAcceso mngAcc;
	
	private List<Menu> menu;
	private String alias;
	private String pass;
	
	public SesionBean() {
		menu = new ArrayList<Menu>();
	}

	/**
	 * @return the menu
	 */
	public List<Menu> getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	/**
	 * Maneja los accesos al Sistema
	 * @return String
	 */
	public String logIn(){
		try {
			if(getAlias().trim().isEmpty() || getPass().trim().isEmpty()){
				Mensaje.crearMensajeWARN("Usuario y contraseña son obligatorios.");
				return "";
			}else{
				if(StringUtils.isAlpha(getAlias()))
					logInAdmin();
				else{
					Mensaje.crearMensajeWARN("Usuario no debe contener caracteres especiales.");
					return "";
				}
				setPass("");
				return "/views/index?faces-redirect=true";
			}
		} catch (Exception e) {
			System.out.println("Error Login YMOBIL:");
			e.printStackTrace();
			Mensaje.crearMensajeWARN("Error al acceder al sistema "+e.getMessage());
			return "";
		}
	}
		
	/**
	 * Acceso para Usuario Administrador
	 * @throws Exception
	 */
	private void logInAdmin() throws Exception{
		setMenu(mngAcc.loginWS(getAlias(), getPass(), "YMOBIL"));
	}
	
	/**
	 * Permite deslogearse del sistema
	 * @return
	 */
	 public String logOut(){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        setMenu(new ArrayList<Menu>());limpiarFrm();
    	return "/index?faces-redirect=true";
	 }
	
	/**
	 * Borra datos del formulario de login
	 */
	private void limpiarFrm(){
		setPass("");setAlias("");
	}
	
	/**
	  * Verifica y devuelve el usuario en sesión
	  * @param vista página principal de acceso
	  * @return String
	 */
	public String validarSesionPermiso(String vista){
		 SesionBean user = (SesionBean) ((HttpSession) FacesContext.getCurrentInstance()
	                .getExternalContext().getSession(false)).getAttribute("sesionBean");
		 if (user==null || user.getAlias() == null ||  user.getAlias().isEmpty()) {
			 try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/y-mobile/index.xhtml");
            } catch (IOException ex) {
            	Mensaje.crearMensajeERROR(ex.getMessage());
            }
            return null;
		 }else{
			 if(mngAcc.poseePermiso(vista, user.getMenu())){
				 return user.getAlias();
			 }else{
        		try {
	                FacesContext.getCurrentInstance().getExternalContext().redirect("/y-mobile/views/index.xhtml");
	            } catch (IOException ex) {
	            	Mensaje.crearMensajeERROR(ex.getMessage());
	            }
	            return null;
        	}
		 }
	}
	
	/**
	  * Método para validar sesión en el INDEX
	  */
	 public void validaIndex(){
		 SesionBean user = (SesionBean) ((HttpSession) FacesContext.getCurrentInstance()
	                .getExternalContext().getSession(false)).getAttribute("sesionBean");
	     if (user==null || user.getAlias() == null ||  user.getAlias().isEmpty()) {
	            try {
	                FacesContext.getCurrentInstance().getExternalContext().redirect("/y-mobile/index.xhtml");
	            } catch (IOException ex) {
	            	Mensaje.crearMensajeERROR(ex.getMessage());
	            }
	     }
	 }

}
