package hospital;

import Vista.Login;
import Controlador.CLogin;
import javax.swing.*;

public class Hospital {

    public static void main(String[] args) {
        // Configurar Look & Feel (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error al aplicar Look & Feel: " + e.getMessage());
        }

        // Iniciar la interfaz de Login en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);

            // Crear el controlador del login
            CLogin cLogin = new CLogin(login.getTxtUsuario(), login.getTxtClave());

            // Asignar acción al botón de login
            login.getBtnLogin().addActionListener(e -> cLogin.iniciarSesion());
        });
    }
}
