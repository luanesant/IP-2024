import java.util.Scanner;
import java.util.Random;

public class MatrizLaplace{
    private int[][]  matriz;
    private int ordem;

    public MatrizLaplace(int ordem){
        this.setOrdem(ordem);
        this.matriz = new int[this.getOrdem()][this.getOrdem()];
    }

// Sets 
    public void setElemento(int linha, int coluna, int elemento){
        this.matriz[linha][coluna] = elemento;
    }

    public void setOrdem(int ordem){
        this.ordem = ordem;
    }

// Gets 
    public int getElemento(int linha, int coluna){
       return this.matriz[linha][coluna];
    }

    public int getOrdem(){
        return this.ordem;
    }
    
// Methods void
    public void imprimeMatriz(){
        for(int i = 0; i < this.getOrdem(); i++){
           for(int j = 0; j < this.getOrdem(); j++){
                System.out.print(" " + this.getElemento(i, j));
            } 
            System.out.println();
        }
    }

	public void initRandom(){
		int entrada;
		Random gerador = new Random();
        for(int linha = 0; linha < this.getOrdem(); linha++){
			for(int coluna = 0; coluna < this.getOrdem(); coluna++){
				entrada = gerador.nextInt(this.getOrdem()*this.getOrdem());
				this.setElemento(linha,coluna,entrada);
			}
		}
	}

    public void checkLinhaComMaisZero(){
        int linhaMaiorQtdZero = 0;
        int contaQtdDeZeroLinha = 0;
        int qtdDeZeroLinha = 0;

        for(int linha = 0; linha < this.getOrdem(); linha++){
			for(int coluna = 0; coluna < this.getOrdem(); coluna++){
                if(this.getElemento(linha, coluna) == 0){
                    contaQtdDeZeroLinha++;
                }
			}

            if (contaQtdDeZeroLinha > qtdDeZeroLinha){
                qtdDeZeroLinha = contaQtdDeZeroLinha;
                linhaMaiorQtdZero = linha;
                contaQtdDeZeroLinha = 0;
            }
		}

        System.out.println("Linha com + 0: " + linhaMaiorQtdZero);
    }

    public static void main(String[] args){
        MatrizLaplace mat = new MatrizLaplace(5);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a ordem: ");
        mat.setOrdem(scanner.nextInt());
        int entrada;
        for(int i = 0; i < mat.getOrdem(); i++){
			for(int j = 0; j < mat.getOrdem(); j++){
				entrada = scanner.nextInt();
				mat.setElemento(i,j,entrada);
			}
		}

        mat.imprimeMatriz();
        mat.checkLinhaComMaisZero();
    }
}