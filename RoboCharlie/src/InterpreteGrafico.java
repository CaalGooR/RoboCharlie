import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import PseudoLexer.PseudoLexer;

public class InterpreteGrafico extends Ventana implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private static int OFFSET_INSTRUCC = 8;
	private int offset_digitos = 0;
	private Scanner scanner;
	private String interCode;
	private String instruccion;
	private int desplazamiento;
	private int inicio;
	private int fin;
	
	public InterpreteGrafico() throws FileNotFoundException {
		scanner = new Scanner(new File("src/codeRobot.txt"));
		String content = scanner.useDelimiter("\\Z").next();
		PseudoLexer pseuLexer = new PseudoLexer(content);
		new PseudoParser(pseuLexer);
		scanner = new Scanner(new File("src/codeIntermedio.rob"));
		interCode = scanner.useDelimiter("\\Z").next();
		this.txtArea.setText("Instrucciones \n");
		
	}
	
	private void mov_izq(int x) {
		x += 10; // Se suman mas pixeles para que dure mas la simulacion
		for (int i = 0;i<x;i++) {
			if (personaje.getLocation().x < 517 && personaje.getLocation().y < 380 
					&& personaje.getLocation().x > 5 && personaje.getLocation().y > 5) {
				this.personaje.setLocation(personaje.getLocation().x - i, personaje.getLocation().y);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/walkingLeft.gif")));
				try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else {
				this.personaje.setLocation(personaje.getLocation().x+10,personaje.getLocation().y);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
				return;
			}
		}
		this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
			
	}
	
	private void mov_der(int x) {
		x += 10; // Se suman mas pixeles para que dure mas la simulacion
		for (int i = 0;i<x;i++) {
			if (personaje.getLocation().x < 450 && personaje.getLocation().y < 380 
					&& personaje.getLocation().x > 5 && personaje.getLocation().y > 5) {
				this.personaje.setLocation(personaje.getLocation().x + i, personaje.getLocation().y);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/walkingRigth.gif")));
				try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else {
				this.personaje.setLocation(personaje.getLocation().x-10,personaje.getLocation().y);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
				return;
			}
		}
		this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
	}
	
	private void mov_dia(int x) {
		x += 10; // Se suman mas pixeles para que dure mas la simulacion
		for (int i = 0;i<x;i++) {
			if (personaje.getLocation().x < 517 && personaje.getLocation().y < 380 
					&& personaje.getLocation().x > 5 && personaje.getLocation().y > 5) {
				this.personaje.setLocation(personaje.getLocation().x + i, personaje.getLocation().y-i);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/walkingUp.gif")));
				try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else {
				this.personaje.setLocation(personaje.getLocation().x-10,personaje.getLocation().y-10);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
				return;
			}
		}
		this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
	}
	
	private void mov_arr(int x) {
		x += 10; // Se suman mas pixeles para que dure mas la simulacion
		for (int i = 0;i<x;i++) {
			if (personaje.getLocation().x < 517 && personaje.getLocation().y < 380
					&& personaje.getLocation().x > 5 && personaje.getLocation().y > 5) {
				this.personaje.setLocation(personaje.getLocation().x, personaje.getLocation().y-i);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/walkingUp.gif")));
				try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else {
				this.personaje.setLocation(personaje.getLocation().x, 6);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
				return;
			}
		}
		this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
	}
	
	private void mov_aba(int x) {
		x += 10; // Se suman mas pixeles para que dure mas la simulacion
		for (int i = 0;i<x;i++) {
			if (personaje.getLocation().x < 517 && personaje.getLocation().y < 330
					&& personaje.getLocation().x > 5 && personaje.getLocation().y > 5) {
				this.personaje.setLocation(personaje.getLocation().x, personaje.getLocation().y+i);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/walkingDown.gif")));
				try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else {
				this.personaje.setLocation(personaje.getLocation().x, personaje.getLocation().y-10);
				this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
				return;
			}
		}
		
		this.personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
	}

	@Override
	public void run() {
		JOptionPane.showMessageDialog(null, "Enter para iniciar","RoboCharlie!! 100% REAL NO FAKE", 0);
		inicio = 0;
		fin = 10;
		do {
			offset_digitos = 1;
			instruccion = interCode.substring(inicio,fin-3);
			while(isNumeric(""+interCode.charAt(inicio+OFFSET_INSTRUCC+offset_digitos)))
				offset_digitos++;
			System.out.println(instruccion);
			System.out.println(interCode.substring(inicio+OFFSET_INSTRUCC, inicio+OFFSET_INSTRUCC+offset_digitos));
			desplazamiento = Integer.parseInt(interCode.substring(inicio+OFFSET_INSTRUCC, inicio+OFFSET_INSTRUCC+offset_digitos));
			switch(instruccion) {
				case "mov_izq":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_izq(desplazamiento);
				break;
				case "mov_der":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_der(desplazamiento);
				break;
				case "mov_dia":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_dia(desplazamiento);
				break;
				case "mov_par":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_par(desplazamiento);
				break;
				case "mov_arr":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_arr(desplazamiento);
				break;
				case "mov_aba":
					this.txtArea.setText(txtArea.getText()
							+interCode.substring(inicio,fin+offset_digitos-1)+"\n");
					mov_aba(desplazamiento);
				break;
				
			}
			inicio = fin + 1;
			if (offset_digitos > 1) {
				fin += 10+offset_digitos;
				inicio += offset_digitos-1;
			}
			else
				fin +=11;
			JOptionPane.showMessageDialog(null,"Ejecutando", "Animacion", 1);
		}while(fin <= interCode.length());
		
	}

	private void mov_par(int desplazamiento2) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isNumeric(String cadena) {
		return cadena.matches("-?[0-9]+(\\\\.([0-9]+))?");
	}
	
}
