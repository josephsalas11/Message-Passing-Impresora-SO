
package Controller;
import Model.Mailbox;
import Model.Message;
import Model.MessageType;
import Model.Producer;
import Model.QueueType;
import Model.Receiver;
import Model.SynchronizationType;
import Model.Process;
import static java.lang.Thread.sleep;
import java.util.*; 
import Model.IProducer;
import java.io.File;



public class FunctionManager {

    private Hashtable<String, Process> processList;
    private Hashtable<Integer, Mailbox> mailboxList;
    private static FunctionManager singleton = null;

    public String destinyPath;
    
    private int processCounter = 1;
    private int mailboxCounter = 1;
    
    private FunctionManager() {
        processList = new Hashtable<String, Process>();
        mailboxList = new Hashtable<Integer, Mailbox>();
        String filePath = new File("").getAbsolutePath();
        filePath =  filePath.concat("\\Impresos");
        destinyPath = filePath;

    }
    
    public static FunctionManager getInstance()
    {
        if(singleton == null)
        {
            singleton = new FunctionManager();
        }
        return singleton;
    }
    
    public void createDirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, String name, boolean printer)
    {
        if(!isProcessNameTaken(name))
            processList.put(name, new Process(processCounter, STP, queueType, queueSizeType,STR, name, printer));
    }
    
    public Process createStaticProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, String name, boolean printer)
    {
        Process process = null;
        if(!isProcessNameTaken(name)){
            createMailbox(mailboxCounter,queueSizeType,queueType, false);
            mailboxCounter++;
            process = new Process(processCounter, STP, queueType, queueSizeType,STR, name, printer);
            processList.put(name, process);
            addReceiverMailbox(mailboxList.size(),name);
        }
        return process;
    }
    
    public Process createDynamicProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, String name, boolean printer)
    {
        Process process = null;
        if(!isProcessNameTaken(name)){
            process = new Process(processCounter, STP, queueType, queueSizeType,STR, name, printer);
            processList.put(name, process);
        }
        return process;
    }
    
    private boolean isProcessNameTaken(String name){
        if(processList.get(name) != null)
            return true;
        return false;
    }
    
    //Ya no se usa
    public void resetSystem()
    {
        for (int i = 1; i <= processList.size(); i++) {
            processList.get(i).stopProcess();    
        }
        processList.clear();
        mailboxList.clear();
    }
    
    public boolean sendDirectProcess(String sourceProcessName, String destinationProcessName, MessageType messageType, int messageLength, String messageContent, int priority)
    { 
        Process source = processList.get(sourceProcessName);
        Process destination = processList.get(destinationProcessName);
        
        //validacion para determinar que el producer y el receiver existan en el controlador
        if(source == null || destination == null){
            System.out.println("Operacion invalida"); //hacer log de error
            return false;
        }
        
        else{
            Message message = null;
        
            if(priority == 0)
                message = source.createMessage(destination, messageContent, messageType, messageLength, processList.get(sourceProcessName), false);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority, processList.get(sourceProcessName), false);

            source.send(destination, message);
            return true;
        }
    }
    
    public boolean sendIndirectProcess(String sourceProcessName, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority, String destinationProcessName) throws InterruptedException
    {        
        Process source = processList.get(sourceProcessName);
        Process destination = processList.get(destinationProcessName);
        Mailbox mailbox = mailboxList.get(idDestinationProcess);
        
        //validacion para determinar que el producer, el receiver y el mailbox existan en el controlador
        if(source == null || mailbox == null){
            System.out.println("Operacion invalida" +sourceProcessName+" "+idDestinationProcess); //hacer log de error
            return false;
        }
        
        else{
            Message message = null;
        
            if(priority == 0)
                message = source.createMessage(destination, messageContent, messageType, messageLength, processList.get(sourceProcessName), true);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority, processList.get(sourceProcessName), true);

            source.sendMailbox(mailbox, message);
            return true;
        } 
    }
    
    public void receiveMessage(String sourceProcessName, String destinationProcessName)
    {
        Process source = processList.get(sourceProcessName);
        Process destination = processList.get(destinationProcessName);
        destination.receive(source);
    }
    
    public void receiveImplicitMessage(String destinationProcessName)
    {
        Process destination = processList.get(destinationProcessName);
        destination.receive();
    }
    
    public void receiveIndirectMessage(int mailboxId, String destinacionProcessName)
    {
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process destination = processList.get(destinacionProcessName);
        destination.receiveMailbox(mailbox);
    }
    
    public void createMailbox(int ID, int queueSize, QueueType queueType, boolean dynamic)
    {
        mailboxList.put(ID, new Mailbox(ID,queueSize, queueType, dynamic));
    }  
    
    public void addReceiverMailbox(int mailboxId, String receiverName)
    {
        
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process receiver = processList.get(receiverName);
        mailbox.addReceiver(receiver);
        
        //funcion auxiliar para que funcione impresora
        receiver.setMailbox(mailbox);
        
    }
    
    public void addProducerMailbox(int mailboxId, String producerName)
    {   
        //Añadir excepción si no encuentra el producer o sino encuentra el mailbox
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process producer = processList.get(producerName);
        mailbox.addProducer(producer);
        //LOG
    }
    
    public Process getProcess(String name)
    {
        Process displayProcess = processList.get(name);
        return displayProcess;
    }
    
    public Hashtable<String, Process> getProcessList() {
        return processList;
    }

    public Hashtable<Integer, Mailbox> getMailboxList() {
        return mailboxList;
    }
    
    public Process getProcessPrinter(String name){
        Process process = processList.get(name);
        if(process != null && process.isPrinter())
            return process;
        return null;
    }
    
    public ArrayList<String> getPrinterKeys(){
        ArrayList<String> printers = new ArrayList<>();
        for(String i:processList.keySet()){
            if(processList.get(i).isPrinter())
                printers.add(i);
        }
        return printers;
    }
    
    public Process disposeProcess(String processName){
        Process process = processList.remove(processName);
        process.stopProcess();
        System.out.println("Se ha eliminado el proceso");
        return process;
    }
    
    public Mailbox disposeMailbox(int mailboxId){
        System.out.println("Se ha eliminado el mailbox");
        return mailboxList.remove(mailboxId);
    }

    public int getProcessCounter() {
        return processCounter;
    }

    public int getMailboxCounter() {
        return mailboxCounter;
    }

    public void setProcessCounter(int processCounter) {
        this.processCounter = processCounter;
    }

    public void setMailboxCounter(int mailboxCounter) {
        this.mailboxCounter = mailboxCounter;
    }
    
    
    
}
