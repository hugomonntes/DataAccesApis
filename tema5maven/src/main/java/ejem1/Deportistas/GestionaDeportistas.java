package ejem1.Deportistas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/deportista")
public class GestionaDeportistas {
    private static final String url = "jdbc:mariadb://localhost:3306/ad_tema6";
    private static final String user = "root";
    private static final String password = "";

    public Connection initConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            return connection;
        } catch (Exception e) {
            return null;
        }
    };

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllDesportistas() throws ClassNotFoundException {
        ArrayList<Deportista> listaDeportistas = new ArrayList<>();
        try (Connection connection = initConnection()) {
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
            GenericEntity<List<Deportista>> entity = new GenericEntity<List<Deportista>>(listaDeportistas) {
            };

            return Response.ok(entity).build();
        } catch (Exception e) {
            return null;
        }
    }

    // 3. Buscar jugador (/{id}): devuelve la información relativa al deportista id.
    @Path("/{id}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response infoDeportista(@PathParam("id") int id) {
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = String.format("SELECT * FROM deportistas WHERE id = %d", id);
            ResultSet rs = stm.executeQuery(query);
            Deportista deportista = new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                    rs.getString("genero"), rs.getString("deporte"));
            return Response.ok(deportista).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No hay deportistas con ese id").build();
        }
    }

    // 4. Por deporte (/deporte/{nombreDeporte}): Lista los deportistas de un
    // deporte.
    @Path("/deporte/{nombreDeporte}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarPorDeporte(@PathParam("nombreDeporte") String nombreDeporte) {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = String.format("SELECT * FROM deportistas WHERE deporte = '%s'", nombreDeporte);
            System.out.println(query);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                        rs.getString("genero"), rs.getString("deporte")));
            }
            return Response.ok(deportistas).build();
        } catch (SQLException sqle) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No se encuentra ese deporte").build();
        }
    }

    // 5. Activos (/activos): Lista los deportistas activos.
    @Path("/activos")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarActivos() {
        ArrayList<Deportista> activos = new ArrayList<>();
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas WHERE activo = true";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                activos.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("Activo"),
                        rs.getString("Genero"), rs.getString("Deporte")));
            }
            return Response.ok(activos).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity("No hay deportistas activos").build();
        }
    }

    // 6. Retirados (/retirados): Lista los deportistas retirados.
    @Path("/retirados")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarRetirados() {
        ArrayList<Deportista> retirados = new ArrayList<>();
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas WHERE activo = false";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                retirados.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("Activo"),
                        rs.getString("Genero"), rs.getString("Deporte")));
            }
            return Response.ok(retirados).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity("No hay deportistas retirados").build();
        }
    }

    // 7. Masculinos (/masculinos): Lista los deportistas masculinos.
    @Path("/masculinos")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarMasculinos() {
        ArrayList<Deportista> masculinos = new ArrayList<>();
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas WHERE genero = masculino";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                masculinos.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("Activo"),
                        rs.getString("Genero"), rs.getString("Deporte")));
            }
            return Response.ok(masculinos).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity("No hay deportistas masculinos").build();
        }
    }

    // 8. Femeninos (/femeninos): Lista los deportistas femeninos.
    @Path("/femeninos")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarFemeninos() {
        ArrayList<Deportista> femeninos = new ArrayList<>();
        try (Connection connection = initConnection()) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas WHERE genero = femenino";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                femeninos.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("Activo"),
                        rs.getString("Genero"), rs.getString("Deporte")));
            }
            return Response.ok(femeninos).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity("No hay deportistas retirados").build();
        }
    }

    // 9. Deportes por genero (/xg): Lista un array con dos elementos: uno con todos
    // los deportistas masculinos y otro con todos los deportistas femeninos.
    @Path("/xg")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarPorGenero() {
        ArrayList<Response> masculinos = new ArrayList<>();
        masculinos.add(buscarMasculinos());
        ArrayList<Response> femeninos = new ArrayList<>();
        femeninos.add(buscarFemeninos());
        ArrayList<ArrayList<Response>> general = new ArrayList<>();
        general.add(masculinos);
        general.add(femeninos);
        return Response.ok(general).build();
    }
}
