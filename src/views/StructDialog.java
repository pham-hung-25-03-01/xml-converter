/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import common.Config;
import services.Structer;

/**
 *
 * @author sing1
 */
public class StructDialog extends javax.swing.JDialog {
    private Structer structer = new Structer();
    private ListStructDialog listStruct;
    private String structName;
    /**
     * Creates new form AddStructDialog
     */
    public StructDialog(java.awt.Frame parent, boolean modal, ListStructDialog listStruct, String title, String structName) {
        super(parent, modal);
        initComponents();
        setHotKeys();
        setTitle(title);
        this.listStruct = listStruct;
        this.structName = structName;
        try {
            loadData();
            this.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot load data", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void setHotKeys() {
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnSaveActionPerformed(e);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void loadData() throws IOException {
        String[] headers = Config.getHeaderFile().keySet().toArray(String[]::new);
        String[] objects = Config.getObjectFile().keySet().toArray(String[]::new);
        DefaultComboBoxModel<String> modelHeaders = new DefaultComboBoxModel<String>(headers);
        modelHeaders.insertElementAt("none", 0);
        DefaultComboBoxModel<String> modelObjects = new DefaultComboBoxModel<String>(objects);
        modelObjects.insertElementAt("none", 0);
        this.cbbHeader.setModel(modelHeaders);
        this.cbbObject.setModel(modelObjects);
        if (this.structName != null) {
            HashMap<String, String> struct = structer.readStruct(structName);
            this.txtTypeFile.setText(struct.get("TYPE_FILE"));
            this.txtTypeList.setText(struct.get("TYPE_LIST"));
            this.cbbHeader.setSelectedItem(struct.get("HEADER"));
            this.cbbObject.setSelectedItem(struct.get("OBJECT"));
            this.txtFilenameOutput.setText(struct.get("FILE_NAME_OUTPUT"));
            this.txtStructName.setText(struct.get("STRUCT_NAME"));
            this.txtStructName.setEditable(false);
            this.txtStructName.setFocusable(false);
        } else {
            this.cbbHeader.setSelectedIndex(0);
            this.cbbObject.setSelectedIndex(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbbHeader = new javax.swing.JComboBox<>();
        txtTypeFile = new javax.swing.JTextField();
        cbbObject = new javax.swing.JComboBox<>();
        txtTypeList = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtStructName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFilenameOutput = new javax.swing.JTextField();
        btnSetFilenameOutput = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(((MainForm) this.getParent()).getIconImage());
        setMaximumSize(new java.awt.Dimension(2147483647, 550));
        setMinimumSize(new java.awt.Dimension(450, 550));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Type file:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Type list:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Header:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Object:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Struct name:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel6.setText("Ex: ApplicationList");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setText("Ex: ApplicationFile");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("File name output:");

        txtFilenameOutput.setEditable(false);
        txtFilenameOutput.setFocusable(false);

        btnSetFilenameOutput.setText("Set");
        btnSetFilenameOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetFilenameOutputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStructName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                            .addComponent(txtFilenameOutput, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSetFilenameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbHeader, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTypeList)
                            .addComponent(cbbObject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTypeFile))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTypeFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(3, 3, 3)
                .addComponent(jLabel7)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbHeader)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTypeList)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbObject)
                    .addComponent(jLabel4))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtFilenameOutput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSetFilenameOutput)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStructName)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (this.cbbHeader.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(this, "Please select header", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.cbbObject.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(this, "Please select object", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String typeFile = this.txtTypeFile.getText();
        String typeList = this.txtTypeList.getText();
        String header = this.cbbHeader.getSelectedItem().toString();
        String object = this.cbbObject.getSelectedItem().toString();
        String fileNameOutput = this.txtFilenameOutput.getText();
        String name = this.txtStructName.getText();
        try {
            if (this.structName == null) {
                HashMap<String, String> struct = structer.createStruct(name, typeFile, header, typeList, object, fileNameOutput);
                MainForm rootParent = (MainForm) this.getParent();
                rootParent.addStruct(struct.get("STRUCT_NAME"));
                JOptionPane.showMessageDialog(this, "Created struct: " + struct.get("STRUCT_NAME"), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                HashMap<String, String> struct = structer.updateStruct(name, typeFile, header, typeList, object, fileNameOutput);
                this.listStruct.updateStruct(struct.get("STRUCT_NAME"), struct.get("TYPE_FILE"), struct.get("HEADER"), struct.get("TYPE_LIST"), struct.get("OBJECT"), struct.get("FILE_NAME_OUTPUT"));
                JOptionPane.showMessageDialog(this, "Updated struct: " + struct.get("STRUCT_NAME"), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSetFilenameOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetFilenameOutputActionPerformed
        FileNameOutputDialog fileNameOutputDialog = new FileNameOutputDialog((MainForm) this.getParent(), true);
        if (fileNameOutputDialog.isOK()) {
            this.txtFilenameOutput.setText(fileNameOutputDialog.getFileNameOutput());
        }
        fileNameOutputDialog.dispose();
    }//GEN-LAST:event_btnSetFilenameOutputActionPerformed

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
            java.util.logging.Logger.getLogger(StructDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StructDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StructDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StructDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StructDialog dialog = new StructDialog(new javax.swing.JFrame(), true, new ListStructDialog(null, true, null), "New struct", "Struct");
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
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSetFilenameOutput;
    private javax.swing.JComboBox<String> cbbHeader;
    private javax.swing.JComboBox<String> cbbObject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txtFilenameOutput;
    private javax.swing.JTextField txtStructName;
    private javax.swing.JTextField txtTypeFile;
    private javax.swing.JTextField txtTypeList;
    // End of variables declaration//GEN-END:variables
}
