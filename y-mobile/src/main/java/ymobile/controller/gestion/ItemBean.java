package ymobile.controller.gestion;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import ymobile.controller.acceso.SesionBean;
import ymobile.model.dao.entities.InfCategoria;
import ymobile.model.dao.entities.InfImagen;
import ymobile.model.dao.entities.InfInformacion;
import ymobile.model.dao.entities.InfItem;
import ymobile.model.generico.Mensaje;
import ymobile.model.generico.Parametro;
import ymobile.model.manager.ManagerInformacion;

@ManagedBean
@ViewScoped
public class ItemBean implements Serializable{

	private static final long serialVersionUID = 5639015305984311205L;
	private String ALL_CATEGORIAS = "ALL";
	
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
	private String categoriaId;
	private List<SelectItem> categoriasList;
	private String leerMas;
	private Date fecha;
	private MapModel draggableModel;
    private Marker marker;
	private double lat, lng;
    private List<InfInformacion> informacionList;
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
	private boolean edicion, abrirFrm, creado, edicionImg, 
		abrirFrmImg, poseeLink, poseeFecha, poseeMapa;
	private String urlImgSeleccionada;
	
	@Inject
    SesionBean sesionBean;
	
	public ItemBean() {}
	
	@PostConstruct
	public void init(){
		sesionBean.validarSesionPermiso("informacion.xhtml");
		estado = Parametro.ID_ESTADO_ACTIVO;
		estadosList = new ArrayList<SelectItem>();
		categoriaId = ALL_CATEGORIAS;
		categoriasList = new ArrayList<SelectItem>();
		informacionList = new ArrayList<InfInformacion>();
		imagenList = new ArrayList<InfImagen>();
		defaultData();
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
	 * @return the categoriaId
	 */
	public String getCategoriaId() {
		return categoriaId;
	}

	/**
	 * @param categoriaId the categoriaId to set
	 */
	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	/**
	 * @return the categoriasList
	 */
	public List<SelectItem> getCategoriasList() {
		return categoriasList;
	}

	/**
	 * @param categoriasList the categoriasList to set
	 */
	public void setCategoriasList(List<SelectItem> categoriasList) {
		this.categoriasList = categoriasList;
	}

	/**
	 * @return the leerMas
	 */
	public String getLeerMas() {
		return leerMas;
	}

	/**
	 * @param leerMas the leerMas to set
	 */
	public void setLeerMas(String leerMas) {
		this.leerMas = leerMas;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the draggableModel
	 */
	public MapModel getDraggableModel() {
		return draggableModel;
	}

	/**
	 * @param draggableModel the draggableModel to set
	 */
	public void setDraggableModel(MapModel draggableModel) {
		this.draggableModel = draggableModel;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the informacionList
	 */
	public List<InfInformacion> getInformacionList() {
		return informacionList;
	}

	/**
	 * @param informacionList the informacionList to set
	 */
	public void setInformacionList(List<InfInformacion> informacionList) {
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
	 * @return the poseeLink
	 */
	public boolean isPoseeLink() {
		return poseeLink;
	}

	/**
	 * @param poseeLink the poseeLink to set
	 */
	public void setPoseeLink(boolean poseeLink) {
		this.poseeLink = poseeLink;
	}

	/**
	 * @return the poseeFecha
	 */
	public boolean isPoseeFecha() {
		return poseeFecha;
	}

	/**
	 * @param poseeFecha the poseeFecha to set
	 */
	public void setPoseeFecha(boolean poseeFecha) {
		this.poseeFecha = poseeFecha;
	}

	/**
	 * @return the poseeMapa
	 */
	public boolean isPoseeMapa() {
		return poseeMapa;
	}

	/**
	 * @param poseeMapa the poseeMapa to set
	 */
	public void setPoseeMapa(boolean poseeMapa) {
		this.poseeMapa = poseeMapa;
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
		for (InfItem item : mngInfo.findItemsByCatalogo(Parametro.ID_CATALOGO_ESTADOS)) {
			getEstadosList().add(new SelectItem(item.getIteCodigo(), item.getIteNombre()));
		}
		getCategoriasList().add(new SelectItem(ALL_CATEGORIAS,"Seleccionar"));
		for (InfCategoria cat : mngInfo.findAllServicios()) {
			getCategoriasList().add(new SelectItem(cat.getCatCodigo(),cat.getCatTitulo()));
		}
	}
	
	public void cargarDatosInformacion(InfInformacion info){
		try{
			InfInformacion informacion = mngInfo.findInformacionByID(info.getInfCodigo());
			setCodigo(informacion.getInfCodigo());setTitulo(informacion.getInfTitulo());
			setCategoriaId(informacion.getInfCategoria().getCatCodigo());setDescripcion(informacion.getInfDescripcion());
			setEstado(informacion.getInfEstado());setOrden(informacion.getInfOrden().toString());
			setFecha(informacion.getInfFecha());setLeerMas(informacion.getInfUrl());
			setLat(Double.parseDouble(informacion.getInfLatitud()));
			setLng(Double.parseDouble(informacion.getInfLongitud()));
			mostrarDatosOpcionales(informacion);
			cargarImagenes(informacion.getInfCodigo());
			setEdicion(true);setAbrirFrm(true);setCreado(true);
		} catch (Exception e) {
			System.out.println("-----------> Error cargarDatosInformacion: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al consultar los datos: "+e.getMessage());
		}
	}
	
	private void mostrarDatosOpcionales(InfInformacion informacion) {
		if(!(new SimpleDateFormat("yyyy-MM-dd").format(informacion.getInfFecha())).equals(Parametro.DEFAULT_DATE))
			setPoseeFecha(true);
		if(Double.parseDouble(informacion.getInfLatitud())!=Parametro.DEFAULT_COORD){
			setPoseeMapa(true);
			modeloMapa();
		}
		if(!informacion.getInfUrl().equals(Parametro.DEFAULT_LINK))
			setPoseeLink(true);
	}

	public void nuevo(){
		defaultMapa();
		setEdicion(false);setAbrirFrm(true);
		cargarImagenes(null);
	}
	
	public void sugerirOrden(){
		if(!getCategoriaId().equals(ALL_CATEGORIAS))
			setOrden(mngInfo.ultimoOrdenInformacion(getCategoriaId()).toString());
		else
			setOrden(null);
	}
	
	public void guardarEditarInformacion(){
		try {
			if(camposOpcionalesValidos()){
				if(!getCategoriaId().equals(ALL_CATEGORIAS)){
					if(!isEdicion())
						setCodigo(Parametro.ID_INF_ITEM+mngInfo.findIdentificadorById(Parametro.ID_NUMERACION_INF_ITEM).getIdNumero());
					InfInformacion i = new InfInformacion();
					i.setInfCodigo(getCodigo());i.setInfCategoria(mngInfo.findServicioById(getCategoriaId()));
					i.setInfTitulo(getTitulo().trim());i.setInfDescripcion(getDescripcion().trim());i.setInfOrden(Integer.parseInt(getOrden().trim()));
					i.setInfEstado(getEstado());i.setInfFecha(getFecha());i.setInfUrl(getLeerMas());
					i.setInfLatitud(valorCoordenadaLat(getLat()));i.setInfLongitud(valorCoordenadaLng(getLng()));
					if(isEdicion())
						mngInfo.modificarInformacion(i);
					else{
						mngInfo.ingresarInformacion(i);
						mngInfo.incrementarIdentificador(Parametro.ID_NUMERACION_INF_ITEM);
						setEdicion(true);setCreado(true);
					}
					cargarInformacion();
				}else
					Mensaje.crearMensajeWARN("Debe seleccionar una categoría.");
			}
		} catch (Exception e) {
			System.out.println("-----------> Error guardarEditarInformacion Item: ");e.printStackTrace();
			Mensaje.crearMensajeERROR(" Error al almacenar los datos: "+e.getMessage());
		}
	}
	
	private boolean camposOpcionalesValidos() {
		StringBuilder msj  = new StringBuilder();
		if(isPoseeFecha() && (new SimpleDateFormat("yyyy-MM-dd").format(getFecha()).equals(Parametro.DEFAULT_DATE)))
			msj.append("Debe seleccionar una fecha diferente. ");
		if(isPoseeLink() && getLeerMas()==null)
			msj.append("El campo link no debe ser nulo. ");
		else if(isPoseeLink() && getLeerMas().trim().isEmpty())
			msj.append("El campo link no debe contener sólo espacios blancos. ");
		else if(isPoseeLink() && getLeerMas().trim().equals(Parametro.DEFAULT_LINK))
			msj.append("Debe esribir una dirección URL para el link leer más. ");
		else if(isPoseeLink() && !Parametro.isUrlValid(getLeerMas().trim()))
			msj.append("Debe esribir una dirección URL válida para el link leer más. ");
		if(isPoseeMapa() && getLat()==Parametro.DEFAULT_COORD_LAT && getLng()==Parametro.DEFAULT_COORD_LNG)
			msj.append("Debe seleccionar una ubicación diferente a la por defecto. ");
		if(msj.toString().isEmpty())
			return true;
		else{
			Mensaje.crearMensajeWARN(msj.toString());
			return false;
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
			getImagenSeleccionada().setImaOrden(Integer.parseInt(getImagenOrden()));
			getImagenSeleccionada().setImaDireccion(getImagenDireccion());
			getImagenSeleccionada().setImaNombre(getImagenDireccion());
			getImagenSeleccionada().setInfInformacion(mngInfo.findInformacionByID(getCodigo()));
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
		setCodigo(null);setTitulo(null);setCategoriaId(ALL_CATEGORIAS);defaultData();
		setDescripcion(null);setEstado(Parametro.ID_ESTADO_ACTIVO);setOrden(null);
		cargarImagenes(null);setEdicion(false);setAbrirFrm(false);setCreado(false);
		setPoseeLink(false);setPoseeFecha(false);setPoseeMapa(false);
	}
	
	public void limpiarDatosImagen(){
		setImagenSeleccionada(null);setImagenOrden("");setImagenDireccion("");
		setEdicionImg(false);setAbrirFrmImg(false);
	}
		
	private void cargarInformacion(){
		getInformacionList().clear();
		getInformacionList().addAll(mngInfo.findAllInformacion());
	}
	
	private void defaultData(){
		defaultLink();
		defaultFecha();
		defaultMapa();
	}
	
	public void defaultLink(){
		setLeerMas(Parametro.DEFAULT_LINK);
	}
	
	public void defaultFecha(){
		try {
			setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(Parametro.DEFAULT_DATE));
		} catch (ParseException e) {
			System.out.println("-------------> Error defaultFecha:");
			e.printStackTrace();
		}
	}
	
	public void defaultMapa(){
		setLat(Parametro.DEFAULT_COORD_LAT);setLng(Parametro.DEFAULT_COORD_LNG);
		modeloMapa();
	}
	
	public void modeloMapa(){
		setDraggableModel(new DefaultMapModel());   
        getDraggableModel().addOverlay(new Marker(new LatLng(getLat(),getLng()), "Ubicación"));
        for(Marker premarker : getDraggableModel().getMarkers()) {
            premarker.setDraggable(true);
        } 
	}
	
	public String valorCoordenadaLat(Double coordenada){
		if(coordenada.equals(Parametro.DEFAULT_COORD_LAT))
			return "0";
		else
			return coordenada.toString();
	}
	
	public String valorCoordenadaLng(Double coordenada){
		if(coordenada.equals(Parametro.DEFAULT_COORD_LNG))
			return "0";
		else
			return coordenada.toString();
	}
		
	public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker();
        setLat(marker.getLatlng().getLat());
        setLng(marker.getLatlng().getLng());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La ubicación es", "Latitud: " 
        		+ marker.getLatlng().getLat() + " Longitud: " + marker.getLatlng().getLng()));
    }
	
	public void nuevaImagen(){
		setEdicionImg(false);setAbrirFrmImg(true);
		setImagenSeleccionada(new InfImagen());
		setImagenOrden(mngInfo.ultimoOrdenImagen(getCodigo(), Parametro.COD_ITEM).toString());
	}
	
	private void cargarImagenes(String codInformacion) {
		getImagenList().clear();
		if(codInformacion!=null)
			getImagenList().addAll(mngInfo.findImagenesInformacion(codInformacion));
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
