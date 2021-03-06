import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Servidor {

    public static void main(String[] args) throws IOException {
        // inicia o servidor
        new Servidor(12345).executa();
    }

    private int porta;
    private List<PrintStream> clientes;
    private String [] respostaJogadores = new String [3];
    private boolean [] ready = new boolean [3];
    private boolean [] jogou = new boolean [4];
    int matrizResp[][] = new int[3][3];

    /**
     * 
     */
    public Servidor (int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
        ready[0] = false;
        ready[1] = false;
        ready[2] = false;
        jogou[0] = false;
        jogou[1] = false;
        jogou[2] = false;

        for (int a = 0; a < 3; a++){
            for(int b = 0; b < 3; b++){
                matrizResp[a][b] = 0;
            }
        }
    }

    /**
     * 
     * @throws IOException
     */
    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Aguardando conexao com os jogadores.");

        int i = 1;
        while (i < 4) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            System.out.println("Jogador  " + i + " conectado!");
            System.out.println(i);

            // adiciona saida do cliente à lista
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            this.clientes.add(ps);

            // cria tratador de cliente numa nova thread
            TrataCliente tc = new TrataCliente(cliente.getInputStream(), this, i);
            new Thread(tc).start();
            i++;
            System.out.println(tc);
        }
        
        while(true) {
        	if(jogoAcabou()) {
        		String mensagem = play();
        		distribuiMensagem(mensagem);
        	}
        }

    }
    
    /**
     * 
     * @return
     */
    public synchronized String play() {
        
        int largest = 0;
        for ( int i = 0; i < 3; i++ )
        {
           if (respostaJogadores[i] == "0"){
               if (respostaJogadores[i+1] == "1"){

               }
           }
        }
        return ("quem ganhou foi"); // position of the first largest found
	}
    /**
     * @param resposta
     * @param numeroJogador
     */

    public void respostaJogador(String resposta, int numeroJogador) {
       // System.out.println(resposta+"Num"+numeroJogador);
        respostaJogadores[numeroJogador] = resposta;

    	jogou[numeroJogador] = true;
    }
    
    /**
     * Verifica se é verdadeiro
     */
    public boolean jogoAcabou() {
    	for(int i = 0; i <= 4; i++) {
    		if(jogou[i] == false)
    			return false;
    	}
    	return true;
    }

    public void todosJogaram(int numeroJogador){
        jogou[numeroJogador] = true;
    }
    /**
     * 
     * @param msg
     */
    public void distribuiMensagem(String msg) {
        // envia msg para todo mundo
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }
    
    /**
     * 
     * @return
     */
    public boolean isReady() {
    	for(int i = 0; i < 3; i++) {
    		if(ready[i] == false)
    			return false;
    	}
    	return true;
    }
    
    /**
     * 
     */
    public void jogadorPronto(int numeroJogador) {
    	ready[numeroJogador] = true;
    }
    
}