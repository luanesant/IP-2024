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

    public void checkColunaComMaisZero(MatrizLaplace mat){
        int colunaMaiorQtdZero = 0;
        int contaQtdDeZeroColuna = 0;
        int qtdDeZeroColuna = 0;

        for(int coluna = 0; coluna < mat.getOrdem(); coluna++){
            for(int linha = 0; linha < mat.getOrdem(); linha++){
                if(this.getElemento(linha, coluna) == 0){
                    contaQtdDeZeroColuna++;
                }
                if (contaQtdDeZeroColuna > qtdDeZeroColuna){
                    qtdDeZeroColuna = contaQtdDeZeroColuna;
                    colunaMaiorQtdZero = coluna;
                    contaQtdDeZeroColuna = 0;
                }
			}
		}

        if (colunaMaiorQtdZero>0) {
            mat.setColunaComMaisZero(colunaMaiorQtdZero);
        }
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
	
	public float detLaPlaceRecursivoLinha(MatrizLaplace mat, int linha){
		MatrizLaplace matLinha = new MatrizLaplace(mat.getOrdem()-1);	
		
		float acum = 0.0f;

		for(int contColuna = 0; contColuna < mat.getOrdem(); contColuna++){	
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
    	
	public float detLaPlaceRecursivoColuna(MatrizLaplace mat, int coluna){
		MatrizLaplace matLinha = new MatrizLaplace(mat.getOrdem()-1);	
		
		float acum = 0.0f;

		for(int linha = 0; linha < mat.getOrdem(); linha++){	
			matLinha.copiaMatrizGrandeParaMatrizPequena(mat,matLinha,linha,coluna);
			acum = acum +
			      (float)(mat.getElemento(linha,coluna) *
			        Math.pow(-1, linha+coluna) *
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
                if (mat.getLinhaComMaisZero() > mat.getColunaComMaisZero()){       
			        resultado = mat.detLaPlaceRecursivoLinha(mat, mat.getLinhaComMaisZero());
                }else{
                    resultado = mat.detLaPlaceRecursivoColuna(mat, mat.getColunaComMaisZero());
                }
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

      //  mat.initRandom();
        for(int linha = 0; linha < mat.getOrdem(); linha++){
			for(int coluna = 0; coluna < mat.getOrdem(); coluna++){
                mat.setElemento(linha, coluna, scanner.nextInt());
			}
		}

        mat.checkLinhaComMaisZero(mat);
        mat.checkColunaComMaisZero(mat);

        mat.imprimeMatriz();
        
        System.out.println("Determinante: " + mat.detLaPlace());
        System.err.println("Linha com + 0: " + mat.getLinhaComMaisZero());
        System.err.println("Coluna com + 0: " + mat.getColunaComMaisZero());

    }
}