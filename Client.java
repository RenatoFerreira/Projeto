import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
public class Client extends Thread {
	private Socket socket;
	private DataInputStream dis;
	private String path;
	private OutputStream out;
	private InputStream in;
	private int counter;
	private String fileName;
	public Client(String address, int port) throws IOException {
		this.Connect(address,port);
	}  
	public void Connect(String ip, int port) throws IOException {
		this.socket = new Socket(ip, port);
		System.out.println("Connecting...");
		System.out.println("Connection: " + this.socket);
	}
	public void receiveFile() throws IOException{
		byte[] by  = new byte [1024];
		this.dis=new DataInputStream(this.socket.getInputStream());
		fileName = dis.readUTF();
		in = socket.getInputStream();
		try {
			out = new FileOutputStream(getPath()+fileName);
		}catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }
		int count;
		counter=0;
        while ((count = in.read(by)) > 0) {
            out.write(by, 0, count);
            counter+=count;
            System.out.println("received: " + counter);
        }
        System.out.println("File downloaded");
        this.out.close();
        this.in.close();
        this.socket.close();
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return this.path;
	}
	public Runnable recebe = new Runnable(){
	public void run() {
		try {
			receiveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}};
}
