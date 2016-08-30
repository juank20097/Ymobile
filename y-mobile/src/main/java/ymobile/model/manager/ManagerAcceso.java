package ymobile.model.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ymobile.controller.acceso.Menu;
import ymobile.controller.acceso.Submenu;
import ymobile.model.dao.entities.InfParametro;
import ymobile.model.manager.ManagerDAO;
import ymobile.model.servicios.ConsumeREST;

@Stateless
public class ManagerAcceso {
	
	@EJB
	private ManagerDAO mngDAO;
	
	public ManagerAcceso() {}
	
	/**
	 * Lista de menus para menú dinámico (ADMINISTRADOR)
	 * @param menu
	 * @return List<Menu>
	 */
	public List<Menu> cargarMenu(JSONArray menu){
		List<Menu> menus = new ArrayList<Menu>();
		for (Object objmenu : menu) {
			Menu gmenu = new Menu();
			gmenu.setNombre(((JSONObject)objmenu).get("nombre").toString());
			gmenu.setLstLinks(new ArrayList<Submenu>());
			JSONArray jvistas = (JSONArray) ((JSONObject)objmenu).get("vistas");
			for (Object objvis : jvistas) {
				gmenu.getLstLinks().add(new Submenu(((JSONObject) objvis).get("nombre").toString(),
						((JSONObject) objvis).get("link").toString()));
			}
			menus.add(gmenu);
			gmenu=null;
		}	
		return menus;
	}
	
	/**
	 * Lista de menus para menú dinámico (OPERADOR)
	 * @return List<Menu>
	 */
	public List<Menu> cargarMenu(){
		List<Menu> menus = new ArrayList<Menu>();
		Menu gmenu = new Menu();
		gmenu.setNombre("Gestión");
		gmenu.setLstLinks(new ArrayList<Submenu>());
		gmenu.getLstLinks().add(new Submenu("Solicitud Ingreso","solingreso.xhtml"));
		gmenu.getLstLinks().add(new Submenu("Solicitud Salida","solsalida.xhtml"));
		menus.add(gmenu);
		return menus;
	}
	
	/***
	 * Consulta los permisos en el SWLogin
	 * @param usr
	 * @param pass
	 * @param aplicacion
	 * @return List<Menu>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> loginWS(String usr, String pass, String aplicacion) throws Exception
	{
		InfParametro param = findParametroByID("login_ws");
		if(param==null)
			throw new Exception("Error al consultar parámetro de logeo.");
		List<Menu> lmenu = new ArrayList<Menu>();
		JSONObject salida = new JSONObject();
		salida.put("usr", usr);salida.put("pwd", pass);salida.put("apl", aplicacion);
		JSONObject respuesta = ConsumeREST.postClient(param.getParValor(),salida);
		if(!respuesta.get("status").equals("OK"))
			throw new Exception("ERROR al consultar sus permisos: "+respuesta.get("mensaje").toString());
		else
			lmenu = cargarMenu((JSONArray) respuesta.get("value"));
		return lmenu;
		
	}
		
	/**
	 * Valida si posee permisos
	 * @param vista
	 * @param permisos
	 * @return true o false
	 */
	public boolean poseePermiso(String vista, List<Menu> permisos){
		for (Menu menu : permisos) {
			for (Submenu submenu : menu.getLstLinks()) {
				if(submenu.getLink().equals(vista))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Buscar un parámetro por id
	 * @param id
	 * @return ZedParametro
	 * @throws Exception
	 */
	public InfParametro findParametroByID(String id) throws Exception{
		return (InfParametro) mngDAO.findById(InfParametro.class, id);
	}
	
}
