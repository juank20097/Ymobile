package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inf_imagen database table.
 * 
 */
@Entity
@Table(name="inf_imagen")
@NamedQuery(name="InfImagen.findAll", query="SELECT i FROM InfImagen i")
public class InfImagen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ima_codigo", length=150)
	private String imaCodigo;

	@Column(name="ima_direccion", columnDefinition="text")
	private String imaDireccion;

	@Column(name="ima_nombre", columnDefinition="text")
	private String imaNombre;

	@Column(name="ima_orden")
	private Integer imaOrden;

	//bi-directional many-to-one association to InfInformacion
	@ManyToOne
	@JoinColumn(name="inf_codigo")
	private InfInformacion infInformacion;

	//bi-directional many-to-one association to InfInformacionGeneral
	@ManyToOne
	@JoinColumn(name="inf_codigo_gen")
	private InfInformacionGeneral infInformacionGeneral;

	public InfImagen() {
	}

	public String getImaCodigo() {
		return this.imaCodigo;
	}

	public void setImaCodigo(String imaCodigo) {
		this.imaCodigo = imaCodigo;
	}

	public String getImaDireccion() {
		return this.imaDireccion;
	}

	public void setImaDireccion(String imaDireccion) {
		this.imaDireccion = imaDireccion;
	}

	public String getImaNombre() {
		return this.imaNombre;
	}

	public void setImaNombre(String imaNombre) {
		this.imaNombre = imaNombre;
	}

	public Integer getImaOrden() {
		return this.imaOrden;
	}

	public void setImaOrden(Integer imaOrden) {
		this.imaOrden = imaOrden;
	}

	public InfInformacion getInfInformacion() {
		return this.infInformacion;
	}

	public void setInfInformacion(InfInformacion infInformacion) {
		this.infInformacion = infInformacion;
	}

	public InfInformacionGeneral getInfInformacionGeneral() {
		return this.infInformacionGeneral;
	}

	public void setInfInformacionGeneral(InfInformacionGeneral infInformacionGeneral) {
		this.infInformacionGeneral = infInformacionGeneral;
	}

}