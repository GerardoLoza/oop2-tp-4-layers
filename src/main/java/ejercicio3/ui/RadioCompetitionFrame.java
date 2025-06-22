package ejercicio3.ui;

import ejercicio3.modelo.Concurso;
import ejercicio3.modelo.ServicioInscripcion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RadioCompetitionFrame extends JFrame {
    private final ServicioInscripcion servicioInscripcion;

    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtId;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JComboBox<Concurso> comboBox;
    private JButton btnOk;

    public RadioCompetitionFrame(ServicioInscripcion servicioInscripcion) {
        this.servicioInscripcion = servicioInscripcion;
        initializeComponents();
        setupLayout();
        cargarConcursos();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Inscription to Competition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 451, 229);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Campos de texto
        txtName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtId = new JTextField(10);
        txtPhone = new JTextField(10);
        txtEmail = new JTextField(10);

        // ComboBox para concursos
        comboBox = new JComboBox<>();
        comboBox.addItem(null); // Opción vacía por defecto

        // Botón
        btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOk.setEnabled(false);
                try {
                    guardarInscripcion();
                } finally {
                    btnOk.setEnabled(true);
                }
            }
        });
    }

    private void setupLayout() {
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        // Labels
        JLabel lblName = new JLabel("Nombre:");
        JLabel lblLastName = new JLabel("Apellido:");
        JLabel lblId = new JLabel("Dni:");
        JLabel lblPhone = new JLabel("Telefono:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblCompetition = new JLabel("Concurso:");

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblName)
                                                        .addComponent(lblLastName)
                                                        .addComponent(lblId)
                                                        .addComponent(lblPhone)
                                                        .addComponent(lblEmail)
                                                        .addComponent(lblCompetition))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtName, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                                        .addComponent(txtLastName)
                                                        .addComponent(txtId)
                                                        .addComponent(txtPhone)
                                                        .addComponent(txtEmail)
                                                        .addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(btnOk, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblName)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblLastName)
                                        .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblId)
                                        .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPhone)
                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEmail)
                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCompetition)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnOk)
                                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }

    private void cargarConcursos() {
        try {
            List<Concurso> concursos = servicioInscripcion.obtenerConcursosDisponibles();
            comboBox.removeAllItems();
            comboBox.addItem(null); // Opción vacía

            for (Concurso concurso : concursos) {
                comboBox.addItem(concurso);
            }

            if (concursos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay concursos disponibles en este momento");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar concursos: " + e.getMessage());
        }
    }

    private void guardarInscripcion() {
        if (!validarCampos()) {
            return;
        }

        try {
            Concurso concursoSeleccionado = (Concurso) comboBox.getSelectedItem();

            servicioInscripcion.inscribirParticipante(
                    txtLastName.getText().trim(),
                    txtName.getText().trim(),
                    txtId.getText().trim(),
                    txtPhone.getText().trim(),
                    txtEmail.getText().trim(),
                    concursoSeleccionado.getId()
            );

            JOptionPane.showMessageDialog(this, "Inscripción guardada exitosamente");
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar inscripción: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre no puede ser vacío");
            return false;
        }

        if (txtLastName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Apellido no puede ser vacío");
            return false;
        }

        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI no puede ser vacío");
            return false;
        }

        if (!validarEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email debe ser válido");
            return false;
        }

        if (!validarTelefono(txtPhone.getText())) {
            JOptionPane.showMessageDialog(this, "El teléfono debe ingresarse de la siguiente forma: NNNN-NNNNNN");
            return false;
        }

        if (comboBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe elegir un Concurso");
            return false;
        }

        return true;
    }

    private boolean validarEmail(String email) {
        if (email == null) return false;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean validarTelefono(String telefono) {
        if (telefono == null) return false;
        String regex = "\\d{4}-\\d{6}";
        return telefono.matches(regex);
    }

    private void limpiarCampos() {
        txtName.setText("");
        txtLastName.setText("");
        txtId.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        comboBox.setSelectedIndex(0);
    }
}

