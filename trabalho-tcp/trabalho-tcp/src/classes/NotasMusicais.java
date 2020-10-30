package classes;

public class NotasMusicais {

	/* Valor de cada nota */
	private final int VALOR_NOTA_A = 69;
	private final int VALOR_NOTA_B = 71;
	private final int VALOR_NOTA_C = 60;
	private final int VALOR_NOTA_D = 62;
	private final int VALOR_NOTA_E = 64;
	private final int VALOR_NOTA_F = 65;
	private final int VALOR_NOTA_G = 67;

	// As notas musicais utilizadas
	private int DO; // C
	private int RE; // D
	private int MI; // E
	private int FA; // F
	private int SOL; // G
	private int LA; // A
	private int SI; // B

	private int octave;
	private final int OCTAVE_INICIAL = 1;

	public NotasMusicais() {
		this.setOctave(OCTAVE_INICIAL);
		this.inicializaNotas();
	}
	
	public void inicializaNotas()
	{
		this.setDO();
		this.setRE();
		this.setMI();
		this.setFA();
		this.setSOL();
		this.setLA();
		this.setSI();
	}
	
	public int retornaValorNotaOctave(char letra)
	{
		switch(letra)
		{
			case 'A':
				return this.getLA();
			case 'B':
				return this.getSI();
			case 'C':
				return this.getDO();
			case 'D':
				return this.getRE();
			case 'E':
				return this.getMI();
			case 'F':
				return this.getFA();
			case 'G':
				return this.getSOL();
			default:
				return 0;
		}
	}

	private int getDO() {
		return DO;
	}

	private void setDO() {
		//this.DO = (this.getOctave() + 1) * 12 + VALOR_NOTA_C;
		this.DO = this.getOctave() + VALOR_NOTA_C;
	}

	private int getRE() {
		return RE;
	}

	private void setRE() {
		//this.RE = (this.getOctave() + 1) * 12 + VALOR_NOTA_D;
		this.RE = this.getOctave() + VALOR_NOTA_D;
	}

	private int getMI() {
		return MI;
	}

	private void setMI() {
		//this.MI = (this.getOctave() + 1) * 12 + VALOR_NOTA_E;
		this.MI = this.getOctave() + VALOR_NOTA_E;
	}

	private int getFA() {
		return FA;
	}

	private void setFA() {
		//this.FA = (this.getOctave() + 1) * 12 + VALOR_NOTA_F;
		this.FA = this.getOctave() + VALOR_NOTA_F;
	}

	private int getSOL() {
		return SOL;
	}

	private void setSOL() {
		//this.SOL = (this.getOctave() + 1) * 12 + VALOR_NOTA_G;
		this.SOL = this.getOctave() + VALOR_NOTA_G;
	}

	private int getLA() {
		return LA;
	}

	private void setLA() {
		//this.LA = (this.getOctave() + 1) * 12 + VALOR_NOTA_A;
		this.LA = this.getOctave() + VALOR_NOTA_A;
	}

	private int getSI() {
		return SI;
	}

	private void setSI() {
		//this.SI = (this.getOctave() + 1) * 12 + VALOR_NOTA_B;
		this.SI = this.getOctave() + VALOR_NOTA_B;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	//Aumentar uma octave equivale a somar + 12 no valor atual
	public void aumentaUmaOctave()
	{
		int novaOctave = this.getOctave() + 12;
		if(novaOctave > -24 && novaOctave < 24)
		{
			this.setOctave(novaOctave);
		}
		else {
			this.setOctave(OCTAVE_INICIAL);
		}
	}
}
