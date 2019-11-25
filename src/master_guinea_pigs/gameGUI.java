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
import java.lang.Object;

public class gameGUI implements Runnable {
	static JButton[] button = new JButton[100];
	static int index = 0;
	static JFrame frame = new JFrame("기니피그를 잡아라!");

	int key;
	private static int[] mtr_one = new int[] { 13, 65, 93, 54, 81 };
	private static int[] mtr_two, mtr_three, mtr_four, mtr_five;
	private static int[] pig = new int[] { 12, 64, 92, 53, 91 };

	ImageIcon room = new ImageIcon("ground2.png");
	Image newImage = room.getImage();
	Image changedImage = newImage.getScaledInstance(1024, 768, Image.SCALE_SMOOTH);
	ImageIcon newRoom = new ImageIcon(changedImage);
	JLayeredPane jlp = frame.getLayeredPane();

	@SuppressWarnings("serial")
	/*
	 * JPanel value panel set background room image for RoomGUI class
	 */
	JPanel panel = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(newRoom.getImage(), 0, 0, null);
		}
	};

	/*
	 * method runGUI gets imageicon value of object image
	 */
	public void runGUI() {
		frame.setBounds(0, 0, 1042, 816);
		frame.getContentPane().add(panel);
		ImageIcon grass = new ImageIcon("tileGrass2.png");
		
		ImageIcon one = new ImageIcon("1.png");
		ImageIcon two = new ImageIcon("2.png");
		ImageIcon three = new ImageIcon("3.png");
		ImageIcon four = new ImageIcon("4.png");
		ImageIcon five = new ImageIcon("5.png");
		ImageIcon sadpig = new ImageIcon("sadpig.png");
		ImageIcon jail = new ImageIcon("jail.png");
		
		/* Prison installation */
		JLabel jail_ = new JLabel(jail);
		jail_.setBounds(826, 178, 181, 555);
		panel.add(jail_);
		jlp.add(jail_,1);

		/* Get map information from the server and sprinkle it-> Guinea Pig */
		for (int i = 0; i < pig.length; i++) {
			int x_ = pig[i] % 10;
			int y_ = pig[i] / 10;
			JLabel comp = new JLabel(sadpig);
			comp.setBounds(x_*64+64, y_*64+64, 64, 64);
			panel.add(comp);
			jlp.add(comp,1);
		}
		
		/* Get map information from the server and sprinkle it-> Guinea Pig */
		for (int i = 0; i < mtr_one.length; i++) {
			int x_ = mtr_one[i] % 10;
			int y_ = mtr_one[i] / 10;
			JLabel comp = new JLabel(one);
			comp.setBounds(x_*64+64, y_*64+64, 64, 64);
			panel.add(comp);
			jlp.add(comp,1);
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

	public void run() {
		this.runGUI();
	}

	private class TheHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < 100; i++) {
				if (event.getSource() == button[i]) {
					System.out.println("object_clicked" + i);
					button[i].setVisible(false); // Get rid of the button
				}
			}
		}
	}

	public static void main(String[] args) {
		gameGUI client = new gameGUI();
		client.run();
	}
}
