package ejercicio1.ui;

import ejercicio1.modelo.ParticipanteService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarParticipanteFrame extends JFrame implements ParticipanteView {
    private final ParticipanteService participanteService;
    private JTextField nombre;
    private JTextField telefono;
    private JTextField region;

    public AgregarParticipanteFrame(ParticipanteService participanteService) {
        this.participanteService = participanteService;
        setupUIComponents();
    }

    private void setupUIComponents() {
        setTitle("Add Participant");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.nombre = new JTextField(10);
        this.telefono = new JTextField(10);
        this.region = new JTextField(10);

        this.nombre.setText("");
        this.telefono.setText("");
        this.region.setText("China");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new FlowLayout());

        contentPane.add(new JLabel("Nombre: "));
        contentPane.add(nombre);
        contentPane.add(new JLabel("Telefono: "));
        contentPane.add(telefono);
        contentPane.add(new JLabel("Region: "));
        contentPane.add(region);

        JButton botonCargar = new JButton("Cargar");
        botonCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBotonCargar();
            }
        });

        contentPane.add(botonCargar);
        setContentPane(contentPane);
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pack();
    }

    private void onBotonCargar() {
        try {
            participanteService.agregarParticipante(
                    nombre.getText().trim(),
                    telefono.getText().trim(),
                    region.getText().trim()
            );
            dispose();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar el participante: " + e.getMessage());
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    @Override
    public void cerrar() {
        dispose();
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }
}
