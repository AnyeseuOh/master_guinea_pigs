package master_guinea_pigs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.*;

public class Client implements Runnable {
	static BufferedReader in;
	static PrintWriter out;
	JPanel panel = new JPanel();
	JTextField textField = new JTextField(30);
	JTextArea messageArea = new JTextArea(4, 30);

	static JFrame frame = new JFrame("Master geinea pigs");
	ImageIcon flat = new ImageIcon("flat.png");
	Image newflat = flat.getImage();
	Image changedflat = newflat.getScaledInstance(1306, 816, Image.SCALE_SMOOTH);
	ImageIcon newFlat = new ImageIcon(changedflat);

	JPanel panel_flat = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newFlat.getImage(), 18, 18,null);}};

	public Client() {
		messageArea.setEditable(false);
		textField.setEditable(false);
		gameGUI.frame.setBounds(0, 0, 1306, 816);
		gameGUI.frame.getContentPane().add(panel_flat);
		gameGUI.frame.getContentPane().add(textField, "South");
		gameGUI.frame.getContentPane().add(new JScrollPane(messageArea), "East");
		gameGUI.frame.setVisible(true);
		textField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText("");
			}
		});
	}

	/*
	 * get server's address
	 */
	private String getServerAddress() {
		return JOptionPane.showInputDialog(frame, "서버의 IP주소를 입력해주세요:", "Master geinea pigs", JOptionPane.PLAIN_MESSAGE);
	}

	/* get users nickname */
	private String getsName() {
		return JOptionPane.showInputDialog(frame, "게임에서 사용할 닉네임을 입력해주세요:", "Master geinea pigs",
				JOptionPane.PLAIN_MESSAGE);
	}

	/*
	 * runChat method send and gets reply with server with various protocol
	 */
	void runChat(String[] players, int page) throws IOException {
		// Make connection and initialize streams

		String serverAddress = new String(getServerAddress());
		JFrame actionFrame = new JFrame();
		Socket socket = new Socket(serverAddress, 9998);
		boolean is_kicked = false;

		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		out = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			String line = in.readLine();
			if (line.startsWith("SUBMITNAME")) { // get user's nickname
				out.println(getsName());
			} else if (line.startsWith("NAMEACCEPTED")) { // if server accept the username
				textField.setEditable(true);
				out.println("GAMESTART");//임시
			} else if (line.startsWith("MESSAGE")) { // if client get message protocol the message is for chatting
				// if (line.substring(8).equals("game start"))
				messageArea.append(line.substring(8) + "\n");
			} else if (line.startsWith("ERROR")) {
				JOptionPane.showMessageDialog(null, line.substring(6));
			} else if (line.startsWith("GAMESTART")) {
				panel_flat.setVisible(false);
				Client.frame.setVisible(false);
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
