import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

class ExpPosition { // izberi pozicijo za expansitions
	byte x;
	byte y;

	public ExpPosition(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
}

public class MCTTree {
	public final int W = 19;
	public final int L = 19;
	Node root;
	public final int simulationNum = 20;
	public int freePos = 0;

	public MCTTree(byte[][] board) {
		root = new Node();
		root.state = board;
	}

	public ExpPosition randomAction1(ArrayList<ExpPosition> exp) {
		ExpPosition e;
		int len = exp.size();  
		int i = (int) ((Math.random() * len) + 0); 
		e = exp.get(i);
		exp.set(i, exp.get(exp.size() - 1));
		exp.remove(exp.size() - 1);
		return e;
	}

	public ArrayList shrinkBoardSize2(byte[][] board, int i, int j) {
		ArrayList<ExpPosition> shrB = new ArrayList<ExpPosition>(L * W);
		ExpPosition e;
		int ixExp = 0;
		int zi = i - 2;
		int zj = j - 2;
		int ki = i + 2;
		int kj = j + 2;
		if (zi < 0) {
			zi = 0;
		}
		if (zj < 0) {
			zj = 0;
		}
		if (ki > L - 1) {
			ki = L - 1;
		}
		if (kj > W - 1) {
			kj = W - 1;
		}
		for (int u = zi; u <= ki; u++) {
			for (int v = zj; v <= kj; v++) {
				if (board[u][v] == 0) {
					board[u][v] = 50;
				}
			}
		}

		for (byte u = 0; u < L; u++) {
			for (byte v = 0; v < W; v++) {
				if (board[u][v] == 50) {
					e = new ExpPosition(u, v);
					shrB.add(e);
				}
			}
		}
		return shrB;
	}

	public ArrayList<ExpPosition> expansion1(byte[][] board, int lastI, int lastJ) {
		ArrayList<ExpPosition> exp = new ArrayList<ExpPosition>(360);
		ArrayList<ExpPosition> shrB = shrinkBoardSize2(board, lastI, lastJ);
		int len = shrB.size();
		/*if (len > 20) {
			len = 20;
		}*/

		for (int i = 0; i < len; i++) {

	    	exp.add(randomAction1(shrB));

		}
		return exp;
	}

	public void copyBoard(byte[][] src, byte[][] dest) {

		for (int i = 0; i < L; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, W);
		}

	}

	public void test(int lastI,int lastJ) {
		
	}

	public double simulation1(byte[][] board, int lastI, int lastJ, int SfreePos,int ixC, int i, int repeat) {
		String name = String.valueOf(repeat)+" "+String.valueOf(ixC)+" "+String.valueOf(i);
		byte[][] simBoard = new byte[L][W];
		double estValue = 0;
		double QValue = 0;
		byte role = 30;
		int freeP = SfreePos;
		int win = 0;
		ExpPosition e;
		ArrayList<ExpPosition> shrB;
		int last_i = lastI;
		int last_j = lastJ;
		int simNum = simulationNum;
       
		//try {
		//PrintWriter w = new PrintWriter(name+".txt");
       
		while (simNum > 0) {             
			
			boolean trigger = true;
			copyBoard(board, simBoard);
			//w.println("-----------------------------------------------------------------------"+simNum+"-------------------------------------");
			//printBoard(simBoard,w);
			freeP = SfreePos;
			
			last_i = lastI;
			last_j = lastJ;
			role = 30;
            //w.println("-----------------------------------------------------------------------"+simNum+"-------------------------------------");
			while (freeP > 0 && trigger) {
				//w.println("freeP "+freeP);
				
				shrB = shrinkBoardSize2(simBoard, last_i, last_j);									
				e = randomAction1(shrB);				
				simBoard[e.x][e.y] = role;
				//printBoard(simBoard,w);
                last_i = e.x;
                last_j = e.y;
               
			    if((L*W-freeP) > 8) {
			    	 win = ifWin(simBoard, role);
			  
					if (win == role) {
						if (role == 30) {
							estValue += 1;
							trigger = false;
						} else {
							estValue -= 3;
							trigger = false;
						}
					}
			    }
               
				role = switchRole(role);
				freeP--;
			}

			simNum--;
		}

		QValue = estValue / simulationNum;
		//w.close();		
		
		/*}catch(FileNotFoundException er) {
			System.out.println("error");
		}*/
		return QValue;
	}

	public byte switchRole(byte role) {
		if (role == 30) {
			role = 20;
		} else {
			role = 30;
		}
		return role;
	}

	public void backPropagation(Node n) {
		Node tmp = n;
		while (tmp != root) {
			tmp.numVisited++;
			tmp.parent.value = tmp.parent.value + tmp.value;
			tmp = tmp.parent;
		}
		root.numVisited++;
	}

	public Node search(byte[][] board, int round, int lastI, int lastJ) {   // loop for selection,expansion,simulation,backpropagation.
																			// Build MCTTree
		try {
			String textName = "test" + round + ".txt";
			PrintWriter writer = new PrintWriter(textName, "UTF-8");

			ArrayList<ExpPosition> exp;
			copyBoard(board, root.state);

			// printBoard(root.state,writer);

			long startTime = System.nanoTime(); //System.out.println("start "+startTime);
			long endTime = System.nanoTime();   //System.out.println("ent   "+startTime);
			long t = 2000000000L;
			int repeat = 49;
			int ix = -1;
			int freeP = freePos;
			int level = 0;
			boolean trigger = true;
			while (trigger/*repeat > 0*//* (endTime - startTime) < t */) {
				Node temp = root;
				temp.lastI = lastI;
				temp.lastJ = lastJ;
				freeP = freePos;
				//writer.println("-------------------" + repeat + "------------------------");

				// select
                level = 0;

				while (temp.children.size() > 0) {
					//writer.println("temp value" + temp.value);
					// select the best child
					ix = temp.findBestChild(2);
				    for (int i = 0; i < temp.children.size(); i++) {
						writer.println("child " + i + " uct " + temp.children.get(i).uct + " value "+ temp.children.get(i).value);								
						printBoard(temp.children.get(i).state, writer);
					}
					temp = temp.children.get(ix);
                    
					if(temp.numVisited > 0) {
                    	writer.print("the best of first level "+ix);
                    	printBoard(temp.state,writer);
                    	writer.close();
                    	trigger = false;
                    	return temp;
                    }
					
					writer.println("the best " + ix);
					printBoard(temp.state, writer);
					level++;
				}
				// expand
				exp = expansion1(temp.state, temp.lastI, temp.lastJ);
                
				// simulation
				freeP = freeP - level;
				for (int i = 0; i < exp.size(); i++) {

					Node expandNode = new Node();
					copyBoard(temp.state, expandNode.state);
					byte x = exp.get(i).x;
					byte y = exp.get(i).y;
					expandNode.state[x][y] = 30; // printBoard(expandNode.state, writer);
					expandNode.lastI = x;
					expandNode.lastJ = y;
					expandNode.parent = temp;	
					expandNode.value = simulation1(expandNode.state,x,y,freeP-1,ix,i,repeat); 	
					//writer.println("sim value: "+expandNode.value);

					temp.children.add(expandNode);
					temp.value = temp.value + expandNode.value; // add them all then average them

				}
				temp.value = temp.value / exp.size();
				// backpropagation
				backPropagation(temp);
				writer.println("backpropagation temp.value " + temp.value + " uct " + temp.uct + " NumV " + temp.numVisited);
				repeat--;
				//endTime = System.nanoTime(); 
			}
			
			ix = root.findBestChild(0);
			writer.print("---------------------------------------------the best-------------------------------------------------------------------------------");
			  for (int i = 0; i < root.children.size(); i++) {
					writer.println("child " + i + " uct " + root.children.get(i).uct + " value "+ root.children.get(i).value);								
					printBoard(root.children.get(i).state, writer);
				}
			writer.println("the best child "+root.children.get(ix).uct+" "+root.children.get(ix).value);
			printBoard(root.children.get(ix).state, writer);
			writer.close();
			
			return root.children.get(ix);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("error return root");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error return root");
		}
		return root;
	}

	public int ifWin(byte[][] board, int role) {

		byte[][] checked = new byte[L][W];
		for (int i = 0; i < L; i++) {
			System.arraycopy(board[i], 0, checked[i], 0, W);
		}

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < W; j++) {
				if (checked[i][j] != 0) {
					// printBoard(checked);
					if (checked[i][j] == role) {
						// System.out.println("pos"+" "+i+" "+j);
						if (ifWinVertical(checked, i, j, role) || ifWinHorizontal(checked, i, j, role)
								|| ifWinDiagonal1(checked, i, j, role) || ifWinDiagonal2(checked, i, j, role)) {
							return role;
						}
					} else if (checked[i][j] >= 1 && checked[i][j] <= 16) {
						// System.out.println("pos"+" "+i+" "+j);
						if (!getBit(checked[i][j], 0)) {
							if (ifWinVertical(checked, i, j, role)) {
								return role;
							}
						}
						if (!getBit(checked[i][j], 1)) {
							if (ifWinHorizontal(checked, i, j, role)) {
								return role;
							}
						}

						if (!getBit(checked[i][j], 2)) {
							if (ifWinDiagonal1(checked, i, j, role)) {
								return role;
							}
						}

						if (!getBit(checked[i][j], 3)) {
							if (ifWinDiagonal2(checked, i, j, role)) {
								return role;
							}
						}
					}
				}
			}
		}
		return -1; // ne vemo, ce je nasprotnik zmagal.
	}

	public boolean getBit(byte position, int id) { // bit: | - \ /
		return ((position >> id) & 1) == 1;
	}

	public boolean ifWinVertical(byte[][] checked, int pColumn, int pRow, int role) {
		for (int i = 1; i < 5; i++) {
			int currentColumn = pColumn + i;

			if (currentColumn >= L) {
				return false;
			}
			// System.out.println("vertical"+" "+currentColumn);
			if (checked[currentColumn][pRow] == role) {
				checked[currentColumn][pRow] = 1;
			} else if (checked[currentColumn][pRow] >= 1 && checked[currentColumn][pRow] <= 16) {
				if (!getBit(checked[currentColumn][pRow], 0)) {
					checked[currentColumn][pRow] += 1;
				}
			} else
				return false;

		}
		return true;
	}

	public boolean ifWinHorizontal(byte[][] checked, int pColumn, int pRow, int role) {
		for (int i = 1; i < 5; i++) {
			int currentRow = pRow + i;

			if (currentRow >= W) {
				return false;
			}
			// System.out.println("horizontal"+" "+currentRow);
			if (checked[pColumn][currentRow] == role) {
				checked[pColumn][currentRow] = 2;
			} else if (checked[pColumn][currentRow] >= 1 && checked[pColumn][currentRow] <= 16) {
				if (!getBit(checked[pColumn][currentRow], 1)) {
					checked[pColumn][currentRow] += 2;
				}
			} else
				return false;

		}
		return true;
	}

	public boolean ifWinDiagonal1(byte[][] checked, int pColumn, int pRow, int role) { // diagonal1 : \
		for (int i = 1; i < 5; i++) {
			int currentRow = pRow + i;
			int currentColumn = pColumn + i;

			if (currentRow >= W || currentColumn >= L) {
				return false;
			}
			// System.out.println("diagonal1"+" "+currentColumn+" "+currentRow);
			if (checked[currentColumn][currentRow] == role) {
				checked[currentColumn][currentRow] = 4;
			} else if (checked[currentColumn][currentRow] >= 1 && checked[currentColumn][currentRow] <= 16) {
				if (!getBit(checked[currentColumn][currentRow], 2)) {
					checked[currentColumn][currentRow] += 4;
				}
			} else
				return false;

		}
		return true;
	}

	public boolean ifWinDiagonal2(byte[][] checked, int pColumn, int pRow, int role) { // diagonal2 : / dol
		for (int i = 1; i < 5; i++) {
			int currentRow = pRow - i;
			int currentColumn = pColumn + i;

			if (currentRow < 0 || currentColumn >= L) {
				return false;
			}
			// System.out.println("diagonal2"+" "+currentColumn+" "+currentRow);
			if (checked[currentColumn][currentRow] == role) {
				checked[currentColumn][currentRow] = 8;
			} else if (checked[currentColumn][currentRow] >= 1 && checked[currentColumn][currentRow] <= 16) {
				if (!getBit(checked[currentColumn][currentRow], 3)) {
					checked[currentColumn][currentRow] += 8;
				}
			} else
				return false;
		}
		return true;
	}

	// za test

	public void printBoard(byte[][] checked, PrintWriter writer) {

		for (int i = 0; i < L; i++) {

			//writer.println("------------------------------");
		    writer.println("------------------------------------------------------------------------------");
			writer.print("|");
			for (int j = 0; j < W; j++) {
				switch (checked[i][j]) {
				case 0:
					writer.print(" " + " " + " " + "|");
					break;
				case 20:
					writer.print(" " + "O" + " " + "|");
					break;
				case 30:
					writer.print(" " + "x" + " " + "|");
					break;
				case 40:
					writer.print(" " + "e" + " " + "|");
					break;
				case 50:
					writer.print(" " + "s" + " " + "|");
					break;
				default:
					writer.print(" " + checked[i][j] + " " + "|");

				}

			}
			writer.println();
		}
		//writer.println("------------------------------");
		 writer.println("------------------------------------------------------------------------------");
	}

}
