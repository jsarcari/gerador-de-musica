package classes;

import java.util.List;

public class Conversao {
	private String texto;
	private ConfiguracaoGeral configuracao;

	public void aumentarBpm() {
		int atual = this.configuracao.getBpm();
		this.configuracao.setBpm(atual + 50);
	}

	public void diminuirBpm() {
		int atual = this.configuracao.getBpm();
		this.configuracao.setBpm(atual - 50);
	}

	public void aumentarVolume() {
		int atual = this.configuracao.getVolume();
		this.configuracao.setVolume(atual*2);
	}
	
	public void volumePadrao() {
		this.configuracao.setVolume(50);
	}
	
	public void gerarArquivo() {
		
	}

}
