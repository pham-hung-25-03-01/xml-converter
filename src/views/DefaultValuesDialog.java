/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import utils.Config;

/**
 *
 * @author ASUS RG
 */
public class DefaultValuesDialog extends javax.swing.JDialog {
    private HashMap<String, String> updateListDefaultValues = new HashMap<String, String>();
    

    /**
     * Creates new form NewJDialog
     */
    public DefaultValuesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            loadDefaultValues();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        loadImgApp("/img/openway-way4-logo.png");

        tblDefaultValues.getModel().addTableModelListener(
                new TableModelListener() {
            public void tableChanged(TableModelEvent evt) {
                int selectedRow = tblDefaultValues.getSelectedRow();
                if (selectedRow >= 0 && tblDefaultValues.isEditing()) {

                    String key = tblDefaultValues.getValueAt(tblDefaultValues.getSelectedRow(), 0).toString();
                    String value = tblDefaultValues.getValueAt(tblDefaultValues.getSelectedRow(), 1).toString();
                    updateListDefaultValues.put(key, value);
                }
            }
        });

    }
    
    private void loadImgApp(String path)
    {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
        this.setIconImage(icon.getImage());
    }
    
    private void loadDefaultValues() throws IOException
    {
        DefaultTableModel tblDefaultValues = (DefaultTableModel) this.tblDefaultValues.getModel();
        tblDefaultValues.setRowCount(0);

        Config.getConfigDefaultValues().forEach((key, value) -> {
            Object[] row = {key, value};
            tblDefaultValues.addRow(row);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDefaultValues = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKey = new javax.swing.JTextField();
        txtValue = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Default values");

        tblDefaultValues.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Key", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDefaultValues);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Key: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Value:");

        txtKey.setToolTipText("Enter a name of default value");

        txtValue.setToolTipText("Enter a value");

        btnAdd.setText("Add");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jScrollPane1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(68, 68, 68)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtValue)
                                .addComponent(txtKey)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(29, 29, 29)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(29, 29, 29)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtKey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        if (txtKey.getText().isEmpty() || txtValue.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the key and value!", "Notification", JOptionPane.WARNING_MESSAGE);
        } 
        else 
        {
            try 
            {
                if (Config.getConfigDefaultValues().containsKey(txtKey.getText()))
                    JOptionPane.showMessageDialog(this, txtKey.getText() + " already exists!", "Notification", JOptionPane.ERROR_MESSAGE);
                else
                {
                    try
                    {
                        Config.setConfigDefaultValues(new HashMap<String, String>(){{
                            put(txtKey.getText(), txtValue.getText());
                        }});
                        
                        DefaultTableModel tblDefaultValues = (DefaultTableModel) this.tblDefaultValues.getModel();
                        tblDefaultValues.addRow(new Object[]{txtKey.getText(), txtValue.getText()});
                        
                        JOptionPane.showMessageDialog(this, "Add " + txtKey.getText() + " success!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                        txtKey.setText("");
                        txtValue.setText("");
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Add fail!", "Notification", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } 
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        if(tblDefaultValues.getCellEditor() != null)
        {
            tblDefaultValues.getCellEditor().stopCellEditing();
        }
        
        if(updateListDefaultValues.size()<1)
        {
            JOptionPane.showMessageDialog(this, "No change!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure to update?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) 
        {
            try {
                Config.setConfigDefaultValues(updateListDefaultValues);
                updateListDefaultValues.clear();
                JOptionPane.showMessageDialog(this, "Update success!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Update fail!", "Notification", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (tblDefaultValues.getSelectedRow() == -1 || tblDefaultValues.getSelectionModel().isSelectionEmpty()) {
            // No row is selected by the user
            JOptionPane.showMessageDialog(this, "Please select a row to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            try {
                int[] selectedRows = tblDefaultValues.getSelectedRows();
                HashMap<String, String> deleteListDefaultValues = new HashMap<>();

                for (int selectedRow : selectedRows) {
                    String key = tblDefaultValues.getValueAt(selectedRow, 0).toString();
                    String value = tblDefaultValues.getValueAt(selectedRow, 1).toString();
                    deleteListDefaultValues.put(key, value);
                }

                Config.removeConfigDefaultValues(deleteListDefaultValues);

                DefaultTableModel tblDefaultValues = (DefaultTableModel) this.tblDefaultValues.getModel();

                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tblDefaultValues.removeRow(selectedRows[i]);
                }

                JOptionPane.showMessageDialog(this, "Delete success!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Delete fail!", "Notification", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(DefaultValuesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DefaultValuesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DefaultValuesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DefaultValuesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DefaultValuesDialog dialog = new DefaultValuesDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDefaultValues;
    private javax.swing.JTextField txtKey;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
