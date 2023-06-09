/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import common.TemplateType;
import services.Templater;

/**
 *
 * @author sing1
 */
public class ListTemplateDialog extends javax.swing.JDialog {
    private Templater templater = new Templater();
    private TemplateType type;
    /**
     * Creates new form ConfigTemplateDialog
     */
    public ListTemplateDialog(java.awt.Frame parent, boolean modal, TemplateType type, String title) {
        super(parent, modal);
        initComponents();
        setTitle(title);
        this.type = type;
        try {
            loadData();
            reset();
            this.setVisible(true);
        } catch (Exception e) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "Cannot load data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() throws IOException {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) this.tblTemplate.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        this.tblTemplate.setModel(this.templater.getListTemplates(this.type));
        this.tblTemplate.setDefaultEditor(Object.class, null);
        this.tblTemplate.setFocusable(false);
        this.tblTemplate.setAutoCreateRowSorter(true);
        this.tblTemplate.getColumnModel().getColumn(0).setMinWidth(160);
        this.tblTemplate.getColumnModel().getColumn(0).setPreferredWidth(160);
        this.tblTemplate.getColumnModel().getColumn(1).setMinWidth(300);
        this.tblTemplate.getColumnModel().getColumn(1).setPreferredWidth(400);
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
        tblTemplate = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDuplicate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(((MainForm) this.getParent()).getIconImage());
        setMinimumSize(new java.awt.Dimension(500, 400));

        tblTemplate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
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
        tblTemplate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTemplateMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTemplate);

        btnEdit.setText(" Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
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
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDuplicate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnDuplicate))
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void reset() {
        this.tblTemplate.clearSelection();
        this.btnEdit.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnDuplicate.setEnabled(false);
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (this.tblTemplate.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "Please select only one template to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.tblTemplate.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a template to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = this.tblTemplate.getSelectedRow();
        String name = (String) this.tblTemplate.getValueAt(row, 0);
        String title = this.type == TemplateType.HEADER ? "Edit header" : "Edit object";
        new TemplateDialog((MainForm) this.getParent(), true, this.type, title, name);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int[] selectedRows = this.tblTemplate.getSelectedRows();
        if (selectedRows.length < 1) {
            JOptionPane.showMessageDialog(this, "Please select a template to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected templates?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            List<String> deleted = new ArrayList<String>();
            List<String> notDeleted = new ArrayList<String>();
            List<String> errors = new ArrayList<String>();
            DefaultTableModel model = (DefaultTableModel) this.tblTemplate.getModel();
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                String name = (String) this.tblTemplate.getValueAt(selectedRows[i], 0);
                try {
                    String result = this.templater.deleteTemplate(this.type, name);
                    deleted.add(result);
                    model.removeRow(selectedRows[i]);
                } catch (Exception e) {
                    notDeleted.add(name);
                    errors.add(e.getMessage());
                }
            }
            reset();
            String error = errors.size() > 0 ? "\nError: " + String.join("\n", errors) : "";
            JOptionPane.showMessageDialog(this, "Deleted: " + String.join(", ", deleted) + "\nNot deleted: " + String.join(", ", notDeleted) + error, "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDuplicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuplicateActionPerformed
        if (this.tblTemplate.getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select a template to duplicate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.tblTemplate.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "Please select only one template to duplicate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TemplateNameDialog templateNameDialog = new TemplateNameDialog((MainForm) this.getParent(), true, this.type);
        try {
            if (templateNameDialog.isOK()) {
                String sourceTemplateName = (String) this.tblTemplate.getValueAt(this.tblTemplate.getSelectedRow(), 0);
                HashMap<String, String> result = this.templater.duplicateTemplate(this.type, sourceTemplateName, templateNameDialog.getInput());
                if (result == null) {
                    throw new Exception("Failed to duplicate template. See log for more details.");
                }
                DefaultTableModel model = (DefaultTableModel) this.tblTemplate.getModel();
                model.addRow(new Object[]{result.get("NAME"), result.get("PATH")});
                reset();
                JOptionPane.showMessageDialog(this, "Duplicated template: " + result.get("NAME"), "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        templateNameDialog.dispose();
    }//GEN-LAST:event_btnDuplicateActionPerformed

    private void tblTemplateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTemplateMouseClicked
        if (this.tblTemplate.getSelectedRowCount() > 1) {
            this.btnEdit.setEnabled(false);
            this.btnDuplicate.setEnabled(false);
            this.btnDelete.setEnabled(true);
            return;
        }
        if (this.tblTemplate.getSelectedRowCount() < 1) {
            this.btnEdit.setEnabled(false);
            this.btnDuplicate.setEnabled(false);
            this.btnDelete.setEnabled(false);
            return;
        }
        if (this.tblTemplate.getSelectedRowCount() == 1) {
            this.btnEdit.setEnabled(true);
            this.btnDuplicate.setEnabled(true);
            this.btnDelete.setEnabled(true);
                if (evt.getClickCount() == 2) {
                    btnEditActionPerformed(null);
                }
            return;
        }
    }//GEN-LAST:event_tblTemplateMouseClicked

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
            java.util.logging.Logger.getLogger(ListTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListTemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListTemplateDialog dialog = new ListTemplateDialog(new MainForm(), true, TemplateType.HEADER, null);
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
    private javax.swing.JTable tblTemplate;
    // End of variables declaration//GEN-END:variables
}
