package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the inf_categoria database table.
 * 
 */
@Entity
@Table(name="inf_categoria")
@NamedQuery(name="InfCategoria.findAll", query="SELECT i FROM InfCategoria i")
public class InfCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_codigo", length=5)
	private String catCodigo;

	@Column(name="cat_descripcion", length=50)
	private String catDescripcion;

	@Column(name="cat_estado", length=100)
	private String catEstado;

	@Column(name="cat_icon", columnDefinition="text")
	private String catIcon;

	@Column(name="cat_orden")
	private Integer catOrden;

	@Column(name="cat_titulo", length=50)
	private String catTitulo;

	//bi-directional many-to-one association to InfInformacion
	@OneToMany(mappedBy="infCategoria")
	private List<InfInformacion> infInformacions;

	public InfCategoria() {
	}

	public String getCatCodigo() {
		return this.catCodigo;
	}

	public void setCatCodigo(String catCodigo) {
		this.catCodigo = catCodigo;
	}

	public String getCatDescripcion() {
		return this.catDescripcion;
	}

	public void setCatDescripcion(String catDescripcion) {
		this.catDescripcion = catDescripcion;
	}

	public String getCatEstado() {
		return this.catEstado;
	}

	public void setCatEstado(String catEstado) {
		this.catEstado = catEstado;
	}

	public String getCatIcon() {
		return this.catIcon;
	}

	public void setCatIcon(String catIcon) {
		this.catIcon = catIcon;
	}

	public Integer getCatOrden() {
		return this.catOrden;
	}

	public void setCatOrden(Integer catOrden) {
		this.catOrden = catOrden;
	}

	public String getCatTitulo() {
		return this.catTitulo;
	}

	public void setCatTitulo(String catTitulo) {
		this.catTitulo = catTitulo;
	}

	public List<InfInformacion> getInfInformacions() {
		return this.infInformacions;
	}

	public void setInfInformacions(List<InfInformacion> infInformacions) {
		this.infInformacions = infInformacions;
	}

	public InfInformacion addInfInformacion(InfInformacion infInformacion) {
		getInfInformacions().add(infInformacion);
		infInformacion.setInfCategoria(this);

		return infInformacion;
	}

	public InfInformacion removeInfInformacion(InfInformacion infInformacion) {
		getInfInformacions().remove(infInformacion);
		infInformacion.setInfCategoria(null);

		return infInformacion;
	}

}