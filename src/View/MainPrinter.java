/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BashFile;
import Controller.FunctionManager;
import Model.Log;
import Model.LogMessage;
import Model.QueueType;
import Model.SynchronizationType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Joseph Salas
 */
public class MainPrinter extends javax.swing.JFrame {

    
    private File file;
    private FunctionManager functionManager = FunctionManager.getInstance();
    private String[] str;
    private int windowCounter = 1;
    private static MainPrinter singleton = null;
    private String sourcePath;
    /**
     * Creates new form MainPaint
     */
    private MainPrinter() {
        initComponents();
        //logArea.setEditable(false);
        functionManager.createMailbox(1, 50, QueueType.FIFO);
        functionManager.createIndirectProcess(1, SynchronizationType.BLOCKING, QueueType.FIFO, 10, SynchronizationType.BLOCKING, 1);
        str = new String[3]; 
        functionManager.addReceiverMailbox(1, 1);
    }
    
    public static MainPrinter getInstance()
    {
        if(singleton == null)
        {
            singleton = new MainPrinter();
        }
        return singleton;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        newFileHelp = new javax.swing.JLabel();
        openFileHelp = new javax.swing.JLabel();
        logHelp = new javax.swing.JLabel();
        logBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Impresora");

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

        logArea.setColumns(20);
        logArea.setRows(5);
        jScrollPane1.setViewportView(logArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Log de eventos");

        newFileHelp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        newFileHelp.setForeground(new java.awt.Color(255, 0, 0));
        newFileHelp.setText("?");
        newFileHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newFileHelpMouseClicked(evt);
            }
        });

        openFileHelp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        openFileHelp.setForeground(new java.awt.Color(255, 0, 0));
        openFileHelp.setText("?");
        openFileHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openFileHelpMouseClicked(evt);
            }
        });

        logHelp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        logHelp.setForeground(new java.awt.Color(255, 0, 0));
        logHelp.setText("?");
        logHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logHelpMouseClicked(evt);
            }
        });

        logBtn.setText("Actualizar Log");
        logBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(openFileBtn)
                        .addGap(18, 18, 18)
                        .addComponent(openFileHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(newFileBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newFileHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(logHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(logHelp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openFileBtn)
                    .addComponent(newFileBtn)
                    .addComponent(newFileHelp)
                    .addComponent(openFileHelp)
                    .addComponent(logBtn))
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
        sourcePath = file.getAbsolutePath();
        try {
            fileText = bashFile.getFileText(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        str[0] = fileText;
        str[1] = Integer.toString(windowCounter);
        str[2] = sourcePath;
        WordUI.main(str);
        
        
    }//GEN-LAST:event_openFileBtnActionPerformed

    private void newFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileBtnActionPerformed

        windowCounter +=1;
        str[0] = "";
        str[1] = Integer.toString(windowCounter);
        WordUI.main(str);
       
   
        
    }//GEN-LAST:event_newFileBtnActionPerformed

    private void newFileHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newFileHelpMouseClicked
         JOptionPane.showMessageDialog(null, "Este botón desplegará una ventana donde se podrá crear un nuevo archivo de texto desde cero, para posteriormente imprimirlo. \n"
                 + "Este archivo también se podrá guardar."
                    , "Información de logs", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_newFileHelpMouseClicked

    private void openFileHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openFileHelpMouseClicked
                 JOptionPane.showMessageDialog(null, "Con este botón podrá seleccionar un archivo que desee imprimir y se desplegará una pantalla de previsualización del \n"
                         + "archivo donde se podrá editar antes de mandar a imprimir"
                    , "Información- abrir archivo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_openFileHelpMouseClicked

    private void logHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logHelpMouseClicked
                 JOptionPane.showMessageDialog(null, "En esta pantalla se desplegarán los logs de cada uno de los eventos de procesamiento de \n"
                         + "message passing para simular el envío de archivos hacia una impresora"
                    , "Información - nuevo archivo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_logHelpMouseClicked

    private void logBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logBtnActionPerformed
         String messageLog = "";
        ArrayList<LogMessage> logs = Log.getInstance().getLogs();
        for (int i = 0; i < logs.size(); i++) {
            messageLog +=logs.get(i).getMessage()+"\n";
        }
        logArea.setText(messageLog);
    }//GEN-LAST:event_logBtnActionPerformed

    public JTextArea getLogArea() {
        return logArea;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton logBtn;
    private javax.swing.JLabel logHelp;
    private javax.swing.JButton newFileBtn;
    private javax.swing.JLabel newFileHelp;
    private javax.swing.JButton openFileBtn;
    private javax.swing.JLabel openFileHelp;
    // End of variables declaration//GEN-END:variables
}
