package ADO1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class POO_ADO1 {

    public static void main(String[] args) throws IOException {
        String linha_1 = null;

        //indica separação de estado
        String conteudo = "";

        FileReader arq_1 = new FileReader("pib.txt");
        BufferedReader lerArq_1 = new BufferedReader(arq_1);

        //loop para ler cada linha até o fim do arquivo
        while ((linha_1 = lerArq_1.readLine()) != null) {
            
            //Divide a linha em duas partes a partir do ';'
            String[] stringDividida = linha_1.split(";");

            double v = Double.parseDouble(stringDividida[1]);

            //Calcula porcentagem do PIB
            double porc = (v / totalPercePib()) * 100;
            porc = Double.valueOf(String.format(Locale.US, "%.2f", porc));

            System.out.println(stringDividida[0] + ": " + porc + "%");
            conteudo += linha_1 + ": " + porc + "% \r\n";
        }
        lerArq_1.close();

        //prencherVetoresEstado();
        sumarizaPib();

        //Gerar arquivo de saída
        escreveSaida(somaRegiao);
    }

    //soma valor total do pib
    public static Double totalPercePib() throws FileNotFoundException, IOException {
        String linha = null;

        FileReader arqPib = new FileReader("pib.txt");
        BufferedReader lerArq = new BufferedReader(arqPib);
        double totalPerc = 0;

        while ((linha = lerArq.readLine()) != null) {
            String[] stringDividida = linha.split(";");
            double v = Double.parseDouble(stringDividida[1]);
            totalPerc = totalPerc + v;
        }
        
        return totalPerc;
    }

    //recebe o nome dos estados
    static String[] norte = new String[8];
    static String[] nordeste = new String[10];
    static String[] centro = new String[5];
    static String[] sudeste = new String[5];
    static String[] sul = new String[4];

    //vetor para somar por regiões
    static double[] somaRegiao = new double[5];

    // 0 = norte
    // 1 = nordeste
    // 2 = sul
    // 3 = sudeste
    // 4 = centro      
    public static void preencheVetoresEstado() throws FileNotFoundException, IOException {

        String linha = null;
        String conteudo = "";

        FileReader arqRegioes = new FileReader("regioes.txt");
        BufferedReader lerArquivo = new BufferedReader(arqRegioes);

        //contador para percorrer vetor
        int cont;

        //enquanto tiver linha para ser lida
        while ((linha = lerArquivo.readLine()) != null) {

            try {
                cont = -1;

                if (linha.contains("Norte")) {
                    while (!linha.equals("")) {

                        cont = cont + 1;
                        norte[cont] = linha;

                        linha = lerArquivo.readLine();

                    }
                } else if (linha.contains("Nordeste")) {
                    while (!linha.equals("")) {

                        cont = cont + 1;
                        nordeste[cont] = linha;

                        linha = lerArquivo.readLine();
                    }
                } else if (linha.contains("Sudeste")) {
                    while (!linha.equals("")) {

                        cont = cont + 1;
                        sudeste[cont] = linha;

                        linha = lerArquivo.readLine();
                    }
                } else if (linha.contains("Sul")) {
                    while (!linha.equals("")) {

                        cont = cont + 1;
                        sul[cont] = linha;

                        linha = lerArquivo.readLine();
                    }
                } else if (linha.contains("Centro-Oeste")) {
                    while (!linha.equals("") || linha == null) {

                        if (linha == null) {

                        } else {
                            cont = cont + 1;
                            centro[cont] = linha;

                            linha = lerArquivo.readLine();
                        }

                    }
                }
            } catch (Exception ex) {
                System.out.println("Fim do arquivo");
            }
        }
        lerArquivo.close();

    }

    public static void sumarizaPib() throws IOException {
        preencheVetoresEstado();

        String linha = null;

        String conteudo = "";

        FileReader arqPib = new FileReader("pib.txt");
        BufferedReader lerArq = new BufferedReader(arqPib);

         while ((linha = lerArq.readLine()) != null) {
            //Divide em duas partes a partir do ';'
            String[] stringDividida = linha.split(";");

            for (int i = 0; i < norte.length; i++) {
                //Verificar se a String do vetor não é o próprio estado
                if (!norte[i].contains("Norte")) {
                    // Se existir, somar com o pib da região
                    if (stringDividida[0].contains(norte[i])) {
                        somaRegiao[0] = somaRegiao[0] + Double.parseDouble(stringDividida[1]);
                    } 
                } 
            }

            for (int i = 0; i < nordeste.length; i++) {
                if (!nordeste[i].contains("Nordeste")) {
                    if (stringDividida[0].contains(nordeste[i])) {
                        somaRegiao[1] = somaRegiao[1] + Double.parseDouble(stringDividida[1]);
                    } 
                } 
            }

            for (int i = 0; i < sul.length; i++) {
                if (!sul[i].equals("Sul")) {
                    if (stringDividida[0].contains(sul[i])) {
                        somaRegiao[2] = somaRegiao[2] + Double.parseDouble(stringDividida[1]);
                    } 
                } 
            }

            for (int i = 0; i < sudeste.length; i++) {
                if (!sudeste[i].equals("Sudeste")) {
                    if (stringDividida[0].equals(sudeste[i])) {
                        somaRegiao[3] = somaRegiao[3] + Double.parseDouble(stringDividida[1]);
                    } 
                } 
            }

            for (int i = 0; i < centro.length; i++) {
                if (!centro[i].equals("Centro")) {
                    if (stringDividida[0].equals(centro[i])) {
                        somaRegiao[4] = somaRegiao[4] + Double.parseDouble(stringDividida[1]);
                    } 
                } 
            }
        }
         
        lerArq.close();
    }

    public static void escreveSaida(double[] soma) throws IOException {
        String arqSaida = "saida.txt";
        
        try {
            FileWriter fileWriter = new FileWriter(arqSaida);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < soma.length; i++) {
                String nomeEst = null;
                switch (i) {
                    case 0:
                        nomeEst = "Norte = ";
                        break;
                    case 1:
                        nomeEst = "Nordeste = ";
                        break;
                    case 2:
                        nomeEst = "Sul = ";
                        break;
                    case 3:
                        nomeEst = "Sudeste = ";
                        break;
                    case 4:
                        nomeEst = "Centro - Oeste = ";
                        break;

                }
                bufferedWriter.write(nomeEst + String.valueOf(soma[i]));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException ex) {
            System.out.println("Erro de escrita em '" + arqSaida + "'");
        }
    }

}
