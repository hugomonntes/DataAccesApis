package ejem1.All;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlRootElement;

@Path("/persona")
@XmlRootElement
public class GestionaPersona {
    private static Persona persona;
    public static ArrayList<Persona> personas = new ArrayList<>();

    // Un método leer que retornará una persona en formato XML o JSON.
    // 2. Un método guardar, en el atributo de clase persona, que guarda una persona
    // pasada como parámetro en formato XML o JSON
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static Response guardar(Persona p) {
        persona = p;
        personas.add(p);
        return Response.ok().build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static Persona leer() {
        return persona;
    }
}
