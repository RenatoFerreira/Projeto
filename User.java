import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class User extends JFrame {

	private static Servidor sv;
	private static Client cl;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtEnviado;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		sv = new Servidor();
		cl = new Client("localhost",3000);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User frame = new User();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 */
	public User() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser ch = new JFileChooser();
				int ret = ch.showOpenDialog(null);
				if(ret == JFileChooser.APPROVE_OPTION) {
					textField.setText(ch.getSelectedFile().getPath());
				}
			}
		});
		btnUp.setBounds(97, 10, 45, 23);
		contentPane.add(btnUp);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.setPath("C:/Users/Renato/Desktop/");//altere aqui pra uma pasta de destino
				sv.setPath(textField.getText());
				System.out.println(textField.getText());
				try {
					new Thread(sv.play).start();
				    new Thread(cl.recebe).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setBounds(20, 51, 89, 23);
		contentPane.add(btnSend);
		
		txtEnviado = new JTextField();
		txtEnviado.setText("enviado: " + sv.Transfer + "%");
		txtEnviado.setEditable(false);
		txtEnviado.setBounds(248, 23, 86, 20);
		contentPane.add(txtEnviado);
		txtEnviado.setColumns(10);
	}
}
