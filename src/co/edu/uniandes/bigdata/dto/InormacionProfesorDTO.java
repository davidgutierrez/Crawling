package co.edu.uniandes.bigdata.dto;

public class InormacionProfesorDTO {
	
	private String nombre;
	private String escalafon;
	private String dependencia;
	private String email;
	private String extencionTelefonica;
	private String sitioWeb;
	
	public InormacionProfesorDTO(){
		
	}
	
	public InormacionProfesorDTO(String nombre, String escalafon, String dependencia, String email,
			String extencionTelefonica, String sitioWeb) {
		super();
		this.nombre = nombre;
		this.escalafon = escalafon;
		this.dependencia = dependencia;
		this.email = email;
		this.extencionTelefonica = extencionTelefonica;
		this.sitioWeb = sitioWeb;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEscalafon() {
		return escalafon;
	}
	public void setEscalafon(String escalafon) {
		this.escalafon = escalafon;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExtencionTelefonica() {
		return extencionTelefonica;
	}
	public void setExtencionTelefonica(String extencionTelefonica) {
		this.extencionTelefonica = extencionTelefonica;
	}
	public String getSitioWeb() {
		return sitioWeb;
	}
	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
	
	@Override
	public String toString() {
		return "InormacionProfesorDTO ["
				+ "\nnombre=" + nombre
				+ "\nescalafon=" + escalafon 
				+ "\ndependencia=" + dependencia
				+ "\nemail=" + email 
				+ "\nextencionTelefonica=" + extencionTelefonica
				+ "\nsitioWeb=" + sitioWeb 
				+ "\n]";
	}
	
}
