
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

    Hashtable<Integer, Process> processList;
    Hashtable<Integer, Mailbox> mailboxList;
    static FunctionManager singleton = null;
    public boolean indirect = true;
    public String destinyPath ;
    
    private FunctionManager() {
        processList = new Hashtable<Integer, Process>();
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
    
    public void createExplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, int ID_SP)
    {
        System.out.println(ID_SP);
        Process SP = processList.get(1);
        processList.put(processCounter, new Process(processCounter, STP, queueType, queueSizeType, STR, SP));

    }
    
    public Model.Process createImplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR)
    {
        Process psTemp = new Process(processCounter, STP,queueType, queueSizeType,STR);
         processList.put(processCounter, psTemp);
         return psTemp;
    }
    
    public void createIndirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, int ID_MB)
    {
        Mailbox mailbox = mailboxList.get(ID_MB);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR, mailbox));
    }
    
    public void sendDirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority)
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        Message message = null;
        
        if(priority != -1)
            message = source.createMessage(destination, messageContent, messageType, messageLength);
        else
            message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);
        
        source.send(destination, message);
        
    }
    
public void sendIndirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority) throws InterruptedException
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        Mailbox mailbox = mailboxList.get(idDestinationProcess);

        Message message = null;
        
        if(priority == -1)
            message = source.createMessage(destination, messageContent, messageType, messageLength);
        else
            message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);
        
        source.sendMailbox(mailbox, message);
        
    }
    
    public void receiveMessage(int idSourceProcess, int idDestinationProcess)
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        destination.receive(source);
    }
    
    public void createMailbox(int ID, int queueSize, QueueType queueType)
    {
        mailboxList.put(ID, new Mailbox(ID,queueSize, queueType));
    } 
    
    public void addReceiverMailbox(int mailboxId,int receiverId)
    {
        
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process receiver = processList.get(receiverId);
        mailbox.addReceiver(receiver);
    }
    
    public Process display(int processID)
    {
        Process displayProcess = processList.get(processID);
        return displayProcess;
    }
    
    public Hashtable<Integer, Process> getProcessList() {
        return processList;
    }

    public void setProcessList(Hashtable<Integer, Process> processList) {
        this.processList = processList;
    }

    public Hashtable<Integer, Mailbox> getMailboxList() {
        return mailboxList;
    }
    
    public void deleteProcess(int idProcess){
        processList.remove(idProcess);
        System.out.println("Proceso "+idProcess+" eliminado exitosamente");
    }
    
}
