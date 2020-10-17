package classes;

import javax.sound.midi.*;

public class Conversao {
	private String texto;
	private ConfiguracaoGeral configuracao;
	private Sequence sequencia;
	private int notaAtual; //Para armazenar a nota mais recente
	private int notaAntiga; //Para armazenar a nota antiga
	
	private int octave; //Distancia de uma nota para a mesma ocorrência da mesma. Ex: A B C D E F G A B...
	private int tick;
	private int time = 4;

	//Quando aumentar ou diminuir o Octave fazer + ou - 12
	
	//Notas musicais -- ACHO QUE DA PRA OTIMIZAR...
	private int DO;
	private int RE;
	private int MI;
	private int FA;
	private int SOL;
	private int LA;
	private int SI;
	
	public Conversao(ConfiguracaoGeral configuracaoGeral, String conteudoTexto) {
		this.configuracao = configuracaoGeral;
		this.setTexto(conteudoTexto);
		this.setOctave(4); //Valor inicial
		this.setTick(1); //Tick
		this.inicializaNotas(); //Inicia os valores das notas
	}
	
	private void inicializaNotas()
	{
		this.setDO();
		this.setRE();
		this.setMI();
		this.setFA();
		this.setSOL();
		this.setLA();
		this.setSI();
	}

	private void aumentarBpm() {
		int atual = this.configuracao.getBpm();
		this.configuracao.setBpm(atual + 50);
	}

	private void diminuirBpm() {
		int atual = this.configuracao.getBpm();
		this.configuracao.setBpm(atual - 50);
	}

	private void aumentarVolume() {
		int atual = this.configuracao.getVolume();
		this.configuracao.setVolume(atual * 2);
	}

	private void volumePadrao() {
		this.configuracao.setVolume(50);
	}
	
	private int getNotaAtual() {
		return notaAtual;
	}

	private void setNotaAtual(int notaAtual) {
		this.notaAtual = notaAtual;
	}
	
	private int getNotaAntiga() {
		return notaAntiga;
	}

	private void setNotaAntiga() {
		this.notaAntiga = this.getNotaAtual();
	}

	private void trocaInstrumento() {
		/*
		 * String instrumentoAtual = configuracao.getInstrumento();
		 * switch(instrumentoAtual) { case "Acoustic Grand Piano":
		 * configuracao.setInstrumento("Harpsichord"); break; case "Harpsichord": break;
		 * case "Electric Grand Piano": break;
		 * 
		 * case "Xylophone": break; case "Church Organ": break; case
		 * "Acoustic Guitar (nylon)": break; case "Acoustic Bass": break; case "Violin":
		 * break; case "Trumpet": break; case "Ocarina": break; }
		 */
	}

	private boolean verificaNotaMusical(char letra) {
		switch (letra) {
		// Caso seja alguma nota musical retorna true.
		// Caso contrário, retorna false
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
			return true;
		default:
			return false;
		}
	}

	private void atribuiNota(char nota) {
		switch (nota) {
		case 'A':
			this.setNotaAtual(this.getLA());
			break;
		case 'B':
			this.setNotaAtual(this.getSI());
			break;
		case 'C':
			this.setNotaAtual(this.getDO());
			break;
		case 'D':
			this.setNotaAtual(this.getRE());
			break;
		case 'E':
			this.setNotaAtual(this.getMI());
			break;
		case 'F':
			this.setNotaAtual(this.getFA());
			break;
		case 'G':
			this.setNotaAtual(this.getSOL());
			break;
		//Caso for alguma dessas letras em minúsculo, pega a nota antiga
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
			this.setNotaAtual(this.getNotaAntiga());
			break;
		}
	}

	public Sequence converteTexto() {
		try {
			sequencia = new Sequence(Sequence.PPQ, time); // Ver melhor depois
			Track track = sequencia.createTrack();

			char[] letrasArray = this.texto.toCharArray();
			for (char letra : letrasArray) {
				boolean retornoVerificacao = this.verificaNotaMusical(letra);
				if (retornoVerificacao) {
					//Caso seja uma nota musical válida
					//Atribuit a respectiva nota
					this.atribuiNota(letra);
					//Atribuit a nota antiga
					this.setNotaAntiga();
					//
					
					ShortMessage message = new ShortMessage();
					message.setMessage(144, 0, getNotaAtual() + getOctave(), configuracao.getVolume());
					MidiEvent event = new MidiEvent(message, tick);
					
					//Teste
					ShortMessage message2 = new ShortMessage();
					message2.setMessage(128, 0, getNotaAtual() + getOctave(), configuracao.getVolume());
					MidiEvent event2 = new MidiEvent(message2, tick + 2);
					
					
					track.add(event);	
					track.add(event2);
					
					ShortMessage messageOld = new ShortMessage();
					messageOld.setMessage(144, 0, getNotaAntiga() + getOctave(), configuracao.getVolume());
					MidiEvent eventOld = new MidiEvent(message, tick);
					
					//Teste
					ShortMessage messageOld2 = new ShortMessage();
					messageOld2.setMessage(128, 0, getNotaAntiga() + getOctave(), configuracao.getVolume());
					MidiEvent eventOld2 = new MidiEvent(message2, tick + 2);
					
					track.add(eventOld);
					track.add(eventOld2);
					
					ShortMessage messageInstrument = new ShortMessage();
					messageInstrument.setMessage(192, 0, 25, 0);
					MidiEvent eventInstrument = new MidiEvent(messageInstrument, tick);
					
					track.add(eventInstrument);
					
					this.updateTick();
					
				}
			}

		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
		}
		return sequencia;
	}
	
	private void updateTick()
	{
		this.setTick(getTick() + time);
	}
	
	private String getTexto() {
		return texto;
	}

	private void setTexto(String texto) {
		this.texto = texto;
	}

	private int getOctave() {
		return octave;
	}

	private void setOctave(int octave) {
		this.octave = octave;
	}

	private int getDO() {
		return DO;
	}

	private void setDO() {
		this.DO = this.getOctave() * 12;
	}

	private int getRE() {
		return RE;
	}

	private void setRE() {
		this.RE = this.getDO() + 2;
	}

	private int getMI() {
		return MI;
	}

	private void setMI() {
		this.MI = this.getRE() + 2;
	}

	private int getFA() {
		return FA;
	}

	private void setFA() {
		this.FA = this.getMI() + 2;
	}

	private int getSOL() {
		return SOL;
	}

	private void setSOL() {
		this.SOL = this.getFA() + 2;
	}

	private int getLA() {
		return LA;
	}

	private void setLA() {
		this.LA = this.getSOL() + 2;
	}

	private int getSI() {
		return SI;
	}

	private void setSI() {
		this.SI = this.getSI() + 2;
	}

	private int getTick() {
		return tick;
	}

	private void setTick(int tick) {
		this.tick = tick;
	}

}
