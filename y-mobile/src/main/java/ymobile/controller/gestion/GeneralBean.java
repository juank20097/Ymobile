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
import org.hibernate.validator.constraints.URL;

import ymobile.model.dao.entities.InfImagen;
import ymobile.model.dao.entities.InfInformacionGeneral;
import ymobile.model.dao.entities.InfItem;
import ymobile.model.generico.Mensaje;
import ymobile.model.generico.Parametro;
import ymobile.model.manager.ManagerInformacion;
import ymobile.controller.acceso.SesionBean;

@ManagedBean
@ViewScoped
public class GeneralBean implements Serializable{

	private static final long serialVersionUID = -245909736512460241L;
	
	@EJB
	private ManagerInformacion mngInfo;

	private String codigo;
	
	@NotNull(message="Descripción no debe ser nulo.")
	@NotBlank(message="Descripción no debe ser sólo espacios blancos.")
	private String descripcion;
	
	@NotNull(message="Título no debe ser nulo.")
	@NotBlank(message="Título no debe ser sólo espacios blancos.")
	@Pattern(regexp="[a-záéíóúÁÉÍÓÚA-Z¿? ]+", message="Título sólo debe contener letras.")
	private String titulo;
	
	@NotNull(message="Orden no debe ser nulo.")
	@NotBlank(message="Orden no debe ser sólo espacios blancos.")
	@Pattern(regexp="[0-9]+", message="Orden sólo debe contener enteros positivos.")
	private String orden;
	
	private String estado;
	private List<SelectItem> estadosList;
	private String icono;
	private List<SelectItem> iconosList;
	private List<InfInformacionGeneral> informacionList;
	private InfImagen imagenSeleccionada;
	
	@NotNull(message="Dirección Url no debe ser nulo")
	@NotBlank(message="Dirección Url no debe ser sólo espacios blancos.")
	@URL(message="Dirección Url no válida.")
	private String imagenDireccion;
	
	@NotNull(message="Orden no debe ser nulo.")
	@NotBlank(message="Orden no debe ser sólo espacios blancos.")
	@Pattern(regexp="[0-9]+", message="Orden sólo debe contener enteros positivos.")
	private String imagenOrden;
	
	private List<InfImagen> imagenList;
	private boolean edicion, abrirFrm, creado, edicionImg, abrirFrmImg;
	private String urlImgSeleccionada;
	
	@Inject
    SesionBean sesionBean;
	
	public GeneralBean() {}
	
	@PostConstruct
	public void init(){
		sesionBean.validarSesionPermiso("categoria.xhtml");
		icono = Parametro.ID_ICON_ACTION;
		iconosList = new ArrayList<SelectItem>();
		estado = Parametro.ID_ESTADO_ACTIVO;
		estadosList = new ArrayList<SelectItem>();
		informacionList = new ArrayList<InfInformacionGeneral>();
		imagenList = new ArrayList<InfImagen>();
		cargarItems();
		cargarInformacion();
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return the informacionList
	 */
	public List<InfInformacionGeneral> getInformacionList() {
		return informacionList;
	}

	/**
	 * @param informacionList the informacionList to set
	 */
	public void setInformacionList(List<InfInformacionGeneral> informacionList) {
		this.informacionList = informacionList;
	}

	/**
	 * @return the imagenSeleccionada
	 */
	public InfImagen getImagenSeleccionada() {
		return imagenSeleccionada;
	}

	/**
	 * @param imagenSeleccionada the imagenSeleccionada to set
	 */
	public void setImagenSeleccionada(InfImagen imagenSeleccionada) {
		this.imagenSeleccionada = imagenSeleccionada;
	}

	/**
	 * @return the imagenDireccion
	 */
	public String getImagenDireccion() {
		return imagenDireccion;
	}

	/**
	 * @param imagenDireccion the imagenDireccion to set
	 */
	public void setImagenDireccion(String imagenDireccion) {
		this.imagenDireccion = imagenDireccion;
	}

	/**
	 * @return the imagenOrden
	 */
	public String getImagenOrden() {
		return imagenOrden;
	}

	/**
	 * @param imagenOrden the imagenOrden to set
	 */
	public void setImagenOrden(String imagenOrden) {
		this.imagenOrden = imagenOrden;
	}

	/**
	 * @return the imagenList
	 */
	public List<InfImagen> getImagenList() {
		return imagenList;
	}

	/**
	 * @param imagenList the imagenList to set
	 */
	public void setImagenList(List<InfImagen> imagenList) {
		this.imagenList = imagenList;
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

	/**
	 * @return the creado
	 */
	public boolean isCreado() {
		return creado;
	}

	/**
	 * @param creado the creado to set
	 */
	public void setCreado(boolean creado) {
		this.creado = creado;
	}

	/**
	 * @return the edicionImg
	 */
	public boolean isEdicionImg() {
		return edicionImg;
	}

	/**
	 * @param edicionImg the edicionImg to set
	 */
	public void setEdicionImg(boolean edicionImg) {
		this.edicionImg = edicionImg;
	}

	/**
	 * @return the abrirFrmImg
	 */
	public boolean isAbrirFrmImg() {
		return abrirFrmImg;
	}

	/**
	 * @param abrirFrmImg the abrirFrmImg to set
	 */
	public void setAbrirFrmImg(boolean abrirFrmImg) {
		this.abrirFrmImg = abrirFrmImg;
	}
	
	/**
	 * @return the urlImgSeleccionada
	 */
	public String getUrlImgSeleccionada() {
		return urlImgSeleccionada;
	}

	/**
	 * @param urlImgSeleccionada the urlImgSeleccionada to set
	 */
	public void setUrlImgSeleccionada(String urlImgSeleccionada) {
		this.urlImgSeleccionada = urlImgSeleccionada;
	}

	private void cargarItems() {
		for (InfItem item : mngInfo.findItemsByCatalogo(Parametro.ID_CATALOGO_ICONOS)) {
			getIconosList().add(new SelectItem(item.getIteCodigo(), item.getIteNombre()));
		}
		for (InfItem item : mngInfo.findItemsByCatalogo(Parametro.ID_CATALOGO_ESTADOS)) {
			getEstadosList().add(new SelectItem(item.getIteCodigo(), item.getIteNombre()));
		}
	}
	
	public void cargarDatosInformacion(InfInformacionGeneral info){
		try{
			InfInformacionGeneral informacion = mngInfo.findInformacionGeneralByID(info.getInfCodigoGen());
			setCodigo(informacion.getInfCodigoGen());setTitulo(informacion.getInfTitulo());
			setDescripcion(informacion.getInfDescripcion());setIcono(informacion.getInfIcono());
			setEstado(informacion.getInfEstado());setOrden(informacion.getInfOrden().toString());
			cargarImagenes(informacion.getInfCodigoGen());
			setEdicion(true);setAbrirFrm(true);setCreado(true);
		} catch (Exception e) {
			System.out.println("-----------> Error cargarDatosInformacion: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al consultar los datos: "+e.getMessage());
		}
	}
	
	public void nuevo(){
		setEdicion(false);setAbrirFrm(true);
		setOrden(mngInfo.ultimoOrdenInformacionGeneral().toString());
		cargarImagenes(null);
	}
	
	public void guardarEditarInformacion(){
		try {
			if(!isEdicion())
				setCodigo(Parametro.ID_INF_GENERAL+mngInfo.findIdentificadorById(Parametro.ID_NUMERACION_INF_GENERAL).getIdNumero());
			InfInformacionGeneral informacion = new InfInformacionGeneral();
			informacion.setInfCodigoGen(getCodigo());informacion.setInfTitulo(getTitulo().trim());informacion.setInfDescripcion(getDescripcion().trim());
			informacion.setInfIcono(getIcono());informacion.setInfOrden(Integer.parseInt(getOrden().trim()));informacion.setInfEstado(getEstado());
			if(isEdicion())
				mngInfo.modificarInformacionGeneral(informacion);
			else{
				mngInfo.ingresarInformacionGeneral(informacion);
				mngInfo.incrementarIdentificador(Parametro.ID_NUMERACION_INF_GENERAL);
				setEdicion(true);setCreado(true);
			}
			cargarInformacion();
		} catch (Exception e) {
			System.out.println("-----------> Error guardarEditarInformacion General: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al almacenar los datos: "+e.getMessage());
		}
	}
			
	public void agregarImagen(){
		setEdicionImg(false);setAbrirFrmImg(true);
		setImagenSeleccionada(new InfImagen());
	}
	
	public void cargarDatosImagen(InfImagen imagen){
		try{
			InfImagen img = mngInfo.findImagenById(imagen.getImaCodigo());
			setImagenSeleccionada(img);setImagenOrden(img.getImaOrden().toString());
			setImagenDireccion(img.getImaDireccion());
			setEdicionImg(true);setAbrirFrmImg(true);
		} catch (Exception e) {
			System.out.println("-----------> Error cargarDatosImagen: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al consultar los datos: "+e.getMessage());
		}
	}
	
	public void verImagenSeleccionada(InfImagen img){
		setUrlImgSeleccionada(img.getImaDireccion());
	}
	
	public void guardarEditarImagen(){
		try {
			getImagenSeleccionada().setImaOrden(Integer.parseInt(getImagenOrden().trim()));
			getImagenSeleccionada().setImaDireccion(getImagenDireccion().trim());
			getImagenSeleccionada().setImaNombre(getImagenDireccion().trim());
			getImagenSeleccionada().setInfInformacionGeneral(mngInfo.findInformacionGeneralByID(getCodigo()));
			if(isEdicionImg())
				mngInfo.modificarImagen(getImagenSeleccionada());
			else{
				getImagenSeleccionada().setImaCodigo("img_"+Parametro.nombreImagen());
				mngInfo.ingresarImagen(getImagenSeleccionada());
			}
			limpiarDatosImagen();
			cargarImagenes(getCodigo());
		} catch (Exception e) {
			System.out.println("-----------> Error guardarEditarImagen InfoGeneral: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al almacenar los datos: "+e.getMessage());
		}
	}
	
	public void volver(){
		limpiarDatosInformacion();
		limpiarDatosImagen();
		cargarInformacion();
	}
	
	private void limpiarDatosInformacion(){
		setCodigo(null);setTitulo(null);
		setDescripcion(null);setIcono(Parametro.ID_ICON_ACTION);
		setEstado(Parametro.ID_ESTADO_ACTIVO);setOrden(null);
		cargarImagenes(null);setEdicion(false);setAbrirFrm(false);
		setCreado(false);
	}
	
	public void limpiarDatosImagen(){
		setImagenSeleccionada(null);setImagenOrden(null);setImagenDireccion(null);
		setEdicionImg(false);setAbrirFrmImg(false);
	}
	
	private void cargarInformacion(){
		getInformacionList().clear();
		getInformacionList().addAll(mngInfo.findAllInformacionGeneral());
	}
	
	public void nuevaImagen(){
		setEdicionImg(false);setAbrirFrmImg(true);
		setImagenSeleccionada(new InfImagen());
		setImagenOrden(mngInfo.ultimoOrdenImagen(getCodigo(), Parametro.COD_GENERAL).toString());
	}
	
	private void cargarImagenes(String codInformacion) {
		getImagenList().clear();
		if(codInformacion!=null)
			getImagenList().addAll(mngInfo.findImagenesInformacionGeneral(codInformacion));
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
