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
    private boolean [] jogou = new boolean [3];

    public Servidor (int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
        ready[0] = false;
        ready[1] = false;
        ready[2] = false;
        jogou[0] = false;
        jogou[1] = false;
        jogou[2] = false;
    }

    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Aguardando conexao com os jogadores.");

        int i = 1;
        while (i < 4) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            System.out.println("Jogador  " + i + " conectado!");

            // adiciona saida do cliente à lista
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            this.clientes.add(ps);

            // cria tratador de cliente numa nova thread
            TrataCliente tc = new TrataCliente(cliente.getInputStream(), this, i);
            new Thread(tc).start();
            i++;
        }
        
        while(true) {
        	if(todosJogaram()) {
        		String mensagem = play();
        		distribuiMensagem(mensagem);
        	}
        }

    }
    
    public synchronized String play() {
    	distribuiMensagem("Todos os jogadores estao prontos!");
    	distribuiMensagem("Selecione uma opcao:");
    	distribuiMensagem("0: Pedra \t 1: Papel \t 2: Tesoura");
    	
		if(respostaJogadores.equals("000") || respostaJogadores.equals("111") || respostaJogadores.equals("222") ||
				respostaJogadores.equals("012") || respostaJogadores.equals("021") || respostaJogadores.equals("102") ||
				respostaJogadores.equals("120") || respostaJogadores.equals("201") || respostaJogadores.equals("210"))
			return "Empate! Nenhum jogador venceu.";
		else if(respostaJogadores.equals("022") || respostaJogadores.equals("100") || respostaJogadores.equals("211"))
			return "O jogador 1 venceu!";
		else if(respostaJogadores.equals("020") || respostaJogadores.equals("010") || respostaJogadores.equals("121"))
			return "O jogador 2 venceu!";
		else if(respostaJogadores.equals("002") || respostaJogadores.equals("001") || respostaJogadores.equals("112"))
			return "O jogador 3 venceu!";
		else if(respostaJogadores.equals("002") || respostaJogadores.equals("110") || respostaJogadores.equals("221"))
			return "Os jogadores 1 e 2 venceram!";
		else if(respostaJogadores.equals("200") || respostaJogadores.equals("011") || respostaJogadores.equals("122"))
			return "Os jogadores 2 e 3 venceram!";
		else if(respostaJogadores.equals("020") || respostaJogadores.equals("101") || respostaJogadores.equals("212"))
			return "Os jogadores 1 e 3 venceram!";
		else
			return "Erro.";
	}
    
    public void respostaJogador(String resposta, int numeroJogador) {
    	respostaJogadores[numeroJogador] = resposta;
    	jogou[numeroJogador] = true;
    	ready[numeroJogador] = false;
    }
    
    public boolean todosJogaram() {
    	for(int i = 0; i < 3; i++) {
    		if(jogou[i] == false)
    			return false;
    	}
    	return true;
    }

    public void distribuiMensagem(String msg) {
        // envia msg para todo mundo
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }
    
    public boolean isReady() {
    	for(int i = 0; i < 3; i++) {
    		if(ready[i] == false)
    			return false;
    	}
    	return true;
    }
    
    public void jogadorPronto(int numeroJogador) {
    	ready[numeroJogador] = true;
    }
    
}