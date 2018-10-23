import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PainelDeDesenho extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private InterfaceGrafica aplicacao;
	/** Pontos de uma curva */
	private ArrayList <Point> pontos;
	private Point ultimoPonto, coordAtual;
	public PainelDeDesenho (InterfaceGrafica p)
	{
		aplicacao = p;
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		pontos = new ArrayList<Point>();
		ultimoPonto = new Point(0,0);
	}
	
	public void paint(Graphics g)
	{	super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create(); // nunca trabalhe diretamente sobre g, apenas sobre uma cópia de g  
        g2d.setColor(aplicacao.getCorAtual());
		
		for(int i=1; i < pontos.size(); i++)
		{	Point anterior = pontos.get(i-1);
			Point atual = pontos.get(i);
			g2d.drawLine(anterior.x, anterior.y,atual.x , atual.y);
		}
		for(int i=0; i < pontos.size(); i++)
		{	Point p = pontos.get(i);
			g2d.fillOval(p.x-5, p.y-5, 10, 10);
		}
		//Desenha aquela linha do último ponto até o cursor
		if(pontos.size()>0)
		{	// Desenhando uma linha grossa e tracejada, verde, com as extremidades redondas
			float[] dash = {20f};
        	g2d.setStroke(new BasicStroke (10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, dash, 0f));
        	g2d.setColor(Color.GREEN);
        	g2d.drawLine(ultimoPonto.x, ultimoPonto.y, coordAtual.x, coordAtual.y);
		}
		g2d.dispose(); //toda vez que você usa "create" é necessário usar "dispose"
	}
	
	//Implementação da interface MouseListener
	public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e)
    {	requestFocusInWindow(); //Isso é necessário para que o KeyListener funcione adequadamente
    	if(e.getButton() == MouseEvent.BUTTON1) //Botão 1
    	{	Point p = e.getPoint();
    		pontos.add(p);
    		ultimoPonto = p;
    	}
    	else //Outro botão do mouse apertado
    	{	//fechar a figura
    		pontos.add(pontos.get(0));
    		ultimoPonto = pontos.get(0);
    	}
    	repaint();
    }
    
    public void mouseMoved(MouseEvent e)
    {	coordAtual = e.getPoint();
    	repaint();
    }
    
	//Implementação da interface KeyListener
 
	/**
	 * Quando a tecla ESC é pressionada, limpo a figura sendo desenhada no momento
	 *   @param e objeto relativo ao evento de teclas
	 */
	public void keyPressed(KeyEvent e) {
		// Tecla Apertada
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)  //Tecla ESC 
		{	pontos.clear();	
		}
		if(e.getKeyCode() == KeyEvent.VK_1)  //Tecla 1 
		{	Point p = new Point(
				ultimoPonto.x-50, ultimoPonto.y-50);
			pontos.add(p);
			ultimoPonto = p;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	//Método de acesso e modificador para a coleção de pontos
	public ArrayList<Point> getPontos()
	{	return pontos;
	}
	public void setPontos(ArrayList<Point> pontos)
	{	this.pontos = pontos;
		ultimoPonto = pontos.get(pontos.size()-1);
	}
}