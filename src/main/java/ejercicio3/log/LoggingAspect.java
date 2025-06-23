package ejercicio3.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
public class LoggingAspect {

    private static final String LOG_FILE = "method_calls.log";
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Before("@annotation(ejercicio3.aop.Log)")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);

        String parameters = formatParameters(args);
        String logEntry = String.format("\"%s\", \"%s\", \"%s\"",
                methodName, parameters, timestamp);

        writeToLogFile(logEntry);
    }

    private String formatParameters(Object[] args) {
        if (args == null || args.length == 0) {
            return "sin parametros";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(args[i] != null ? args[i].toString() : "null");
        }
        return sb.toString();
    }

    private void writeToLogFile(String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error escribiendo al archivo de log: " + e.getMessage());
        }
    }
}