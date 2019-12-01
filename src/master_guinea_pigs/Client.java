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
	String[] userlist;

	JPanel panel = new JPanel();

	JTextField textField = new JTextField(30);
	JTextArea messageArea = new JTextArea(4, 30);
	JFrame frameWhisper = new JFrame("Select to 대전"); // user state

	static JFrame frame = new JFrame("Master geinea pigs");
	ImageIcon flat = new ImageIcon("flat.png");
	Image newflat = flat.getImage();
	Image changedflat = newflat.getScaledInstance(1090, 816, Image.SCALE_SMOOTH);
	ImageIcon newFlat = new ImageIcon(changedflat);
	Button btnWhisper = new Button("Whisper");

	JPanel panel_flat = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newFlat.getImage(), 18, 18, null);
		}
	};

	public Client() {

		Client.frame.setTitle("[Master geinea pigs] - Wating Room");
		Client.frame.getContentPane().setLayout(null);

		messageArea.setEditable(false);
		textField.setEditable(false);

		Client.frame.setBounds(0, 0, 1090, 816);
		Client.frame.getContentPane().add(panel_flat);

		textField.setBounds(14, 710, 600, 29);
		Client.frame.getContentPane().add(textField, "South");
		// textField.setColumns(10);

		messageArea.setBounds(14, 15, 600, 683);
		Client.frame.getContentPane().add(messageArea);
		// gameGUI.frame.getContentPane().add(new JScrollPane(messageArea), "East");

		btnWhisper.setBounds(630, 15, 430, 100); // 가로시작점, 세로시작점, 가로길이, 세로길이
		Client.frame.getContentPane().add(btnWhisper);
		btnWhisper.addActionListener(new ActionListener() {
			// 익명클래스
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println("USERLIST");// 현재 접속중인 유저 리스트를 서버에 요청하는 프로토콜입니다. GUI에 전체 유저 정보를 띄워 줄 예정입니다.
				frameWhisper.setVisible(true);// 접속 유저 목록 프레임 띄우기
				frameWhisper.setBackground(Color.WHITE);
				frameWhisper.setPreferredSize(new Dimension(45, 270));

				Button[] btn = new Button[500];// 최대 500명 유저

				for (int i = 0; i < userlist.length; i++) {// 모든 유저(자기 자신 포함)의 목록을 버튼으로 나타내기
					btn[i] = new Button(userlist[i]);
					btn[i].setPreferredSize(new Dimension(45, 45));
					btn[i].setMaximumSize(new Dimension(45, 45));
					btn[i].setBounds(0, 45 * (i), 45, 45);
					frameWhisper.getContentPane().add(btn[i], BorderLayout.SOUTH);// 버튼 배치
					btn[i].addActionListener(new ActionListener() {// 각각의 버튼이 눌렸을 때
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String label = e.getSource().toString();// 유저가 누른 버튼에 써져있는 다른 유저 이름 가져오기
							label = label.split("=")[1];
							label = label.replace("]", "");
							System.out.println(label);
							out.println("WHISPER " + label + ":");
						}
					});
				}
				frameWhisper.pack();
			}
		});

		Client.frame.setVisible(true);
		textField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText(""); // user chatting input
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
		return JOptionPane.showInputDialog(frame, "Enter your name:", "Master geinea pigs", JOptionPane.PLAIN_MESSAGE);
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
				// out.flush();

				// String path = Server.class.getResource("").getPath();
				// File file = new File("serverinfo.txt");
				// FileWriter fw = new FileWriter(file, true);

				// fw.write(getsName() + " 0 0"+ "\n");
				// fw.flush();
			} else if (line.startsWith("READY")) {
				out.println("GAMESTART");
			} else if (line.startsWith("NAMEACCEPTED")) { // if server accept the username
				textField.setEditable(true);
			} else if (line.startsWith("MESSAGE")) { // if client get message protocol the message is for chatting
				messageArea.append(line.substring(8) + "\n");
			} else if (line.startsWith("ERROR")) {
				JOptionPane.showMessageDialog(null, line.substring(6));
			} else if (line.startsWith("GAMESTART")) {
				panel_flat.setVisible(false);
				Client.frame.setVisible(false);
				frameWhisper.setVisible(false);
				Thread t2 = new Thread(new personalChat());
				t2.start();
				Thread t1 = new Thread(new gameGUI(line));
				t1.start();
			} else if (line.startsWith("ENDMESSAGE")) {
				// if (line.substring(8).equals("game start"))
				messageArea.append(line.substring(11) + "\n");
				textField.setVisible(false);
				socket.close();
			} else if (line.startsWith("LOCK")) {
				for (int i = 0; i < gameGUI.index; i++) {
					gameGUI.button[i].setEnabled(false);
				}
			} else if (line.startsWith("GO")) {
				for (int i = 0; i < gameGUI.index; i++) {
					gameGUI.button[i].setEnabled(false);
				}
			} else if (line.startsWith("USERLIST")) {// 서버로부터 현재 접속중인 유저들의 전체 리스트를 전달받습니다.
				line = line.replace("USERLIST", "").replace("[", "").replace("]", "");
				userlist = null;
				userlist = line.split(", ");
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
