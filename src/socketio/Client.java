package socketio;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

/**
 *
 * @author Architect
 */
public class Client {
    
    public static void main(String[] args) {
        
        Client client = new Client();
        String hostName = (String)args[0];
        Integer portNumber = Integer.parseInt(args[1]);
        String name = (String)args[2];
        
        try (Socket toServer = new Socket(hostName, portNumber);) {
            
            if (name.trim().isEmpty()) {
                throw new StringFormatException();
            }
            else {
            System.out.println("Sending name " + name + " to server");
            client.sendData(toServer, name);
            System.out.println("Response from Server: " + client.retrieveData(toServer));
        }
            
        }
        
        catch (UnknownHostException uhe) {
            
            System.err.println("Unknown host: " + hostName);
            
        }
        
        catch (IOException ioe) {
            
            System.err.println("Couldn't establish a connection to host " + hostName + " on port " + portNumber);
            
        }
        
        catch (JSONException je) {
            
            System.err.println("Invalid data format.  Please ensure that the data is in the correct format (String) and try again.");
            
        }
        
        catch (StringFormatException sfe) {
            
            System.err.println("Name cannot be null");
            
        }
        
    }
    
    private void sendData(Socket toServer, String name) throws IOException, JSONException {
        
            JSONOutputStream outToServer = new JSONOutputStream(toServer.getOutputStream());
            
            outToServer.writeObject(name);
            
        }
    
    private String retrieveData(Socket toServer) throws IOException, JSONException {
        
     JSONInputStream inFromServer = new JSONInputStream(toServer.getInputStream());
     
     Object in = inFromServer.readObject();
     
     String messageFromServer = (String)in;
     
     return messageFromServer;
        
    }
    
}
