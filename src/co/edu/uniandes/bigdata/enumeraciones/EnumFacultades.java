package co.edu.uniandes.bigdata.enumeraciones;


public enum EnumFacultades {
	
	FACULTAD_ADMINISTRACION("Facultad de Administración"),
	FACULTAD_ARQUITECTURA("Facultad de Arquitectura y Diseño"),
	FACULTAD_ARTES("Facultad de Artes y Humanidades"),
	FACULTAD_CIENCIAS("Facultad de Ciencias"),
	FACULTAD_SOCIALES("Facultad de Ciencias Sociales"),  
	FACULTAD_DERECHO("Facultad de Derecho"),
	FACULTAD_ECONOMIA("Facultad de Economía"),  
	FACULTAD_INGENIERIA("Facultad de Ingeniería"),  
	FACULTAD_MEDICINA("Facultad de Medicina"),
	DEPARTAMENTO_ANTROPOLOGIA("Departamento de Antropología"),  
	DEPARTAMENTO_ARQUITECTURA("Departamento de Arquitectura"),
	DEPARTAMENTO_ARTES("Departamento de Arte"),
	FACULTAD_CIENCIA_POLITICA("Departamento de Ciencia Politica"),  
	DEPARTAMENTO_BIOLOGIA("Departamento de Ciencias Biológicas"), 
	DEPARTAMENTO_DISEÑO("Departamento de Diseño"),
	DEPARTAMENTO_FILOSOFIA("Departamento de Filosofia"),  
	DEPARTAMENTO_FISICA("Departamento de Fisica");  
	
	public static EnumFacultades[] ENUM_VALUES = EnumFacultades.values();
	
	private final String nombre;

	private EnumFacultades(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public static EnumFacultades get(String nombre) {
		for (EnumFacultades facultades : EnumFacultades.ENUM_VALUES) {
			if (facultades.getNombre().equals(nombre)) {
				return facultades;
			}
		}
		return null;
	}

}
