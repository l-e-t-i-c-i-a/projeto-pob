/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Veiculo;
import modelo.Bilhete;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private JLabel label_4;

	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					new TelaConsulta();
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaConsulta() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);

		frame.setResizable(false);
		frame.setTitle("Consultas");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.LIGHT_GRAY);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		button = new JButton("Consultar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index<0)
					label_4.setText("consulta nao selecionada");
				else {
					label_4.setText("");
					switch(index) {
					case 0: 
						String valorStr = JOptionPane.showInputDialog("digite o valor");
						double valor = Double.parseDouble(valorStr);
						List<Bilhete> resultado1 = Fachada.consultarBilhetesPorValor(valor) ;
						listagemBilhete(resultado1);
						break;
					case 1: 
						String dataStr = JOptionPane.showInputDialog("digite a data");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			            LocalDate data = LocalDate.parse(dataStr, formatter);
						List<Veiculo> resultado2 = Fachada.consultarVeiculosPorDataDeSaida(data.atStartOfDay());
						listagemVeiculo(resultado2);		
						break;
					case 2: 
						String n = JOptionPane.showInputDialog("digite X");
						int numero = Integer.parseInt(n);
						List<Veiculo> resultado3 = Fachada.consultarVeiculosPorQuantidadeBilhetes(numero);
						listagemVeiculo(resultado3);
						break;

					}
				}

			}
		});
		button.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(button);

		comboBox = new JComboBox<String>();
		comboBox.setToolTipText("selecione a consulta");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"bilhetes com valor maior que X", "veiculos com valor pago na data X", "veiculos com mais de X bilhetes"}));
		comboBox.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboBox);
	}
	
	public void listagemBilhete(List<Bilhete> lista) {
	    try {
	        // Criando o modelo da tabela
	        DefaultTableModel model = new DefaultTableModel();
	        table.setModel(model);

	        // Adicionando as colunas na tabela
	        model.addColumn("Código de Barra");
	        model.addColumn("Placa do Veículo");
	        model.addColumn("Data e Hora de Entrada");
	        model.addColumn("Data e Hora de Saída");
	        model.addColumn("Valor Pago");

	        // Preenchendo as linhas da tabela com os dados dos bilhetes
	        for (Bilhete b : lista) {
	            String codigoDeBarra = b.getCodigoDeBarra();
	            String placaVeiculo = b.getVeiculo() != null ? b.getVeiculo().getPlaca() : "N/A"; // Supondo que o veículo tenha a placa
	            String dataHoraInicial = b.getDataHoraInicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	            String dataHoraFinal = b.getDataHoraFinal() != null ? b.getDataHoraFinal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A";
	            double valorPago = b.getValorPago();

	            // Adicionando a linha na tabela
	            model.addRow(new Object[] { codigoDeBarra, placaVeiculo, dataHoraInicial, dataHoraFinal, valorPago });
	        }

	        // Ajustando a largura das colunas (se necessário)
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        table.getColumnModel().getColumn(0).setMaxWidth(120); // Coluna código de barra
	        table.getColumnModel().getColumn(1).setMinWidth(100); // Coluna placa do veículo
	        table.getColumnModel().getColumn(2).setMinWidth(150); // Coluna data de entrada
	        table.getColumnModel().getColumn(3).setMinWidth(150); // Coluna data de saída
	        table.getColumnModel().getColumn(4).setMinWidth(100); // Coluna valor pago
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Habilita redimensionamento automático

	    } catch (Exception erro) {
	        label.setText("Erro ao listar bilhetes: " + erro.getMessage());
	    }
	}

	
	public void listagemVeiculo(List<Veiculo> lista) {
	    try {
	        // Objeto model contém todas as linhas e colunas da tabela
	        DefaultTableModel model = new DefaultTableModel();
	        table.setModel(model);

	        // Criar as colunas (Placa, Bilhetes) da tabela
	        model.addColumn("Placa");
	        model.addColumn("Bilhetes");

	        // Criar as linhas da tabela
	        String bilhetes;
	        for (Veiculo v : lista) {
	            // Obter a placa do veículo
	            String placa = v.getPlaca();

	            // Obter os bilhetes associados, transformando em um string formatada
	            bilhetes = v.getBilhetes().isEmpty() 
	                ? "Sem bilhetes" 
	                : v.getBilhetes().stream()
	                    .map(b -> b.getCodigoDeBarra()) // Obter apenas o código de barras do bilhete
	                    .collect(Collectors.joining(", "));

	            // Adicionar linha na tabela
	            model.addRow(new Object[] { placa, bilhetes });
	        }

	        // Redimensionar as colunas
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desabilita redimensionamento automático
	        table.getColumnModel().getColumn(0).setMaxWidth(100); // Coluna da placa
	        table.getColumnModel().getColumn(1).setMinWidth(200); // Coluna dos bilhetes
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Habilita redimensionamento automático

	    } catch (Exception erro) {
	        label.setText(erro.getMessage());
	    }
	}



	/*public void listagemPessoa(List<Pessoa> lista) {
		try {
			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("Nascimento");
			model.addColumn("Apelidos");
			model.addColumn("Telefones");

			// criar as linhas da tabela
			String texto1, texto2;
			for (Pessoa p : lista) {
				texto1 = String.join(",", p.getApelidos()); // concatena strings
				if (p.getTelefones().size() > 0) {
					texto2 = "";
					for (Telefone t : p.getTelefones())
						texto2 += t.getNumero() + " ";
				} else
					texto2 = "sem telefone";
				//adicionar linha no table
				model.addRow(new Object[] { p.getId(), p.getNome(), p.getDtNascimento(), texto1, texto2 });

			}
			// redimensionar a coluna 0,3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.getColumnModel().getColumn(3).setMinWidth(200); // coluna dos apelidos
			table.getColumnModel().getColumn(4).setMinWidth(200); // coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}*/


}
