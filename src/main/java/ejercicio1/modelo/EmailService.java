package ejercicio1.modelo;

public interface EmailService {
    void enviarEmail(String destinatario, String asunto, String mensaje) throws Exception;
}
