package master_guinea_pigs;

import java.util.Random;

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
    private String mineArr[][]          = null;
    
    static int pigArr_x[] = new int[5];
    static int pigArr_y[] = new int[5];
    
    static int key_x;
	static int key_y;
    static int cnt=0;
    

    public static void main(String[] args) {

    	Server server = new Server();

    	server.setInit();

    	server.setMine(pig_CNT);
        
    	server.setKey(key);

    	server.printMine();
        
    	for (int i = 0; i < cnt; i++) {
    		System.out.println(pigArr_x[i]*10 + pigArr_y[i]);
    	}
    	
        System.out.println(key_x + " "+ key_y);
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
    private boolean isExistMine(int row, int col){
        // ArrayIndexOutOfBoundsException ����
        if(row < 0 || row >= ROW || col < 0 || col >= COL){
            return false;
        }

        return mineArr[row][col].equals(MINE);
    }

    // �ش� �迭 ���� �ڱ� �ڽ��� ������ 8ĭ���� ���ڸ� ã�� �� ī���� �Ѵ�.
    private int getMineNumber(int row, int col){
        int mineCnt = 0;
        if(isExistMine(row-1, col-1))mineCnt++;
        if(isExistMine(row-1, col))mineCnt++;
        if(isExistMine(row-1, col+1))mineCnt++;
        if(isExistMine(row, col-1))mineCnt++;
        if(isExistMine(row, col+1))mineCnt++;
        if(isExistMine(row+1, col-1))mineCnt++;
        if(isExistMine(row+1, col))mineCnt++;
        if(isExistMine(row+1, col+1))mineCnt++;

        return mineCnt;
    }

    // ���� ��ó�� ���� ���� ���� ����
    private void setNumber(int row, int col){
        if(mineArr[row][col].equals(NONE) && getMineNumber(row,col)!=0){
            mineArr[row][col] = " "+getMineNumber(row,col)+" ";
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
}


