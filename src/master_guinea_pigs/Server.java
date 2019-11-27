package master_guinea_pigs;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.net.*;

/**
 * Created by developerkhy@gmail.com on 2017. 6. 15.
 * Blog      : http://soulduse.tistory.com
 * Github    : http://github.com/soulduse
 */

public class Server {

    private static final int ROW        = 10;
    private static final int COL        = 10;
    private static final int pig_CNT   	= 5;
    //����Ǳ� ��
    private static final int key 		= 1;
    //���� ��
    
    private static final String MINE    = " * ";
    private static final String KEY 	= " K ";
    private static final String NONE    = " x ";
    private static String mineArr[][]          = null;
    
    static int pigArr_x[] = new int[5];
    static int pigArr_y[] = new int[5];

    static int number1[] = new int[100];
    static int number2[] = new int[100];
    static int number3[] = new int[100];
    static int number4[] = new int[100];
    static int number5[] = new int[100];

    static int key_x;
	static int key_y;
    static int cnt = 0;    
    static int cnt_1 =0, cnt_2 = 0, cnt_3 = 0, cnt_4 = 0, cnt_5 = 0;
    

    public static void main(String[] args) {
    	Server server = new Server();
    	server.setInit();
    	server.setMine(pig_CNT);
    	server.setKey(key);
    	server.printMine();
        
    	for (int i = 0; i < cnt; i++) {
    		System.out.println(pigArr_x[i]*10 + pigArr_y[i]);
    	}
    	int key_value = key_x*10 + key_y;
        System.out.println("\nKEY's position : " + key_value);
        
        System.out.print("\nNUMBER 1 :");
        for (int i = 0; i < cnt_1; i++) {
    		System.out.print(number1[i] + " ");
    	}
    	
        System.out.print("\nNUMBER 2 :");
    	for (int i = 0; i < cnt_2; i++) {
    		System.out.print(number2[i]+ " ");
    	}
    	
        System.out.print("\nNUMBER 3 :");
    	for (int i = 0; i < cnt_3; i++) {
    		System.out.print(number3[i]+ " ");
    	}
    	
        System.out.print("\nNUMBER 4 :");
    	for (int i = 0; i < cnt_4; i++) {
    		System.out.print(number4[i]+ " ");
    	}
    	
        System.out.print("\nNUMBER 5 :");
    	for (int i = 0; i < cnt_5; i++) {
    		System.out.print(number5[i]+ " ");
    	}

    	
    	
    	try {
			ServerSocket listener = new ServerSocket(9998);
			System.out.println("\nServer ���� �� . . .");
			ExecutorService pool = Executors.newFixedThreadPool(20);
			//thread�� 20������ �����ϴ�.
			while (true) {
				Socket sock = listener.accept();
				System.out.println("Server ���� �Ϸ�.");
				try {
					pool.execute(new Server_(sock));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		
		} catch(IOException e) {
			e.printStackTrace();
		}



        
        
    }

    // ������
    public Server(){
        mineArr = new String[ROW][COL];
    }

    // �ʱ�ȭ ������ ����
    private void setInit(){
        for(int i=0; i<ROW; i++){
            for (int j=0; j<COL; j++){
                mineArr[i][j] = NONE;
            }
        }
    }

    // ���� ������ ���� ����
    private void setMine(int mineCnt){
        Random ran = new Random();

        while (mineCnt-- > 0){
            int row = ran.nextInt(ROW);
            int col = ran.nextInt(COL);

            // ������ �迭 �ּҿ� �̹� ���ڰ� �ִ� ���, ������ �ѹ��� ������ ������ġ �����
            if(mineArr[row][col].equals(MINE)){
                mineCnt++;
            }
            // ���� ��� �ִ� ��� ���ڸ� �߰��Ѵ�
            if(mineArr[row][col].equals(NONE)){
                mineArr[row][col] = MINE;
                
                pigArr_x[cnt] = row;
                pigArr_y[cnt] = col;
                cnt++;
                
                //������ ��ġ�� �����Ѵ�
            }
        }
    }
    
 // ���� ������ ���� ����
    private void setKey(int keyCnt){
        Random ran = new Random();

        while (keyCnt-- > 0){
            int row = ran.nextInt(ROW);
            int col = ran.nextInt(COL);

            // ������ �迭 �ּҿ� �̹� ���ڰ� �ִ� ���, ������ �ѹ��� ������ ������ġ �����
            if(mineArr[row][col].equals(MINE)){
            	keyCnt++;
            }
            // ���� ��� �ִ� ��� ���ڸ� �߰��Ѵ�
            if(mineArr[row][col].equals(NONE)){
                mineArr[row][col] = KEY;
                key_x = row;
                key_y = col;
                //key�� ��ġ�� �����Ѵ�
            }
        }
    }
    
    

    // ���� ���翩�� �Ǵ�
    private static boolean isExistMine(int row, int col){
        // ArrayIndexOutOfBoundsException ����
        if(row < 0 || row >= ROW || col < 0 || col >= COL){
            return false;
        }

        return mineArr[row][col].equals(MINE);
    }
    
    private static boolean isExistKey(int row, int col){
        // ArrayIndexOutOfBoundsException ����
        if(row < 0 || row >= ROW || col < 0 || col >= COL){
            return false;
        }

        return mineArr[row][col].equals(KEY);
    }
    
    

    // �ش� �迭 ���� �ڱ� �ڽ��� ������ 8ĭ���� ���ڸ� ã�� �� ī���� �Ѵ�.
    private static int getMineNumber(int row, int col){
        int mineCnt = 0;
        if(isExistMine(row-1, col-1) || isExistKey(row-1, col-1))mineCnt++;
        if(isExistMine(row-1, col) || isExistKey(row-1, col))mineCnt++;
        if(isExistMine(row-1, col+1)|| isExistKey(row-1, col+1))mineCnt++;
        if(isExistMine(row, col-1)|| isExistKey(row, col-1))mineCnt++;
        if(isExistMine(row, col+1)|| isExistKey(row, col+1))mineCnt++;
        if(isExistMine(row+1, col-1)|| isExistKey(row+1, col-1))mineCnt++;
        if(isExistMine(row+1, col)|| isExistKey(row+1, col))mineCnt++;
        if(isExistMine(row+1, col+1)|| isExistKey(row+1, col+1))mineCnt++;

        return mineCnt;
    }

    // ���� ��ó�� ���� ���� ���� ����
    private void setNumber(int row, int col){
        if(mineArr[row][col].equals(NONE) && getMineNumber(row,col)!=0){
            mineArr[row][col] = " "+getMineNumber(row,col)+" ";
        }
        
        if (getMineNumber(row, col) == 1) {
        	number1[cnt_1] = row*10 + col;
        	cnt_1++;
        }
        if (getMineNumber(row, col) == 2) {
        	number2[cnt_2] = row*10 + col;
        	cnt_2++;
        }
        if (getMineNumber(row, col) == 3) {
        	number3[cnt_3] = row*10 + col;
        	cnt_3++;
        }
        if (getMineNumber(row, col) == 4) {
        	number4[cnt_4] = row*10 + col;
        	cnt_4++;
        }
        if (getMineNumber(row, col) == 5) {
        	number5[cnt_5] = row*10 + col;
        	cnt_5++;
        }

        
        
    }

    // ����ã�� �迭 ���
    private void printMine(){
        for(int i=0; i<ROW; i++){
            for (int j=0; j<COL; j++){
                setNumber(i, j);
                System.out.print(mineArr[i][j]);
            }
            System.out.println();
        }
    }
    
    
    private static class Server_ implements Runnable {
		Socket socket = null;
		
		public Server_(Socket socket){
			this.socket = socket;
		}
		
		//@Override
		public void run() {
			ServerSocket listener = null;

		try {

			BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));
			

			
			while (true) {
				String inputMessage = in.readLine();
				String res = "";
				if (inputMessage.equals("GAMESTART")) {
					
					
					res+="GPigs:";
			    	for (int i = 0; i < cnt; i++) {
			    		System.out.println(pigArr_x[i]*10 + pigArr_y[i]);
			    		res += pigArr_x[i]*10 + pigArr_y[i] + " ";
			    	}
			    	int key_value = key_x*10 + key_y;
			        System.out.println("\nKEY: " + key_value);

			        res += "KEY: " + key_value + " ";

			        
			        System.out.print("\nNUMBER1:");
			        res+="NUMBER1:";
			        for (int i = 0; i < cnt_1; i++) {
			    		System.out.print(number1[i] + " ");
			    		res += number1[i] + " ";
			    	}
			    	
			        System.out.print("\nNUMBER2:");
			        res+="NUMBER2:";
			        for (int i = 0; i < cnt_2; i++) {
			    		System.out.print(number2[i] + " ");
			    		res += number2[i] + " ";
			        }
			    	
			        System.out.print("\nNUMBER3:");
			        res+="NUMBER3:";
			        for (int i = 0; i < cnt_3; i++) {
			    		System.out.print(number3[i] + " ");
			    		res += number3[i] + " ";
			    	}

			    	
			        System.out.print("\nNUMBER4:");
			        res+="NUMBER4:";
			        for (int i = 0; i < cnt_4; i++) {
			    		System.out.print(number4[i] + " ");
			    		res += number4[i] + " ";
			    	}

			    	
			        System.out.print("\nNUMBER5:");
			        res+="NUMBER5:";
			        for (int i = 0; i < cnt_5; i++) {
			    		System.out.print(number5[i] + " ");
			    		res += number5[i] + " ";
			    	}
			        out.write(res+"\n");
			        out.flush();
					
					
					
					
				}
				

			}
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(socket != null) socket.close(); // ��ſ� ���� �ݱ�
				if(listener != null) listener.close(); // ���� ���� �ݱ�
			} catch (IOException e) {
			System.out.println("Ŭ���̾�Ʈ�� �ְ� �޴� �� ������ �߻��߽��ϴ�.");
			}
		}
			
			
			
			
		}
	}

    
}

