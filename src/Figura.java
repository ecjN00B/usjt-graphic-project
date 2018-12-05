import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Figura implements Serializable{
	private static final long serialVersionUID = 1L;
	private Point ponto_inicial;
	private int passo;
	private int codigo[];
	private Color cor;
	
	public Figura(Color cor) {
		setCor(cor);
	}

	public Point getPonto() {
		return ponto_inicial;
	}

	public void setPonto(Point ponto_inicial) {
		this.ponto_inicial = ponto_inicial;
	}

	public int getPasso() {
		return passo;
	}

	public void setPasso(int passo) {
		this.passo = passo;
	}
	
	
	public int[] getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo[]) {
		this.codigo=codigo;
	}
	
	public void addCodigo(int codigos[]) {
		
		if(codigo == null)
			codigo = codigos;
		else {
			int finalcods[] = new int[codigo.length+codigos.length];
			for(int i = 0 ; i<codigo.length; i++)
				finalcods[i] = codigo[i];
			for(int i = 0; i<codigos.length; i++)
				finalcods[codigo.length+i] = codigos[i];
			codigo = finalcods;
		}
	}
	
	public void addCodigo(int cod) {
		int codigos[] = {cod};
		if(codigo == null)
			codigo = codigos;
		else {
			int finalcods[] = new int[codigo.length+codigos.length];
			for(int i = 0 ; i<codigo.length; i++)
				finalcods[i] = codigo[i];
			for(int i = 0; i<codigos.length; i++)
				finalcods[codigo.length+i] = codigos[i];
			codigo = finalcods;
		}
	}
	
	public void removerCodigo() {
		if(codigo.length == 1)
			codigo = null;		
		else{
			int finalcods[] = new int[codigo.length-1];
			for(int i = 0 ; i<finalcods.length; i++)
				finalcods[i] = codigo[i];
			codigo = finalcods;
		}
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
}
