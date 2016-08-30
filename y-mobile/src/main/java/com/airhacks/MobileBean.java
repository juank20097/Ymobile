package com.airhacks;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import ymobile.model.generico.Mensaje;

@ManagedBean
public class MobileBean {
	
	private MapModel emptyModel;
	private double lat;
    private double lng;
	
	@PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
        limpiarDatos();
    }
	
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public void addMarker() {
        emptyModel.addOverlay(new Marker(new LatLng(lat, lng), "Ubicación"));
        Mensaje.crearMensajeINFO("Marker Added --> [Lat]:" + lat + ", [Lng]: " + lng+" [#Marcadores]: "+emptyModel.getMarkers().size());
    }
	
	public void limpiarDatos(){
		setLat(0.0);setLng(0.0);
	}
}
