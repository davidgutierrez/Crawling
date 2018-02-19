package co.edu.uniandes.bigdata.rss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import com.saxonica.xqj.SaxonXQDataSource;

/**
 * @author User
 *
 */
@ManagedBean(name = "filterRSS")
@SessionScoped
public class FilterRSS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String parametroConsulta;
	private List<String> resultado;

//	public static void main(String[] args) 
//	{
//		try
//		{
//			executeInicial();
//			//NOTA: Incluir instruccion condicional para que el metodo de filtrado se ejecute al hacer
//			//click en el boton. Incluir la palabra de filtrado ingresada por el usuario en el
//			//textfield como parametro del siguiente metodo:
//			execute("moneda");
//		}
//
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//
//		catch (XQException e)
//		{
//			e.printStackTrace();
//		}
//		
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	public void processRss()
	{
		try
		{
			this.resultado = new ArrayList<String>();
			executeInicial();
			//NOTA: Incluir instruccion condicional para que el metodo de filtrado se ejecute al hacer
			//click en el boton. Incluir la palabra de filtrado ingresada por el usuario en el
			//textfield como parametro del siguiente metodo:
			if(parametroConsulta!=null)
				execute(parametroConsulta);
			else
				execute("moneda");
		}

		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		catch (XQException e)
		{
			e.printStackTrace();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * Metodo que toma la palabra de filtrado ingresada por el usuario y realiza el filtrado
	 * correspondiente sobre las fuentes asignadas, revisando el titulo, la descripcion y
	 * la categoria de cada noticia.
	 * @param palabraFiltrado Palabra de filtrado ingresada por el usuario
	 * @throws FileNotFoundException
	 * @throws XQException
	 */
	private void execute(String palabraFiltrada) throws FileNotFoundException, XQException, IOException
	{
		//Crear el archivo de query para BBC Technology
		File fileQuery1 = new File("docs", "queryBBC.xqy");
		FileWriter fw = new FileWriter(fileQuery1);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("for $x in doc(\"http://feeds.bbci.co.uk/news/technology/rss.xml\")/rss/channel/item");
		pw.println("where $x[contains (title, "+"\'"+palabraFiltrada+"\')] or contains ($x/description, "+"\'"
		+palabraFiltrada+"\') or contains ($x/category, "+"\'"+palabraFiltrada+"\')");
		pw.println("return ($x/title, $x/pubDate, $x/link)");
		pw.close();
		fw.close();
		
		//Crear el archivo de query para Microsiervos
		File fileQuery2 = new File("docs", "queryMicro.xqy");
		FileWriter fw2 = new FileWriter(fileQuery2);
		PrintWriter pw2 = new PrintWriter(fw2);
		pw2.println("for $x in doc(\"http://www.microsiervos.com/index.xml\")/rss/channel/item");
		pw2.println("where $x[contains (title, "+"\'"+palabraFiltrada+"\')] or contains ($x/description, "+"\'"
		+palabraFiltrada+"\') or contains ($x/category, "+"\'"+palabraFiltrada+"\')");
		pw2.println("return ($x/title, $x/pubDate, $x/link)");
		pw2.close();
		fw2.close();
		
		//Estructura del query para la fuente "BBC Technology"
		InputStream inputStream = new FileInputStream(new File("docs"+File.separator+"queryBBC.xqy"));
		XQDataSource ds = new SaxonXQDataSource();
		XQConnection conn = ds.getConnection();
		XQPreparedExpression exp = conn.prepareExpression(inputStream);
		//Resultado del query para la fuente "BBC Technology"
		XQResultSequence result = exp.executeQuery();
		
		//Estructura del query para la fuente "Microsiervos"
		InputStream inputStream2 = new FileInputStream(new File("docs"+File.separator+"queryMicro.xqy"));
		XQDataSource ds2 = new SaxonXQDataSource();
		XQConnection conn2 = ds2.getConnection();
		XQPreparedExpression exp2 = conn2.prepareExpression(inputStream2);
		//Resultado del query para la fuente "BBC Technology"
		XQResultSequence result2 = exp2.executeQuery();
		
		while (result.next()) 
		{
			//Impresion del resultado en consola NOTA: Este metodo retorna el resultado del query, para
			//desplegarlo en la aplicación web. 
			this.resultado.add(result.getItemAsString(null));
			System.out.println(result.getItemAsString(null));
		}
		
		while (result2.next())
		{
			//Impresion del resultado en consola NOTA: Este metodo retorna el resultado del query, para
			//desplegarlo en la aplicación web. 
			this.resultado.add(result2.getItemAsString(null));
			System.out.println(result2.getItemAsString(null));
		}
		
	}
	
	/**
	 * Metodo que toma todas las noticias de las fuentes asignadas y retorna sus titulos 
	 * @throws FileNotFoundException
	 * @throws XQException
	 */
	private void executeInicial() throws FileNotFoundException, XQException
	{
		//Estructura del query para la fuente "BBC Technology"
		InputStream inputStream = new FileInputStream(new File("docs"+File.separator+"queryInicBBC.xqy"));
		XQDataSource ds = new SaxonXQDataSource();
		XQConnection conn = ds.getConnection();
		XQPreparedExpression exp = conn.prepareExpression(inputStream);
		//Resultado del query para la fuente "BBC Technology"
		XQResultSequence result = exp.executeQuery();
		
		//Estructura del query para la fuente "Microsiervos"
		InputStream inputStream2 = new FileInputStream(new File("docs"+File.separator+"queryInicMicro.xqy"));
		XQDataSource ds2 = new SaxonXQDataSource();
		XQConnection conn2 = ds2.getConnection();
		XQPreparedExpression exp2 = conn2.prepareExpression(inputStream2);
		//Resultado del query para la fuente "BBC Technology"
		XQResultSequence result2 = exp2.executeQuery();
		
		while (result.next()) 
		{
			//Impresion del resultado en consola NOTA: Este metodo retorna el resultado del query, para
			//desplegarlo en la aplicación web. 
			this.resultado.add(result.getItemAsString(null));
			System.out.println(result.getItemAsString(null));
		}
		
		while (result2.next())
		{
			//Impresion del resultado en consola NOTA: Este metodo retorna el resultado del query, para
			//desplegarlo en la aplicación web. 
			this.resultado.add(result2.getItemAsString(null));
			System.out.println(result2.getItemAsString(null));
			System.out.println("");
		}
	}


	public String getParametroConsulta() {
		return parametroConsulta;
	}


	public void setParametroConsulta(String parametroConsulta) {
		this.parametroConsulta = parametroConsulta;
	}


	public List<String> getResultado() {
		return resultado;
	}


	public void setResultado(List<String> resultado) {
		this.resultado = resultado;
	}


}
