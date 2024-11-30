/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Veiculo;
import modelo.Bilhete;
import regras_negocio.Fachada;

public class TelaVeiculo {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btn_buscarPlaca;
	private JButton btnRegistrarEntrada;
	private JButton button_3;
	private JButton btnApagar;
	private JButton btnAtualizar;
	private JLabel label;
	private JLabel lblSelecioneUmVeculo;
	private JLabel lblPlaca_2;
	private JLabel lblPlaca;
	private JLabel lblNovaPlaca;
	private JTextField textField_placaBusca;
	private JTextField textField_placaLeitura;
	private JTextField textField_placaAtualiza;

	/**
	 * Create the application.
	 */
	public TelaVeiculo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true); // janela modal

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Fachada.inicializar();
				listagem();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});
		frame.setTitle("Veículos");
		frame.setBounds(100, 100, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			// proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		// evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, data nascimento e apelidos da pessoa selecionada
						String placa = (String) table.getValueAt(table.getSelectedRow(), 0);
						//Veiculo v = Fachada.localizarVeiculo(v);
						//String data = p.getDtNascimento();
						textField_placaLeitura.setText(placa);
						//textField_2.setText(data);
						//textField_3.setText(String.join(",", p.getApelidos()));
						textField_placaAtualiza.setText("");
						label.setText("");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}

			}
		});

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		btnApagar = new JButton("Apagar");
		btnApagar.setToolTipText("apagar veículo e seus bilhetes");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_placaLeitura.getText().isEmpty()) {
						label.setText("placa vazia");
						return;
					}
					// pegar o nome na linha selecionada
					String placa = textField_placaLeitura.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operacao apagara o veiculo de placa: " + placa, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.apagarVeiculo(placa);
						label.setText("veiculo excluido");
						listagem(); // listagem
					} else
						label.setText("exclusao cancelada");

				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnApagar.setBounds(291, 315, 74, 23);
		frame.getContentPane().add(btnApagar);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 372, 668, 14);
		frame.getContentPane().add(label);

		btn_buscarPlaca = new JButton("Buscar por placa");
		btn_buscarPlaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btn_buscarPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_buscarPlaca.setBounds(175, 27, 149, 23);
		frame.getContentPane().add(btn_buscarPlaca);

		textField_placaBusca = new JTextField();
		textField_placaBusca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_placaBusca.setColumns(10);
		textField_placaBusca.setBounds(62, 28, 106, 20);
		frame.getContentPane().add(textField_placaBusca);

		lblSelecioneUmVeculo = new JLabel("selecione um veículo para editar");
		lblSelecioneUmVeculo.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(lblSelecioneUmVeculo);

		lblPlaca_2 = new JLabel("placa:");
		lblPlaca_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlaca_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlaca_2.setBounds(21, 239, 62, 14);
		frame.getContentPane().add(lblPlaca_2);

		textField_placaLeitura = new JTextField();
		textField_placaLeitura.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_placaLeitura.setColumns(10);
		textField_placaLeitura.setBackground(Color.WHITE);
		textField_placaLeitura.setBounds(101, 235, 86, 20);
		frame.getContentPane().add(textField_placaLeitura);

		btnRegistrarEntrada = new JButton("Registrar Entrada");
		btnRegistrarEntrada.setToolTipText("registrar entrada de veículo");
		btnRegistrarEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_placaLeitura.getText().isEmpty()) {
						label.setText("placa vazia");
						return;
					}
					String placa = textField_placaLeitura.getText().trim();
					//String nascimento = textField_2.getText().trim();
					//String[] apelidos = textField_3.getText().trim().split(",");

					Fachada.registrarEntrada(placa);
					/*String numero = textField_placaAtualiza.getText();
					if (!numero.isEmpty())
						Fachada1.criarTelefone(nome, numero);*/

					label.setText("veiculo registrado");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnRegistrarEntrada.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRegistrarEntrada.setBounds(21, 317, 147, 23);
		frame.getContentPane().add(btnRegistrarEntrada);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setToolTipText("atualizar placa");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_placaAtualiza.getText().trim().isEmpty() || textField_placaLeitura.getText().trim().isEmpty())  {
						label.setText("Placa atual e nova placa devem estar preenchidas");
						return;
					}
					String novaPlaca = textField_placaAtualiza.getText();
					String placa = textField_placaLeitura.getText();
					/*String nascimento = textField_2.getText();
					String[] apelidos = textField_3.getText().trim().split(",");*/

					Fachada.alterarPlacaVeiculo(placa, novaPlaca);
					/*String numero = textField_placaAtualiza.getText();
					if (!numero.isEmpty())
						Fachada1.criarTelefone(nome, numero);*/
					label.setText("placa alterada");
					listagem();
				} catch (Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAtualizar.setBounds(194, 315, 87, 23);
		frame.getContentPane().add(btnAtualizar);

		lblPlaca = new JLabel("Placa:");
		lblPlaca.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(lblPlaca);

		button_3 = new JButton("Limpar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_placaLeitura.setText("");
				//textField_2.setText("");
				//textField_3.setText("");
				textField_placaAtualiza.setText("");
			}
		});
		button_3.setBounds(276, 234, 89, 23);
		frame.getContentPane().add(button_3);

		lblNovaPlaca = new JLabel("nova placa:");
		lblNovaPlaca.setHorizontalAlignment(SwingConstants.LEFT);
		lblNovaPlaca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovaPlaca.setBounds(21, 268, 74, 14);
		frame.getContentPane().add(lblNovaPlaca);

		textField_placaAtualiza = new JTextField();
		textField_placaAtualiza.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_placaAtualiza.setColumns(10);
		textField_placaAtualiza.setBounds(101, 264, 86, 20);
		frame.getContentPane().add(textField_placaAtualiza);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
	        // Consulta veículos com base na placa buscada
	        List<Veiculo> lista = Fachada.consultarVeiculos(textField_placaBusca.getText());

	        // Configurar o modelo da tabela
	        DefaultTableModel model = new DefaultTableModel();
	        table.setModel(model);

	        // Adicionar as colunas na tabela
	        model.addColumn("Placa");
	        model.addColumn("Bilhetes");

	        // Criar as linhas da tabela
	        for (Veiculo v : lista) {
	            // Obter a placa
	            String placa = v.getPlaca();

	            // Obter os bilhetes associados
	            String bilhetes = v.getBilhetes().isEmpty()
	                ? "Sem bilhetes"
	                : v.getBilhetes().stream()
	                      .map(Bilhete::getCodigoDeBarra) // Utiliza o método específico para o código do bilhete
	                      .collect(Collectors.joining(", "));

	            // Adicionar a linha na tabela
	            model.addRow(new Object[] { placa, bilhetes });
	        }

	        // Atualizar label de resultados
	        lblSelecioneUmVeculo.setText("Resultados: " + lista.size() + " veículos encontrados.");

	        // Configurar redimensionamento e tamanhos das colunas
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        table.getColumnModel().getColumn(0).setMaxWidth(100); // Configurar largura para a coluna Placa
	        table.getColumnModel().getColumn(1).setMinWidth(200); // Configurar largura mínima para a coluna Bilhetes
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	    } catch (Exception erro) {
	        label.setText(erro.getMessage());
	    }
	}

}
