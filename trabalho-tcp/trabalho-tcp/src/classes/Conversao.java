package classes;

import javax.sound.midi.*;

public class Conversao {
	
	/*Comandos necessários para criar os eventos*/
	private final int NOTE_ON = 144;
	private final int NOTE_OFF = 128;
	private final int MUDA_INSTRUMENTO = 192;
	private final int CANAL = 0;
	
	/*Valores iniciais*/
	//private final int OCTAVE_INICIAL = 1;
	private final int TICK_INICIAL = 1;
	
	/*Valores limites*/
	private final int LIMITE_VOLUME = 127;
	private final int LIMITE_INSTRUMENTOS = 126;
	
	private String texto;
	private ConfiguracaoGeral configuracao;
	private NotasMusicais notasMusicais;
	private Sequence sequencia;
	private int notaAtual; //Para armazenar a nota mais recente
	private int notaAntigaConvertidaEmInteiro; //Para armazenar a nota antiga em inteiros (Usada ao criar os events)
	private char notaAntigaEmChar; //Usada para guardar a última nota solicitada (em char)
	private char notaAtualEmChar;
	
	//private int octave = OCTAVE_INICIAL;
	private int tick;
	private int time = 4;

	public Conversao(ConfiguracaoGeral configuracaoGeral, String conteudoTexto) {
		this.configuracao = configuracaoGeral;
		this.setTexto(conteudoTexto);
		//this.setOctave(OCTAVE_INICIAL);
		this.setTick(TICK_INICIAL);
	}
	
	public Sequence converteTexto() {
		try {
			sequencia = new Sequence(Sequence.PPQ, time); // Ver melhor depois
			Track track = sequencia.createTrack();

			notasMusicais = new NotasMusicais();
			this.geraEventoInstrumento(track);
			
			char[] letrasArray = this.getTexto().toCharArray();
			for (char caractere : letrasArray) {
				boolean retornoVerificacao = this.verificaNotaMusical(caractere);
				if (retornoVerificacao) { //Caso seja uma nota musical válida
					//Atribui a respectiva nota
					this.atribuiNota(caractere);
					//Atribui a nota antiga
					this.setNotaAntigaConvertidaEmInteiro(); //Para o caso de ser nota musical
					this.setNotaAntigaChar();
					
					//Gera os eventos necessários de acordo com as notas
					this.geraEventosNotasAtuais(track);
					this.geraEventosNotasAnteriores(track);
					
					this.updateTick();
				}else { //Caso NÃO seja uma nota músical
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
	
	private void tratamentoCaracteresQueNaoSaoNotasMusicais(char caractere, Track track)
	{
		boolean verificaNotaAnterior;
		switch(caractere)
		{
			case ' ':
				this.aumentarDobroVolume();
				break;
			case '!':
				this.atribuiInstrumentoEspecifico(114);
				track = this.geraEventoInstrumento(track);
				break;
			case 'O':
			case 'o':
			case 'I':
			case 'i':
			case 'U':
			case 'u':
				this.atribuiInstrumentoEspecifico(7);
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
				//Valor do instrumento atual + valor do dígito
				int novoValorDoInstrumento = configuracao.getInstrumento() + Character.getNumericValue(caractere);
				if(novoValorDoInstrumento < LIMITE_INSTRUMENTOS)
				{
					this.atribuiInstrumentoEspecifico(novoValorDoInstrumento);
					this.geraEventoInstrumento(track);
				}
				break;
			case '?':
				notasMusicais.aumentaUmaOctave();
				break;
			case '\n':
				this.atribuiInstrumentoEspecifico(15);
				this.geraEventoInstrumento(track);
				break;
			case ';':
				this.atribuiInstrumentoEspecifico(76);
				this.geraEventoInstrumento(track);
				break;
			case ',':
				this.atribuiInstrumentoEspecifico(20);
				this.geraEventoInstrumento(track);
				break;
			default:
				//Tanto as consoantes (que não são notas), quanto as notas (minúsculas) quanto qualquer outro tipo
				//de caractere leva a mesma coisa. Por conta disso, todos esses caminhos citados levam a 'default'
				verificaNotaAnterior = this.verificaNotaAnterior(this.getNotaAntigaChar());
				if(verificaNotaAnterior)
				{
					this.geraEventosNotasAnteriores(track);
				}
				break;
		}
	}
		
	private boolean verificaNotaAnterior(char notaAnterior)
	{
		switch(notaAnterior)
		{
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
	
	//VER
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
			return true;
		default:
			return false;
		}
	}

	private void atribuiNota(char nota) {
		switch (nota) {
		case 'A':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('A'));
			this.setNotaAtualEmChar('A');
			break;
		case 'B':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('B'));
			this.setNotaAtualEmChar('B');
			break;
		case 'C':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('C'));
			this.setNotaAtualEmChar('C');
			break;
		case 'D':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('D'));
			this.setNotaAtualEmChar('D');
			break;
		case 'E':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('E'));
			this.setNotaAtualEmChar('E');
			break;
		case 'F':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('F'));
			this.setNotaAtualEmChar('F');
			break;
		case 'G':
			this.setNotaAtual(notasMusicais.retornaValorNotaOctave('G'));
			this.setNotaAtualEmChar('G');
			break;
		//Caso for alguma dessas letras em minúsculo, pega a nota antiga
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
			this.setNotaAtual(this.getNotaAntigaConvertidaEmInteiro());
			break;
		}
	}

	private Track geraEventosNotasAtuais(Track track)
	{
		try {
			//Para as notas atuais
			ShortMessage message = new ShortMessage();
			message.setMessage(NOTE_ON, CANAL, getNotaAtual(), configuracao.getVolume());
			MidiEvent evento_nota_atual_on = new MidiEvent(message, tick);
			
			ShortMessage message2 = new ShortMessage();
			message2.setMessage(NOTE_OFF, CANAL, getNotaAtual(), configuracao.getVolume());
			MidiEvent evento_nota_atual_off = new MidiEvent(message2, tick + 2);
					
			//Adiciona todos os eventos no track
			track.add(evento_nota_atual_on);
			track.add(evento_nota_atual_off);
			
			return track;
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Track geraEventosNotasAnteriores(Track track)
	{
		try {
			//Para as notas anteriores
			ShortMessage messageOld = new ShortMessage();
			messageOld.setMessage(NOTE_ON, CANAL, getNotaAntigaConvertidaEmInteiro(), configuracao.getVolume());
			MidiEvent evento_nota_antiga_on = new MidiEvent(messageOld, tick);
			
			ShortMessage messageOld2 = new ShortMessage();
			messageOld2.setMessage(NOTE_OFF, CANAL, getNotaAntigaConvertidaEmInteiro(), configuracao.getVolume());
			MidiEvent evento_nota_antiga_off = new MidiEvent(messageOld2, tick + 2);
			
			//Adiciona todos os eventos no track
			track.add(evento_nota_antiga_on);
			track.add(evento_nota_antiga_off);
			
			return track;
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Track geraEventoInstrumento(Track track)
	{
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
	
	private void atribuiInstrumentoEspecifico(int valorInstrumento)
	{
		configuracao.atribuiInstrumentoEspecifico(valorInstrumento);
	}
	
	private void updateTick()
	{
		this.setTick(getTick() + time);
	}
	
	private void aumentarDobroVolume() {
		int atual = this.configuracao.getVolume();
		if(atual * 2 < LIMITE_VOLUME) //Verifica se o valor excede o limite do volume
		{
			this.configuracao.setVolume(atual * 2);
		}else {
			this.volumePadrao();
		}
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
	
	private int getNotaAntigaConvertidaEmInteiro() {
		return notaAntigaConvertidaEmInteiro;
	}

	private void setNotaAntigaConvertidaEmInteiro() {
		this.notaAntigaConvertidaEmInteiro = this.getNotaAtual();
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
