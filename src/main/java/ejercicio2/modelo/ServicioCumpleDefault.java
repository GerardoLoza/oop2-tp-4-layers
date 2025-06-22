package ejercicio2.modelo;

import java.util.List;

public class ServicioCumpleDefault implements ServicioCumple {
    private final EmpleadoRepository empleadoRepository;
    private final EmailService emailService;

    public ServicioCumpleDefault(EmpleadoRepository empleadoRepository, EmailService emailService) {
        this.empleadoRepository = empleadoRepository;
        this.emailService = emailService;
    }

    @Override
    public void enviarFelicitacionesCumple() throws Exception {
        List<Empleado> empleados = empleadoRepository.obtenerTodos();

        for (Empleado empleado : empleados) {
            if (empleado.cumpleHoy()) {
                enviarFelicitacion(empleado);
            }
        }
    }

    private void enviarFelicitacion(Empleado empleado) throws Exception {
        String asunto = "¡Feliz Cumpleaños!";
        String mensaje = String.format(
                "Querido/a %s,\n\n" +
                        "¡Feliz cumpleaños! Esperamos que tengas un día maravilloso.\n\n" +
                        "Saludos cordiales,\n" +
                        "El equipo de Recursos Humanos",
                empleado.getNombreCompleto()
        );

        emailService.enviarEmail(empleado.getEmail(), asunto, mensaje);
    }
}
