package ejem1;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.annotation.XmlRootElement;

@Path("/persona")
@XmlRootElement
public class GestionaPersona {
    private static Persona persona;

    // Un método leer que retornará una persona en formato XML o JSON.
    // 2. Un método guardar, en el atributo de clase persona, que guarda una persona
    // pasada como parámetro en formato XML o JSON

    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static Persona getPersona() {
        return persona;
    }
}
