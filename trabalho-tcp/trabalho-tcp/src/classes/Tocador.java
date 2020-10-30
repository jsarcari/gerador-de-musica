package classes;

import java.io.File;

import javax.sound.midi.*;

public class Tocador {
	private Conversao conversao;

	public void tocaMusica(Sequence musica) {
		Sequencer player;
		try {
			player = MidiSystem.getSequencer();
			player.open();
			player.setSequence(musica);
			player.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void baixaMusica(Sequence musica, File arquivoMidi) {
		try {
			MidiSystem.write(musica, 0, arquivoMidi);
			// player.open();
			// player.setSequence(musica);
			// player.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
