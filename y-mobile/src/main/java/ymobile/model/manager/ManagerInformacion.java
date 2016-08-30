package ymobile.model.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ymobile.model.dao.entities.InfCategoria;
import ymobile.model.dao.entities.InfId;
import ymobile.model.dao.entities.InfImagen;
import ymobile.model.dao.entities.InfInformacion;
import ymobile.model.dao.entities.InfInformacionGeneral;
import ymobile.model.dao.entities.InfItem;
import ymobile.model.generico.Parametro;

@Stateless
public class ManagerInformacion {

	@EJB
	private ManagerDAO mngDAO;
	
	public ManagerInformacion() {}
	
	//A continuación se presentan los métodos relacionados con la utilización de catálogo
	
	@SuppressWarnings("unchecked")
	public List<InfItem> findItemsByCatalogo(String idCatalogo){
		return mngDAO.findWhere(InfItem.class, "o.infCatalogo.catCodigo='"+idCatalogo+"'", "o.iteNombre");
	}
	
	public InfItem findItemByID(String itemCodigo) throws Exception{
		return (InfItem) mngDAO.findById(InfItem.class, itemCodigo);
	}
	
	//A continuación se presentan los métodos relacionados con la utilización de identificadores
	public void incrementarIdentificador(String id) throws Exception{
		InfId identificador = findIdentificadorById(id);
		identificador.setIdNumero(identificador.getIdNumero()+1);
		mngDAO.actualizar(identificador);
	}
	
	public InfId findIdentificadorById(String id) throws Exception{
		return (InfId) mngDAO.findById(InfId.class, id);
	}
	//A continuación se presentan los métodos relacionados con la gestión de información general
	
	@SuppressWarnings("unchecked")
	public List<InfInformacionGeneral> findAllInformacionGeneral(){
		return mngDAO.findAll(InfInformacionGeneral.class);
	}
	
	public InfInformacionGeneral findInformacionGeneralByID(String idInformacion) throws Exception{
		return (InfInformacionGeneral) mngDAO.findById(InfInformacionGeneral.class, idInformacion);
	}
	
	public void ingresarInformacionGeneral(InfInformacionGeneral informacion) throws Exception{
		mngDAO.insertar(informacion);
	}
	
	public void modificarInformacionGeneral(InfInformacionGeneral informacion) throws Exception{
		mngDAO.actualizar(informacion);
	}
	
	public Integer ultimoOrdenInformacionGeneral() {
		Integer orden = mngDAO.tomarValorIntJPQL("select max(o.infOrden) from InfInformacionGeneral o");
		if (orden==null)
			orden=1;
		else
			orden+=1;
		return orden;
	}
	
	//A continuación se presentan los métodos relacionados con la gestión de servicios
	
	@SuppressWarnings("unchecked")
	public List<InfCategoria> findAllServicios(){
		return mngDAO.findAll(InfCategoria.class);
	}
	
	public InfCategoria findServicioById(String idServicio) throws Exception{
		return (InfCategoria) mngDAO.findById(InfCategoria.class, idServicio);
	}
	
	public void ingresarServicio(InfCategoria servicio) throws Exception{
		mngDAO.insertar(servicio);
	}
	
	public void modificarServicio(InfCategoria servicio) throws Exception{
		mngDAO.actualizar(servicio);
	}
		
	public Integer ultimoOrdenServicio() {
		Integer orden = mngDAO.tomarValorIntJPQL("select max(o.catOrden) from InfCategoria o");
		if (orden==null)
			orden=1;
		else
			orden+=1;
		return orden;
	}
	
	//A continuación se presentan los métodos relacionados con la gestión información perteneciente a los servicios
	
	@SuppressWarnings("unchecked")
	public List<InfInformacion> findAllInformacion(){
		return mngDAO.findAll(InfInformacion.class, "o.infTitulo");
	}
	
	public InfInformacion findInformacionByID(String idInformacion) throws Exception{
		return (InfInformacion) mngDAO.findById(InfInformacion.class, idInformacion);
	}
	
	@SuppressWarnings("unchecked")
	public List<InfInformacion> findInformacionByServicio(String idServicio){
		return mngDAO.findWhere(InfInformacion.class, "o.infCategoria.catCodigo='"+idServicio+"'", "o.infTitulo");
	}
	
	public void ingresarInformacion(InfInformacion informacion) throws Exception{
		mngDAO.insertar(informacion);
	}
	
	public void modificarInformacion(InfInformacion informacion) throws Exception{
		mngDAO.actualizar(informacion);
	}
	
	public Integer ultimoOrdenInformacion(String idServicio) {
		Integer orden = mngDAO.tomarValorIntJPQL("select max(o.infOrden) from InfInformacion o where o.infCategoria.catCodigo='"+idServicio+"'");
		if (orden==null)
			orden=1;
		else
			orden+=1;
		return orden;
	}
	
	//A continuación se presentan los métodos relacionados con la gestión de imágenes
	
	public InfImagen findImagenById(String imgCod) throws Exception{
		return (InfImagen) mngDAO.findById(InfImagen.class, imgCod);
	}
	
	public void ingresarImagen(InfImagen imagen) throws Exception{
		mngDAO.insertar(imagen);
	}
	
	public void modificarImagen(InfImagen imagen) throws Exception{
		mngDAO.actualizar(imagen);
	}

	@SuppressWarnings("unchecked")
	public List<InfImagen> findImagenesInformacionGeneral(String idInformacion) {
		return mngDAO.findWhere(InfImagen.class, "o.infInformacionGeneral.infCodigoGen='"+idInformacion+"'", "o.imaOrden");
	}
	
	@SuppressWarnings("unchecked")
	public List<InfImagen> findImagenesInformacion(String idInformacion) {
		return mngDAO.findWhere(InfImagen.class, "o.infInformacion.infCodigo='"+idInformacion+"'", "o.imaOrden");
	}
	
	public Integer ultimoOrdenImagen(String idInformacion, String tipo) {
		String query;
		if(tipo.equals(Parametro.COD_GENERAL))
			query ="select max(o.imaOrden) from InfImagen o where o.infInformacionGeneral.infCodigoGen='"+idInformacion+"'";
		else
			query="select max(o.imaOrden) from InfImagen o where o.infInformacion.infCodigo='"+idInformacion+"'";
		Integer orden = mngDAO.tomarValorIntJPQL(query);
		if (orden==null)
			orden=1;
		else
			orden+=1;
		return orden;
	}
}
