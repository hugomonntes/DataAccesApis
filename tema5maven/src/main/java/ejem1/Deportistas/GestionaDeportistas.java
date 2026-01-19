package ejem1.Deportistas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deportista")
public class GestionaDeportistas {
    private static final String url = "jdbc:mariadb://localhost:3306/ad_tema6";
    private static final String user = "root";
    private static final String password = "";

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllDesportistas() throws ClassNotFoundException {
        ArrayList<Deportista> listaDeportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Driver not found").build();
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                listaDeportistas.add(new Deportista(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("Activo"),
                        rs.getString("Deporte"),
                        rs.getString("Genero")));
            }
            GenericEntity<List<Deportista>> entity = new GenericEntity<List<Deportista>>(listaDeportistas) {};
            
            return Response.ok(entity).build();
        } catch (Exception e) {
        }

        return null;
    }
}
