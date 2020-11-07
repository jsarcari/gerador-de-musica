package classes;

import java.io.File; //Para utilizar arquivos

import javax.sound.midi.*;

public class Tocador {
	private Sequencer player;

	// Fun��o respons�vel por tocar a m�sica
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

	// Fun��o respons�vel por fazer o download a m�sica
	public void baixaMusica(Sequence musica, File arquivoMidi) {
		try {
			MidiSystem.write(musica, 0, arquivoMidi);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Fun��o respons�vel por verificar se j� tem alguma m�sica tocando
	public boolean musicaTaTocando() {
		try {
			if (player == null) {
				return false;
			}

			// Se n�o ta aberto, ent�o n�o ta tocando
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
