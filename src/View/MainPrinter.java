/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BashFile;
import Controller.FunctionManager;
import Model.QueueType;
import Model.SynchronizationType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Joseph Salas
 */
public class MainPrinter extends javax.swing.JFrame {

    
    private File file;
    private FunctionManager functionManager = FunctionManager.getInstance();
    private String[] str;
    private int windowCounter = 0;
    /**
     * Creates new form MainPaint
     */
    public MainPrinter() {
        initComponents();
        logArea.setEditable(false);
        functionManager.createMailbox(1, 50, QueueType.FIFO);
        functionManager.createIndirectProcess(1, SynchronizationType.BLOCKING, QueueType.FIFO, 10, SynchronizationType.BLOCKING, 1);
        str = new String[2]; 
        functionManager.addReceiverMailbox(1, 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openFileBtn = new javax.swing.JButton();
        newFileBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        openFileBtn.setText("Abrir Archivo");
        openFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileBtnActionPerformed(evt);
            }
        });

        newFileBtn.setText("Nuevo Archivo");
        newFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileBtnActionPerformed(evt);
            }
        });

        jButton3.setText("Imprimir");

        logArea.setColumns(20);
        logArea.setRows(5);
        jScrollPane1.setViewportView(logArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Log de eventos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(openFileBtn)
                .addGap(117, 117, 117)
                .addComponent(newFileBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openFileBtn)
                    .addComponent(newFileBtn)
                    .addComponent(jButton3))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileBtnActionPerformed

        windowCounter +=1;
        String fileText = "";
        BashFile bashFile = new BashFile();
        JFileChooser openFile = new JFileChooser();
        openFile.showOpenDialog(null);
        openFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
        file = openFile.getSelectedFile();
        try {
            fileText = bashFile.getFileText(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        str[0] = fileText;
        str[1] = Integer.toString(windowCounter);
        WordUI.main(str);
        
        
    }//GEN-LAST:event_openFileBtnActionPerformed

    private void newFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileBtnActionPerformed

        windowCounter +=1;
        str[0] = "";
        str[1] = Integer.toString(windowCounter);
        WordUI.main(str);
   
        
    }//GEN-LAST:event_newFileBtnActionPerformed

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
            java.util.logging.Logger.getLogger(MainPrinter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPrinter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPrinter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPrinter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPrinter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton newFileBtn;
    private javax.swing.JButton openFileBtn;
    // End of variables declaration//GEN-END:variables
}
