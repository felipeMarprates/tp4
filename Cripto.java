import java.util.Arrays;

public class Cripto {
    private byte[] dados;
    private int chave = 9817652;// chave nao pode ter digitos repetidos

    public Cripto(byte[] aDados) {
        this.dados = aDados;
    }

    public byte[] cifrar() { // metodo retorna dados cifrado passando por 3 etapas de cifragem

        byte[] tmpBA = dados;
        tmpBA = cifrar1(tmpBA);// cifragem de vigenerem utiliza uma matriz 256 x 10 x=numeros de bytes
                               // possiveis, y= digito da chave 0 a 9
        printBytes(tmpBA, "primeira cifragem");
        tmpBA = cifrar2(tmpBA);
        printBytes(tmpBA, "segunda cifragem");
        tmpBA = cifrar3(tmpBA);
        printBytes(tmpBA, "terceira cifragem");

        return tmpBA;

    }

    private byte[] cifrar1(byte[] ent) { // cifragem de Vigenere

        byte[][] tabela = criarTabelaVigere();// "alfabeto" de bytes

        int max = ent.length;// tamanho final da chave composta
        String chavesimples = Integer.toString(chave);// chave simples tamanho da chave / chave composta
                                                      // tamanho da entrada de bytes
        String chavecomp = criarChaveComposta(chavesimples, max);

        byte[] res = cifrarvigere(ent, chavecomp, max, tabela);

        return res;
    }// percebi depois que era somente somar os valores da chave com os numeros mas
     // dessa forma funciona tambem

    private byte[][] criarTabelaVigere() {
        byte[][] tabela = new byte[256][10];
        for (int i = -128; i <= 127; i++) {
            for (int j = 0; j < 10; j++) {
                byte term = (byte) (((128 + j + i) % 256) - 128);
                // System.out.print("termo = " + term + " pos( " + (i + 128) + " , " + j + " )
                // ");
                tabela[(i + 128)][j] = term;// conversao para fazer um loop
                                            // entre bytes
                // ex: 128 + 127 + 1 = 256-> 256 % 256 = 0 ->
                // 0-128 = -128
            }

        }
        return tabela;

    }

    private String criarChaveComposta(String chavesimples, int max) {
        String chavecomposta = chavesimples;
        int tamChave = chavesimples.length();
        int j = 0;
        for (int i = tamChave - 1; i < max; i++) {
            j = j % tamChave;
            chavecomposta += chavesimples.charAt(j);
            j++;
        }
        return chavecomposta;

    }

    private byte[] cifrarvigere(byte[] ent, String chavecomposta, int max, byte[][] tabela) {
        byte[] saida = new byte[max];
        for (int i = 0; i < max; i++) {
            int num = chavecomposta.charAt(i) - '0';// transformar '9' para 9

            saida[i] = tabela[ent[i] + 128][num];

        }

        return saida;
    }

    private byte[] cifrar2(byte[] ent) {
        int max = ent.length;
        byte[][] matriz = criarMatriz(ent, max);
        String chaves = Integer.toString(chave);
        int[] instrucoes = getInstrucoes(chaves);// ordem a qual as colunas devem ficar
        // printInts(instrucoes, "instrucoees");
        byte[][] matrizReorganizada = reoganizarCol(instrucoes, matriz, max);
        byte sai[] = matrizParaArray(matrizReorganizada, max);

        return sai;
    }

    private byte[][] criarMatriz(byte[] ent, int max) {
        int numColunas = Integer.toString(chave).length();// 5
        int numLinhas = (int) Math.ceil((float) max / numColunas);// 3
        int c = 0;
        // System.out.print("\nCOl " + numColunas + " Line " + numLinhas + " Max " + max
        // + "\n");

        byte[][] ma = new byte[numLinhas][numColunas];
        System.out.print("\n");
        for (int i = 0; i < numLinhas; i++) {

            for (int j = 0; j < numColunas; j++) {

                if (c >= max) {
                    // System.out.print("(" + i + "," + j + ")=" + -1 + "\n");
                    ma[i][j] = -1;

                } else {
                    // System.out.print("(" + i + "," + j + ")=" + ent[c] + "\n");
                    ma[i][j] = ent[c];
                    c++;
                }
            }

        }
        return ma;
    }

    private int[] getInstrucoes(String achave) {
        int tamMax = achave.length();
        int[] velhaOrdem = new int[tamMax];
        for (int i = 0; i < tamMax; i++) {
            velhaOrdem[i] = achave.charAt(i) - '0';

        }
        int[] novaOrdem = new int[tamMax];
        novaOrdem = velhaOrdem.clone();
        Arrays.sort(novaOrdem);
        int[] instrucoes = new int[tamMax];
        instrucoes = novaOrdem.clone();
        for (int i = 0; i < tamMax; i++) {

            instrucoes[i] = Arrays.binarySearch(novaOrdem, velhaOrdem[i]);

        }
        return instrucoes;
    }

    private byte[][] reoganizarCol(int[] instrucoes, byte[][] matriz, int max) {
        int numColunas = Integer.toString(chave).length();// 5
        int numLinhas = (int) Math.ceil((float) max / numColunas);// 4
        byte[][] reorganizado = new byte[numLinhas][numColunas];
        byte[] res = new byte[max];
        // System.out.print("\n");

        // System.out.println("numero de linhas: " + numLinhas + "numero de colunas: " +
        // numColunas
        // + " tamanhoInstrucoes: " + instrucoes.length);

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                reorganizado[i][instrucoes[j]] = matriz[i][j];
                // System.out.println("i = linha j = coluna(" + i + "," + instrucoes[j] + ")=" +
                // matriz[i][j]
                // + "instrucao = " + instrucoes[j]);

            }

        }
        return reorganizado;
    }

    private byte[] matrizParaArray(byte[][] ma, int max) {
        int numColunas = Integer.toString(chave).length();// 5
        int numLinhas = (int) Math.ceil((float) max / numColunas);// 4
        byte[] res = new byte[max];
        int c = 0;
        for (int i = 0; i < numColunas; i++) {
            for (int j = 0; j < numLinhas; j++) {
                byte termo = ma[j][i];
                if (termo != -1) {
                    res[c] = termo;
                    // System.out.print("\n i=" + i + "j=" + j + " c =" + termo);
                    c++;
                }

            }

        }

        return res;
    }

    private byte[] matrizParaArray2(byte[][] ma, int max) { // para o metedo de coluna da cifragem 2
        int numColunas = Integer.toString(chave).length();// 5
        int numLinhas = (int) Math.ceil((float) max / numColunas);// 4
        byte[] res = new byte[max];
        int c = 0;
        for (int j = 0; j < numLinhas; j++) {
            for (int i = 0; i < numColunas; i++) {
                byte termo = ma[j][i];
                if (termo != -1) {
                    res[c] = termo;
                    // System.out.print("\n i=" + i + "j=" + j + " c =" + termo);
                    c++;
                }

            }

        }

        return res;
    }

    private byte[] cifrar3(byte[] ent) {
        return ent;
    }

    public byte[] decifrar() { // metodo retorna dados decifrado passando por 3 etapas de decifragem

        byte[] tmpBA = dados;
        tmpBA = decifrar3(tmpBA);
        printBytes(tmpBA, "decifragem 3");
        tmpBA = decifrar2(tmpBA);
        printBytes(tmpBA, "decifragem 2");
        tmpBA = decifrar1(tmpBA);
        printBytes(tmpBA, "decifragem 1");

        return tmpBA;

    }

    private byte[] decifrar1(byte[] ent) {
        int max = ent.length;
        String chavesimples = Integer.toString(chave);// chave simples tamanho da chave / chave composta
        // tamanho da entrada de bytes
        String chavecomp = criarChaveComposta(chavesimples, max);
        ent = decifrarvigere(ent, chavecomp, max);
        return ent;
    }

    private byte[] decifrarvigere(byte[] ent, String chavecomposta, int max) {
        byte[] sai = new byte[max];

        for (int i = 0; i < max; i++) {
            int term = chavecomposta.charAt(i) - '0';

            sai[i] = (byte) (ent[i] - (byte) term);

        }
        return sai;

    }

    private byte[] decifrar2(byte[] ent) {
        int max = ent.length;
        byte[][] matriz = criarMatriz(ent, max);
        String chaves = Integer.toString(chave);
        int[] deinstrucoes = getDeinstrucoes(chaves);// ordem a qual as colunas devem ficar
        // printInts(deinstrucoes, "desintrucoes:");
        matriz = decifrarColunasMatriz(matriz, ent, max, deinstrucoes);
        byte[] sai = matrizParaArray2(matriz, max);

        return sai;
    }

    private int[] getDeinstrucoes(String achave) {
        // 98176 16789 35421
        int tamMax = achave.length();
        int[] velhaOrdem = new int[tamMax];
        for (int i = 0; i < tamMax; i++) {
            velhaOrdem[i] = achave.charAt(i) - '0';

        }
        int[] novaOrdem = new int[tamMax];
        novaOrdem = velhaOrdem.clone();
        Arrays.sort(novaOrdem);
        int[] instrucoes = new int[tamMax];
        instrucoes = novaOrdem.clone();
        // printInts(velhaOrdem, "velha ordem");
        // printInts(novaOrdem, "nova ordem");

        for (int i = 0; i < tamMax; i++) {

            instrucoes[i] = pesquisarArray(velhaOrdem, novaOrdem[i]);

        }
        return instrucoes;
    }

    private byte[][] decifrarColunasMatriz(byte[][] matriz, byte[] ent, int max, int[] instrucoes) {
        int numColunas = Integer.toString(chave).length();
        int numLinhas = (int) Math.ceil((float) max / numColunas);
        int c = 0;

        for (int j = 0; j < numColunas; j++) {
            for (int i = 0; i < numLinhas; i++) {
                byte termo = matriz[i][instrucoes[j]];
                if (termo != -1 || c >= max) {
                    matriz[i][instrucoes[j]] = ent[c];
                    // System.out.print("(" + i + "," + instrucoes[j] + ")=" + ent[c] + "\n");
                    c++;

                } else {
                    i = numLinhas;

                }
            }
        }
        return matriz;
    }

    private byte[] decifrar3(byte[] ent) {
        return ent;
    }

    private int pesquisarArray(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                return i;
            }
        }
        return -1;
    }

    private void printBytes(byte[] printar, String msg) {
        System.out.println("\n-----------------------------------------\n" + msg);
        for (int i = 0; i < printar.length; i++) {

            System.out.print("\t" + printar[i] + ",");
        }
        System.out.println("\n-----------------------------------------");
    }

    private void printInts(int[] printar, String msg) {
        System.out.println("\n-----------------------------------------\n" + msg);
        for (int i = 0; i < printar.length; i++) {

            System.out.print("\t" + printar[i] + ",");
        }
        System.out.println("\n-----------------------------------------");
    }

}
