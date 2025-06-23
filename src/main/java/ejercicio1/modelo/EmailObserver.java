package ejercicio1.modelo;

public class EmailObserver implements Observer {
    private EmailService emailService;

    public EmailObserver(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void update(Participante participante) {
        try {
            String asunto = "Confirmación de inscripción";
            String mensaje = "Estimado/a " + participante.getNombre() + ",\n\n" +
                    "Su inscripción ha sido registrada exitosamente.\n\n" +
                    "Datos registrados:\n" +
                    "Nombre: " + participante.getNombre() + "\n" +
                    "Teléfono: " + participante.getTelefono() + "\n" +
                    "Región: " + participante.getRegion() + "\n\n" +
                    "Gracias por participar.";

            emailService.enviarEmail(participante.getEmail(), asunto, mensaje);
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
        }
    }
}
