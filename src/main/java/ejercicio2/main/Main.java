package ejercicio2.main;

import ejercicio2.modelo.EmpleadoRepository;
import ejercicio2.modelo.EmailService;
import ejercicio2.modelo.ServicioCumple;
import ejercicio2.persistencia.EmpleadoRepositoryArchivo;
import ejercicio2.servicios.EmailServiceMailTrap;
import ejercicio2.modelo.ServicioCumpleDefault;

public class Main {
    public static void main(String[] args) {
        try {
            // Configuración
            String rutaArchivo = "empleados.txt";
            String mailTrapHost = "sandbox.smtp.mailtrap.io";
            int mailTrapPort = 2525;
            String mailTrapUser = "4d4ad4081c22c6";
            String mailTrapPassword = "1602d032d7d98b";
            String emailRemitente = "rrhh@empresa.com";

            // Inyección de dependencias
            EmpleadoRepository empleadoRepository = new EmpleadoRepositoryArchivo(rutaArchivo);
            EmailService emailService = new EmailServiceMailTrap(
                    mailTrapHost, mailTrapPort, mailTrapUser, mailTrapPassword, emailRemitente
            );

            ServicioCumple servicioCumple = new ServicioCumpleDefault(empleadoRepository, emailService);
            servicioCumple.enviarFelicitacionesCumple();

        } catch (Exception e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
