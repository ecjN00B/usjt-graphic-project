/**
 * A classe que cuida da interface gráfica com o usuário, especialmente do gerenciamento
 *   dos dois menus (Arquivo e Cores).
 */

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InterfaceGrafica extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private Color corDeBordaAtual; 
	private Color corInternaAtual;
	private PainelDeDesenho painel; //painel onde o desenho é feito 
	private JMenuItem novo, abrir, salvar, sair, mudaCorInterna, mudaCorDeBorda; //itens de menu
	private String ultimoDiretorioAcessado; //memoriza em qual diretório houve a última operação
	                                        //de gravação ou leitura

	/** Constrói um objeto InterfaceGrafica
	 */
	public InterfaceGrafica ()
	{
		super("MiniDraw versão 2.0");
		setSize(750, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		corDeBordaAtual = Color.BLACK; //Preto
		corInternaAtual = Color.WHITE; //Branco

		JMenu menuArquivo = criaMenuArquivo();
		JMenu menuDeCores = criaMenuDeCores();
		JMenuBar barraDeMenus = new JMenuBar();
		barraDeMenus.add(menuArquivo);
		barraDeMenus.add(menuDeCores);
		setJMenuBar(barraDeMenus);

		painel = new PainelDeDesenho(this);
		getContentPane().add(painel, BorderLayout.CENTER);

		ultimoDiretorioAcessado = "."; //O ponto faz alusão ao diretório corrente
	}

	private JMenu criaMenuArquivo() {
		JMenu menuArquivo = new JMenu("Arquivo");
		novo = new JMenuItem("Novo Desenho"); //Cria um item de menu para começar uma nova figura
		novo.addActionListener(this); //Configura o ouvinte de ação
		menuArquivo.add(novo); //Adiciona o item "Novo Desenho" ao Menu

		abrir = new JMenuItem("Abrir");
		abrir.addActionListener(this); //Configura o ouvinte de ação
		menuArquivo.add(abrir); //Adiciona o item "Abrir" ao Menu

		salvar = new JMenuItem("Salvar");
		salvar.addActionListener(this); //Configura o ouvinte de ação
		menuArquivo.add(salvar); //Adiciona o item "Salvar" ao Menu

		sair = new JMenuItem("Sair");
		sair.addActionListener(this); //Configura o ouvinte de ação
		menuArquivo.add(sair); //Adiciona o item "Salvar" ao Menu

		return menuArquivo;
	}
	private JMenu criaMenuDeCores()
	{
		JMenu menuDeCores = new JMenu("Cores");

		mudaCorInterna = new JMenuItem("Configurar Cor Interna");
		mudaCorInterna.addActionListener(this);
		menuDeCores.add(mudaCorInterna);

		mudaCorDeBorda = new JMenuItem("Configurar Cor de Borda");
		mudaCorDeBorda.addActionListener(this);
		menuDeCores.add(mudaCorDeBorda);

		return menuDeCores;
	}

	public void actionPerformed(ActionEvent e) {

		if     (e.getSource() == novo)            novoArquivo();
		else if(e.getSource() == abrir)           abrirArquivo();
		else if(e.getSource() == salvar)          salvarArquivo();
		else if(e.getSource() == sair)            sair();
		else if(e.getSource() == mudaCorInterna)  mudarCorInterna();
		else if(e.getSource() == mudaCorDeBorda)  mudarCorBorda();
	}

	/**
	 * Método para reinicializar todo o desenho corrente
	 */
	private void novoArquivo()
	{
		String[] opcoes = {"Sim","Não"};
		int i = JOptionPane.showOptionDialog(this,
				"Tem certeza que deseja continuar? A figura atual será perdida.",
				"Novo Desenho", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, opcoes, opcoes[0]);
		if (i == JOptionPane.YES_OPTION) {
			corDeBordaAtual = Color.BLACK;
			corInternaAtual = Color.WHITE;
			painel.repaint();
		}
	}

	/**
	 * Método para ler um arquivo de desenho
	 */
	@SuppressWarnings("unchecked")
	private void abrirArquivo()
	{
		JFileChooser escolhedorDeArquivos = new JFileChooser(ultimoDiretorioAcessado);
		escolhedorDeArquivos.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".mdr") || f.isDirectory();
			}
			public String getDescription() {
				return "*.mdr - Arquivos do MiniDraw";
			}
		});
		int situacao = escolhedorDeArquivos.showOpenDialog(this);
		if (situacao == JFileChooser.APPROVE_OPTION) {
			ultimoDiretorioAcessado = escolhedorDeArquivos.getCurrentDirectory().toString();
			String nomeDoArquivo = escolhedorDeArquivos.getSelectedFile().getAbsolutePath();
			try {
				FileInputStream fis = new FileInputStream(nomeDoArquivo);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				fis.close();
				ois.close();
				painel.setPontos((ArrayList<Point>) obj);
				corDeBordaAtual = Color.BLACK;
				corInternaAtual = Color.WHITE;
				painel.repaint();
			} 
			catch (FileNotFoundException exc) { exc.printStackTrace(); } 
			catch (IOException exc) { exc.printStackTrace(); }
			catch (ClassNotFoundException exc) { exc.printStackTrace(); }
		}
	}

	/**
	 * Método para gravar um arquivo de desenho
	 */
	private void salvarArquivo()
	{
		String nomeDoArquivo;
		JFileChooser escolhedorDeArquivos = new JFileChooser(ultimoDiretorioAcessado);
		escolhedorDeArquivos.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".mdr") || f.isDirectory();
			}
			public String getDescription() {
				return "*.mdr - Arquivos do MiniDraw";
			}
		});
		int situacao = escolhedorDeArquivos.showSaveDialog(this);
		if (situacao == JFileChooser.APPROVE_OPTION) {
			ultimoDiretorioAcessado = escolhedorDeArquivos.getCurrentDirectory().toString();
			nomeDoArquivo = escolhedorDeArquivos.getSelectedFile().getAbsolutePath();
			if(! nomeDoArquivo.toLowerCase().endsWith(".mdr"))
				nomeDoArquivo += ".mdr";
			try {
				FileOutputStream fos = new FileOutputStream(nomeDoArquivo);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(painel.getPontos());
				fos.close();
				oos.close();
			} 
			catch (FileNotFoundException exc) { exc.printStackTrace(); } 
			catch (IOException exc) { exc.printStackTrace(); }
		}
		if (situacao == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(this, "Arquivo não salvo",
					"Salvar", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Método para encerrar a aplicação
	 */
	private void sair()
	{
		String[] opcoes = {"Sim","Não"};
		int i = JOptionPane.showOptionDialog(this,
				"Tem certeza que deseja sair?",
				"Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, opcoes, opcoes[0]);
		if (i == JOptionPane.YES_OPTION) System.exit(0);
	}

	/**
	 * Exibe a caixa de diálogo de seleção de cores e, em seguida, 
	 *	 configura a cor interna de cada um dos protótipos de figuras.
	 *	 A exibição é então redesenhada.
	 */
	private void mudarCorInterna()
	{
		corInternaAtual = JColorChooser.showDialog(this,
				"Selecionar Cor Interna", corInternaAtual);
		if(corInternaAtual == null) corInternaAtual = Color.WHITE;

		repaint();
	}

	/**
	 * Exibe a caixa de diálogo de seleção de cores e, em seguida, 
	 *	 configura a cor de borda de cada um dos protótipos de figuras.
	 *	 A exibição é então redesenhada.
	 */
	private void mudarCorBorda()
	{
		corDeBordaAtual = JColorChooser.showDialog(this,
				"Selecionar Cor de Borda", corDeBordaAtual);
		if(corDeBordaAtual == null) corDeBordaAtual = Color.BLACK;
		
		repaint();
	}
	public Color getCorAtual()
	{	return corDeBordaAtual;
	}
}