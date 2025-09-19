package Vista;

import javax.swing.*;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnLogin;

    public Login() {
        setTitle("Login Hospital");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Correo:");
        lblUsuario.setBounds(20, 20, 80, 25);
        add(lblUsuario);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 20, 200, 25);
        add(txtUsuario);

        JLabel lblClave = new JLabel("Contraseña:");
        lblClave.setBounds(20, 60, 80, 25);
        add(lblClave);
        txtClave = new JPasswordField();
        txtClave.setBounds(100, 60, 200, 25);
        add(txtClave);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(100, 100, 150, 30);
        add(btnLogin);
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtClave() {
        return txtClave;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
