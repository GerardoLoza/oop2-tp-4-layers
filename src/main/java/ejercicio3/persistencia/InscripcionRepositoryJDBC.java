package ejercicio3.persistencia;

import ejercicio3.modelo.Inscripcion;
import ejercicio3.modelo.InscripcionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionRepositoryJDBC implements InscripcionRepository {
    private final String url;
    private final String usuario;
    private final String password;

    public InscripcionRepositoryJDBC(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
        inicializarTablas();
    }

    private void inicializarTablas() {
        String sqlConcursos = """
        CREATE TABLE concursos (
            id INTEGER PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            fecha_inicio_inscripcion DATE NOT NULL,
            fecha_fin_inscripcion DATE NOT NULL
        )
    """;

        String sqlInscripciones = """
        CREATE TABLE inscripciones (
            id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
            apellido VARCHAR(255) NOT NULL,
            nombre VARCHAR(255) NOT NULL,
            dni VARCHAR(20) NOT NULL,
            telefono VARCHAR(20) NOT NULL,
            email VARCHAR(255) NOT NULL,
            id_concurso INTEGER NOT NULL,
            fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    """;

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             Statement stmt = conn.createStatement()) {

            try {
                stmt.execute(sqlConcursos);
            } catch (SQLException e) {
                // Ignorar si la tabla ya existe (código de error X0Y32)
                if (!e.getSQLState().equals("X0Y32")) {
                    throw e;
                }
            }

            try {
                stmt.execute(sqlInscripciones);
            } catch (SQLException e) {
                // Ignorar si la tabla ya existe
                if (!e.getSQLState().equals("X0Y32")) {
                    throw e;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar tablas: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveInscription(Inscripcion inscripcion) {
        String sql = """
            INSERT INTO inscripciones (apellido, nombre, dni, telefono, email, id_concurso) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inscripcion.getApellido());
            stmt.setString(2, inscripcion.getNombre());
            stmt.setString(3, inscripcion.getDni());
            stmt.setString(4, inscripcion.getTelefono());
            stmt.setString(5, inscripcion.getEmail());
            stmt.setInt(6, inscripcion.getIdConcurso());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar inscripción: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Inscripcion> obtenerPorConcurso(int idConcurso) {
        String sql = """
            SELECT apellido, nombre, dni, telefono, email, id_concurso 
            FROM inscripciones 
            WHERE id_concurso = ?
        """;

        List<Inscripcion> inscripciones = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConcurso);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inscripcion inscripcion = new Inscripcion(
                            rs.getString("apellido"),
                            rs.getString("nombre"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getInt("id_concurso")
                    );
                    inscripciones.add(inscripcion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener inscripciones por concurso: " + e.getMessage(), e);
        }

        return inscripciones;
    }

    @Override
    public List<Inscripcion> obtenerTodas() {
        String sql = "SELECT apellido, nombre, dni, telefono, email, id_concurso FROM inscripciones";
        List<Inscripcion> inscripciones = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion(
                        rs.getString("apellido"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getInt("id_concurso")
                );
                inscripciones.add(inscripcion);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }
}

