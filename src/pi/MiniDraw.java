/**
 * A classe que dispara a aplicação pela instanciação de um objeto InterfaceGrafica.
 * 
 * @author  Carlos Menezes, simplificando um projeto do livro "Objetos, Abstração,
 * 			Estruturas de Dados e Projeto usando Java versão 5.0", de Elliot B. Koffman
 * 			e Paul A. T. Wolfgang, Editora LTC.
 * @see     InterfaceGrafica
 */

public class MiniDraw {
	/** Método main. Esse método instancia um objeto da classe InterfaceGrafica e o exibe.
        @param args Um vetor de Strings (não usado)
	 */
	public static void main(String args[])
	{
		InterfaceGrafica aplicacaoDeDesenho = new InterfaceGrafica();
		aplicacaoDeDesenho.setVisible(true);
	}
}