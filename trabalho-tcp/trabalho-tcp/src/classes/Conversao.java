package classes;

import javax.sound.midi.*;

public class Conversao {

	/* Comandos necessários para criar os eventos */
	private final int NOTE_ON = 144;
	private final int NOTE_OFF = 128;
	private final int MUDA_INSTRUMENTO = 192;
	private final int CANAL = 0;

	/* Valores iniciais */
	private final int TICK_INICIAL = 1;

	/* Valores limites */
	private final int LIMITE_VOLUME = 127;
	private final int LIMITE_INSTRUMENTOS = 126;

	private String texto;
	private ConfiguracaoGeral configuracao;
	private NotasMusicais notasMusicais;
	private Sequence sequencia;

	/*
	 * Para armazenar a nota mais recente. Possui o valor do cálculo --> valor da
	 * nota + valor da octave (Usada ao criar os events)
	 */
	private int valorCalculadoNotaAtual;
	/*
	 * Para armazenar a nota antiga. Possui o valor do cálculo --> valor da nota
	 * antiga + valor da octave antiga (Usada ao criar os events)
	 */
	private int valorCalculadoNotaAntiga;

	private char notaAtualEmChar; // Usada para guardar a nota atual (em char)
	private char notaAntigaEmChar; // Usada para guardar a última nota solicitada (em char)

	private int tick;
	private int time = 4;

	/* Valores de instrumentos que foram solicitados no mapeamento */
	private final int INSTRUMENTO_AGOGO = 114;
	private final int INSTRUMENTO_HARPSICHORD = 7;
	private final int INSTRUMENTO_TUBULAR_BELLS = 15;
	private final int INSTRUMENTO_PAN_FLUTE = 76;
	private final int INSTRUMENTO_CHURCH_ORGAN = 20;

	// Construtor
	public Conversao(ConfiguracaoGeral configuracaoGeral, String conteudoTexto) {
		this.configuracao = configuracaoGeral;
		this.setTexto(conteudoTexto);
		this.setTick(TICK_INICIAL);
	}

	/*
	 * Função responsável por percorrer caractere por caractere e dar o devido
	 * tratamento para cada símbolo Retorna uma Sequence utilizada para tocar a
	 * música
	 */
	public Sequence converteTexto() {
		try {
			sequencia = new Sequence(Sequence.PPQ, time); // Inicializa a Sequence
			Track track = sequencia.createTrack(); // Inicializa o Track

			/*
			 * Inicializa a classe de NotasMusicais dando o valor inicial para a Octave e
			 * para cada Nota (No próprio Construtor é chamado esses métodos)
			 */
			notasMusicais = new NotasMusicais();
			this.geraEventoInstrumento(track); // Gera o evento para o instrumento inicial e adiciona ao Track

			// Essa próxima parte irá transformar o texto informado pelo usuário em um vetor
			// de char
			char[] letrasArray = this.getTexto().toCharArray();
			for (char caractere : letrasArray) { // Percorre caractere por caractere
				boolean retornoVerificacao = this.verificaNotaMusical(caractere); // Verifica se é uma nota musical
				if (retornoVerificacao) { // Caso seja uma nota musical válida
					// Atribui a respectiva nota
					this.atribuiNotaAtual(caractere);
					// Atribui a nota antiga aqui para sempre ficar salvo a última nota
					this.setValorCalculadoNotaAntiga();
					this.setNotaAntigaChar();

					// Gera os eventos necessários de acordo com as notas
					this.geraEventosNotasAtuais(track);
					this.updateTick(); // Atualiza o Tick
				} else { // Caso NÃO seja uma nota músical
					this.tratamentoCaracteresQueNaoSaoNotasMusicais(caractere, track);
					this.setNotaAtualEmChar(caractere);
					this.setNotaAntigaChar();
					this.updateTick();
				}
			}

		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
		}
		return sequencia;
	}

	/*
	 * Função responsável por verificar se é uma das notas musicais. Caso for,
	 * retorna true. Caso contrário, retorna false
	 */
	private boolean verificaNotaMusical(char letra) {
		switch (letra) {
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
			return true;
		default:
			return false;
		}
	}

	// Função responsável por atribuir a notaAtual com seus respectivos valores
	private void atribuiNotaAtual(char nota) {
		switch (nota) {
		case 'A':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('A'));
			this.setNotaAtualEmChar('A');
			break;
		case 'B':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('B'));
			this.setNotaAtualEmChar('B');
			break;
		case 'C':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('C'));
			this.setNotaAtualEmChar('C');
			break;
		case 'D':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('D'));
			this.setNotaAtualEmChar('D');
			break;
		case 'E':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('E'));
			this.setNotaAtualEmChar('E');
			break;
		case 'F':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('F'));
			this.setNotaAtualEmChar('F');
			break;
		case 'G':
			this.setValorCalculadoNotaAtual(notasMusicais.retornaValorNotaOctave('G'));
			this.setNotaAtualEmChar('G');
			break;
		default:
			break;
		}
	}

	// Função responsável por fazer o mapeamento para caracteres que não são notas
	// musicais
	private void tratamentoCaracteresQueNaoSaoNotasMusicais(char caractere, Track track) {
		switch (caractere) {
		case ' ':
			this.aumentarDobroVolume();
			break;
		case '!':
			this.atribuiInstrumentoEspecifico(INSTRUMENTO_AGOGO);
			track = this.geraEventoInstrumento(track);
			break;
		case 'O':
		case 'o':
		case 'I':
		case 'i':
		case 'U':
		case 'u':
			this.atribuiInstrumentoEspecifico(INSTRUMENTO_HARPSICHORD);
			track = this.geraEventoInstrumento(track);
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			// novoValorDoInstrumento = Valor do instrumento atual + valor do dígito
			int novoValorDoInstrumento = configuracao.getInstrumento() + Character.getNumericValue(caractere);
			// Verifica se está dentro do limite pois se não acontecia exceção na hora de
			// gerar o evento
			if (novoValorDoInstrumento < LIMITE_INSTRUMENTOS) {
				this.atribuiInstrumentoEspecifico(novoValorDoInstrumento); // Muda o instrumento para o novo valor
				this.geraEventoInstrumento(track);
			}
			break;
		case '?':
			notasMusicais.aumentaUmaOctave();
			break;
		case '\n':
			this.atribuiInstrumentoEspecifico(INSTRUMENTO_TUBULAR_BELLS);
			this.geraEventoInstrumento(track);
			break;
		case ';':
			this.atribuiInstrumentoEspecifico(INSTRUMENTO_PAN_FLUTE);
			this.geraEventoInstrumento(track);
			break;
		case ',':
			this.atribuiInstrumentoEspecifico(INSTRUMENTO_CHURCH_ORGAN);
			this.geraEventoInstrumento(track);
			break;
		default:
			/*
			 * Tanto as consoantes (que não são notas), quanto as notas (minúsculas) quanto
			 * qualquer outro tipo de caractere leva ao mesmo resultado. Por conta disso,
			 * todos esses caminhos citados levam a 'default'
			 */
			boolean verificaNotaAnterior = this.verificaNotaMusical(this.getNotaAntigaChar());
			if (verificaNotaAnterior) {
				this.geraEventosNotasAnteriores(track);
			}
			break;
		}
	}

	private Track geraEventosNotasAtuais(Track track) {
		try {
			// Para as notas atuais
			ShortMessage message = new ShortMessage();
			message.setMessage(NOTE_ON, CANAL, getValorCalculadoNotaAtual(), configuracao.getVolume());
			MidiEvent evento_nota_atual_on = new MidiEvent(message, tick);

			ShortMessage message2 = new ShortMessage();
			message2.setMessage(NOTE_OFF, CANAL, getValorCalculadoNotaAtual(), configuracao.getVolume());
			MidiEvent evento_nota_atual_off = new MidiEvent(message2, tick + 2);

			// Adiciona todos os eventos no track
			track.add(evento_nota_atual_on);
			track.add(evento_nota_atual_off);

			return track;

		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private Track geraEventosNotasAnteriores(Track track) {
		try {
			// Para as notas anteriores
			ShortMessage messageOld = new ShortMessage();
			messageOld.setMessage(NOTE_ON, CANAL, getValorCalculadoNotaAntiga(), configuracao.getVolume());
			MidiEvent evento_nota_antiga_on = new MidiEvent(messageOld, tick);

			ShortMessage messageOld2 = new ShortMessage();
			messageOld2.setMessage(NOTE_OFF, CANAL, getValorCalculadoNotaAntiga(), configuracao.getVolume());
			MidiEvent evento_nota_antiga_off = new MidiEvent(messageOld2, tick + 2);

			// Adiciona todos os eventos no track
			track.add(evento_nota_antiga_on);
			track.add(evento_nota_antiga_off);

			return track;

		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private Track geraEventoInstrumento(Track track) {
		try {
			ShortMessage messageInstrument = new ShortMessage();
			messageInstrument.setMessage(MUDA_INSTRUMENTO, 0, configuracao.getInstrumento(), getTick());
			MidiEvent eventInstrument = new MidiEvent(messageInstrument, tick);

			track.add(eventInstrument);

			return track;

		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private void atribuiInstrumentoEspecifico(int valorInstrumento) {
		configuracao.atribuiInstrumentoEspecifico(valorInstrumento);
	}

	private void updateTick() {
		this.setTick(getTick() + time);
	}

	private void aumentarDobroVolume() {
		int atual = this.configuracao.getVolume();
		if (atual * 2 < LIMITE_VOLUME) // Verifica se o valor excede o limite do volume
		{
			this.configuracao.setVolume(atual * 2);
		} else {
			this.volumePadrao();
		}
	}

	private void volumePadrao() {
		this.configuracao.setVolume(50);
	}

	private int getValorCalculadoNotaAtual() {
		return valorCalculadoNotaAtual;
	}

	private void setValorCalculadoNotaAtual(int notaAtual) {
		this.valorCalculadoNotaAtual = notaAtual;
	}

	private int getValorCalculadoNotaAntiga() {
		return valorCalculadoNotaAntiga;
	}

	private void setValorCalculadoNotaAntiga() {
		this.valorCalculadoNotaAntiga = this.getValorCalculadoNotaAtual();
	}

	private char getNotaAntigaChar() {
		return notaAntigaEmChar;
	}

	private void setNotaAntigaChar() {
		this.notaAntigaEmChar = this.getNotaAtualEmChar();
	}

	private String getTexto() {
		return texto;
	}

	private void setTexto(String texto) {
		this.texto = texto;
	}

	private int getTick() {
		return tick;
	}

	private void setTick(int tick) {
		this.tick = tick;
	}

	public char getNotaAtualEmChar() {
		return notaAtualEmChar;
	}

	public void setNotaAtualEmChar(char notaAtualEmChar) {
		this.notaAtualEmChar = notaAtualEmChar;
	}

}
