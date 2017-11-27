
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
	private ServerSocket serverSocket;
	private DataOutputStream dos;
	private Socket socket;
	private String path;
	private InputStream in;
	private OutputStream out;
	private int counter;
	private long size;
	public long Transfer;
	private File file;
	public Servidor()throws IOException {
		this.serverSocket = new ServerSocket(3000);
	}
	
	public void setPath(String Path) {
		this.path = Path;
	}
	public String getPath() {
		return this.path;
	}
	public void sendFile()throws IOException{
		//try{
			this.socket = this.serverSocket.accept();
			System.out.println("Accepted connection : " + this.socket);
			file = new File(getPath());
			System.out.println("nome do arquivo: "+file.getName());
			byte[] bytes = new byte[1024];
			this.in = new FileInputStream(file);
			this.dos= new DataOutputStream(socket.getOutputStream());
			this.dos.writeUTF(file.getName());
			this.dos.flush();
			this.out = this.socket.getOutputStream();
			System.out.println("Sending " + getPath());
			int count;
			counter=0;
			while ((count = in.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	            counter+=count;
	            System.out.println("Sended "+((counter)*100)/file.length()+"% bytes of "+file.length());
	            size = file.length();
	            Transfer = ((counter)*100)/size;
			}
			System.out.println("Done");
	        this.out.close();
	        this.in.close();
	        this.socket.close();
	        this.serverSocket.close();
			
	}
	public Runnable play = new Runnable(){
	public void run() {
		try {
			sendFile();
			} catch (IOException e) {

			e.printStackTrace();
		}
		
	}};
}
