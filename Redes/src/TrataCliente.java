import java.io.InputStream;
import java.util.Scanner;

public class TrataCliente implements Runnable {
	private InputStream cliente;
    private Servidor servidor;
    private int numeroJogador;
    private String [] respostaJogadores = new String [3];

    public TrataCliente(InputStream cliente, Servidor servidor, int numeroJogador) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.numeroJogador = numeroJogador;
    }

    public void run() {
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {
            if(servidor.isReady()) {
                if(!servidor.jogoAcabou()){
                	String linha = s.nextLine();

                    if(linha.equals("0")){
                        servidor.distribuiMensagem("O jogador " + numeroJogador + " jogou Pedra");
                        servidor.respostaJogador("0", numeroJogador-1);
                    }
                    else if(linha.equals("1")){
                        servidor.distribuiMensagem("O jogador " + numeroJogador + " jogou Papel");
                        servidor.respostaJogador("1", numeroJogador-1);
                    }
                    else if(linha.equals("2")){
                        servidor.distribuiMensagem("O jogador " + numeroJogador + " jogou Tesoura");
                        servidor.respostaJogador("2", numeroJogador-1);
                    }
                } else {
                    servidor.play();
                }
                // String resp = s.nextLine();
                // System.out.println(resp);
                // servidor.distribuiMensagem(resp);
                // servidor.respostaJogador(resp, numeroJogador - 1);   
            } else {
            	String linha = s.nextLine();
                if(linha.equals("ready")) {
                    servidor.jogadorPronto(numeroJogador - 1);
                    servidor.distribuiMensagem("O jogador " + numeroJogador + " esta pronto.");
                    servidor.distribuiMensagem("Num"+numeroJogador);

                    if(servidor.isReady()){
                        servidor.distribuiMensagem("Todos os jogadores estao prontos!");
                        servidor.distribuiMensagem("Selecione uma opcao:");
                        servidor.distribuiMensagem("0: Pedra \t 1: Papel \t 2: Tesoura");
                    }
                }    
            }
        }
        s.close();
    }
}