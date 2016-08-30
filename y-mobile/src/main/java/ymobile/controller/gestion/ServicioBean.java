package ymobile.controller.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ymobile.controller.acceso.SesionBean;
import ymobile.model.dao.entities.InfCategoria;
import ymobile.model.dao.entities.InfItem;
import ymobile.model.generico.Mensaje;
import ymobile.model.generico.Parametro;
import ymobile.model.manager.ManagerInformacion;

@ManagedBean
@ViewScoped
public class ServicioBean implements Serializable{

	private static final long serialVersionUID = -3847703785625682269L;
	
	@EJB
	private ManagerInformacion mngInfo;
	
	private String id;
	
	@NotNull(message="Título no debe ser nulo.")
	@NotBlank(message="Título no debe ser sólo espacios blancos.")
	@Pattern(regexp="[a-záéíóúÁÉÍÓÚA-Z¿? ]+", message="Título sólo debe contener letras.")
	private String titulo;
	
	@NotNull(message="Descripción no debe ser nulo.")
	@NotBlank(message="Descripción no debe ser sólo espacios blancos.")
	private String descripcion;
	
	@NotNull(message="Orden no debe ser nulo.")
	@NotBlank(message="Orden no debe ser sólo espacios blancos.")
	@Pattern(regexp="[0-9]+", message="Orden sólo debe contener enteros positivos.")
	private String orden;
	
	private String icono;
	private List<SelectItem> iconosList;
	private String estado;
	private List<SelectItem> estadosList;
	private List<InfCategoria> serviciosList;
	private boolean edicion, abrirFrm;
	
	@Inject
    SesionBean sesionBean;
	
	public ServicioBean() {}
	
	@PostConstruct
	public void init(){
		sesionBean.validarSesionPermiso("informaciong.xhtml");
		icono = Parametro.ID_ICON_ACTION;
		iconosList = new ArrayList<SelectItem>();
		estado = Parametro.ID_ESTADO_ACTIVO;
		estadosList = new ArrayList<SelectItem>();
		serviciosList = new ArrayList<InfCategoria>();
		cargarItems();
		cargarServicios();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}

	/**
	 * @return the iconosList
	 */
	public List<SelectItem> getIconosList() {
		return iconosList;
	}

	/**
	 * @param iconosList the iconosList to set
	 */
	public void setIconosList(List<SelectItem> iconosList) {
		this.iconosList = iconosList;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estadosList
	 */
	public List<SelectItem> getEstadosList() {
		return estadosList;
	}

	/**
	 * @param estadosList the estadosList to set
	 */
	public void setEstadosList(List<SelectItem> estadosList) {
		this.estadosList = estadosList;
	}

	/**
	 * @return the serviciosList
	 */
	public List<InfCategoria> getServiciosList() {
		return serviciosList;
	}

	/**
	 * @param serviciosList the serviciosList to set
	 */
	public void setServiciosList(List<InfCategoria> serviciosList) {
		this.serviciosList = serviciosList;
	}
	
	/**
	 * @return the edicion
	 */
	public boolean isEdicion() {
		return edicion;
	}

	/**
	 * @param edicion the edicion to set
	 */
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	/**
	 * @return the abrirFrm
	 */
	public boolean isAbrirFrm() {
		return abrirFrm;
	}

	/**
	 * @param abrirFrm the abrirFrm to set
	 */
	public void setAbrirFrm(boolean abrirFrm) {
		this.abrirFrm = abrirFrm;
	}

	private void cargarItems() {
		for (InfItem item : mngInfo.findItemsByCatalogo(Parametro.ID_CATALOGO_ICONOS)) {
			getIconosList().add(new SelectItem(item.getIteCodigo(), item.getIteNombre()));
		}
		for (InfItem item : mngInfo.findItemsByCatalogo(Parametro.ID_CATALOGO_ESTADOS)) {
			getEstadosList().add(new SelectItem(item.getIteCodigo(), item.getIteNombre()));
		}
	}
		
	public void cargarDatos(InfCategoria serv){
		try {
			InfCategoria servicio = mngInfo.findServicioById(serv.getCatCodigo());
			setId(servicio.getCatCodigo());setTitulo(servicio.getCatTitulo());
			setDescripcion(servicio.getCatDescripcion());setIcono(servicio.getCatIcon());
			setOrden(servicio.getCatOrden().toString());setEstado(servicio.getCatEstado());
			setEdicion(true);setAbrirFrm(true);
		} catch (Exception e) {
			System.out.println("-----------> Error cargarDatos: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al consultar los datos: "+e.getMessage());
		}
	}
	
	public void nuevo(){
		setEdicion(false);setAbrirFrm(true);
		setOrden(mngInfo.ultimoOrdenServicio().toString());
	}
	
	public void guardarEditar(){
		try {
			if(!isEdicion())
				setId(Parametro.ID_CATEGORIA+mngInfo.findIdentificadorById(Parametro.ID_NUMERACION_SERVICIO).getIdNumero());
			InfCategoria servicio = new InfCategoria();
			servicio.setCatCodigo(getId());servicio.setCatDescripcion(getDescripcion().trim());servicio.setCatEstado(getEstado());
			servicio.setCatIcon(getIcono());servicio.setCatOrden(Integer.parseInt(getOrden().trim()));servicio.setCatTitulo(getTitulo().trim());
			if(isEdicion())
				mngInfo.modificarServicio(servicio);
			else{
				mngInfo.ingresarServicio(servicio);
				mngInfo.incrementarIdentificador(Parametro.ID_NUMERACION_SERVICIO);
			}
			limpiarDatos();
			cargarServicios();
		} catch (Exception e) {
			System.out.println("-----------> Error guardarEditar Servicio: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al almacenar los datos: "+e.getMessage());
		}
	}
	
	public void limpiarDatos(){
		setId("");setTitulo("");
		setDescripcion("");setIcono(Parametro.ID_ICON_ACTION);
		setOrden("");setEstado(Parametro.ID_ESTADO_ACTIVO);
		setEdicion(false);setAbrirFrm(false);
		cargarServicios();
	}
	
	private void cargarServicios() {
		getServiciosList().clear();
		getServiciosList().addAll(mngInfo.findAllServicios());
	}
	
	public String valorItem(String codigo){
		try {
			return mngInfo.findItemByID(codigo).getIteNombre();
		} catch (Exception e) {
			System.out.println("-----------> Error valorItem: ");e.printStackTrace();
			return "Error";
		}
	}
	
	public String pintar(String codigo){
		if(codigo.equals("A"))
			return "green";
		else if(codigo.equals("I"))
			return "red";
		else
			return "black";
	}
}
