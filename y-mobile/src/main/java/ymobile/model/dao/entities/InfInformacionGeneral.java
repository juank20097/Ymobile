package ymobile.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the inf_informacion_general database table.
 * 
 */
@Entity
@Table(name="inf_informacion_general")
@NamedQuery(name="InfInformacionGeneral.findAll", query="SELECT i FROM InfInformacionGeneral i")
public class InfInformacionGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inf_codigo_gen", length=5)
	private String infCodigoGen;

	@Column(name="inf_descripcion", columnDefinition="text")
	private String infDescripcion;

	@Column(name="inf_estado", length=20)
	private String infEstado;

	@Column(name="inf_icono", columnDefinition="text")
	private String infIcono;

	@Column(name="inf_orden")
	private Integer infOrden;

	@Column(name="inf_titulo", length=150)
	private String infTitulo;

	//bi-directional many-to-one association to InfImagen
	@OneToMany(mappedBy="infInformacionGeneral", fetch = FetchType.EAGER)
	private List<InfImagen> infImagens;

	public InfInformacionGeneral() {
	}

	public String getInfCodigoGen() {
		return this.infCodigoGen;
	}

	public void setInfCodigoGen(String infCodigoGen) {
		this.infCodigoGen = infCodigoGen;
	}

	public String getInfDescripcion() {
		return this.infDescripcion;
	}

	public void setInfDescripcion(String infDescripcion) {
		this.infDescripcion = infDescripcion;
	}

	public String getInfEstado() {
		return this.infEstado;
	}

	public void setInfEstado(String infEstado) {
		this.infEstado = infEstado;
	}

	public String getInfIcono() {
		return this.infIcono;
	}

	public void setInfIcono(String infIcono) {
		this.infIcono = infIcono;
	}

	public Integer getInfOrden() {
		return this.infOrden;
	}

	public void setInfOrden(Integer infOrden) {
		this.infOrden = infOrden;
	}

	public String getInfTitulo() {
		return this.infTitulo;
	}

	public void setInfTitulo(String infTitulo) {
		this.infTitulo = infTitulo;
	}

	public List<InfImagen> getInfImagens() {
		return this.infImagens;
	}

	public void setInfImagens(List<InfImagen> infImagens) {
		this.infImagens = infImagens;
	}

	public InfImagen addInfImagen(InfImagen infImagen) {
		getInfImagens().add(infImagen);
		infImagen.setInfInformacionGeneral(this);

		return infImagen;
	}

	public InfImagen removeInfImagen(InfImagen infImagen) {
		getInfImagens().remove(infImagen);
		infImagen.setInfInformacionGeneral(null);

		return infImagen;
	}

}