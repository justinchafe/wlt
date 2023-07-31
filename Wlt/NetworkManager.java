/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Wlt;

import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 *
 * @author jchafe
 */
public class NetworkManager {
    
    private boolean socketCreated;
    String server;
    int port;
    Socket echoSocket;
    BufferedOutputStream out;
    //BufferedReader in;
    
    public NetworkManager() {
        socketCreated = false;
        port = Wlt.PORT;
        server = Wlt.SERVER;
        
    }
    
       
    public NetworkManager(String server, int port) {
        socketCreated = false;
        this.server = server;
        this.port = port;
    }
    
    public void setServer(String server){
        this.server = server;
    }
    
    public void setPort(int port) {
        this.port = port;
    }    
    
    public String getServer() {
        return server;
    }
    
    public int getPort() {
        return port;
    }
    
   
    public boolean createSocket() {
            
            if (socketCreated) {
                closeSocket();
                
            }
            
            try {
                echoSocket = new Socket(server, port);
                out = new BufferedOutputStream(echoSocket.getOutputStream());
                socketCreated = true;
                return true;
     
            }catch (UnknownHostException e) {
                return false;
               
            
            }catch (IOException e) {
                return false;
            }
    }
    
    
    public boolean isSocketCreated() {
        return socketCreated;
    }
    
   
    
    public boolean sendValue(byte[] value) {
        if (!socketCreated) {
            return false;
        }else {
            try {
                out.write(value, 0, value.length);
                out.flush();
                echoSocket.getOutputStream().flush(); //this SHOULD NOT BE NEEDED!!
                return true;
            }catch (IOException e) {
                //DisplayManager.getInstance().showError("Could not send data");
                return false;
            }
        }
    }
  
    public void closeSocket() {
        try {
            out.close();
            echoSocket.close();
        }catch (IOException e) {
           // DisplayManager.getInstance().showError("Could not close I/O");
            System.exit(1);
        }
    }
}
