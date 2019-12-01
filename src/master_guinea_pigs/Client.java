package master_guinea_pigs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.lang.Object;

import javax.swing.*;

import master_guinea_pigs.gameGUI;

import java.util.*;

public class Client implements Runnable {
	static BufferedReader in;
	static PrintWriter out;
	
	JPanel panel = new JPanel();
	
	JTextField textField = new JTextField(30);
	JTextArea messageArea = new JTextArea(4, 30);
	JTextArea showInfo = new JTextArea(); //user state

	static JFrame frame = new JFrame("Master geinea pigs");
	ImageIcon flat = new ImageIcon("flat.png");
	Image newflat = flat.getImage();
	Image changedflat = newflat.getScaledInstance(1090, 816, Image.SCALE_SMOOTH);
	ImageIcon newFlat = new ImageIcon(changedflat);

	JPanel panel_flat = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newFlat.getImage(), 18, 18,null);}};

	public Client() {
		
		Client.frame.setTitle("[Master geinea pigs] - Wating Room");
		Client.frame.getContentPane().setLayout(null);
		
		messageArea.setEditable(false);
		textField.setEditable(false);
		showInfo.setEditable(false);
		
		Client.frame.setBounds(0, 0, 1090, 816);
		Client.frame.getContentPane().add(panel_flat);
		
		textField.setBounds(14, 710, 600, 29);
		Client.frame.getContentPane().add(textField, "South");
		//textField.setColumns(10);
		
		messageArea.setBounds(14, 15, 600, 683);
		Client.frame.getContentPane().add(messageArea);
		//gameGUI.frame.getContentPane().add(new JScrollPane(messageArea), "East");
		
		showInfo.setText("[ User state ]");
		showInfo.setBounds(630, 15, 430, 719); // 가로시작점, 세로시작점, 가로길이, 세로길이
		Client.frame.getContentPane().add(showInfo);
		
		Client.frame.setVisible(true);
		textField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText("");  //user chatting input
			}
		});
	}

	/*
	 * get server's address
	 */
	private String getServerAddress() {
		return JOptionPane.showInputDialog(frame, "Input IP address:", "Master geinea pigs", JOptionPane.PLAIN_MESSAGE);
	}

	/* get users nickname */
	private String getsName() {
		return JOptionPane.showInputDialog(frame, "Enter your name:", "Master geinea pigs",
				JOptionPane.PLAIN_MESSAGE);
	}

	/*
	 * runChat method send and gets reply with server with various protocol
	 */
	void runChat(String[] players, int page) throws IOException {
		// Make connection and initialize streams

		String serverAddress = new String(getServerAddress());
		Socket socket = new Socket(serverAddress, 9998);
		boolean is_kicked = false;

		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		out = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			String line = in.readLine();
			if (line.startsWith("SUBMITNAME")) { // get user's nickname
				out.println(getsName());
				//out.flush();
				
				//String path = Server.class.getResource("").getPath();
		        //File file = new File("serverinfo.txt");
		        //FileWriter fw = new FileWriter(file, true);

		        //fw.write(getsName() + " 0 0"+ "\n");
		        //fw.flush();		        
			} else if (line.startsWith("NAMEACCEPTED")) { // if server accept the username
				textField.setEditable(true);
				out.println("GAMESTART");//�엫�떆
			} else if (line.startsWith("MESSAGE")) { // if client get message protocol the message is for chatting
				messageArea.append(line.substring(8) + "\n");
			} else if (line.startsWith("ERROR")) {
				JOptionPane.showMessageDialog(null, line.substring(6));
			} else if (line.startsWith("GAMESTART")) {
				panel_flat.setVisible(false);
				Client.frame.setVisible(false);
				showInfo.setVisible(false);
				Thread t2 = new Thread(new personalChat());
				t2.start();
				Thread t1 = new Thread(new gameGUI(line));
				t1.start();
			} else if (line.startsWith("ENDMESSAGE")) {
				// if (line.substring(8).equals("game start"))
				messageArea.append(line.substring(11) + "\n");
				textField.setVisible(false);
				socket.close();
			}
		}
	}

	public void run() {
		try {
			this.runChat(null, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
