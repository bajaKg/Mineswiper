import java.util.Random;


public class Engine {
	private Random r = new Random();
	private int[][] mat;
	public int dimMatrice = 6;
	public int brMina = 5;
	public int brOtvorenihPolja = 0;
	public int brBezbednihPolja = dimMatrice*dimMatrice - brMina;
	
	public Engine(){
		r = new Random();
		reset();
	}
	
	public void reset(){
		postaviMatricu();
		this.brOtvorenihPolja = 0;
		this.brBezbednihPolja = dimMatrice*dimMatrice - brMina;
	}
	
	public int susedneMine(int i, int j){
		int brMina = 0;
		if(i > 0 && j > 0)
			if(mat[i-1][j-1] == -1) brMina++;
		if(i>0)
			if(mat[i-1][j] == -1) brMina++;
		if(i>0 && j<dimMatrice-1)
			if(mat[i-1][j+1] == -1) brMina++;
		if(j<dimMatrice-1)
			if(mat[i][j+1] == -1) brMina++;
		if(i<dimMatrice-1 && j<dimMatrice-1)
			if(mat[i+1][j+1] == -1) brMina++;
		if(i<dimMatrice-1)
			if(mat[i+1][j] == -1) brMina++;
		if(i<dimMatrice-1 && j>0)
			if(mat[i+1][j-1] == -1) brMina++;
		if(j>0)
			if(mat[i][j-1] == -1) brMina++;
		return brMina;
	}
	
	public void postaviMatricu(){
		int a, b;
		mat = new int[dimMatrice][dimMatrice];
		for (int i = 0; i < dimMatrice; i++) {
			for (int j = 0; j < dimMatrice; j++) {
				mat[i][j] = 1;
			}
		}
		
		for (int i = 0; i < brMina; i++) {			
			int k = 1;
			while(k == 1){	
				a = r.nextInt(dimMatrice);				
				b = r.nextInt(dimMatrice);
				if(mat[a][b] != -1){
					mat[a][b] = -1;
					k=0;
				}
			}
		}
		for (int i = 0; i < dimMatrice; i++) {
			for (int j = 0; j < dimMatrice; j++) {
				if(mat[i][j] != -1)
					mat[i][j] = susedneMine(i,j);
			}
		}				
	}
	
	public int proveri(int i, int j, int x) {		
		return mat[i][j];
	}
	public int proveri(int i, int j) {
		this.brOtvorenihPolja++;
		return mat[i][j];
	}
	
	public void setBrMina(int brMina){
		this.brMina = brMina;
	}
	public void setDim(int dimMatrice){
		this.dimMatrice = dimMatrice;
	}
	
}

class Zavrsena extends Exception{
	
}

class Zavrsena1 extends Exception{
	
}
