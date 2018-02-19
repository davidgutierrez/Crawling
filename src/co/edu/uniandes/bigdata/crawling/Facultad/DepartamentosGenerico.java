package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentosGenerico {


	public static List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
	public static List<String> linksProfesores = new ArrayList<String>();


	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
		return infoProfesores;

	}
	public static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
