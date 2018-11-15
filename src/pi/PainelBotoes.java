import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelBotoes extends JPanel {
	private JButton cadeiaFreeman, desfazer, refazer, limpar;
	private JButton quadrado,livre,triangulo;
	
	public PainelBotoes(PainelDesenho painel) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		livre = new JButton("Livre");
		livre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.setTipoDesenho(Desenho.LIVRE);				
			}
		});
		add(livre);
		
		cadeiaFreeman = new JButton("Freeman");
		cadeiaFreeman.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				painel.setTipoDesenho(Desenho.FREEMAN);
			}
		});
		add(cadeiaFreeman);
		
		quadrado = new JButton("Quadrado");
		quadrado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.setTipoDesenho(Desenho.QUADRADO);
			}
		});
		add(quadrado);
		
		triangulo = new JButton("Triangulo");
		triangulo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.setTipoDesenho(Desenho.TRIANGULO);
			}
		});
		add(triangulo);
		
		desfazer = new JButton("DESFAZER");
		desfazer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.desfazer();
			}
		});
		add(desfazer);
		
		refazer = new JButton("REFAZER");
		refazer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.refazer();				
			}
		});
		add(refazer);
		
		limpar = new JButton("LIMPAR");
		limpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				painel.limpar();
			}
		});
		add(limpar);
	}

}
