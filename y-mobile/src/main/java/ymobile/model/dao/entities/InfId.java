package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inf_ids database table.
 * 
 */
@Entity
@Table(name="inf_ids")
@NamedQuery(name="InfId.findAll", query="SELECT i FROM InfId i")
public class InfId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ide", length=4)
	private String idIde;

	@Column(name="id_nombre", length=100)
	private String idNombre;

	@Column(name="id_numero")
	private Integer idNumero;

	public InfId() {
	}

	public String getIdIde() {
		return this.idIde;
	}

	public void setIdIde(String idIde) {
		this.idIde = idIde;
	}

	public String getIdNombre() {
		return this.idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public Integer getIdNumero() {
		return this.idNumero;
	}

	public void setIdNumero(Integer idNumero) {
		this.idNumero = idNumero;
	}

}