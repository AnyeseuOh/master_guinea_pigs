package master_guinea_pigs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import java.lang.Object;
import java.lang.reflect.Array;

public class gameGUI implements Runnable {
	static JButton[] button = new JButton[100];
	static int index = 0;
	static JFrame frame = new JFrame("기니피그를 잡아라!");
	static String objInfo;

	private static int[] mtr_one, mtr_two, mtr_three, mtr_four, mtr_five, pig;
	int pig_x_me = 810;
	int pig_y_me = 625;//

	ImageIcon room = new ImageIcon("ground2.png");
	Image newImage = room.getImage();
	Image changedImage = newImage.getScaledInstance(1024 + 64, 768, Image.SCALE_SMOOTH);
	ImageIcon newRoom = new ImageIcon(changedImage);
	JLayeredPane jlp = gameGUI.frame.getLayeredPane();

	/* Prison installation */
	ImageIcon jail = new ImageIcon("jail.png");
	JLabel jail_ = new JLabel(jail);

	@SuppressWarnings("serial")
	/*
	 * JPanel value panel set background room image for RoomGUI class
	 */
	JPanel panel = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newRoom.getImage(), 0, 0, null);
		}
	};

	public gameGUI(String line) {
		objInfo = line;
	}

	/*
	 * method runGUI gets imageicon value of object image
	 */
	public void runGUI(int[] _pig, int[] _1, int[] _2, int[] _3, int[] _4, int[] _5) {
		pig = _pig;
		mtr_one = _1;
		mtr_two = _2;
		mtr_three = _3;
		mtr_four = _4;
		mtr_five = _5;
		
		System.out.println(pig);
		System.out.println(mtr_one);
		System.out.println(mtr_two);
		

		frame.setBounds(0, 0, 1042 + 64 + 320, 816);// frame -> chat
		frame.getContentPane().add(panel);
		ImageIcon grass = new ImageIcon("tileGrass2.png");

		ImageIcon one = new ImageIcon("1.png");
		ImageIcon two = new ImageIcon("2.png");
		ImageIcon three = new ImageIcon("3.png");
		ImageIcon four = new ImageIcon("4.png");
		ImageIcon five = new ImageIcon("5.png");
		ImageIcon sadpig = new ImageIcon("sadpig.png");

		if (pig.length != 0) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < pig.length; i++) {
				int x_ = pig[i] % 10;
				int y_ = pig[i] / 10;
				JLabel comp = new JLabel(sadpig);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		if (mtr_one != null) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < mtr_one.length; i++) {
				int x_ = mtr_one[i] % 10;
				int y_ = mtr_one[i] / 10;
				JLabel comp = new JLabel(one);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		if (mtr_two != null) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < mtr_two.length; i++) {
				int x_ = mtr_two[i] % 10;
				int y_ = mtr_two[i] / 10;
				JLabel comp = new JLabel(two);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		if (mtr_three != null) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < mtr_three.length; i++) {
				int x_ = mtr_three[i] % 10;
				int y_ = mtr_three[i] / 10;
				JLabel comp = new JLabel(three);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		if (mtr_four != null ) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < mtr_four.length; i++) {
				int x_ = mtr_four[i] % 10;
				int y_ = mtr_four[i] / 10;
				JLabel comp = new JLabel(four);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		if (mtr_five != null) {
			/* Get map information from the server and sprinkle it-> Guinea Pig */
			for (int i = 0; i < mtr_five.length; i++) {
				int x_ = mtr_five[i] % 10;
				int y_ = mtr_five[i] / 10;
				JLabel comp = new JLabel(five);
				comp.setBounds(x_ * 64 + 64, y_ * 64 + 64, 64, 64);
				panel.add(comp);
				jlp.add(comp, 1);
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.setObject(grass, j * 64 + 64, i * 64 + 64);
			}
		}

	}

	/*
	 * method setObject initalize object's location
	 */
	public void setObject(ImageIcon object, int x, int y) {

		panel.setLayout(null);
		button[index] = new JButton(object); // initialize button array

		Dimension size = button[0].getPreferredSize();
		button[index].setBackground(Color.red);
		button[index].setBorderPainted(false);
		button[index].setFocusPainted(false);
		button[index].setContentAreaFilled(false);

		jlp.add(button[index], 0);
		button[index].setBounds(x, y, 64, 64);
		panel.setVisible(true);
		frame.setVisible(true);

		button[index].addActionListener(new TheHandler());
		index++;
	}

	@SuppressWarnings("null")
	public void run() {
		int[] key = null;
		int[] temp = null, temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
		String[] ob = null;

		System.out.println(objInfo);
		objInfo = objInfo.substring(9);

		int k = 0;
		StringTokenizer stt = new StringTokenizer(objInfo, "#");

		while (stt.hasMoreTokens()) {
			int j = 0;
			ob = new String[stt.countTokens()];
			ob[j] = stt.nextToken();
			j++;
		}

		for (String n : ob) {
			if (!n.isEmpty() && k == 0) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp = new int[st.countTokens()];
					temp[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 1) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					key = new int[st.countTokens()];
					key[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 2) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp1 = new int[st.countTokens()];
					temp1[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 3) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp2 = new int[st.countTokens()];
					temp2[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 4) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp3 = new int[st.countTokens()];
					temp3[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 5) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp4 = new int[st.countTokens()];
					temp4[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			} else if (!n.isEmpty() && k == 6) {
				StringTokenizer st = new StringTokenizer(n, " ");
				while (st.hasMoreTokens()) {
					int j = 0;
					temp5 = new int[st.countTokens()];
					temp5[j] = Integer.parseInt(st.nextToken());
					j++;
				}
			}
			k++;
		}

		this.runGUI(temp, temp1, temp2, temp3, temp4, temp5);
	}

	private class TheHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < 100; i++) {
				if (event.getSource() == button[i]) {
					System.out.println("object_clicked" + i);
					button[i].setVisible(false); // Get rid of the button
					int j = i;
					if (IntStream.of(pig).anyMatch(x -> x == j)) {
						System.out.println("기니잡았다");
						Random random = new Random();
						int n = random.nextInt(5);
						switch (n) {
						case 0:
							ImageIcon nn = new ImageIcon("pig0.png");
							JLabel comp = new JLabel(nn);
							comp.setBounds(pig_x_me, pig_y_me, 70, 100);
							panel.add(comp);
							jlp.add(comp, 1);
							pig_x_me = pig_x_me + 50;
							break;
						case 1:
							ImageIcon nnn = new ImageIcon("pig1.png");
							JLabel compn = new JLabel(nnn);
							compn.setBounds(pig_x_me, pig_y_me, 70, 100);
							panel.add(compn);
							jlp.add(compn, 1);
							pig_x_me = pig_x_me + 50;
							break;
						case 2:
							ImageIcon nnnn = new ImageIcon("pig2.png");
							JLabel compnn = new JLabel(nnnn);
							compnn.setBounds(pig_x_me, pig_y_me, 70, 100);
							panel.add(compnn);
							jlp.add(compnn, 1);
							pig_x_me = pig_x_me + 50;
							break;
						case 3:
							ImageIcon nnnnn = new ImageIcon("pig3.png");
							JLabel compnnn = new JLabel(nnnnn);
							compnnn.setBounds(pig_x_me, pig_y_me, 70, 100);
							panel.add(compnnn);
							jlp.add(compnnn, 1);
							pig_x_me = pig_x_me + 50;
							break;
						case 4:
							ImageIcon nnnnnn = new ImageIcon("pig4.png");
							JLabel compnnnn = new JLabel(nnnnnn);
							compnnnn.setBounds(pig_x_me, pig_y_me, 70, 100);
							panel.add(compnnnn);
							jlp.add(compnnnn, 1);
							pig_x_me = pig_x_me + 50;
							break;
						}
						jail_.setBounds(820, 178, 250, 555);
						panel.add(jail_);
						jlp.add(jail_, 1);
					}
					// Send_socket.out.println("object_clicked" + i);// send to server
				}
			}
		}
	}
}
