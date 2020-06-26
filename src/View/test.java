package View;

import Controller.FunctionManager;
import Model.Command.SendMessageCommand;
import Model.Mailbox;
import Model.Message;
import Model.MessageType;
import Model.Producer;
import Model.QueueType;
import Model.Receiver;
import Model.SynchronizationType;
import Model.Process;
import static java.lang.Thread.sleep;



/**
 *
 * @author Paulo
 */
public class test{
    public static void main(String [] args) throws InterruptedException
	{
            
            
            Message m1 = new Message(MessageType.FIXED, 2, 1, 10, "Soy 3",3);
            Message m2 = new Message(MessageType.FIXED, 2, 1, 10, "Soy 1",1);
            Message m3 = new Message(MessageType.FIXED, 2, 1, 10, "Soy 2",2);
            
            /*
            Process p1 = new Process(1, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING);
            Process p2 = new Process(2, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING, p1);
            p1.setWaitReceive(false);
            p2.setWaitReceive(false);
            */
            
            Process p1 = new Process(1, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.BLOCKING);
            Mailbox mailbox = new Mailbox(1, 5, QueueType.PRIORITY);
            Process p2 = new Process(2, SynchronizationType.BLOCKING, QueueType.PRIORITY, 5, SynchronizationType.BLOCKING, mailbox);
            Process p3 = new Process(3, SynchronizationType.BLOCKING, QueueType.PRIORITY, 5, SynchronizationType.BLOCKING, mailbox);

            //p1.setWaitReceive(false);
            //p2.setWaitReceive(false);
            //p3.setWaitReceive(false);

            
            mailbox.addReceiver(p2);
            mailbox.addReceiver(p3);

            p1.sendMailbox(mailbox, m1);
            p1.sendMailbox(mailbox, m2);
            p1.sendMailbox(mailbox, m3);
            
            /*
            p1.send(p2, m1);
            p1.send(p2, m2);
            p1.send(p2, m3);*/
            
            System.out.println(mailbox.getMessageQueue().getQueueSize());
            
            p2.receive(p1);
            p3.receive(p1); 
            sleep(3000);
            System.out.println(mailbox.getMessageQueue().getQueueSize());

            p2.receive(p1);
            p3.receive(p1);
            sleep(3000);
            System.out.println(mailbox.getMessageQueue().getQueueSize());

            p2.receive(p1);
            p3.receive(p1);
            sleep(3000);
            System.out.println(mailbox.getMessageQueue().getQueueSize());            

            FunctionManager functionManager = new FunctionManager();
            /*En la impresora debe existir un mailbox, el cual va a recibir todas las peticiones de impresión*/
            functionManager.createMailbox(1, 50, QueueType.FIFO);
            /*La impresora va a tener un proceso blocking, para que imprima uno por uno*/
            functionManager.createIndirectProcess(1, SynchronizationType.BLOCKING, QueueType.FIFO, 10, SynchronizationType.BLOCKING, 1);
            
            /*Por cada instancia de editor, se debe crear un proceso*/
            functionManager.createImplicitProcess(2, SynchronizationType.NONBLOCKING, QueueType.FIFO, 10, SynchronizationType.NONBLOCKING);
            
            /*Cuando se mande a imprimir*/
            //SendMessageCommand sendMessageCommand = new SendMessageCommand(2, 1, "Aquí va el mensaje");
            functionManager.sendIndirectProcess(2, 1, MessageType.FIXED, 100, "Soy el mensaje", -1);
        }
        
}

            //Producer p = new Producer(5, SynchronizationType.NONBLOCKING, QueueType.FIFO);
            //p.getMessageQueue().addMessage(m1);
            //p.getMessageQueue().addMessage(m2);
            //p.getMessageQueue().addMessage(m3);

            //p.start();
            //Receiver r = new Receiver(p, SynchronizationType.NONBLOCKING);
            //r.start();