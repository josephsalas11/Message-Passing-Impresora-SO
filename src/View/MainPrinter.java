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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
    private int printerId;
    private static MainPrinter singleton = null;
    private String sourcePath;
    private Model.Process process;
    /**
     * Creates new form MainPaint
     */
    public MainPrinter(int printerId, String printerName) {
        initComponents();
        logArea.setEditable(false);
        process = functionManager.createStaticProcess(printerId, SynchronizationType.NONBLOCKING, QueueType.FIFO, 10, SynchronizationType.PRUEBA_LLEGADA, printerName, true);
        //functionManager.addReceiverMailbox(1, printerName);
        this.setTitle(printerName);
        str = new String[3]; 
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
                Model.Process p = functionManager.disposeProcess(process.getName());
                functionManager.disposeMailbox(p.getMailbox().getId());
                dispose();
            }
        });
    }

    public Model.Process getProcess() {
        return process;
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
        logArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        logHelp = new javax.swing.JLabel();
        logBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Impresora");

        logArea.setColumns(20);
        logArea.setRows(5);
        jScrollPane1.setViewportView(logArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Log de eventos");

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
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(logHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(logBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(18, 18, 18)
                .addComponent(logBtn)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logHelpMouseClicked
                 JOptionPane.showMessageDialog(null, "En esta pantalla se desplegarán los logs de cada uno de los eventos de procesamiento de \n"
                         + "message passing para simular el envío de archivos hacia una impresora"
                    , "Información - nuevo archivo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_logHelpMouseClicked

    private void logBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logBtnActionPerformed
         String messageLog = getProcessLogs(process.getId(), -1);  
        logArea.setText(messageLog);
    }//GEN-LAST:event_logBtnActionPerformed

    public JTextArea getLogArea() {
        return logArea;
    }

    public String getProcessLogs(int sourceId, int logsQuantity){
        ArrayList<String> logMessages = Log.getInstance().getProcessLog(sourceId) ;
        String logs = "";
        System.out.println(sourceId);
        if(logsQuantity == -1){
            for (int y = 0; y < logMessages.size(); y++) 
            {
                logs += Log.getInstance().getLogs().get(y).getDetail() + "\n";
            }
        }
        else{
            int index = logMessages.size()-1;
            while(logsQuantity > 0 && index >=0){
                logs += Log.getInstance().getLogs().get(index).getDetail() + "\n";
                index--;
                logsQuantity--;
            }
            }
            return logs;
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

        //args[0] = idProceso
        //args[1] = nombreImpresora
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainPrinter(Integer.parseInt(args[0]), args[1]).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton logBtn;
    private javax.swing.JLabel logHelp;
    // End of variables declaration//GEN-END:variables
}
