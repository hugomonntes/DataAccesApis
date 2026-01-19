package ejem1;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/personas")
public class Personas {
    private static Persona persona;
    public static ArrayList<Persona> personas = new ArrayList<>();

    // 3. Crea la clase Personas ubicada en la URI personas. Tendrá como atributo
    // estático
    // un ArrayList de personas llamado personas y los siguientes métodos:
    // 1. Un método guardar que pasándole una persona en XML o JSON inserte esa
    // persona en el ArrayList.
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static Response guardarPersona(Persona p) {
        persona = p;
        personas.add(p);
        return Response.ok().build();
    }

    // 2. Un método listar que devuelva el ArrayList personas como XML.
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static ArrayList<Persona> listar() {
        return personas;
    }

    // 3. Un método ver que pasándole en la URI un nombre liste, en caso de existir,
    // los datos de esa persona como JSON.
    @Path("/{nombre}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static Persona verPersona(@PathParam("nombre") String nombre) {
        for (Persona p : personas) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null; // O lanzar una excepción si no se encuentra la persona
    }

    // 4. Un método ver que en el path buscar y pasándole una cadena como query en
    // la URI, muestre las personas que tengan esa cadena en el nombre ignorando las
    // mayúsculas y minúsculas.
    @Path("/cadena")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static ArrayList<Persona> buscarPersona(@PathParam("cadena") String cadena) {
        ArrayList<Persona> resultado = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getNombre().toLowerCase().contains(cadena.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // 5. Crea un formulario para insertar datos de personas.
    @Path("/formulario")
    @POST
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Persona insertarPersonaFormulario(@PathParam("nombre") String nombre,
            @PathParam("casado") boolean casado,
            @PathParam("sexo") String sexo) {
        Persona nuevaPersona = new Persona(22, nombre, casado, sexo);
        personas.add(nuevaPersona);
        return nuevaPersona;
    }

    // 6. Crea un método que permita insertar personas en el ArrayList personas
    // mediante el formulario anterior.
    @Path("/insertar")
    @POST
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Persona insertarPersona(@PathParam("nombre") String nombre,
            @PathParam("casado") boolean casado,
            @PathParam("sexo") String sexo) {
        Persona nuevaPersona = new Persona(22, nombre, casado, sexo);
        personas.add(nuevaPersona);
        return nuevaPersona;
    }

    // 7. Crea, en el path add, un método que permita insertar varias personas de
    // forma simultánea en el ArrayList personas.
    @Path("/add")
    @POST
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Persona> insertarPersonas(@PathParam("nombre") String nombre,
            @PathParam("casado") boolean casado, @PathParam("sexo") String sexo) {
        return personas;
    }
    // 8. Crea un método, que pasándole en el path id una persona permita borrarla
    // del∏
    // ArrayList personas.
    @Path("id/{idPersona}")
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response borrarPersona(@PathParam("idPersona") int idPersona) {
        for (Persona persona : personas) {
            if (persona.getId() == idPersona) {
                personas.remove(persona);
            }
        }
        return Response.ok().build();
    }
    // 9. Modifica el ejercicio del punto 4 para que los parámetros de la query
    // tengan
    // valores por defecto.
    // 10. Crea un método, en el path XML, que devuelva los datos de una persona
    // indicada en el path en XML y en JSON siendo el id un atributo de persona.
    // ¿Qué
    // diferencias encuentras entre la representación obtenida en XML y en JSON?
    // 11. Nos piden que los nombre de los atributos devueltos deben estar en
    // gallego.
    // Crea un método en el path galego que realice esta acción.
    // 12. Modifica los ejercicios anteriores para que devuelvan el Response
    // adecuado.
}
