/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import services.Configurator;

/**
 *
 * @author sing1
 */
public class DelimiterDialog extends javax.swing.JDialog {
    private Configurator configurator = new Configurator();
    private HashMap<String, String> delimiterMap = new HashMap<>() {{
        put("Tab", "\t");
        put("Semicolon", ";");
        put("Comma", ",");
        put("Space", " ");
    }};
    /**
     * Creates new form DelimiterDialog
     */
    public DelimiterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDisplay();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup = new javax.swing.ButtonGroup();
        rdBtnTab = new javax.swing.JRadioButton();
        rdBtnSemicolon = new javax.swing.JRadioButton();
        rdBtnComma = new javax.swing.JRadioButton();
        rdBtnSpace = new javax.swing.JRadioButton();
        rdBtnOther = new javax.swing.JRadioButton();
        btnOK = new javax.swing.JButton();
        txtDelimiter = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delimiter");
        setIconImage(((MainForm) this.getParent()).getIconImage());
        setResizable(false);

        rdBtnTab.setText("Tab");

        rdBtnSemicolon.setText("Semicolon");

        rdBtnComma.setText("Comma");

        rdBtnSpace.setText("Space");

        rdBtnOther.setText("Other:");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rdBtnSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdBtnComma, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdBtnSemicolon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdBtnTab, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(rdBtnOther, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(rdBtnTab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnSemicolon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnComma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnSpace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnOther)
                    .addComponent(txtDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnOK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void setDisplay() {
        addBtnToBtnGr();
        reset();
    }

    private void reset() {
        this.rdBtnTab.setSelected(true);
        this.txtDelimiter.setEnabled(false);
        this.txtDelimiter.setFocusable(false);
        this.txtDelimiter.setText("");
    }

    private void addBtnToBtnGr()
    {
        this.rdBtnTab.setActionCommand("Tab");
        this.rdBtnSemicolon.setActionCommand("Semicolon");
        this.rdBtnComma.setActionCommand("Comma");
        this.rdBtnSpace.setActionCommand("Space");
        this.rdBtnOther.setActionCommand("Other");
        this.rdBtnOther.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (rdBtnOther.isSelected()) {
                    txtDelimiter.setEnabled(true);
                    txtDelimiter.setFocusable(true);
                } else {
                    txtDelimiter.setEnabled(false);
                    txtDelimiter.setFocusable(false);
                }
            }
        });
        this.btnGroup.add(rdBtnTab);
        this.btnGroup.add(rdBtnSemicolon);
        this.btnGroup.add(rdBtnComma);
        this.btnGroup.add(rdBtnSpace);
        this.btnGroup.add(rdBtnOther);
    }
    
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        String delimiter = "";
        if (this.rdBtnOther.isSelected()) {
            delimiter = this.txtDelimiter.getText();
            if (delimiter.length() != 1) {
                JOptionPane.showMessageDialog(this, "Delimiter must be a single character");
                return;
            }
        } else {
            String typeDelimiter = this.btnGroup.getSelection().getActionCommand();
            delimiter = delimiterMap.get(typeDelimiter);
        }
        try {
            configurator.setDelimiter(delimiter);
            JOptionPane.showMessageDialog(this, "Delimiter set successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error while setting delimiter", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOKActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DelimiterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DelimiterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DelimiterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DelimiterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DelimiterDialog dialog = new DelimiterDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton btnOK;
    private javax.swing.JRadioButton rdBtnComma;
    private javax.swing.JRadioButton rdBtnOther;
    private javax.swing.JRadioButton rdBtnSemicolon;
    private javax.swing.JRadioButton rdBtnSpace;
    private javax.swing.JRadioButton rdBtnTab;
    private javax.swing.JTextField txtDelimiter;
    // End of variables declaration//GEN-END:variables
}
