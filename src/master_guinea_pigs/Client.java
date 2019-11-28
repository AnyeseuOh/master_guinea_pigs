package master_guinea_pigs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	
	static String IPaddress = "localhost";	
	static int portN = 9998;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader in = null;
		PrintWriter out = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			
			socket = new Socket(IPaddress, portN);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
	        out = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				System.out.print("�Է°�:"); // ������Ʈ
				String outputMessage = scanner.nextLine(); // Ű���忡�� ���� �б�
				
				out.write(outputMessage + "\n"); // Ű���忡�� ���� ���� ���ڿ� ����
				out.flush();
				
				String inputMessage = in.readLine(); // �����κ��� ��� ��� ����
				System.out.println(inputMessage);
				
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
		try {
			scanner.close();
			if(socket != null) socket.close(); // Ŭ���̾�Ʈ ���� �ݱ�
		} catch (IOException e) {
			System.out.println("������ �߻��߽��ϴ�.");
		}
		}
		
	}

}
