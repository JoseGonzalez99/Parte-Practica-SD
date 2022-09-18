package py.una.entidad;

public class Banco {

	Integer id;
	String nombre;
	Integer cotizacion;
	
	
	
	
	public Banco(){
		
	}


	public Banco(Integer pId, String pnombre,Integer pcotizacion){
		this.id = pId;
		this.nombre = pnombre;
		this.cotizacion=pcotizacion;
	}
	
	public Integer getID() {
		return id;
	}

	public void setID(Integer pId) {
		this.id = pId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Integer pcotizacion) {
		this.cotizacion = pcotizacion;
	}
}

