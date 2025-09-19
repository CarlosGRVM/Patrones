package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionUsuario extends JFrame {

    // Campos de texto
    private JTextField txtIdUsuario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JPasswordField txtClave;

    // Combo para rol
    private JComboBox<String> cboRol;

    // Botones
    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    // Tabla
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public GestionUsuario() {
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior (formulario)
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        txtIdUsuario = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtClave = new JPasswordField();

        cboRol = new JComboBox<>(new String[]{"Admin", "Recepcionista", "Medico"});

        panelFormulario.add(new JLabel("ID Usuario:"));
        panelFormulario.add(txtIdUsuario);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(txtClave);

        panelFormulario.add(new JLabel("Rol:"));
        panelFormulario.add(cboRol);

        // Panel central (tabla)
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Correo", "Rol"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);

        // Panel inferior (botones)
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnInsertar = new JButton("Insertar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        // Agregar todo al JFrame
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para que el controlador acceda
    public JTextField getTxtIdUsuario() {
        return txtIdUsuario;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JPasswordField getTxtClave() {
        return txtClave;
    }

    public JComboBox<String> getCboRol() {
        return cboRol;
    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
