package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inf_parametros database table.
 * 
 */
@Entity
@Table(name="inf_parametros")
@NamedQuery(name="InfParametro.findAll", query="SELECT i FROM InfParametro i")
public class InfParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="par_id", length=20)
	private String parId;

	@Column(name="par_descripcion", length=254)
	private String parDescripcion;

	@Column(name="par_nombre", length=100)
	private String parNombre;

	@Column(name="par_valor", length=254)
	private String parValor;

	public InfParametro() {
	}

	public String getParId() {
		return this.parId;
	}

	public void setParId(String parId) {
		this.parId = parId;
	}

	public String getParDescripcion() {
		return this.parDescripcion;
	}

	public void setParDescripcion(String parDescripcion) {
		this.parDescripcion = parDescripcion;
	}

	public String getParNombre() {
		return this.parNombre;
	}

	public void setParNombre(String parNombre) {
		this.parNombre = parNombre;
	}

	public String getParValor() {
		return this.parValor;
	}

	public void setParValor(String parValor) {
		this.parValor = parValor;
	}

}