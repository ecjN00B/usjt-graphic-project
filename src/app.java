import java.io.IOException;

public class app{
	public static void main(String[] arg) {
		int shape[] = {3,2,2,1,0,7,6,0,0,0,2,0,7,6,6,6,5,4,2,4,4,4,6,6,5,4,3,2,2,1};
		String workSpace[][] = new String [10][10];
		int x = 2;
		int y = 6;
		int posVet=0;
		
		for (int i=0; i<10; i++)
			for(int j=0; j<10; j++)
				workSpace[i][j]=" ";
		
		drawMatriz(x,y,shape,workSpace,posVet);
		
	}
		
	static void printFrame(String m[][])
	{
		for (int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				System.out.print(m[i][j]);
			}
			System.out.println();
		}
	}
	
	static void delay()
	{
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clearScreen() 
	{  
		try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException ex) {}
	}
	
	static void drawMatriz(int x, int y, int shape[],String workSpace[][], int posVet) {
		if(posVet<shape.length) 
		switch(shape[posVet]) {
			case 0:
				if((y-1)>=0 && (y-1)<10 && x>=0 && x<10 && workSpace[y-1][x]==" ")
				{
					workSpace[y-1][x]="_";
					
					clearScreen();
					
					printFrame(workSpace);
					
					delay();
					
					x++;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}	
				else if ( (y-1)<0 || (y-1)>=10 || x<0 || x>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 1:
				if((y-1)>=0 && (y-1)<10 && x>=0 && x<10 && workSpace[y-1][x]==" ")
				{
					workSpace[y-1][x]="/";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					x++;
					y--;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}	
				else if ( (y-1)<0 || (y-1)>=10 || x<0 || x>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 2:
				if((y-1)>=0 && (y-1)<10 && x>=0 && x<10 && workSpace[y-1][x]==" ")
				{
					workSpace[y-1][x]="|";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					y--;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}	
				else if((y-1)>=0 && (y-1)<10 && (x-1)>=0 && (x-1)<10 && workSpace[y-1][x-1]==" ")
				{
					workSpace[y-1][x-1]="|";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					y--;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( (y-1)<0 || (y-1)>=10 || x<0 || (x-1)>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 3:
				if((y-1)>=0 && (y-1)<10 && (x-1)>=0 && (x-1)<10 && workSpace[y-1][x-1]==" ")
				{
					workSpace[y-1][x-1]="\\";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					x--;
					y--;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( (y-1)<0 || (y-1)>=10 || (x-1)<0 || (x-1)>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 4:
				if((y-1)>=0 && (y-1)<10 && (x-1)>=0 && (x-1)<10 && workSpace[y-1][x-1]==" ")
				{
					workSpace[y-1][x-1]="_";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					x--;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( (y-1)<0 || (y-1)>=10 || (x-1)<0 || (x-1)>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
				case 5:
				if(y>=0 && y<10 && (x-1)>=0 && (x-1)<10 && workSpace[y][x-1]==" ")
				{
					workSpace[y][x-1]="/";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
										
					x--;
					y++;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( y<0 || y>=10 || (x-1)<0 || (x-1)>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 6:
				if(y>=0 && y<10 && (x-1)>=0 && (x-1)<10 && workSpace[y][x-1]==" ")
				{
					workSpace[y][x-1]="|";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
					
					y++;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}	
				else if(y>=0 && y<10 && x>=0 && x<10 && workSpace[y][x]==" ")
				{
					workSpace[y][x]="|";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
					
					y++;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( y<0 || y>=10 || x<0 || (x-1)>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			case 7:
				if(y>=0 && y<10 && x>=0 && x<10 && workSpace[y][x]==" ")
				{
					workSpace[y][x]="\\";

					clearScreen();
					
					printFrame(workSpace);
					
					delay();
					
					x++;
					y++;
					posVet++;
					drawMatriz(x,y,shape,workSpace,posVet);
					break;
				}
				else if ( y<0 || y>=10 || x<0 || x>=10 )
				{
					System.out.println("Suas coordenadas ultrapassam os limites do desenho.\nSeu desenho termina assim!\n");
					break;
				}
				else
				{
					System.out.println("Espaco ja ocupado por outra linha!\nSeu desenho termina assim!\n");
					break;
				}
			default:
				System.out.println("Direcao invalida!\n Seu desenho se encerra neste formato!\n");	
				
		}
		

	}
	

}