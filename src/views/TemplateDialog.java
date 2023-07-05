/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.stream.XMLStreamException;

import common.TemplateType;
import services.Converter;
import services.Templater;

/**
 *
 * @author sing1
 */
public class TemplateDialog extends javax.swing.JDialog {
    private Converter converter = new Converter();
    private Templater templater = new Templater();
    private TemplateType type;
    private boolean isChanged = false;
    private String templateName;
    /**
     * Creates new form TemplateDialog
     */
    public TemplateDialog(java.awt.Frame parent, boolean modal, TemplateType type, String title, String templateName) {
        super(parent, modal);
        initComponents();
        setHotKeys();
        setTitle(title);
        this.type = type;
        this.templateName = templateName;
        try {
            loadTree();
            reset();
            this.setVisible(true);
        } catch(Exception e) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "Cannot load template", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setHotKeys() {
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnSaveActionPerformed(e);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isChanged) {
                    int result = JOptionPane.showConfirmDialog((Component) null, "Do you want to save template before exit?", "Notification", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                dispose();
            }
        });
    }

    private void loadTree() throws XMLStreamException, IOException {
        setNodeIcon();
        DefaultTreeModel model;
        if (this.templateName == null) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("tag (root)");
            model = new DefaultTreeModel(root);
        } else {
            if (this.type == TemplateType.HEADER) {
                model = converter.convertHeaderToJTree(this.templateName);
            } else {
                model = converter.convertObjectToJTree(this.templateName);
            }
            this.txtFileName.setText(this.templateName);
            this.txtFileName.setEditable(false);
            this.txtFileName.setFocusable(false);
        }
        this.treeStruct.setModel(model);
        this.treeStruct.setFocusable(false);
        this.treeStruct.getModel().addTreeModelListener(new TreeModelListener() {
            public void treeNodesChanged(TreeModelEvent e) {
                isChanged = true;
            }

            public void treeNodesInserted(TreeModelEvent e) {
                isChanged = true;
            }

            public void treeNodesRemoved(TreeModelEvent e) {
                isChanged = true;
            }

            public void treeStructureChanged(TreeModelEvent e) {
                isChanged = true;
            }
        });
    }

    private void reset() {
        this.treeStruct.clearSelection();
        this.btnAdd.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.cbbOption.setSelectedIndex(0);
        this.btnSave.requestFocus(true);
    }

    private void setNodeIcon() {
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                    boolean expanded, boolean leaf, int row, boolean hasFocus) {
                JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    if (node.getUserObject().toString().contains("tag")) {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/images/tag.png"));
                        label.setIcon(icon);
                    } else if (node.getUserObject().toString().contains("attributes")) {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/images/attributes.png"));
                        label.setIcon(icon);
                    } else {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/images/value.png"));
                        label.setIcon(icon);
                    }
                }
                return label;
            }
        };
        this.treeStruct.setCellRenderer(renderer);
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
        treeStruct = new javax.swing.JTree();
        cbbOption = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtFileName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 550));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeStruct.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeStruct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeStructMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(treeStruct);

        cbbOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "tag", "attributes", "value" }));
        cbbOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbOptionActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Name:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbbOption, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(txtFileName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave))
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean isExisted(DefaultMutableTreeNode currNode, String regex) {
        if (currNode == null) {
            return false;
        }
        int countChildNode = currNode.getChildCount();
        for (int i = 0; i < countChildNode; i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) currNode.getChildAt(i);
            if (childNode.getUserObject().toString().matches(regex)) {
                return true;
            }
        }
        return false;
    }

    private void redrawTree(DefaultMutableTreeNode currNode, TreePath selectionPath) {
        DefaultTreeModel model = (DefaultTreeModel) this.treeStruct.getModel();
        model.nodeStructureChanged(currNode);
        this.treeStruct.expandPath(selectionPath);
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (this.treeStruct.getSelectionCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only select one node to add new node", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.treeStruct.getSelectionCount() < 1) {
            JOptionPane.showMessageDialog(this, "You must select a node to add new node", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MainForm rootParent = (MainForm) this.getParent();
        String option = this.cbbOption.getSelectedItem().toString();
        TreePath selectionPath = this.treeStruct.getSelectionPath();
        DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        boolean isSuccess = false;
        switch(option) {
            case "tag":
                TagDialog addTagDialog = new TagDialog(rootParent, true, "New tag");
                if (addTagDialog.isOK()) {
                    String nodeName = "tag (" + addTagDialog.getTagName() + ")";
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
                    currNode.insert(node, currNode.getChildCount());
                    isSuccess = true;
                }
                addTagDialog.dispose();
                break;
            case "attributes":
                if (isExisted(currNode, "^attributes \\(.*\\)$")) {
                    JOptionPane.showMessageDialog(null, "Attributes already exist", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                AttributeDialog addAttributeDialog = new AttributeDialog(rootParent, true, this.type, "New attributes");
                if (addAttributeDialog.isOK()) {
                    String nodeName = "attributes (" + addAttributeDialog.getAttributes() + ")";
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
                    currNode.insert(node, 0);
                    isSuccess = true;
                }
                addAttributeDialog.dispose();
                break;
            case "value":
                if (isExisted(currNode, "^value \\(.*\\)$")) {
                    JOptionPane.showMessageDialog(null, "Value already exist", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                ValueDialog addValueDialog = new ValueDialog(rootParent, true, this.type, "New value");
                if(addValueDialog.isOK()) {
                    String nodeName = "value (" + addValueDialog.getValue() + ")";
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
                    if (currNode.getChildCount() == 0) {
                        currNode.insert(node, 0);
                    } else if (((DefaultMutableTreeNode)currNode.getFirstChild()).getUserObject().toString().contains("attributes")) {
                        currNode.insert(node, 1);
                    } else {
                        currNode.insert(node, 0);
                    }
                    isSuccess = true;
                }
                addValueDialog.dispose();
                break;
        }
        if (isSuccess) {
            redrawTree(currNode, selectionPath);
            this.cbbOption.setSelectedIndex(0);
            this.treeStruct.setSelectionPath(selectionPath);
            JOptionPane.showMessageDialog(null, "Add success", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (this.treeStruct.getSelectionCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only select one node to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.treeStruct.getSelectionCount() < 1) {
            JOptionPane.showMessageDialog(this, "You must select a node to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MainForm rootParent = (MainForm) this.getParent();
        TreePath selectionPath = this.treeStruct.getSelectionPath();
        DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        String nodeText = currNode.getUserObject().toString();
        boolean isSuccess = false;
        if (nodeText.contains("tag")) {
            TagDialog editTagDialog = new TagDialog(rootParent, true, "Edit tag");
            if (editTagDialog.isOK()) {
                String nodeName = "tag (" + editTagDialog.getTagName() + ")";
                currNode.setUserObject(nodeName);
                isSuccess = true;
            }
            editTagDialog.dispose(); 
        } else if (nodeText.contains("attributes")) {
            AttributeDialog editAttributeDialog = new AttributeDialog(rootParent, true, this.type, "Edit attributes");
            if (editAttributeDialog.isOK()) {
                String nodeName = "attributes (" + editAttributeDialog.getAttributes() + ")";
                currNode.setUserObject(nodeName);
                isSuccess = true;
            }
            editAttributeDialog.dispose();
        } else {
            ValueDialog editValueDialog = new ValueDialog(rootParent, true, this.type, "Edit value");
            if(editValueDialog.isOK()) {
                String nodeName = "value (" + editValueDialog.getValue() + ")";
                currNode.setUserObject(nodeName);
                isSuccess = true;
            }
            editValueDialog.dispose();
        }
        if (isSuccess) {
            redrawTree(currNode, selectionPath.getParentPath());
            this.treeStruct.setSelectionPath(selectionPath);
            JOptionPane.showMessageDialog(this, "Edit success", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (this.treeStruct.getSelectionCount() < 1) {
            JOptionPane.showMessageDialog(this, "You must select a node to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TreePath[] selectionPaths = this.treeStruct.getSelectionPaths();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete selected nodes?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isSuccess = false;
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.getPathCount() == 1) {
                    JOptionPane.showMessageDialog(this, "Cannot delete root node", "Warning", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                TreePath parentPath = selectionPath.getParentPath();
                DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) currNode.getParent();
                parentNode.remove(currNode);
                redrawTree(parentNode, parentPath);
                isSuccess = true;
            }
            if (isSuccess) {
                reset();
                JOptionPane.showMessageDialog(this, "Delete success", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!isChanged) {
            JOptionPane.showMessageDialog(this, "Nothing to save", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String name = this.txtFileName.getText();
        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!name.matches("\\w+")) {
            JOptionPane.showMessageDialog(this, "Name can only contain letters, numbers and underscore", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.templateName == null) {
            try {
                if (templater.isTemplateExist(this.type, name)) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Template name already exist, do you want to overwrite?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.NO_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        ProgressDialog progress = new ProgressDialog((MainForm)this.getParent(), true, "Save template", "Saving...");
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    templater.saveTemplate(TemplateDialog.this.type, TemplateDialog.this.treeStruct, name, progress);
                    TemplateDialog.this.dispose();
                    JOptionPane.showMessageDialog((MainForm) TemplateDialog.this.getParent(), "Save success", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(TemplateDialog.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    progress.dispose();
                }
            }
        });
        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                progress.setMainThread(mainThread);
                progress.setVisible(true);
            }
        });
        progressThread.start();
        mainThread.start();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void treeStructMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeStructMouseClicked
        if (this.treeStruct.getSelectionCount() > 1) {
            this.btnAdd.setEnabled(false);
            this.btnEdit.setEnabled(false);
            this.btnDelete.setEnabled(true);
            return;
        }
        if (this.treeStruct.getSelectionCount() == 1) {
            if (cbbOption.getSelectedIndex() > 0) {
                this.btnAdd.setEnabled(true);
            } else {
                this.btnAdd.setEnabled(false);
            }
            this.btnEdit.setEnabled(true);
            this.btnDelete.setEnabled(true);
            return;
        }
        if (this.treeStruct.getSelectionCount() < 1) {
            this.btnAdd.setEnabled(false);
            this.btnEdit.setEnabled(false);
            this.btnDelete.setEnabled(false);
            return;
        }
    }//GEN-LAST:event_treeStructMouseClicked

    private void cbbOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbOptionActionPerformed
        if (this.cbbOption.getSelectedIndex() < 1) {
            this.btnAdd.setEnabled(false);
        } else {
            TreePath currNode = this.treeStruct.getSelectionPath();
            if (currNode != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) currNode.getLastPathComponent();
                String nodeText = selectedNode.getUserObject().toString();
                if (nodeText.contains("tag")) {
                    btnAdd.setEnabled(true);
                } else {
                    btnAdd.setEnabled(false);
                }
            } else {
                btnAdd.setEnabled(false);
            }
        }
    }//GEN-LAST:event_cbbOptionActionPerformed

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
            java.util.logging.Logger.getLogger(TemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TemplateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TemplateDialog dialog = new TemplateDialog(new javax.swing.JFrame(), true, TemplateType.HEADER,"Template", "test");
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
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbbOption;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree treeStruct;
    private javax.swing.JTextField txtFileName;
    // End of variables declaration//GEN-END:variables
}
