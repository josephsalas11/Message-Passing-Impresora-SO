/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class LogMessage {
    private int associatedID; //process o mailbox id
    private String detail;
    private LocalDateTime timestamp;
    private boolean isProcess; //si es false es mailbox

    public LogMessage(int associatedID, String detail, LocalDateTime timestamp, boolean isProcess) {
        this.associatedID = associatedID;
        this.detail = detail;
        this.timestamp = timestamp;
        this.isProcess = isProcess;
    }

    public int getAssociatedID() {
        return associatedID;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isIsProcess() {
        return isProcess;
    }

    

    
}
