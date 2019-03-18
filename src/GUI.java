import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.RootPaneContainer;


public class GUI  extends JFrame implements ActionListener,MouseListener {
	private Engine eng = new Engine();
	private JPanel centar, cp;
	private MinaButton[][] dugmici = new MinaButton[eng.dimMatrice][eng.dimMatrice];
	private JLabel pokusaji, pogoci;
	
	private JMenu fileMenu;
	private JMenuBar menuBar;
	private JMenuItem newGame;
	private JMenuItem exit;
	private JRadioButtonMenuItem rbMenuItem;
	String manjeMina = "5 mina";
	String viseMina = "10 mina";
	String manjaDim = "6x6";
	String vecaDim = "10x10";
	JCheckBoxMenuItem cbMenuItem;
	
	private String items[] = {"Nova igra","Open...","Save..."};
	private String iconFile[] = {"-01.jpg","2.jpg",
			"3.jpg","4.jpg"};
	private char itemMnemonics[] = {'N','O','S','E'};	
	
	private Icon icons[];
	
	public GUI(){
		//setBounds(500,200,300,300);
		setTitle("Mineswiper");
		cp = (JPanel)getContentPane();
		namestiKomponente();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public void namestiKomponente(){
		namestiMeni();
		namestiCentar();
		namestiJug();
	}
	
	public void namestiMeni(){			
		fileMenu = new JMenu("File");
		menuBar = new JMenuBar();
		rbMenuItem = new JRadioButtonMenuItem();
		newGame = new JMenuItem(items[0],new ImageIcon(iconFile[0]));						
		newGame.setMnemonic(itemMnemonics[0]);
		fileMenu.add(newGame);
		// Radio Buttons 
		fileMenu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem(manjeMina);
		rbMenuItem.setActionCommand(manjeMina);
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		rbMenuItem.addActionListener(this);
		group.add(rbMenuItem);
		fileMenu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem(viseMina);
		rbMenuItem.setActionCommand(viseMina);
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		rbMenuItem.addActionListener(this);
		group.add(rbMenuItem);
		fileMenu.add(rbMenuItem);		
		// Druga grupa rario batna
		fileMenu.addSeparator();
		ButtonGroup group1 = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem(manjaDim);
		rbMenuItem.setActionCommand(manjaDim);
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		rbMenuItem.addActionListener(this);
		group1.add(rbMenuItem);
		fileMenu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem(vecaDim);
		rbMenuItem.setActionCommand(vecaDim);
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		rbMenuItem.addActionListener(this);
		group1.add(rbMenuItem);
		fileMenu.add(rbMenuItem);
		
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		fileMenu.addSeparator();
			
		exit = new JMenuItem("Exit");
		exit.setMnemonic('E');
		fileMenu.add(exit);
			
		setJMenuBar(menuBar);	
			//Event Listener on JMenuItem[0]
			newGame.addActionListener(
				new ActionListener() {								
					public void actionPerformed(ActionEvent event) {
						nova();
					}
				}  
			);												
			exit.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
				}
			);
	}
	
	public void namestiCentar(){
		centar = new JPanel(new GridLayout(eng.dimMatrice,eng.dimMatrice,0,0)); 
		centar.setBorder(BorderFactory.createLineBorder(Color.white, 5));
		popuniDugmice();
		cp.add(centar);
	}
	
	public void namestiJug(){
		JButton nova = new JButton("Nova");
		nova.setBackground(Color.gray);
		JPanel jug = new JPanel(new FlowLayout());
		jug.add(nova);
		cp.add(jug, BorderLayout.SOUTH);
		nova.addActionListener(this);
		
	}
	
	public void nova(){
		centar.removeAll();
		eng.reset();
		popuniDugmice();
		centar.updateUI();
	}
	
	public void popuniDugmice(){
		dugmici = new MinaButton[eng.dimMatrice][eng.dimMatrice];
		for (int i = 0; i <eng.dimMatrice; i++) 
			for (int j = 0; j < eng.dimMatrice; j++) {
			dugmici[i][j] = new MinaButton();
			dugmici[i][j].addActionListener(this);
			dugmici[i][j].addMouseListener(this);
			dugmici[i][j].setPoziciju(i,j);
			dugmici[i][j].setVisible(true);
			centar.add(dugmici[i][j]);
		}
	}
	
	public static void main(String[] args) {
		GUI n = new GUI();

	}
	
	public void actionPerformed(ActionEvent e){		
		if(e.getSource() instanceof MinaButton){ 		
			MinaButton b = (MinaButton)e.getSource();
			if(!b.zakljucano()){				
				try {
					if(eng.proveri(b.getI(),b.getJ(),1) == -1) throw new Zavrsena1();
					if(eng.brOtvorenihPolja == eng.brBezbednihPolja-1) throw new Zavrsena();
					b.prikaziBroj(eng.proveri(b.getI(),b.getJ()));
					b.zakljucaj();
				}
				catch (Zavrsena e1) {
						b.prikaziBroj(eng.proveri(b.getI(),b.getJ()));
						String poruka = "Svaka cast! Pobedili ste! Nova igra?";
						ImageIcon slika = new ImageIcon("vatromet.jpg");
						int op = JOptionPane.showConfirmDialog(null, poruka, "MINE",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, slika);
						if(op == JOptionPane.NO_OPTION) System.exit(0);
						else nova();											
				}
				catch(Zavrsena1 e2){
					b.prikaziBroj(eng.proveri(b.getI(),b.getJ()));
					String poruka = "Mina! :P\nNova igra? ";
					ImageIcon slika = new ImageIcon("bum.jpg");
					int op = JOptionPane.showConfirmDialog(null, poruka, "MINE",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, slika);
					if(op == JOptionPane.NO_OPTION) System.exit(0);
					else nova();
				}
				//Ako je polje 0, onda otvori sva polja susedna sa njim
				/*if( eng.proveri(b.getI(),b.getJ(),1) == 0) {
					if(b.getJ() < eng.dimMatrice-1){
						dugmici[b.getI()][ b.getJ()+1].prikaziBroj(eng.proveri(b.getI(),b.getJ()+1));
						dugmici[b.getI()][ b.getJ()+1].zakljucaj();
					}					
					if(b.getI() > 0){
						dugmici[b.getI()-1][ b.getJ()].prikaziBroj(eng.proveri(b.getI()-1,b.getJ()));;
						dugmici[b.getI()-1][ b.getJ()].zakljucaj();
					}						
					if(b.getI() > 0 && b.getJ()>0){
						dugmici[b.getI()-1][ b.getJ()-1].prikaziBroj(eng.proveri(b.getI()-1,b.getJ()-1));;
						dugmici[b.getI()-1][ b.getJ()-1].zakljucaj();
					}						
					if(b.getI() < eng.dimMatrice-1 && b.getJ() > 0){
						dugmici[b.getI()+1][ b.getJ()-1].prikaziBroj(eng.proveri(b.getI()+1,b.getJ()-1));;
						dugmici[b.getI()+1][ b.getJ()-1].zakljucaj();
					}						
					if(b.getJ() > 0){
						dugmici[b.getI()][ b.getJ()-1].prikaziBroj(eng.proveri(b.getI(),b.getJ()-1));;
						dugmici[b.getI()][ b.getJ()-1].zakljucaj();
					}						
					if(b.getI() < eng.dimMatrice-1){
						dugmici[b.getI()+1][ b.getJ()].prikaziBroj(eng.proveri(b.getI()+1,b.getJ()));;
						dugmici[b.getI()+1][ b.getJ()].zakljucaj();
					}						
					if(b.getI() < eng.dimMatrice-1 && b.getJ() < eng.dimMatrice-1){
						dugmici[b.getI()+1][ b.getJ()+1].prikaziBroj(eng.proveri(b.getI()+1,b.getJ()+1));;
						dugmici[b.getI()+1][ b.getJ()+1].zakljucaj();
					}	
					if(b.getI() > 0 && b.getJ() < eng.dimMatrice-1){
						dugmici[b.getI()-1][ b.getJ()+1].prikaziBroj(eng.proveri(b.getI()-1,b.getJ()+1));;
						dugmici[b.getI()-1][ b.getJ()+1].zakljucaj();
					}
				}*/				
				
			}
				
		}
		else if(e.getSource() instanceof JRadioButtonMenuItem){		
			if(e.getActionCommand().equals(manjaDim)){
				eng.setDim(6);
				nova();
			}
			else if(e.getActionCommand().equals(vecaDim)){
				eng.setDim(9);
				nova();
			}
			else if(e.getActionCommand().equals(manjeMina)){
				eng.setBrMina(5);
				nova();
			}
			else if(e.getActionCommand().equals(viseMina)){
				eng.setBrMina(10);
				nova();
			}
		}
		else if(e.getSource() instanceof JButton){
			nova();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ( e.isMetaDown() ){			
			MinaButton b = (MinaButton)e.getSource();
			if(!b.zakljucano()){
				if(b.zastava() == 0){
					b.prikaziZastavu();					
					b.zastavaPromeni();
				}
				else{				
					b.skloniZastavu();					
					b.zastavaPromeni();
				}
			}
		}	
	}
	
}

