package ejem1;

/* En gris se ponen los imports que se usan en la versión 2.x de Jersey */
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType; // javax.ws.rs.core.MediaType;
// Establece la ruta del servicio: URL base + /hola

@Path("/hola")
public class Hola {
    // Se ejecuta este método si se pide un Accept de tipo TEXT_PLAIN
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String textHola() {
        return "Hola Rest Soy un texto";
    }

    // Se ejecuta este método si se pide un Accept de tipo TEXT_HTML
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String htmlHola() {
        return "<html><title> Hola Rest asdfasdf</title><body>"
                + "<h1> Hola Rest</h1>"
                + "</body></html>";
    }

    // En los dos métodos siguientes el contenido se crea “a mano” y el valor
    // devuelto es un String genérico. En ejemplos siguientes veremos como
    // mejorar esto
    // Se ejecuta este método si se pide un Accept de tipo APPLICATION_XML
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String xmlHola() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<hola>Hola Rest. Soy un XML</hola>";
    }

    // Se ejecuta este método si se pide un Accept de tipo APPLICATION_JSON
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String jsonHola() {
        return "{\"hola\":\"Hola Rest. Soy un JSON\"}";
    }


}
