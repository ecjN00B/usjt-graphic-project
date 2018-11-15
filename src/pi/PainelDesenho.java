import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PainelDesenho extends JPanel 
	implements MouseListener,MouseMotionListener,ActionListener,KeyListener{

	private ArrayList<Figura> principal = new ArrayList<>();
	private ArrayList<Figura> lixeira = new ArrayList<>();
	private Figura aux_figura = new Figura();
	private Point aux_ponto = new Point();
	private Point aux_ponto2 = new Point();
	private int aux_cord;
	private int tipo_desenho = Desenho.DEFAULT;
	private JPanel aux_painel = new JPanel();
	private JLabel aux_label = new JLabel();
	private JTextField aux_texto = new JTextField(30);
	private boolean mousemoved;
	private boolean limpar = false;
	
	public PainelDesenho() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		aux_painel.setLayout(new FlowLayout());
		aux_painel.add(aux_label);
		aux_painel.add(aux_texto);
		aux_texto.addActionListener(this);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g.create();
		//gg.setStroke(new BasicStroke(5f));
		
		for(Figura figura:principal) 
			doDesenho(figura,gg);
		
		if(aux_figura.getPasso()>0) 
			doDesenho(aux_figura,gg);

	}
	
	public void doDesenho(Figura figura, Graphics2D gg) {
		
		gg.setColor(figura.getCor());
		int passo = figura.getPasso();
		Point ponto = figura.getPonto().getLocation();
		int coordenadas[] = figura.getCoordenadas();
		Point[] desenho;
		if(coordenadas!=null) {
			desenho = new Point[coordenadas.length+1];
			desenho[0] = ponto.getLocation();
			
			for(int i = 0; i<coordenadas.length;i++) {
				switch(coordenadas[i]) {
				case 0:
					ponto.setLocation(ponto.x+passo,ponto.y);
					desenho[i+1] = ponto.getLocation();
					break;
				case 1:
					ponto.setLocation(ponto.x+passo,
							ponto.y-passo);
					desenho[i+1] = ponto.getLocation();
					break;
				case 2:
					ponto.setLocation(ponto.x, ponto.y-passo);
					desenho[i+1] = ponto.getLocation();
					break;
				case 3:
					ponto.setLocation(ponto.x-passo,
							ponto.y-passo);
					desenho[i+1] = ponto.getLocation();
					break;
				case 4: 
					ponto.setLocation(ponto.x- passo, ponto.y);
					desenho[i+1] = ponto.getLocation();
					break;
				case 5:
					ponto.setLocation(ponto.x-passo, 
							ponto.y+passo);
					desenho[i+1] = ponto.getLocation();
					break;
				case 6:
					ponto.setLocation(ponto.x, ponto.y+passo);
					desenho[i+1] = ponto.getLocation();
					break;
				case 7:
					ponto.setLocation(ponto.x+passo, 
							ponto.y+passo);
					desenho[i+1] = ponto.getLocation();
					break;
				}
				
				
				gg.draw(new Line2D.Double(desenho[i].x,desenho[i].y,
							desenho[i+1].x,desenho[i+1].y));
				
			}
		}
		else {
			desenho = new Point[1];
			desenho[0] = figura.getPonto();
		}
				
		if(figura.equals(aux_figura) ) {
			if(tipo_desenho==Desenho.FREEMAN && mousemoved) {
				if(aux_figura.getCoordenadas()!=null)
					aux_ponto = desenho[aux_figura.getCoordenadas().length].getLocation();
				else
					aux_ponto = aux_figura.getPonto().getLocation();
				
				gg.draw(new Line2D.Double(aux_ponto.x,
						aux_ponto.y,
						aux_ponto2.x,aux_ponto2.y));
				mousemoved = false;
				aux_ponto2.setLocation(aux_ponto);
			}
			else if(tipo_desenho==Desenho.LIVRE)
				aux_ponto = desenho[aux_figura.getCoordenadas().length].getLocation();
		}
		
	}
	
	public void removePainelAuxiliar() {
		remove(aux_painel);
		aux_texto.setText("");
		repaint();
		validate();
	}
	
	public void addPainelAuxiliar() {
		add(aux_painel,BorderLayout.SOUTH);
		aux_texto.requestFocus();
		validate();
		repaint();
	}
	
	public void newFigura(){
		removePainelAuxiliar();
		if(aux_figura.getPonto()!=null)
			lixeira.clear();
		if(aux_figura.getCoordenadas()!=null && aux_figura.getPonto()!=null && aux_figura.getPasso()!=0)
			principal.add(aux_figura);
		this.aux_figura = new Figura();				
	}
	
	public void setTipoDesenho(int tipo) {
		aux_figura = new Figura();
		this.tipo_desenho = tipo;
		repaint();
	}
	
	public void desfazer() {
		if(aux_figura.getPonto()!=null) {
			aux_figura = new Figura();
			lixeira.clear();
		}else if(principal.size()>0) {
				lixeira.add(principal.get(principal.size()-1));
				principal.remove(principal.size()-1);
			}
		else if(lixeira.size()>0 && limpar) {
				for(int i = 0; i<lixeira.size(); i++)
					principal.add(lixeira.get(i));
				lixeira.clear();
				limpar = false;
		}
		repaint();
	}
	
	public void refazer() {
		if(aux_figura.getPonto()!=null) {
			aux_figura = new Figura();
			lixeira.clear();
		}else if(lixeira.size()>0 && !limpar) {
			principal.add(lixeira.get(lixeira.size()-1));
			lixeira.remove(lixeira.size()-1);
			limpar = false;
		}
		repaint();
	}
	
	public void limpar() {
		aux_figura = new Figura();
		if(!limpar)
			lixeira.clear();
		for(int i = 0 ; i< principal.size() ; i++)
			lixeira.add(principal.get(i));
		principal.clear();
		limpar = true;
		repaint();
	}
		
	@Override
	public void mouseDragged(MouseEvent e) {
		if(tipo_desenho == Desenho.LIVRE && e.getModifiersEx()!=4096) {
			int y = e.getY() - aux_ponto.y;
			int x = e.getX() - aux_ponto.x ;

			if(x<0) {
				if(y>0)
					aux_cord=5;
				else if(y<0) 
					aux_cord=3;
				else 
					aux_cord=4;
			}
			else if(x>0) {
				if(y>0)
					aux_cord=7;
				else if(y<0) 
					aux_cord=1;
				else 
					aux_cord=0;
			}
			else {
				if(y>0)
					aux_cord=6;
				else if(y<0) 
					aux_cord=2;
			}
			int cord[] = {aux_cord};
			aux_figura.addCoordenadas(cord);
			repaint();
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(tipo_desenho == Desenho.QUADRADO && aux_figura.getPonto()!=null) {
			int y1 = aux_figura.getPonto().y;
			int y2 = e.getY();
			int x1 = aux_figura.getPonto().x;
			int x2 = e.getX();
			int p = Math.abs(y1-y2);
			int p2 = Math.abs(x1-x2);
			if(p>p2) {
				aux_texto.setText(p+"");
				aux_figura.setPasso(p);
			}else {
				aux_texto.setText(p2+"");
				aux_figura.setPasso(p2);
			}
			if(y1<y2) {
				if(x1<x2) {
					int cord[] = {0,6,4,2};
					aux_figura.setCoordenadas(cord);
				}
				else {
					int cord[] = {4,6,0,2};
					aux_figura.setCoordenadas(cord);
				}
			}
			else {
				if(x1<x2) {
					int cord[] = {0,2,4,6};
					aux_figura.setCoordenadas(cord);
				}
				else {
					int cord[] = {2,4,6,0};
					aux_figura.setCoordenadas(cord);
				}
			}
			
			repaint();
		}
		else if(tipo_desenho == Desenho.FREEMAN) {
			int p = aux_figura.getPasso();
			int y = e.getY() - aux_ponto.y;
			int x = e.getX() - aux_ponto.x ;
			
			if(x<(-p/2)) {
				if(y>p/2) {
					aux_ponto2.setLocation(aux_ponto.x-p, aux_ponto.y+p);
					aux_cord=5;
				}
				else if(y<(-p/2)) {
					aux_ponto2.setLocation(aux_ponto.x-p, aux_ponto.y-p);
					aux_cord=3;
				}
				else {
					aux_ponto2.setLocation(aux_ponto.x-p, aux_ponto.y);
					aux_cord=4;
				}
			}
			else if(x>p/2) {
				if(y>p/2) {
					aux_ponto2.setLocation(aux_ponto.x+p, aux_ponto.y+p);
					aux_cord=7;
				}
				else if(y<(-p/2)) {
					aux_ponto2.setLocation(aux_ponto.x+p, aux_ponto.y-p);
					aux_cord=1;
				}
				else {
					aux_ponto2.setLocation(aux_ponto.x+p, aux_ponto.y);
					aux_cord=0;
				}
			}
			else {
				if(y>p/2) {
					aux_ponto2.setLocation(aux_ponto.x, aux_ponto.y+p);
					aux_cord=6;
				}
				else if(y<(-p/2)) {
					aux_ponto2.setLocation(aux_ponto.x, aux_ponto.y-p);
					aux_cord=2;
				}
			}
			mousemoved = true;
			repaint();
		}
		else if(tipo_desenho == Desenho.TRIANGULO  && aux_figura.getPonto()!=null) {
			int x1 = aux_figura.getPonto().x;
			int y1 = aux_figura.getPonto().y;
			int x2 = e.getX();
			int y2 = e.getY();
			int x = x2-x1;
			int y = y2-y1;
			int xd = Math.abs(x);
			int yd = Math.abs(y);
			
			if(xd>yd && yd<(xd/2)) {
				aux_figura.setPasso(xd/2);
				if(y2>y1) {
					if(x2>x1) {
						int cord[] = {0,0,5,3};
						aux_figura.setCoordenadas(cord);
					}else {
						int cord[] = {4,4,7,1};
						aux_figura.setCoordenadas(cord);
					}
				}else {
					if(x2>x1) {
						int cord[] = {0,0,3,5};
						aux_figura.setCoordenadas(cord);
					}else {
						int cord[] = {4,4,1,7};
						aux_figura.setCoordenadas(cord);
					}
				}
				
			}
			else if(yd>xd && xd<(yd/2)) {
				aux_figura.setPasso(yd/2);
				if(y2>y1) {
					if(x2>x1) {
						int cord[] = {6,6,1,3};
						aux_figura.setCoordenadas(cord);
					}else {
						int cord[] = {6,6,3,1};
						aux_figura.setCoordenadas(cord);
					}
				}else {
					if(x2>x1) {
						int cord[] = {2,2,7,5};
						aux_figura.setCoordenadas(cord);
					}else {
						int cord[] = {2,2,5,7};
						aux_figura.setCoordenadas(cord);
					}
				}
			}else {
				if(xd>yd)
					aux_figura.setPasso(xd);
				else
					aux_figura.setPasso(yd);
				
				if((x*y)>0) {
					if(x>0) {
						if(y<x) {
							int cord[] = {0,6,3};
							aux_figura.setCoordenadas(cord);
						}else {
							int cord[] = {6,0,3};
							aux_figura.setCoordenadas(cord);
						}
					}else {
						if(x<y) {
							int cord[] = {4,2,7};
							aux_figura.setCoordenadas(cord);
						}else {
							int cord[] = {2,4,7};
							aux_figura.setCoordenadas(cord);
						}
					}
				}else {
					if(x>0) {
						if(x>(Math.abs(y))) {
							int cord[] = {0,2,5};
							aux_figura.setCoordenadas(cord);
						}else {
							int cord[] = {2,0,5};
							aux_figura.setCoordenadas(cord);
						}
					}else {
						if(y>(Math.abs(x))) {
							int cord[] = {6,4,1};
							aux_figura.setCoordenadas(cord);
						}else {
							int cord[] = {4,6,1};
							aux_figura.setCoordenadas(cord);
						}
					}
				}
			}
			repaint();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		requestFocus();
		if(e.isMetaDown() && tipo_desenho!=Desenho.FREEMAN ) {
			aux_figura = new Figura();
			if(aux_figura.getPonto()!=null)
				lixeira.clear();
			removePainelAuxiliar();
			repaint();
		}
		else if (e.isMetaDown()) {
			newFigura();
		}
		else if(tipo_desenho == Desenho.FREEMAN) {
			if(aux_figura.getPonto()==null) {
				aux_figura.setPonto(e.getPoint());
				aux_label.setText("Passo");
				addPainelAuxiliar();
			}
			else if(aux_figura.getPasso()>0) {
				int cord[] = {aux_cord};
				aux_figura.addCoordenadas(cord);
				repaint();
			}	
		}
		else if(tipo_desenho == Desenho.QUADRADO) {
			if(aux_figura.getPonto()==null) {
				aux_figura.setPonto(e.getPoint());
				lixeira.clear();
				aux_label.setText("Aresta");
				addPainelAuxiliar();
			}
			else
				newFigura();			}
		else if(tipo_desenho == Desenho.TRIANGULO) {
			if(aux_figura.getPonto()==null) {
				aux_figura.setPonto(e.getPoint());
				lixeira.clear();
			}
			else 
				newFigura();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(tipo_desenho == Desenho.LIVRE && aux_figura.getPonto()==null) {
			aux_figura.setPonto(e.getPoint());
			aux_figura.setPasso(1);
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(tipo_desenho == Desenho.LIVRE) {
			if(e.isMetaDown()) {
				aux_figura = new Figura();
				repaint();
			}
			else
				newFigura();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(tipo_desenho == Desenho.FREEMAN) {
			if(aux_figura.getPasso()==0) {
				try {
					int i = Integer.parseInt(aux_texto.getText());
					if(i<1)
						throw new NumberFormatException();
					else {
						aux_figura.setPasso(i);
						aux_label.setText("Coordenadas: ");
						requestFocus();
						aux_texto.setText("");
					}
				}catch(NumberFormatException e1) {
					aux_texto.setText("Apenas numero inteiros positivos");
				}
			}
			else {
				String s = aux_texto.getText();
				s = s.replace(" " , "");
				s = s.replace(",","");
				
				int aux[] = new int[s.length()];
				
				try {
					for(int i = 0 ; i<s.length(); i++) {
						int x = Integer.parseInt(s.substring(i, i+1));
						if(x<0 || x>7)
							throw new NumberFormatException();
						else
							aux[i] = x;
					}			
					
					requestFocus();
					aux_texto.setText("");	
					
					aux_figura.addCoordenadas(aux);
					repaint();
					
				}catch(NumberFormatException e2) {
					aux_texto.setText("Coordenada(s) Invalidas");
				}
				
				
			}
		}
		
		else if(tipo_desenho == Desenho.QUADRADO) {
			try {
				int i = Integer.parseInt(aux_texto.getText());
				if(i<1)
					throw new NumberFormatException();
				else {
					aux_figura.setPasso(i);
					newFigura();
				}
			}catch(NumberFormatException e3) {
				aux_texto.setText("Apenas numero inteiros positivos");
			}
			
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(tipo_desenho==Desenho.FREEMAN && aux_figura.getPonto()!=null && aux_figura.getPasso()>0) {
			try {
				int aux = Integer.parseInt(""+e.getKeyChar());
				if(aux<0 || aux>7)
					throw new NumberFormatException();
				int cord[] = {aux};
				aux_figura.addCoordenadas(cord);
				mousemoved = false;
				repaint();
			}catch(NumberFormatException e1) {}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	public static void main(String[] args) {
		JFrame tela = new JFrame("PIntando");
		PainelDesenho painel = new PainelDesenho();
		PainelBotoes botao = new PainelBotoes(painel);
		tela.add(painel);
		tela.add(botao,BorderLayout.NORTH);
		tela.setSize(800,800);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setVisible(true);
		
	}

	
}
