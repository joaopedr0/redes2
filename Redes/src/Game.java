public class Game {
	
	public static String resolve(String [] respostas) {
		String resposta = respostas[0].concat(respostas[1]).concat(respostas[2]);
		if(resposta.equals("000") || resposta.equals("111") || resposta.equals("222") ||
				resposta.equals("012") || resposta.equals("021") || resposta.equals("102") ||
				resposta.equals("120") || resposta.equals("201") || resposta.equals("210"))
			return "Empate! Nenhum jogador venceu.";
		else if(resposta.equals("022") || resposta.equals("100") || resposta.equals("211"))
			return "O jogador 1 venceu!";
		else if(resposta.equals("020") || resposta.equals("010") || resposta.equals("121"))
			return "O jogador 2 venceu!";
		else if(resposta.equals("002") || resposta.equals("001") || resposta.equals("112"))
			return "O jogador 3 venceu!";
		else if(resposta.equals("002") || resposta.equals("110") || resposta.equals("221"))
			return "Os jogadores 1 e 2 venceram!";
		else if(resposta.equals("200") || resposta.equals("011") || resposta.equals("122"))
			return "Os jogadores 2 e 3 venceram!";
		else if(resposta.equals("020") || resposta.equals("101") || resposta.equals("212"))
			return "Os jogadores 1 e 3 venceram!";
		else
			return "Erro.";
	}
	
	public static void main(String [] args){
		
	}
}
