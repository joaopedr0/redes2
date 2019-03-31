import java.io.InputStream;
import java.util.Scanner;

public class TrataCliente implements Runnable {
	private InputStream cliente;
    private Servidor servidor;
    private int numeroJogador;
    private String [] respostaJogadores = new String [3];
    private boolean [] ready = new boolean [3];

    public TrataCliente(InputStream cliente, Servidor servidor, int numeroJogador) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.numeroJogador = numeroJogador;
        ready[0] = false;
        ready[1] = false;
        ready[2] = false;
        
    }

    public void run() {
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {
        	if(s.nextLine().equals("ready")) {
        		servidor.jogadorPronto(numeroJogador - 1);
                servidor.distribuiMensagem("O jogador " + numeroJogador + " esta pronto.");
                servidor.distribuiMensagem("Num"+numeroJogador);
        	}
        	if(servidor.isReady()) {
                servidor.distribuiMensagem("Todos os jogadores estao prontos!");
    	        servidor.distribuiMensagem("Selecione uma opcao:");
                servidor.distribuiMensagem("0: Pedra \t 1: Papel \t 2: Tesoura");
                if(s.nextLine().equals("0")){
                    servidor.respostaJogador(0, numeroJogador-1);
                }
                if(s.nextLine().equals("1")){
                    servidor.respostaJogador(1, numeroJogador-1);
                }
                if(s.nextLine().equals("2")){
                    servidor.respostaJogador(2, numeroJogador-1);
                }
               
                // String resp = s.nextLine();
                // System.out.println(resp);
                // servidor.distribuiMensagem(resp);
                // servidor.respostaJogador(resp, numeroJogador - 1);   
            }
            // if(servidor.todosJogaram()){
            //     servidor.play();
            // }
        }
        s.close();
    }
    
    public void reset() {
    	for(int i = 0; i < 3; i++) {
    		ready[i] = false;
    		respostaJogadores[i] = "";
    	}
    }
}
