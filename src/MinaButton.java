import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MinaButton extends JButton{
	private int i, j;
	private ImageIcon slika;
	private String zastava = "zastava.jpg";
	private boolean zakljucano = false;
	private int zastavaIndikator = 0;
	
	public MinaButton(){
		setBackground(Color.blue);
		setPreferredSize(new Dimension(50, 50));
	}
	
	public void prikaziBroj(int broj){
		slika = new ImageIcon(""+broj+".jpg");
		setIcon(slika);
		setOpaque(false);
		setBackground(Color.white);
	}
	
	public void prikaziZastavu(){
		slika = new ImageIcon(zastava);
		setIcon(slika);
		setOpaque(false);
		setBackground(Color.white);
	}
	public void skloniZastavu(){
		slika = null;
		setIcon(slika);
		setOpaque(true);		
		setBackground(Color.blue);
		System.out.println("Sklonio zastavu");
	}
	
	public int zastava(){
		if(zastavaIndikator == 0) return 0;
		else return 1;
	}
	public void zastavaPromeni(){
		if(this.zastavaIndikator == 0) this.zastavaIndikator = 1;
		else this.zastavaIndikator = 0;
	}
		
	public void  setPoziciju(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ(){
		return this.j;
	}
	
	public boolean zakljucano(){
		return this.zakljucano;
	}
	
	public void zakljucaj(){
		this.zakljucano = true;
	}
	
	public void otkljucaj(){
		this.zakljucano = false;
	}
}


