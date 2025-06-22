package ejercicio3.persistencia;

import ejercicio3.modelo.Concurso;
import ejercicio3.modelo.ConcursoRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConcursoRepositoryJDBC implements ConcursoRepository {
    private final String url;
    private final String usuario;
    private final String password;

    public ConcursoRepositoryJDBC(String url, String usuario, String password) {
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
    public List<Concurso> todosLosConcursos() {
        String sql = """
            SELECT id, nombre, fecha_inicio_inscripcion, fecha_fin_inscripcion 
            FROM concursos 
            WHERE CURRENT_DATE BETWEEN fecha_inicio_inscripcion AND fecha_fin_inscripcion
        """;

        List<Concurso> concursos = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Concurso concurso = new Concurso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_inicio_inscripcion").toLocalDate(),
                        rs.getDate("fecha_fin_inscripcion").toLocalDate()
                );
                concursos.add(concurso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener concursos abiertos: " + e.getMessage(), e);
        }

        return concursos;
    }

    @Override
    public List<Concurso> obtenerTodos() {
        String sql = "SELECT id, nombre, fecha_inicio_inscripcion, fecha_fin_inscripcion FROM concursos";
        List<Concurso> concursos = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Concurso concurso = new Concurso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_inicio_inscripcion").toLocalDate(),
                        rs.getDate("fecha_fin_inscripcion").toLocalDate()
                );
                concursos.add(concurso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los concursos: " + e.getMessage(), e);
        }

        return concursos;
    }

    @Override
    public void guardar(Concurso concurso) {
        String sql = """
            INSERT INTO concursos (id, nombre, fecha_inicio_inscripcion, fecha_fin_inscripcion) 
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, concurso.getId());
            stmt.setString(2, concurso.getNombre());
            stmt.setDate(3, Date.valueOf(concurso.getFechaInicioInscripcion()));
            stmt.setDate(4, Date.valueOf(concurso.getFechaFinInscripcion()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar concurso: " + e.getMessage(), e);
        }
    }
}

