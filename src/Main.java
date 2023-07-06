
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

import services.Resource;
import views.MainForm;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author sing1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        try {
            Resource.init();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot create resource files", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    new MainForm().setVisible(true);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
