package pi;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Figura {

    private Point ponto_inicial;
    private int passo;
    private ArrayList<Integer> coordenadas = new ArrayList<Integer>();
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

    public ArrayList<Integer> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(ArrayList<Integer> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public void addCoordenadas(ArrayList<Integer> cords) {

        if (this.coordenadas == null) {
            this.coordenadas = cords;
        } else {
            this.coordenadas.addAll(cords);
        }
    }

    public void addCoordenadas(int cords) {
        coordenadas.add(cords);
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
}
