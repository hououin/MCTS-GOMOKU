import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
public class Gomoku {
	public static int W = 19;
	public static int L = 19;
    public static int lastI = 0;
    public static int lastJ = 0;
	public static byte[][] board =  new byte[L][W];    // Nasprotnik:20 ; Agent: 30;  Mozna pozicija: 40 (zacasna oznaka)

	                                                       

	// public static byte[][] board = new byte[L][W];
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
        System.out.println("LGR");
		try {
		PrintWriter w = new PrintWriter("testC.txt");	
			
		MCTTree mct = new MCTTree(board);		
		Node nextStep;
		int NameCounter = 1;
		int freePos = NumFreePosition(board);
		long startTime,endTime;
		while ((mct.ifWin(board, 30) == -1) && mct.ifWin(board, 20) == -1) { 
			
			mct = new MCTTree(board);
			
			play(board);
			System.out.println(lastI+" "+lastJ);
			freePos = NumFreePosition(board);
			mct.freePos = freePos;
			printBoard(board);
			
			System.out.println("mct play");	
			startTime = System.nanoTime();
			nextStep = mct.search(board, NameCounter,lastI,lastJ);
			endTime = System.nanoTime();
			w.println("time "+(endTime-startTime));
			
			NameCounter++;
			copyBoard(nextStep.state, board);
			printBoard(board);
            
			freePos--;
			if (freePos == 0) {
				System.out.println("TIE");
				break;
			}
          
		}
		sc.close();
		w.close();
		}catch(FileNotFoundException e) {
			
		}
		/*int counter = 10;
        while(counter > 0) {
        	play(board);
        	printBoard(board);
        	counter--;
        }*/
		
	}

	public static void copyBoard(byte[][] src, byte[][] dest) {
		for (int i = 0; i < L; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, W);
		}
	}

	public static int NumFreePosition(byte[][] board) {
		int freeNum = 0;
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < W; j++) {
				if (board[i][j] != 20 && board[i][j] != 30) {
					freeNum++;
				}
			}
		}
		return freeNum;
	}

	public static void play(byte[][] board) {
		boolean waitToPlay = true;

		while (waitToPlay) {
			try {
				System.out.println("Izberi pozicijo (napisi A do S za vrstico, 1 do 19 pa za stolpca):");
				String inputPos = sc.nextLine();
				System.out.println(inputPos);
				char row = inputPos.charAt(0);
				int column;
				if(inputPos.length() == 3) {
					int tens = inputPos.charAt(1) - 48;
					int units = inputPos.charAt(2) - 48;
					column = tens * 10 + units;
				}else {
				    column = inputPos.charAt(1) - 48;
				}
				
				// System.out.println(row+" "+column);
				int pRow = row - 'A';
				int pColumn = column - 1; // board se zacne z 1, matrika pa 0;
				// System.out.println(pRow+" "+pColumn);
				if (pColumn >= 0 && pColumn < W && pRow >= 0 && pRow < L) {
					if (board[pRow][pColumn] != 30 && board[pRow][pColumn] != 20) {
						board[pRow][pColumn] = 20;
						lastI = pRow;
						lastJ = pColumn;
						waitToPlay = false;
					} else {
						System.out.println("zasedena pozicija");

					}
				} else {
					System.out.println("ne veljavna pozicija");
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("ne veljaven input");
			}
		}

	}

	public static void printBoard(byte[][] board) {
		
		int a;		
		System.out.print("|");
		for(int i = 0; i < W; i++) {			
			if(i < 10)
				System.out.print(" " + (i+1) + " " + "|");
			if(i >= 10)
				System.out.print("" + (i+1) + " " + "|");
		}
		System.out.println();
		for (int i = 0; i < L; i++) {
			 a = 65 + i;
			//System.out.println("------------------------------");
			System.out.println("------------------------------------------------------------------------------");
			System.out.print("|");
			for (int j = 0; j < W; j++) {
               
				switch (board[i][j]) {
				case 0:
					System.out.print(" " + " " + " " + "|");
					break;
				case 20:
					System.out.print(" " + "O" + " " + "|");
					break;
				case 30:
					System.out.print(" " + "x" + " " + "|");
					break;
				case 40:
					System.out.print(" " + "e" + " " + "|");
					break;
				case 50:
					System.out.print(" " + " " + " " + "|");
					break;
				default:
					System.out.print("  " + board[i][j] + "  " + "|");
				}
                if(j == W-1) {
                	System.out.print(" "+(char)a);
                }
				// System.out.print(" " + board[i][j] + " " + "|");
			}
			System.out.println();
		}
		//System.out.println("------------------------------");
		 System.out.println("------------------------------------------------------------------------------");
	}

}