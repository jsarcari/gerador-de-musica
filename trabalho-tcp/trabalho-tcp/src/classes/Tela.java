package classes;

import java.awt.Color; //Para botar cor nas bordas
import java.awt.event.ActionEvent; //Para a parte de eventos
import java.awt.event.ActionListener; //Para a parte de eventos
import java.io.File; //Para ler o arquivo
import java.util.Scanner; //Para ler o arquivo

import javax.sound.midi.Sequence; //Para utilizar o Sequence
import javax.swing.*; //Para implementar a interface
import javax.swing.border.Border; //Para conseguir implementar as bordas

public class Tela {

	// Atributos correspondentes aos elementos que aparecem na tela
	private JLabel titulo;
	private JLabel labelTextoMusica;
	private JTextArea inputTextoMusica;
	private JScrollPane scrollTextoMusica;
	private JLabel labelNomeArquivo;
	private JTextField inputNomeArquivo;
	private JLabel labelBpm;
	private JTextField inputBpm;
	private JLabel labelInstrumento;
	private JComboBox<String> comboInstrumento;
	private JButton buttonGerarMusica;
	private JButton buttonBaixarMusica;
	private JButton buttonEscolherArquivo;
	private JButton buttonLimparTexto;

	// Atributos responsáveis por pegar o conteúdo dos elementos da tela
	private String conteudoTextoMusica;
	private String conteudoNomeArquivo;
	private String conteudoBpm;
	private String conteudoComboInstrumento;

	/* Tamanho da tela */
	private final int TELA_LARGURA = 1000;
	private final int TELA_ALTURA = 800;

	/* Tamanho do Label do Título */
	private final int POSICAO_TELA_TITULO_X = (TELA_LARGURA / 2) - 280;
	private final int POSICAO_TELA_TITULO_Y = 10;
	private final int LARGURA_TITULO = TELA_LARGURA - 350;
	private final int ALTURA_TITULO = 80;

	/* Tamanho Labels Globais */
	private final int POSICAO_TELA_LABELS_X = 20;
	private final int LABELS_ALTURA_GLOBAL = 30;

	/* Tamanhos para o label de texto */
	private final int POSICAO_TELA_LABEL_TEXTO_Y = 100;
	private final int TAMANHO_LABEL_TEXTO_LARGURA = 500;
	private final int TAMANHO_LABEL_TEXTO_ALTURA = 30;

	/* Tamanho Text Area */
	private final int POSICAO_TEXT_AREA_X = 20;
	private final int POSICAO_TEXT_AREA_Y = 130;
	private final int TEXT_AREA_LARGURA = TELA_LARGURA - POSICAO_TEXT_AREA_X * 2;
	private final int TEXT_AREA_ALTURA = TELA_ALTURA / 4;

	/* Tamanho Label Nome Arquivo */
	private final int POSICAO_TELA_LABEL_NOME_ARQUIVO_X = POSICAO_TELA_LABELS_X;
	private final int POSICAO_TELA_LABEL_NOME_ARQUIVO_Y = POSICAO_TELA_LABEL_TEXTO_Y + POSICAO_TEXT_AREA_Y
			+ POSICAO_TELA_TITULO_Y + 100;
	private final int LABEL_NOME_ARQUIVO_LARGURA = TAMANHO_LABEL_TEXTO_LARGURA;
	private final int LABEL_NOME_ARQUIVO_ALTURA = LABELS_ALTURA_GLOBAL;

	/* Tamanho input Nome Arquivo */
	private final int POSICAO_TELA_INPUT_NOME_ARQUIVO_X = POSICAO_TELA_LABEL_NOME_ARQUIVO_X + 150;
	private final int POSICAO_TELA_INPUT_NOME_ARQUIVO_Y = POSICAO_TELA_LABEL_NOME_ARQUIVO_Y + 7;
	private final int INPUT_NOME_ARQUIVO_LARGURA = 600;
	private final int INPUT_NOME_ARQUIVO_ALTURA = 20;

	/* Tamanho Label BPM */
	private final int POSICAO_TELA_LABEL_BPM_X = POSICAO_TELA_LABELS_X + 88;
	private final int POSICAO_TELA_LABEL_BPM_Y = POSICAO_TELA_LABEL_NOME_ARQUIVO_Y + 50;
	private final int LABEL_BPM_LARGURA = LABEL_NOME_ARQUIVO_LARGURA;
	private final int LABEL_BPM_ALTURA = LABELS_ALTURA_GLOBAL;

	/* Tamanho Input BPM */
	private final int POSICAO_TELA_INPUT_BPM_X = POSICAO_TELA_LABEL_BPM_X + 62;
	private final int POSICAO_TELA_INPUT_BPM_Y = POSICAO_TELA_LABEL_BPM_Y + 7;
	private final int INPUT_BPM_LARGURA = 600;
	private final int INPUT_BPM_ALTURA = 20;

	/* Tamanho Label Instrumento */
	private final int POSICAO_TELA_LABEL_INSTRUMENTO_X = POSICAO_TELA_LABELS_X + 17;
	private final int POSICAO_TELA_LABEL_INSTRUMENTO_Y = POSICAO_TELA_LABEL_BPM_Y + 50;
	private final int LABEL_INSTRUMENTO_LARGURA = LABEL_BPM_LARGURA;
	private final int LABEL_INSTRUMENTO_ALTURA = LABELS_ALTURA_GLOBAL;

	/* Tamanho Combo Instrumento */
	private final int POSICAO_TELA_COMBO_INSTRUMENTO_X = POSICAO_TELA_LABELS_X + 150;
	private final int POSICAO_TELA_COMBO_INSTRUMENTO_Y = POSICAO_TELA_LABEL_INSTRUMENTO_Y + 7;
	private final int COMBO_INSTRUMENTO_LARGURA = 200;
	private final int COMBO_INSTRUMENTO_ALTURA = LABELS_ALTURA_GLOBAL - 10;

	/* Tamanho Botão Gerar Música */
	private final int POSICAO_TELA_BOTAO_GERAR_MUSICA_X = 450;
	private final int POSICAO_TELA_BOTAO_GERAR_MUSICA_Y = POSICAO_TELA_COMBO_INSTRUMENTO_Y + 150;
	private final int BOTAO_GERAR_MUSICA_LARGURA = 150;
	private final int BOTAO_GERAR_MUSICA_ALTURA = 30;

	/* Tamanho Botão Baixar Música */
	private final int POSICAO_TELA_BOTAO_BAIXAR_MUSICA_X = 650;
	private final int POSICAO_TELA_BOTAO_BAIXAR_MUSICA_Y = POSICAO_TELA_COMBO_INSTRUMENTO_Y + 150;
	private final int BOTAO_BAIXAR_MUSICA_LARGURA = 150;
	private final int BOTAO_BAIXAR_MUSICA_ALTURA = 30;

	/* Tamanho Botão Escolher Arquivo */
	private final int POSICAO_TELA_BOTAO_ESCOLHER_ARQUIVO_X = 250;
	private final int POSICAO_TELA_BOTAO_ESCOLHER_ARQUIVO_Y = POSICAO_TELA_COMBO_INSTRUMENTO_Y + 150;
	private final int BOTAO_ESCOLHER_ARQUIVO_LARGURA = 150;
	private final int BOTAO_ESCOLHER_ARQUIVO_ALTURA = 30;

	/* Tamanho Botão Limpar Texto */
	private final int POSICAO_TELA_BOTAO_LIMPAR_TEXTO_X = 50;
	private final int POSICAO_TELA_BOTAO_LIMPAR_TEXTO_Y = POSICAO_TELA_COMBO_INSTRUMENTO_Y + 150;
	private final int BOTAO_LIMPAR_TEXTO_LARGURA = 150;
	private final int BOTAO_LIMPAR_TEXTO_ALTURA = 30;
	
	Tocador tocador = new Tocador();

	public void invocarTela() {
		// Cria o frame
		JFrame frame = new JFrame("Music Text Composition");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Chama os métodos que criam os elementos visuais da tela
		this.criaTitulo();
		this.criarLabels();
		this.criarInputs();
		this.criarBotoes();

		// Adiciona os elementos criados no frame
		adicionaElementosAoFrame(frame);

		// Atribui algumas configurações para o frame
		frame.setSize(TELA_LARGURA, TELA_ALTURA); // Bota altura e largura
		frame.setResizable(false); // Não deixa o usuário maximizar a tela
		frame.setLayout(null); // Não usa o Layout para podermos mover os elementos livremente
		frame.setVisible(true);

		// Ao clicar nos botões, vai para sua determinada função
		this.clicarBotaoEscolherArquivo();
		this.clicarBotaoLimparTexto();
		this.clicarBotaoGerarMusica();
		this.clicarBotaoBaixarMusica();
	}

	//Função responsável por adicionar todos elementos criados ao frame, possibilitando a visualização dos mesmos
	private void adicionaElementosAoFrame(JFrame frame) {
		// Adiciona os elementos criados no frame
		frame.add(titulo);
		frame.add(labelTextoMusica);
		frame.add(scrollTextoMusica);
		frame.add(labelNomeArquivo);
		frame.add(inputNomeArquivo);
		frame.add(labelBpm);
		frame.add(inputBpm);
		frame.add(labelInstrumento);
		frame.add(comboInstrumento);
		frame.add(buttonGerarMusica);
		frame.add(buttonBaixarMusica);
		frame.add(buttonEscolherArquivo);
		frame.add(buttonLimparTexto);
	}

	// Função responsável por criar os inputs da tela
	private void criarInputs() {
		// Cria o text area para o usuário inserir o texto
		inputTextoMusica = new JTextArea();
		inputTextoMusica.setEditable(true);

		// Cria uma borda especificando a cor e a grossura. Essa borda é colocada no
		// TextArea criado acima
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		inputTextoMusica.setBorder(border);
		inputTextoMusica.setBounds(POSICAO_TEXT_AREA_X, POSICAO_TEXT_AREA_Y, TEXT_AREA_LARGURA, TEXT_AREA_ALTURA);

		// Insere uma scroll bar para o text area do texto a ser convertido
		scrollTextoMusica = new JScrollPane(inputTextoMusica);
		scrollTextoMusica.setBounds(POSICAO_TEXT_AREA_X, POSICAO_TEXT_AREA_Y, TEXT_AREA_LARGURA, TEXT_AREA_ALTURA);
		scrollTextoMusica.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Cria um input para pegar o nome arquivo
		inputNomeArquivo = new JTextField();
		inputNomeArquivo.setBounds(POSICAO_TELA_INPUT_NOME_ARQUIVO_X, POSICAO_TELA_INPUT_NOME_ARQUIVO_Y,
				INPUT_NOME_ARQUIVO_LARGURA, INPUT_NOME_ARQUIVO_ALTURA);
		inputNomeArquivo.setEditable(true);

		// Cria um input para pegar o BPM
		inputBpm = new JTextField();
		inputBpm.setBounds(POSICAO_TELA_INPUT_BPM_X, POSICAO_TELA_INPUT_BPM_Y, INPUT_BPM_LARGURA, INPUT_BPM_ALTURA);
		inputBpm.setEditable(true);
		inputBpm.setText("120"); // Valor padrão

		// Cria o combo de instrumentos
		String instrumentos[] = { "Acoustic Grand Piano", "Harpsichord", "Electric Grand Piano", "Xylophone",
				"Church Organ", "Acoustic Guitar (nylon)", "Acoustic Bass", "Violin", "Trumpet", "Ocarina" };
		comboInstrumento = new JComboBox<String>(instrumentos);
		comboInstrumento.setBounds(POSICAO_TELA_COMBO_INSTRUMENTO_X, POSICAO_TELA_COMBO_INSTRUMENTO_Y,
				COMBO_INSTRUMENTO_LARGURA, COMBO_INSTRUMENTO_ALTURA);

	}

	// Função responsável por criar os labels da tela
	private void criarLabels() {
		// Cria o label acima do Text Area
		labelTextoMusica = new JLabel("Digite o texto para ser convertido: ");
		labelTextoMusica.setBounds(POSICAO_TELA_LABELS_X, POSICAO_TELA_LABEL_TEXTO_Y, TAMANHO_LABEL_TEXTO_LARGURA,
				TAMANHO_LABEL_TEXTO_ALTURA);
		labelTextoMusica.setFont(labelTextoMusica.getFont().deriveFont(20.0F));

		// Cria o label ao lado do input nome arquivo
		labelNomeArquivo = new JLabel("Nome Arquivo: ");
		labelNomeArquivo.setBounds(POSICAO_TELA_LABEL_NOME_ARQUIVO_X, POSICAO_TELA_LABEL_NOME_ARQUIVO_Y,
				LABEL_NOME_ARQUIVO_LARGURA, LABEL_NOME_ARQUIVO_ALTURA);
		labelNomeArquivo.setFont(labelNomeArquivo.getFont().deriveFont(20.0F));

		// Cria o label ao lado do input Bpm
		labelBpm = new JLabel("BPM: ");
		labelBpm.setBounds(POSICAO_TELA_LABEL_BPM_X, POSICAO_TELA_LABEL_BPM_Y, LABEL_BPM_LARGURA, LABEL_BPM_ALTURA);
		labelBpm.setFont(labelBpm.getFont().deriveFont(20.0F));

		// Cria o label ao lado do combo de Instrumento
		labelInstrumento = new JLabel("Instrumento: ");
		labelInstrumento.setBounds(POSICAO_TELA_LABEL_INSTRUMENTO_X, POSICAO_TELA_LABEL_INSTRUMENTO_Y,
				LABEL_INSTRUMENTO_LARGURA, LABEL_INSTRUMENTO_ALTURA);
		labelInstrumento.setFont(labelInstrumento.getFont().deriveFont(20.0F));
	}

	// Função responsável por criar os botões da tela
	private void criarBotoes() {
		// Cria o botão de Gerar Música
		buttonGerarMusica = new JButton("Gerar Música");
		buttonGerarMusica.setBounds(POSICAO_TELA_BOTAO_GERAR_MUSICA_X, POSICAO_TELA_BOTAO_GERAR_MUSICA_Y,
				BOTAO_GERAR_MUSICA_LARGURA, BOTAO_GERAR_MUSICA_ALTURA);

		// Cria o botão de Baixar Música
		buttonBaixarMusica = new JButton("Baixar Música");
		buttonBaixarMusica.setBounds(POSICAO_TELA_BOTAO_BAIXAR_MUSICA_X, POSICAO_TELA_BOTAO_BAIXAR_MUSICA_Y,
				BOTAO_BAIXAR_MUSICA_LARGURA, BOTAO_BAIXAR_MUSICA_ALTURA);

		// Cria o botão de Escolher Arquivo
		buttonEscolherArquivo = new JButton("Escolher arquivo");
		buttonEscolherArquivo.setBounds(POSICAO_TELA_BOTAO_ESCOLHER_ARQUIVO_X, POSICAO_TELA_BOTAO_ESCOLHER_ARQUIVO_Y,
				BOTAO_ESCOLHER_ARQUIVO_LARGURA, BOTAO_ESCOLHER_ARQUIVO_ALTURA);

		// Cria o botão de limpar texto
		buttonLimparTexto = new JButton("Limpar Texto");
		buttonLimparTexto.setBounds(POSICAO_TELA_BOTAO_LIMPAR_TEXTO_X, POSICAO_TELA_BOTAO_LIMPAR_TEXTO_Y,
				BOTAO_LIMPAR_TEXTO_LARGURA, BOTAO_LIMPAR_TEXTO_ALTURA);
	}

	// Função responsável por criar o título da tela
	private void criaTitulo() {
		// Cria um label contendo o título
		titulo = new JLabel("<html><span style='color: black;'>Music Text Composition</span></html>");
		titulo.setBounds(POSICAO_TELA_TITULO_X, POSICAO_TELA_TITULO_Y, LARGURA_TITULO, ALTURA_TITULO);
		titulo.setFont(titulo.getFont().deriveFont(50.0F));
	}

	// Função responsável por capturar o click no botão 'EscolherArquivo' e fazer
	// seu devido tratamento
	private void clicarBotaoEscolherArquivo() {
		buttonEscolherArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// Verifica se é possível abrir o fileChooser
				int retornoVerificacao = fileChooser.showOpenDialog(null);

				if (retornoVerificacao == JFileChooser.APPROVE_OPTION) {
					try {
						File arquivo = fileChooser.getSelectedFile();
						Scanner scanner = new Scanner(arquivo);
						while (scanner.hasNextLine()) {
							inputTextoMusica.append(scanner.nextLine());
							inputTextoMusica.append("\n");
						}
						scanner.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}

	// Função responsável por limpar o TextArea contendo o texto
	private void clicarBotaoLimparTexto() {
		buttonLimparTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limpa o TextArea
				inputTextoMusica.setText("");
			}
		});
	}

	// Função responsável por capturar o click no botão 'GerarMusica' e fazer seu
	// devido tratamento
	private void clicarBotaoGerarMusica() {
		buttonGerarMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pegaConteudoDosInputs(); // Seta o conteúdo dos inputs
				// Faz as verificações necessárias dos campos obrigatórios
				Boolean retornoVerificacaoCampos = verificaCamposAoClicarBotaoGerarMusica();
				//Verifica se já está tocando uma música para não sobrepor
				Boolean retornoSeTaTocando = tocador.musicaTaTocando(); 
				// Se retornar true, prossegue com o programa
				if (retornoVerificacaoCampos && !retornoSeTaTocando) {
					try {
						ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral();
						configuracaoGeral.setNomeArquivo(conteudoNomeArquivo);
						configuracaoGeral.setBpm(Integer.parseInt(conteudoBpm));
						configuracaoGeral.setInstrumento(conteudoComboInstrumento);

						Conversao converteOTexto = new Conversao(configuracaoGeral, conteudoTextoMusica);
						Sequence sequencia = converteOTexto.converteTexto();
						
						tocador.tocaMusica(sequencia, configuracaoGeral);
					} catch (NumberFormatException ex) {
						JOptionPane.showOptionDialog(null, "BPM aceita apenas números!", "ERRO",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
					}
				}
			}
		});
	}

	// Função responsável por capturar o click no botão 'BaixarMusica' e fazer seu
	// devido tratamento
	private void clicarBotaoBaixarMusica() {
		buttonBaixarMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pegaConteudoDosInputs(); // Seta o conteúdo dos inputs
				// Faz as verificações necessárias dos campos obrigatórios
				Boolean retornoVerificacaoCampos = verificaCamposAoClicarBotaoBaixarMusica();
				// Se retornar true, prossegue com o programa
				if (retornoVerificacaoCampos) {
					try {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fileChooser.setApproveButtonText("Salvar");
						fileChooser.setDialogTitle("Escolha o local para baixar a música");
						fileChooser.setSelectedFile(new File(conteudoNomeArquivo));
						int selecaoUsuario = fileChooser.showSaveDialog(null);

						if (selecaoUsuario == JFileChooser.APPROVE_OPTION) {
							String name = conteudoNomeArquivo + ".mid";
							name = fileChooser.getCurrentDirectory() + "/" + name;
							File arquivoMidi = new File(name);

							ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral();
							configuracaoGeral.setNomeArquivo(conteudoNomeArquivo);
							configuracaoGeral.setBpm(Integer.parseInt(conteudoBpm));
							configuracaoGeral.setInstrumento(conteudoComboInstrumento);

							Conversao converteOTexto = new Conversao(configuracaoGeral, conteudoTextoMusica);
							Sequence sequencia = converteOTexto.converteTexto();

							Tocador tocador = new Tocador();
							tocador.baixaMusica(sequencia, arquivoMidi);
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showOptionDialog(null, "BPM aceita apenas números!", "ERRO",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
					}
				}
			}
		});
	}

	// Função para verificar os campos obrigatórios ao clicar no botão 'GerarMusica'
	// Caso algum campo não tenha sido informado, a função retorna false
	// Caso todos campos tenham sido informados, a função retorna true
	private boolean verificaCamposAoClicarBotaoGerarMusica() {
		if (getConteudoTextoMusica().isEmpty()) {
			JOptionPane.showOptionDialog(null, "Texto para conversão não informado!", "ERRO",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		if (getConteudoBpm().isEmpty()) {
			JOptionPane.showOptionDialog(null, "BPM não informado!", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}
		
		if(Double.parseDouble(getConteudoBpm()) >= 999999999)
		{
			JOptionPane.showOptionDialog(null, "BPM muito alto! Informe um valor menor", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		if (getConteudoComboInstrumento().isEmpty()) {
			JOptionPane.showOptionDialog(null, "Instrumento não informado!", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		return true;
	}

	// Função para verificar os campos obrigatórios ao clicar no botão
	// 'BaixarMusica'
	// Caso algum campo não tenha sido informado, a função retorna false
	// Caso todos campos tenham sido informados, a função retorna true
	private boolean verificaCamposAoClicarBotaoBaixarMusica() {
		if (getConteudoTextoMusica().isEmpty()) {
			JOptionPane.showOptionDialog(null, "Texto para conversão não informado!", "ERRO",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		if (getConteudoNomeArquivo().isEmpty()) {
			JOptionPane.showOptionDialog(null, "Nome do arquivo não informado!", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		if (getConteudoBpm().isEmpty()) {
			JOptionPane.showOptionDialog(null, "BPM não informado!", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}
		
		if(Double.parseDouble(getConteudoBpm()) >= 999999999)
		{
			JOptionPane.showOptionDialog(null, "BPM muito alto! Informe um valor menor", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		if (getConteudoComboInstrumento().isEmpty()) {
			JOptionPane.showOptionDialog(null, "Instrumento não informado!", "ERRO", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, null, null);
			return false;
		}

		return true;
	}

	// Função responsável por pegar o conteúdo dos inputs e atribuir a sua
	// respectiva variável
	private void pegaConteudoDosInputs() {
		this.conteudoTextoMusica = inputTextoMusica.getText();
		this.conteudoNomeArquivo = inputNomeArquivo.getText();
		this.conteudoBpm = inputBpm.getText();
		this.conteudoComboInstrumento = comboInstrumento.getSelectedItem().toString();
	}

	private String getConteudoTextoMusica() {
		return conteudoTextoMusica;
	}

	private String getConteudoNomeArquivo() {
		return conteudoNomeArquivo;
	}

	private String getConteudoBpm() {
		return conteudoBpm;
	}

	private String getConteudoComboInstrumento() {
		return conteudoComboInstrumento;
	}
}
