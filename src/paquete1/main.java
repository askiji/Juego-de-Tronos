package paquete1;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class main {

	public static void main(String[] args) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementacion = builder.getDOMImplementation();
		
		Document doc = implementacion.createDocument(null, "personajes", null);
		doc.setXmlVersion("1.0");
		

		crearPerosonaje("Lord Ned Stark", "263", "Invernalia", "299", doc);
		crearPerosonaje("Cersei Lannister", "266", "Roca Casterly", "305", doc);
		crearPerosonaje("Arya Stark", "289", "Invernalia", "Viva", doc);
		crearPerosonaje("Daenerys Targaryen", "284", "Rocadragon", "305", doc);
		crearPerosonaje("Jaimito Lannister", "284", "Roca Casterly", "305", doc);
		crearPerosonaje("Tyrion Lannister", "273", "Roca Casterly", "Vivo", doc);
		Source source = new DOMSource(doc);
		Result resultado = new StreamResult(new File("personajes.xml"));
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, resultado);
		leerXML(doc, "Invernalia");
		cantidadPersonajes(doc);
		muerteDespuesDe(doc,280);
		
	}

	private static void muerteDespuesDe(Document doc, int anio) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			NodeList personaje = doc.getElementsByTagName("personaje");
			for (int i = 0; i < personaje.getLength(); i++) {
				Node nodoEmple = personaje.item(i);
				if (nodoEmple.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) nodoEmple;
					int aux = Integer.valueOf(elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("muerte").getNodeValue()); 
					if( aux>anio)  {
					System.out.println("Nombre: " + elem.getElementsByTagName("nombre").item(0).getTextContent());
					System.out.println("Nacimiento: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("nacimiento"));
					System.out.println("Lugar: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("lugar"));
					System.out.println("Muerte: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("muerte"));
					}

				}
			}
		} catch (Exception e) {
		}
	}

	private static void cantidadPersonajes(Document doc) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		int aux=0;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			NodeList personaje = doc.getElementsByTagName("personaje");
			for (int i = 0; i < personaje.getLength(); i++) {
				Node nodoEmple = personaje.item(i);
				if (nodoEmple.getNodeType() == Node.ELEMENT_NODE) {
					aux++;

				}
			}
		} catch (Exception e) {
		}
		System.out.println("Hay " +aux + " personajes");
	}

	protected static void leerXML(Document documento, String lugarNacimiento) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			NodeList personaje = documento.getElementsByTagName("personaje");
			for (int i = 0; i < personaje.getLength(); i++) {
				Node nodoEmple = personaje.item(i);
				if (nodoEmple.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) nodoEmple;
					String aux = elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("lugar").toString(); 
					if( aux.contains(lugarNacimiento))  {
						
					System.out.println("Nombre: " + elem.getElementsByTagName("nombre").item(0).getTextContent());
					System.out.println("Nacimiento: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("nacimiento"));
					System.out.println("Lugar: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("lugar"));
					// aqui en la muerte como he arrastrado el crear personaje con el atributo
					// muerte en todos
					// salen que estan vivo pero no daria problema si no tiene el atributo porque
					// sale null(que esque no se ha muerto)
					System.out.println("Muerte: "
							+ elem.getElementsByTagName("vida").item(0).getAttributes().getNamedItem("muerte"));
					}

				}
			}
		} catch (Exception e) {
		}
	}
	
	protected static void crearPerosonaje(String nombre , String nacimiento,String lugar,String muerte,Document documento) {
		Element padre = documento.createElement("personaje");
		documento.getDocumentElement().appendChild(padre);
		CrearElementoHijo("nombre", nombre, padre, documento);	
		CrearElementoHijo( nacimiento,lugar,muerte ,padre, documento);

	}
	
	protected static void CrearElementoHijo(String vNacimiento,String vLugar,String vMuerte ,Element padre, Document documento) {
		Element elem = documento.createElement("vida");
		Attr attrN = documento.createAttribute("nacimiento");
		Attr attrL = documento.createAttribute("lugar");
		Attr attrM = documento.createAttribute("muerte");
		attrN.setNodeValue(vNacimiento);
		attrL.setNodeValue(vLugar);
		attrM.setNodeValue(vMuerte);
		
		elem.setAttributeNode(attrN);
		elem.setAttributeNode(attrL);
		elem.setAttributeNode(attrM);
		padre.appendChild(elem);
	}
	protected static void CrearElementoHijo(String datoAlumno, String valor, Element
			padre, Document documento) {

			Element elem = documento.createElement(datoAlumno);
			Text texto = documento.createTextNode(valor);

			elem.appendChild(texto);
			padre.appendChild(elem);
			}
}
