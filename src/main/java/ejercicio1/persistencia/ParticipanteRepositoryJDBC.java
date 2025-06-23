package ejercicio1.persistencia;

import ejercicio1.modelo.Participante;
import ejercicio1.modelo.ParticipanteRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParticipanteRepositoryJDBC implements ParticipanteRepository {
    private final String url;
    private final String user;
    private final String password;

    public ParticipanteRepositoryJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void guardar(Participante participante) throws Exception {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "insert into participantes(nombre, telefono, region, email) values(?,?,?,?)";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setString(1, participante.getNombre());
                st.setString(2, participante.getTelefono());
                st.setString(3, participante.getRegion());
                st.setString(4, participante.getEmail());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar participante: " + e.getMessage(), e);
        }
    }
}
