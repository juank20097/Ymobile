package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the inf_catalogo database table.
 * 
 */
@Entity
@Table(name="inf_catalogo")
@NamedQuery(name="InfCatalogo.findAll", query="SELECT i FROM InfCatalogo i")
public class InfCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_codigo", length=4)
	private String catCodigo;

	@Column(name="cat_descripcion", columnDefinition="text")
	private String catDescripcion;

	@Column(name="cat_nombre", length=20)
	private String catNombre;

	//bi-directional many-to-one association to InfItem
	@OneToMany(mappedBy="infCatalogo")
	private List<InfItem> infItems;

	public InfCatalogo() {
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

	public String getCatNombre() {
		return this.catNombre;
	}

	public void setCatNombre(String catNombre) {
		this.catNombre = catNombre;
	}

	public List<InfItem> getInfItems() {
		return this.infItems;
	}

	public void setInfItems(List<InfItem> infItems) {
		this.infItems = infItems;
	}

	public InfItem addInfItem(InfItem infItem) {
		getInfItems().add(infItem);
		infItem.setInfCatalogo(this);

		return infItem;
	}

	public InfItem removeInfItem(InfItem infItem) {
		getInfItems().remove(infItem);
		infItem.setInfCatalogo(null);

		return infItem;
	}

}