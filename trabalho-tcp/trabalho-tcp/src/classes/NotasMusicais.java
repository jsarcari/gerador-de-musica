package classes;

public class NotasMusicais {

	private final int OCTAVE_INICIAL = 1;

	/* Atributos contendo o valor de cada nota */
	private int valorNotaA;
	private int valorNotaB;
	private int valorNotaC;
	private int valorNotaD;
	private int valorNotaE;
	private int valorNotaF;
	private int valorNotaG;

	// As notas musicais utilizadas. Essas variáveis contém o valor da nota + valor
	// da Octave
	private int notaDo; // C
	private int notaRe; // D
	private int notaMi; // E
	private int notaFa; // F
	private int notaSol; // G
	private int notaLa; // A
	private int notaSi; // B

	private int octave;

	// Construtor
	public NotasMusicais() {
		this.setOctave(OCTAVE_INICIAL);
		this.inicializaNotas();
	}

	// Retorna o valor de cada nota musical
	public int retornaValorNotaOctave(char letra) {
		switch (letra) {
		case 'A':
			return this.getNotaLa();
		case 'B':
			return this.getNotaSi();
		case 'C':
			return this.getNotaDo();
		case 'D':
			return this.getNotaRe();
		case 'E':
			return this.getNotaMi();
		case 'F':
			return this.getNotaFa();
		case 'G':
			return this.getNotaSol();
		default:
			return 0;
		}
	}

	// Aumentar uma octave equivale a somar + 12 no valor atual
	public void aumentaUmaOctave() {
		int novaOctave = this.getOctave() + 12;
		if (novaOctave > -24 && novaOctave < 24) {
			this.setOctave(novaOctave);
		} else {
			this.setOctave(OCTAVE_INICIAL);
		}
	}

	// Inicializa o valor de cada letra e o valor de cada nota musical
	private void inicializaNotas() {
		this.setValorNotaA();
		this.setValorNotaB();
		this.setValorNotaC();
		this.setValorNotaD();
		this.setValorNotaE();
		this.setValorNotaF();
		this.setValorNotaG();
		this.setNotaDo();
		this.setNotaRe();
		this.setNotaMi();
		this.setNotaFa();
		this.setNotaSol();
		this.setNotaLa();
		this.setNotaSi();
	}

	private int getNotaDo() {
		return notaDo;
	}

	private void setNotaDo() {
		this.notaDo = this.getOctave() + getValorNotaC();
	}

	private int getNotaRe() {
		return notaRe;
	}

	private void setNotaRe() {
		this.notaRe = this.getOctave() + getValorNotaD();
	}

	private int getNotaMi() {
		return notaMi;
	}

	private void setNotaMi() {
		this.notaMi = this.getOctave() + getValorNotaE();
	}

	private int getNotaFa() {
		return notaFa;
	}

	private void setNotaFa() {
		this.notaFa = this.getOctave() + getValorNotaF();
	}

	private int getNotaSol() {
		return notaSol;
	}

	private void setNotaSol() {
		this.notaSol = this.getOctave() + getValorNotaG();
	}

	private int getNotaLa() {
		return notaLa;
	}

	private void setNotaLa() {
		this.notaLa = this.getOctave() + getValorNotaA();
	}

	private int getNotaSi() {
		return notaSi;
	}

	private void setNotaSi() {
		this.notaSi = this.getOctave() + getValorNotaB();
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	private int getValorNotaA() {
		return valorNotaA;
	}

	private void setValorNotaA() {
		this.valorNotaA = 69;
	}

	private int getValorNotaB() {
		return valorNotaB;
	}

	private void setValorNotaB() {
		this.valorNotaB = 71;
	}

	private int getValorNotaC() {
		return valorNotaC;
	}

	private void setValorNotaC() {
		this.valorNotaC = 60;
	}

	private int getValorNotaD() {
		return valorNotaD;
	}

	private void setValorNotaD() {
		this.valorNotaD = 62;
	}

	private int getValorNotaE() {
		return valorNotaE;
	}

	private void setValorNotaE() {
		this.valorNotaE = 64;
	}

	private int getValorNotaF() {
		return valorNotaF;
	}

	private void setValorNotaF() {
		this.valorNotaF = 65;
	}

	private int getValorNotaG() {
		return valorNotaG;
	}

	private void setValorNotaG() {
		this.valorNotaG = 67;
	}
}
