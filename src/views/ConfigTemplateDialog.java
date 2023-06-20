/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.ini4j.Wini;
import services.Importer;
import utils.Config;

/**
 *
 * @author ASUS RG
 */
public class ConfigTemplateDialog extends javax.swing.JDialog {
    /**
     * Creates new form ConfigTemplateDialog
     */
    public ConfigTemplateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            loadTemplates();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading templates", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTemplates = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDuplicate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Config templates");

        tblTemplates.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Path"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTemplates);
        if (tblTemplates.getColumnModel().getColumnCount() > 0) {
            tblTemplates.getColumnModel().getColumn(0).setResizable(false);
            tblTemplates.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblTemplates.getColumnModel().getColumn(1).setResizable(false);
        }

        tblTemplates.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTemplatesMouseClicked(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setPreferredSize(new java.awt.Dimension(75, 23));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new java.awt.Dimension(75, 23));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDuplicate.setText("Duplicate");
        btnDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuplicateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDuplicate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDuplicate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void tblTemplatesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTemplatesMouseClicked
        int row = tblTemplates.rowAtPoint(evt.getPoint());
        int col = tblTemplates.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            if (evt.getClickCount() == 2) {
                btnEditActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tblTemplatesMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        if(tblTemplates.getSelectedRowCount() < 1)
        {
            JOptionPane.showMessageDialog(this, "Please select a template to delete", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure to delete?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION) {
                int[] selectedRows = tblTemplates.getSelectedRows();
                DefaultTableModel model = (DefaultTableModel) tblTemplates.getModel();
                try {
                    for (int i=selectedRows.length-1; i>=0; i--) {
                        String templateName = model.getValueAt(selectedRows[i], 0).toString();
                        String path = model.getValueAt(selectedRows[i], 1).toString();
                        Files.deleteIfExists(Paths.get(path));
                        Config.removeConfigPath(templateName + "Template");
                        model.removeRow(selectedRows[i]);
                        ((MainForm) this.getParent()).getCbbTemplate().removeItem(templateName);
                    }
                    JOptionPane.showMessageDialog(this, "Delete successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Cannot delete!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tblTemplates.getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select a template to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tblTemplates.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "Please select only one template to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String templateName = tblTemplates.getValueAt(tblTemplates.getSelectedRow(), 0).toString();
            TemplateDialog editTemplateDialog = new TemplateDialog((MainForm)this.getParent(), "Edit template", true, templateName);
            editTemplateDialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot edit!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDuplicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuplicateActionPerformed
        if (tblTemplates.getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select a template to duplicate", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tblTemplates.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "Please select only one template to duplicate", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TemplateNameDialog inputTemplateNameDialog = new TemplateNameDialog((MainForm) this.getParent(), true, "Duplicate template", "Template name");
        inputTemplateNameDialog.setVisible(true);

        if (inputTemplateNameDialog.isOK() && !inputTemplateNameDialog.getText().isBlank()) {
            String sourceTemplateName = tblTemplates.getValueAt(tblTemplates.getSelectedRow(), 0).toString();
            Importer importer = new Importer();
            String targetTemplateName = importer.duplicateTemplate(sourceTemplateName, inputTemplateNameDialog.getText());
            if (targetTemplateName.isBlank()) {
                JOptionPane.showMessageDialog(this, "Cannot duplicate!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ((MainForm) this.getParent()).getCbbTemplate().addItem(targetTemplateName);
            this.tblTemplates.clearSelection();
            DefaultTableModel model = (DefaultTableModel) tblTemplates.getModel();
            try {
                model.addRow(new Object[]{targetTemplateName, Config.getConfigPath().get(targetTemplateName + "Template", "PATH")});
                JOptionPane.showMessageDialog(this, "Duplicate successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Cannot load new template!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDuplicateActionPerformed

    private void loadTemplates() throws IOException
    {
        DefaultTableModel tblDefaultValues = (DefaultTableModel) this.tblTemplates.getModel();
        tblDefaultValues.setRowCount(0);

        getTemplates();
    }
    
    public void getTemplates() throws IOException
    {
        DefaultTableModel model = (DefaultTableModel) tblTemplates.getModel();
        Wini ini = Config.getConfigPath();

        for (String sectionName : ini.keySet()) {
            if (sectionName.matches("^\\w+Template$") && !sectionName.equals("FileHeaderTemplate")) {
                String templateName = sectionName.replaceAll("Template$", "");
                String templatePath = ini.get(sectionName, "PATH");
                Object[] row = {templateName, templatePath};
                model.addRow(row);
            }
        }
    }

    
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
            java.util.logging.Logger.getLogger(ConfigTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigTemplateDialog dialog = new ConfigTemplateDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDuplicate;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTemplates;
    // End of variables declaration//GEN-END:variables
}
