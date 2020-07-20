/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Paulo
 */
public class Process {
    private int id;
    private String name;
    private boolean printer;
    private Producer producer;
    private IReceiver receiver;

    private Mailbox mailbox;


    //para direccionamiento directo
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver, String name, boolean printer){

        this.id = id;
        this.producer = new Producer(synchronizationTypeProducer, queueType, queueSize);
        this.receiver = new Receiver(null, synchronizationTypeReceiver, queueType, queueSize, id);
        this.name = name;
        this.printer = printer;
        
        //LOG
        String detail = "ID del proceso : "+id + " ~~ "+"Tipo de proceso : "+synchronizationTypeProducer.toString()+"-"+synchronizationTypeReceiver.toString()
                + " ~~ " +" ha sido creado exitosamente";
        Log.getInstance().addLog(id, detail, true);

        producer.start();
        receiver.start();
    }
    
    public Message createMessage(Process destination, String messageContent, MessageType messageType, int messageLength, Process source, boolean isMailbox){
        Message message = null;
        if(destination == null){
            message = new Message(messageType, -1, id, messageLength, messageContent, source, destination, isMailbox);
        }
        else{
            message = new Message(messageType, destination.id, id, messageLength, messageContent, source, destination, isMailbox);
        }
        return message;
    }
    
    public Message createMessagePriority(Process destination, String messageContent, MessageType messageType, int messageLength, int priority, Process source, boolean isMailbox){
        Message message = null;
        if(destination == null){
            message = new Message(messageType, -1, id, messageLength, messageContent, priority, source, destination, isMailbox);
        }
        else{
            message = new Message(messageType, destination.id, id, messageLength, messageContent, priority, source, destination, isMailbox);
        }
        return message;
    }
    
    //directo
    public void send(Process destination, Message message){
        
        if(producer.addMessage(message) == false){
            //System.out.println("No se pudo ingresar el mensaje porque la cola esta llena");
            Log.getInstance().addLog(id, "No se pudo ingresar el mensaje porque la cola esta llena", true);
        }
        //LOG
        Log.getInstance().addLog(id, "Proceso: "+id+" ha enviado el comando para enviar el mensaje '"+message.getContent()+"' al proceso: "+destination.id, true);
    }
    
    //indirecto dinamico
    public void sendMailbox(Mailbox mailbox, Message message) throws InterruptedException{
        if(mailbox.addMessage(message) == false){
            //System.out.println("No se pudo ingresar el mensaje porque la cola esta llena o el proceso no está autorizado en el mailbox");
            Log.getInstance().addLog(id, "No se pudo ingresar el mensaje porque la cola esta llena o el proceso no está autorizado en el mailbox", true);
        }
        else{
            message.getDestiny().getReceiver().setCurrentMailbox(mailbox);
            if(message.getDestinyID() == -1)
                Log.getInstance().addLog(id, "Proceso: "+message.getSourceID()+" ha enviado la señal para enviar el mensaje '"+message.getContent()+"' a través del mailbox: "+mailbox.getId(), true);
            else
                Log.getInstance().addLog(id, "Proceso: "+message.getSourceID()+" ha enviado la señal para enviar el mensaje '"+message.getContent()+"' al proceso: "+message.getDestinyID()+ " a través del mailbox: "+mailbox.getId(), true);
            //mailbox.putMessage();
        }
        
    }
    
    //implicito
    public void receive(){
        receiver.setAllowReceive(true);         
        Log.getInstance().addLog(id, "Proceso: "+id+" ha enviado la señal para recibir un mensaje", true);
    }
    
    //explicito
    public void receive(Process source){
       //recibir mensaje
        receiver.setCurrentId(source.getId());
        receiver.setAllowReceive(true);
        Log.getInstance().addLog(id, "Proceso: "+id+" ha enviado la señal para recibir un mensaje del proceso: "+source.id, true);
    }
    
    //indirecto
    public void receiveMailbox(Mailbox mailbox){
        receiver.setCurrentMailbox(mailbox);
        receiver.setAllowReceive(true);
        Log.getInstance().addLog(id, "Proceso "+id+" ha enviado la señal para recibir un mensaje del mailbox "+mailbox.getId(), true);
    }

    public void stopProcess()
    {
        producer.stop();
        receiver.stop();
    }
    

    public int getId() {
        return id;
    }

    public Producer getProducer() {
        return producer;
    }

    public IReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(IReceiver receiver) {
        this.receiver = receiver;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
    
    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();  
        
        String result = dtf.format(now)+"\n"+
                        "ID del proceso: "+id+"\n"+
                        "Sender:\n"+
                        "Sincronización: "+producer.getSynchronizationType().toString()+"\n"+
                        "Estado: "+producer.stateToString()+"\n"+
                        "Tamaño de la cola: "+producer.getQueueSize()+"\n"+
                        "Mensajes:\n"+
                        producer.getQueueMessages()+"\n\n"+
                        "Receiver:\n"+
                        "Sincronización: "+receiver.getSynchronizationType().toString()+"\n"+
                        "Estado: "+receiver.stateToString()+"\n"+
                        "Tamaño de la cola: "+receiver.getQueueSize()+"\n"+
                        receiver.getQueueMessages()+"\n\n";
        
        return result;
    }

    public String getQueueLog() {
        String result = "";
        result = result.concat("Cola del producer:\n"+producer.getQueueLog()+"\n\n");
        result = result.concat("Cola del receiver:\n"+receiver.getQueueLog()+"\n");
        return result;
    }

    public String getName() {
        return name;
    }

    public boolean isPrinter() {
        return printer;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }
    
    
}
