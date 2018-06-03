
import java.applet.Applet;
import java.applet.AudioClip;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Ventana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel personaje;
	private JLabel mapa;
	protected JTextArea txtArea;
	private AudioClip audioSource;
	
	public Ventana () {
		this.setSize(636, 387);
		this.setTitle("RoboCharlie");
		this.setLayout(null);
		//this.setResizable(false);
		//this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapa = new JLabel();
		mapa.setBounds(0,0,517,387);
		mapa.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/Captura.PNG")));
		personaje = new JLabel();
		personaje.setIcon(new ImageIcon(this.getClass().getResource("SpritesLink/0.png")));
		personaje.setSize(18,20);
		personaje.setLocation(this.getWidth()/2,this.getHeight()/2);
		txtArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(txtArea);
		scroll.setBounds(517,0,100,380);
		txtArea.setEditable(false);
		this.add(personaje);
		this.add(mapa);
		this.add(scroll);
		this.setVisible(true);
		audioSource = Applet.newAudioClip(getClass().getResource("media/SoundBackGround.wav"));
		audioSource.loop();
	}

}
