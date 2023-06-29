/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import services.FolderPath;
import services.Logger;

/**
 *
 * @author sing1
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        lbCopyRight.setHorizontalTextPosition(SwingConstants.LEFT);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator12 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtInput = new javax.swing.JTextField();
        btnChooseFile = new javax.swing.JButton();
        btnConvert = new javax.swing.JButton();
        cbbStruct = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lbCopyRight = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        menuLog = new javax.swing.JMenu();
        menuLogError = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuLogDate = new javax.swing.JMenuItem();
        menuPathFolder = new javax.swing.JMenu();
        menuPathInput = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuPathOutput = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuPathError = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuPathProcess = new javax.swing.JMenuItem();
        menuConfig = new javax.swing.JMenu();
        menuConfigConnectDB = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuConfigDefaultValue = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuConfigQuery = new javax.swing.JMenuItem();
        menuHeader = new javax.swing.JMenu();
        menuHeaderNew = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuHeaderList = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        menuHeaderImport = new javax.swing.JMenuItem();
        menuObject = new javax.swing.JMenu();
        menuObjectNew = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        menuObjectImport = new javax.swing.JMenuItem();
        menuStruct = new javax.swing.JMenu();
        menuStructNew = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        menuStructList = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Converter");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CONVERTER");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Input:");

        txtInput.setEditable(false);
        txtInput.setFocusable(false);

        btnChooseFile.setText("Choose file");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        btnConvert.setText("Convert file");
        btnConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertActionPerformed(evt);
            }
        });

        jLabel3.setText("Version 1.0.0.0");

        lbCopyRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        lbCopyRight.setText("Copyright 2023 © by");

        jMenu1.setText("System");

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        menuBar.add(jMenu1);

        menuLog.setText("Log");

        menuLogError.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuLogError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log-error.png"))); // NOI18N
        menuLogError.setText("Error");
        menuLogError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogErrorActionPerformed(evt);
            }
        });
        menuLog.add(menuLogError);
        menuLog.add(jSeparator1);

        menuLogDate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuLogDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log-date.png"))); // NOI18N
        menuLogDate.setText("Date");
        menuLogDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogDateActionPerformed(evt);
            }
        });
        menuLog.add(menuLogDate);

        menuBar.add(menuLog);

        menuPathFolder.setText(" Path folder");

        menuPathInput.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuPathInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/input-folder.png"))); // NOI18N
        menuPathInput.setText("Input");
        menuPathInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPathInputActionPerformed(evt);
            }
        });
        menuPathFolder.add(menuPathInput);
        menuPathFolder.add(jSeparator2);

        menuPathOutput.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuPathOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/output-folder.png"))); // NOI18N
        menuPathOutput.setText("Output");
        menuPathOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPathOutputActionPerformed(evt);
            }
        });
        menuPathFolder.add(menuPathOutput);
        menuPathFolder.add(jSeparator3);

        menuPathError.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuPathError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/error-folder.png"))); // NOI18N
        menuPathError.setText("Error");
        menuPathError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPathErrorActionPerformed(evt);
            }
        });
        menuPathFolder.add(menuPathError);
        menuPathFolder.add(jSeparator4);

        menuPathProcess.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuPathProcess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/process-folder.png"))); // NOI18N
        menuPathProcess.setText("Process");
        menuPathProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPathProcessActionPerformed(evt);
            }
        });
        menuPathFolder.add(menuPathProcess);

        menuBar.add(menuPathFolder);

        menuConfig.setText("Config");

        menuConfigConnectDB.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuConfigConnectDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/database.png"))); // NOI18N
        menuConfigConnectDB.setText("Connect to DB");
        menuConfigConnectDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfigConnectDBActionPerformed(evt);
            }
        });
        menuConfig.add(menuConfigConnectDB);
        menuConfig.add(jSeparator5);

        menuConfigDefaultValue.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuConfigDefaultValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default-value.png"))); // NOI18N
        menuConfigDefaultValue.setText("Default value");
        menuConfigDefaultValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfigDefaultValueActionPerformed(evt);
            }
        });
        menuConfig.add(menuConfigDefaultValue);
        menuConfig.add(jSeparator6);

        menuConfigQuery.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuConfigQuery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/query.png"))); // NOI18N
        menuConfigQuery.setText("Query");
        menuConfigQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfigQueryActionPerformed(evt);
            }
        });
        menuConfig.add(menuConfigQuery);

        menuBar.add(menuConfig);

        menuHeader.setText("Header");

        menuHeaderNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.png"))); // NOI18N
        menuHeaderNew.setText("New");
        menuHeaderNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHeaderNewActionPerformed(evt);
            }
        });
        menuHeader.add(menuHeaderNew);
        menuHeader.add(jSeparator7);

        menuHeaderList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/list.png"))); // NOI18N
        menuHeaderList.setText("List");
        menuHeaderList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHeaderListActionPerformed(evt);
            }
        });
        menuHeader.add(menuHeaderList);
        menuHeader.add(jSeparator8);

        menuHeaderImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/import-template.png"))); // NOI18N
        menuHeaderImport.setText("Import");
        menuHeaderImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHeaderImportActionPerformed(evt);
            }
        });
        menuHeader.add(menuHeaderImport);

        menuBar.add(menuHeader);

        menuObject.setText("Object");

        menuObjectNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.png"))); // NOI18N
        menuObjectNew.setText("New");
        menuObjectNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuObjectNewActionPerformed(evt);
            }
        });
        menuObject.add(menuObjectNew);
        menuObject.add(jSeparator9);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/list.png"))); // NOI18N
        jMenuItem3.setText("List");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuObject.add(jMenuItem3);
        menuObject.add(jSeparator11);

        menuObjectImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/import-template.png"))); // NOI18N
        menuObjectImport.setText("Import");
        menuObjectImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuObjectImportActionPerformed(evt);
            }
        });
        menuObject.add(menuObjectImport);

        menuBar.add(menuObject);

        menuStruct.setText("Struct");

        menuStructNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.png"))); // NOI18N
        menuStructNew.setText("New");
        menuStructNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStructNewActionPerformed(evt);
            }
        });
        menuStruct.add(menuStructNew);
        menuStruct.add(jSeparator10);

        menuStructList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/list.png"))); // NOI18N
        menuStructList.setText("List");
        menuStructList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStructListActionPerformed(evt);
            }
        });
        menuStruct.add(menuStructList);

        menuBar.add(menuStruct);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbCopyRight, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 418, Short.MAX_VALUE)
                        .addComponent(cbbStruct, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConvert, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseFile))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbStruct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConvert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCopyRight)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private String showDialogChooseFolderPath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int choice = fileChooser.showOpenDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            return selectedDirectory.getAbsolutePath();
        }

        return null;
    }

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Are you sure to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btnConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConvertActionPerformed

    private void menuLogErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogErrorActionPerformed
        new LogDialog(this, true, "Log error", Logger.getLogError()).setVisible(true);
    }//GEN-LAST:event_menuLogErrorActionPerformed

    private void menuLogDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogDateActionPerformed
        new LogDialog(this, true, "Log date", Logger.getLogDate()).setVisible(true);
    }//GEN-LAST:event_menuLogDateActionPerformed

    private void menuPathInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPathInputActionPerformed
        String path = showDialogChooseFolderPath();
        if (path != null) {
            FolderPath folderPath = new FolderPath();
            try {
                String msg = folderPath.setInputFolderPath(path);
                JOptionPane.showMessageDialog(this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuPathInputActionPerformed

    private void menuPathOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPathOutputActionPerformed
        String path = showDialogChooseFolderPath();
        if (path != null) {
            FolderPath folderPath = new FolderPath();
            try {
                String msg = folderPath.setOutputFolderPath(path);
                JOptionPane.showMessageDialog(this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuPathOutputActionPerformed

    private void menuPathErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPathErrorActionPerformed
        String path = showDialogChooseFolderPath();
        if (path != null) {
            FolderPath folderPath = new FolderPath();
            try {
                String msg = folderPath.setErrorFolderPath(path);
                JOptionPane.showMessageDialog(this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuPathErrorActionPerformed

    private void menuPathProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPathProcessActionPerformed
        String path = showDialogChooseFolderPath();
        if (path != null) {
            FolderPath folderPath = new FolderPath();
            try {
                String msg = folderPath.setProcessFolderPath(path);
                JOptionPane.showMessageDialog(this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuPathProcessActionPerformed

    private void menuConfigConnectDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfigConnectDBActionPerformed
        new DBDialog(this, true).setVisible(true);
    }//GEN-LAST:event_menuConfigConnectDBActionPerformed

    private void menuConfigDefaultValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfigDefaultValueActionPerformed
        new DefaultValueDialog(this, true).setVisible(true);
    }//GEN-LAST:event_menuConfigDefaultValueActionPerformed

    private void menuConfigQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfigQueryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuConfigQueryActionPerformed

    private void menuHeaderNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHeaderNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuHeaderNewActionPerformed

    private void menuHeaderListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHeaderListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuHeaderListActionPerformed

    private void menuHeaderImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHeaderImportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuHeaderImportActionPerformed

    private void menuObjectNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuObjectNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuObjectNewActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void menuObjectImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuObjectImportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuObjectImportActionPerformed

    private void menuStructNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStructNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuStructNewActionPerformed

    private void menuStructListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStructListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuStructListActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnConvert;
    private javax.swing.JComboBox<String> cbbStruct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel lbCopyRight;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuConfig;
    private javax.swing.JMenuItem menuConfigConnectDB;
    private javax.swing.JMenuItem menuConfigDefaultValue;
    private javax.swing.JMenuItem menuConfigQuery;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuHeader;
    private javax.swing.JMenuItem menuHeaderImport;
    private javax.swing.JMenuItem menuHeaderList;
    private javax.swing.JMenuItem menuHeaderNew;
    private javax.swing.JMenu menuLog;
    private javax.swing.JMenuItem menuLogDate;
    private javax.swing.JMenuItem menuLogError;
    private javax.swing.JMenu menuObject;
    private javax.swing.JMenuItem menuObjectImport;
    private javax.swing.JMenuItem menuObjectNew;
    private javax.swing.JMenuItem menuPathError;
    private javax.swing.JMenu menuPathFolder;
    private javax.swing.JMenuItem menuPathInput;
    private javax.swing.JMenuItem menuPathOutput;
    private javax.swing.JMenuItem menuPathProcess;
    private javax.swing.JMenu menuStruct;
    private javax.swing.JMenuItem menuStructList;
    private javax.swing.JMenuItem menuStructNew;
    private javax.swing.JTextField txtInput;
    // End of variables declaration//GEN-END:variables
}
