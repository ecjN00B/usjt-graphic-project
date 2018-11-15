import java.awt.Color;
import java.awt.Point;

public class Figura {
	private Point ponto_inicial;
	private int passo;
	private int coordenadas[];
	private Color cor;
	
	public Figura() {
		setCor(Color.BLACK);
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

	public int[] getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(int coordenadas[]) {
		this.coordenadas=coordenadas;
	}

	public void addCoordenadas(int cords[]) {
		
		if(coordenadas == null)
			coordenadas = cords;
		else {
			int finalcords[] = new int[coordenadas.length+cords.length];
			for(int i = 0 ; i<coordenadas.length; i++)
				finalcords[i] = coordenadas[i];
			for(int i = 0; i<cords.length; i++)
				finalcords[coordenadas.length+i] = cords[i];
			coordenadas = finalcords;
		}
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
}
