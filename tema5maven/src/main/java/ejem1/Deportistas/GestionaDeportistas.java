package ejem1.Deportistas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllDesportistas() throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> listaDeportistas = new ArrayList<>();
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
            GenericEntity<List<Deportista>> entity = new GenericEntity<List<Deportista>>(listaDeportistas) {
            };

            return Response.ok(entity).build();
        } catch (Exception e) {
            return null;
        }
    }

    @Path("/android")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirDeportistaAndroid(Deportista deportista) throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> listaDeportistas = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = "INSERT INTO deportistas (nombre, deporte) VALUES ('" + deportista.getNombre() + "', '"
                    + deportista.getDeporte() + "')";
            int rs = stm.executeUpdate(query);

            return Response.ok("Subido bien" + rs).build();
        } catch (Exception e) {
            return null;
        }
    }

    // 3. Buscar jugador (/{id}): devuelve la información relativa al deportista id.
    @Path("/{id}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response infoDeportista(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
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
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
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
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> activos = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
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
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> retirados = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
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
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> masculinos = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM deportistas WHERE genero = 'masculino'";
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
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            ArrayList<Deportista> femeninos = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                Statement stm = connection.createStatement();
                String query = "SELECT * FROM deportistas WHERE genero = 'femenino'";
                ResultSet rs = stm.executeQuery(query);
                while (rs.next()) {
                    femeninos.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("Activo"),
                            rs.getString("Genero"), rs.getString("Deporte")));
                }
                return Response.ok(femeninos).build();
            } catch (Exception e) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No hay deportistas femeninos").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity("No hay deportistas femeninos").build();
        }
    }

    // 9. Deportes por genero (/xg): Lista un array con dos elementos: uno con todos
    // los deportistas masculinos y otro con todos los deportistas femeninos.
    @Path("/xg")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarPorGenero() throws ClassNotFoundException {
        ArrayList<Response> masculinos = new ArrayList<>();
        masculinos.add(buscarMasculinos());

        ArrayList<Response> femeninos = new ArrayList<>();
        femeninos.add(buscarFemeninos());

        ArrayList<ArrayList<Response>> general = new ArrayList<>();
        general.add(masculinos);
        general.add(femeninos);
        return Response.ok(general).build();
    }

    // 10. Activos por deporte (/deporte/{nombreDeporte}/activos): Lista los
    // deportistas activos de un deporte.
    @Path("/deporte/{nombreDeporte}/activos")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarPorDeporteActivo(@PathParam("nombreDeporte") String nombreDeporte) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<Deportista> activosPorDeporte = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = String.format("SELECT * FROM deportistas WHERE deporte = '%s' AND activo = true",
                    nombreDeporte);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                activosPorDeporte.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                        rs.getString("genero"), rs.getString("deporte")));
            }
        } catch (Exception e) {
        }
        return Response.ok(activosPorDeporte).build();
    }

    // 11. Contar deportistas (/sdepor): Cuenta el número de deportistas distintos.
    @Path("/sdepor")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response contarDeportistas() throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT nombre) FROM deportistas";
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return Response.ok(count).build();
    }

    // 12. Lista deportes (/deportes): Lista los deportes existentes ordenados
    // alfabéticamente sin repeticiones.
    @Path("/deportes")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response listarDeportes() throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        ArrayList<String> deportes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement stm = connection.createStatement();
            String query = "SELECT DISTINCT deporte FROM deportistas ORDER BY deporte";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                deportes.add(rs.getString("deporte"));
            }
        } catch (Exception e) {
        }
        return Response.ok(deportes).build();
    }

    // 13. Crear deportista (/): Añade un deportista en el sistema.
    @Path("/addDeportista")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response crearDeportista(Deportista deportista) throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO deportistas (nombre, activo, genero, deporte) VALUES (?, ?, ?, ?)");
            ps.setString(1, deportista.getNombre());
            ps.setBoolean(2, deportista.isActivo());
            ps.setString(3, deportista.getGenero());
            ps.setString(4, deportista.getDeporte());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return Response.ok().build();
    }

    // 14. Crear deportista formulario (/): Añade un deportista mediante un
    // formulario.
    @Path("/addDeportistaForm")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response crearDeportistaForm(@FormParam("nombre") String nombre,
            @FormParam("activo") boolean activo,
            @FormParam("genero") String genero,
            @FormParam("deporte") String deporte) throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO deportistas (nombre, activo, genero, deporte) VALUES (?, ?, ?, ?)");
            ps.setString(1, nombre);
            ps.setBoolean(2, activo);
            ps.setString(3, genero);
            ps.setString(4, deporte);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return Response.ok().build();
    }

    // 15. Crear deportistas (/adds): crea deportistas en el sistema.
    @Path("/adds")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response crearDeportistas(List<Deportista> deportistas) throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO deportistas (nombre, activo, genero, deporte) VALUES (?, ?, ?, ?)");
            for (Deportista deportista : deportistas) {
                ps.setString(1, deportista.getNombre());
                ps.setBoolean(2, deportista.isActivo());
                ps.setString(3, deportista.getGenero());
                ps.setString(4, deportista.getDeporte());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
        }
        return Response.ok().build();
    }

    // 16. Actualizar deportista (/): actualiza la información relativa a un
    // deportista.
    @Path("/updateDeportista")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response actualizarDeportista(Deportista deportista) throws ClassNotFoundException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE deportistas SET nombre=?, activo=?, genero=?, deporte=? WHERE id=?");
            ps.setString(1, deportista.getNombre());
            ps.setBoolean(2, deportista.isActivo());
            ps.setString(3, deportista.getGenero());
            ps.setString(4, deportista.getDeporte());
            ps.setInt(5, deportista.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return Response.ok().build();
    }

}