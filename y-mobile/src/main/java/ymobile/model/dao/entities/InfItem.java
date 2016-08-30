package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inf_item database table.
 * 
 */
@Entity
@Table(name="inf_item")
@NamedQuery(name="InfItem.findAll", query="SELECT i FROM InfItem i")
public class InfItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ite_codigo", length=100)
	private String iteCodigo;

	@Column(name="ite_nombre", length=200)
	private String iteNombre;

	@Column(name="ite_padre", length=4)
	private String itePadre;

	//bi-directional many-to-one association to InfCatalogo
	@ManyToOne
	@JoinColumn(name="cat_codigo")
	private InfCatalogo infCatalogo;

	public InfItem() {
	}

	public String getIteCodigo() {
		return this.iteCodigo;
	}

	public void setIteCodigo(String iteCodigo) {
		this.iteCodigo = iteCodigo;
	}

	public String getIteNombre() {
		return this.iteNombre;
	}

	public void setIteNombre(String iteNombre) {
		this.iteNombre = iteNombre;
	}

	public String getItePadre() {
		return this.itePadre;
	}

	public void setItePadre(String itePadre) {
		this.itePadre = itePadre;
	}

	public InfCatalogo getInfCatalogo() {
		return this.infCatalogo;
	}

	public void setInfCatalogo(InfCatalogo infCatalogo) {
		this.infCatalogo = infCatalogo;
	}

}