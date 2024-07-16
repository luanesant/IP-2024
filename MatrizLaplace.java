import java.util.Random;
import java.util.Scanner;

public class MatrizLaplace{
    private int[][] matriz;
    private int ordem;
    private int linhaComMaisZero, colunaComMaisZero;

    public MatrizLaplace(int ordem){
        this.setOrdem(ordem);
        this.matriz = new int[this.getOrdem()][this.getOrdem()];
        this.setLinhaComMaisZero(0);
        this.setColunaComMaisZero(0);
    }

// Sets 
    public void setElemento(int linha, int coluna, int elemento){
        this.matriz[linha][coluna] = elemento;
    }

    public void setOrdem(int ordem){
        this.ordem = ordem;
    }

    public void setLinhaComMaisZero(int linhaComMaisZero){
        this.linhaComMaisZero = linhaComMaisZero;
    }

    public void setColunaComMaisZero(int colunaComMaisZero){
        this.colunaComMaisZero = colunaComMaisZero;
    }

// Gets 
    public int getElemento(int linha, int coluna){
       return this.matriz[linha][coluna];
    }

    public int getOrdem(){
        return this.ordem;
    }
    
    public int getLinhaComMaisZero(){
        return this.linhaComMaisZero;
    }

    public int getColunaComMaisZero(){
        return this.colunaComMaisZero;
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

    public void checkLinhaComMaisZero(MatrizLaplace mat){
        int linhaMaiorQtdZero = 0;
        int contaQtdDeZeroLinha = 0;
        int qtdDeZeroLinha = 0;

        for(int linha = 0; linha < mat.getOrdem(); linha++){
			for(int coluna = 0; coluna < mat.getOrdem(); coluna++){
                if(this.getElemento(linha, coluna) == 0){
                    contaQtdDeZeroLinha++;
                }
                if (contaQtdDeZeroLinha > qtdDeZeroLinha){
                    qtdDeZeroLinha = contaQtdDeZeroLinha;
                    linhaMaiorQtdZero = linha;
                    contaQtdDeZeroLinha = 0;
                }
			}
		}

        if (linhaMaiorQtdZero>0) {
            mat.setLinhaComMaisZero(linhaMaiorQtdZero);
        }
        
    }


    public boolean verificaTemZero(MatrizLaplace mat){
        boolean temZero = false;

        for(int linha = 0; linha < this.getOrdem(); linha++){
			for(int coluna = 0; coluna < this.getOrdem(); coluna++){
                if(this.getElemento(linha, coluna) == 0){
                    temZero = true;
                    break;
                }
			}
		}

        return temZero;
    }

	public void copiaMatrizGrandeParaMatrizPequena(MatrizLaplace matGrande, MatrizLaplace matPequena, int linhaProibida, int colunaProibida){

		int contLG,contCG,contLP,contCP;
		contLP = 0;	
		for(contLG = 0; contLG < matGrande.getOrdem(); contLG++){
			if(contLG != linhaProibida){
				contCP = 0;
				for(contCG =0; contCG < matGrande.getOrdem(); contCG++){
					if(contCG != colunaProibida){	
								matPequena.setElemento(contLP,contCP,matGrande.getElemento(contLG, contCG));
						contCP++;
					}
				}
				contLP++;
			}
		}
	}
	
	public float detLaPlaceRecursivo(MatrizLaplace mat, int linha, int coluna){
		MatrizLaplace matLinha = new MatrizLaplace(mat.getOrdem()-1);	
		
		float acum = 0.0f;
		int contColuna;
		for(contColuna = 0; contColuna < mat.getOrdem(); contColuna++){	
			matLinha.copiaMatrizGrandeParaMatrizPequena(mat,matLinha,linha,contColuna);
			System.out.println(linha + " , " + contColuna);
			matLinha.imprimeMatriz();
			acum = acum +
			      (float)( mat.getElemento(linha,contColuna) *
			        Math.pow(-1, linha+contColuna) *
			        detLaPlace(matLinha));
		}	
		return acum;	
	}
	
	public float detLaPlace(MatrizLaplace mat){
	
		int contOrdem;
		float resultado = 0;
	
		if(mat.getOrdem() == 1){
			resultado = mat.getElemento(0,0);
		}
		else{
			resultado = mat.detLaPlaceRecursivo(mat, mat.getLinhaComMaisZero(), 0);
		}
		
		return resultado;
	}

    public float detLaPlace(){
		return this.detLaPlace(this);
	}

    public static void main(String[] args){
        MatrizLaplace mat = new MatrizLaplace(5);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a ordem: ");
        mat.setOrdem(scanner.nextInt());
       /*   int entrada;
       for(int i = 0; i < mat.getOrdem(); i++){
			for(int j = 0; j < mat.getOrdem(); j++){
				entrada = scanner.nextInt();
				mat.setElemento(i,j,entrada);
			}
		} */
        mat.initRandom();
        mat.imprimeMatriz();
        if(mat.verificaTemZero(mat)){
            mat.checkLinhaComMaisZero(mat);
        }

        System.err.println("Linha com + 0: " + mat.getLinhaComMaisZero());
        System.out.println("Determinante: " + mat.detLaPlace());
    }
}