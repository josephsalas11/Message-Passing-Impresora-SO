/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.FunctionManager;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo
 */
public class Receiver extends Thread implements IReceiver{
    private int processId;
    private IProducer producer;
    private SynchronizationType synchronizationType;
    private boolean allowReceive = true;
    private boolean waitReceive = true;

    public Receiver(IProducer producer, SynchronizationType synchronizationType, int processId) {
        this.processId = processId;
        this.producer = producer;
        this.synchronizationType = synchronizationType;
    }
    
    @Override
    public void run(){
        try {
            while(true){
                if(producer != null && allowReceive){
                    //System.out.println("asdasdasda");
                    if(producer.getMessageQueue().isQueueEmpty()){ //hacer validacion si se cumple condicion sleep(1)
                        //System.out.println("asd");
                        sleep(1000);
                    }
                    else{
                        getProducerMessage();
                    }
                }
            }
        } catch (InterruptedException e) {
        }
    }
    
    @Override
    public synchronized void getProducerMessage() throws InterruptedException{
        Message message = producer.getMessage(this);
        //System.out.println(message.getContent());
        if(message != null){
            if(synchronizationType == SynchronizationType.BLOCKING && producer.getClass() != Mailbox.class){
                //while se obtiene el mensaje: wait
                wait();
                System.out.println(message.getContent());
                sleep(1000);
            }
            else { //if(synchronizationType == SynchronizationType.NONBLOCKING)
                System.out.println(message.getContent());
                sleep(1000);
            }
            try {
                Path moveFile = Files.move(Paths.get(message.getContent()),
                        Paths.get(FunctionManager.getInstance().destinyPath.concat("\\Prueba1")), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else{
            //System.out.println("El receiver no está autorizado para acceder a este recurso");
        }
        /*if(waitReceive)
            allowReceive = false;*/ //para esperar comando de receive()
    }
    
    @Override
    public synchronized void receiveMessage(){
        notify();
    }

    @Override
    public SynchronizationType getSynchronizationType() {
        return synchronizationType;
    }

    public boolean isAllowReceive() {
        return allowReceive;
    }

    @Override
    public void setAllowReceive(boolean allowReceive) {
        this.allowReceive = allowReceive;
    }

    public boolean isWaitReceive() {
        return waitReceive;
    }

    @Override
    public void setWaitReceive(boolean waitReceive) {
        this.waitReceive = waitReceive;
    }

    @Override
    public void setProducer(IProducer producer) {
        this.producer = producer;
    }

    @Override
    public IProducer getProducer() {
        return producer;
    }

    @Override
    public boolean equals(Object obj) {
        Receiver r = (Receiver)obj;
        if(processId == r.processId)
            return true;
        return false;
    }
    
    
    
    
}
