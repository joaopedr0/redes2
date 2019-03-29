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
        	}
        	if(servidor.isReady()) {
        		servidor.play();
        	}
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
