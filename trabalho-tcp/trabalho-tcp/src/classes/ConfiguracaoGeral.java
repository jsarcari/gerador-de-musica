package classes;

public class ConfiguracaoGeral {

	private final int VALOR_PADRAO_VOLUME = 50;

	private int instrumento;
	private int bpm;
	private int volume;
	private String nomeArquivo;

	// Construtor
	public ConfiguracaoGeral() {
		this.volume = VALOR_PADRAO_VOLUME;
	}

	public void atribuiInstrumentoEspecifico(int valorInstrumento) {
		this.instrumento = valorInstrumento;
	}

	public int getInstrumento() {
		return instrumento;
	}

	// Função que possui um switch interno responsável por atribuir um número de
	// acordo com o instrumento
	// Obs: Pegamos apenas alguns instrumentos do programa exemplo, não botamos
	// todos que tinham lá
	public void setInstrumento(String instrumento) {
		switch (instrumento) {
		case "Acoustic Grand Piano":
			this.instrumento = 1;
			break;
		case "Harpsichord":
			this.instrumento = 7;
			break;
		case "Electric Grand Piano":
			this.instrumento = 3;
			break;
		case "Xylophone":
			this.instrumento = 14;
			break;
		case "Church Organ":
			this.instrumento = 20;
			break;
		case "Acoustic Guitar (nylon)":
			this.instrumento = 25;
			break;
		case "Acoustic Bass":
			this.instrumento = 33;
			break;
		case "Violin":
			this.instrumento = 41;
			break;
		case "Trumpet":
			this.instrumento = 57;
			break;
		case "Ocarina":
			this.instrumento = 80;
			break;
		default:
			break;
		}
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}
