package classes;

import java.io.File; //Para utilizar arquivos

import javax.sound.midi.*;

public class Tocador {
	private Sequencer player;

	// Função responsável por tocar a música
	public void tocaMusica(Sequence musica, ConfiguracaoGeral configuracaoGeral) {
		try {
			player = MidiSystem.getSequencer();
			player.open();
			player.setSequence(musica);
			player.setTempoInBPM(configuracaoGeral.getBpm());
			player.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Função responsável por fazer o download a música
	public void baixaMusica(Sequence musica, File arquivoMidi) {
		try {
			MidiSystem.write(musica, 0, arquivoMidi);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Função responsável por verificar se já tem alguma música tocando
	public boolean musicaTaTocando() {
		try {
			if (player == null) {
				return false;
			}

			// Se não ta aberto, então não ta tocando
			if (!player.isOpen()) {
				return false;
			}

			return player.isRunning();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}
}
