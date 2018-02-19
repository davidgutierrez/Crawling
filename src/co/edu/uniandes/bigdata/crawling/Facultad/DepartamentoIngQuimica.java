package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoIngQuimica  extends DepartamentosGenerico {

	


	private static String dependecia="Departamento de Ingeniería Química";

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
		System.out.println("departamento Pendiente: "+linkFacultadDpto);
		return infoProfesores;
	}
}
