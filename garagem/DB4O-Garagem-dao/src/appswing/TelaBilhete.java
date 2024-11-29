/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
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
import java.time.LocalDateTime;
import java.util.List;

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

import modelo.Bilhete;
import modelo.Veiculo;
import regras_negocio.Fachada;

public class TelaBilhete {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button_Listar;
	private JButton button_Apagar;
	private JLabel label;
	private JTextField textField_codigo;
	private JLabel lblPlaca;
	private JLabel lblHoraSaida;
	private JTextField textField_Saida;
	private JTextField textField_placa;
	private JButton btnSaída;

	private JLabel lblCodigo;
	private JLabel lblSelecioneUmBilhete;
	private JButton btnAtualizar;
	private JLabel lblNovaDatahoraSaida;
	private JTextField textField_NovaSaida;
	private JTextField textField_codigo1;

	
	/**
	 * Create the application.
	 */
	public TelaBilhete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);		//janela modal
		
		frame.setTitle("Bilhetes");
		//frame.setBounds(100, 100, 378, 373);
		frame.setBounds(100, 100, 600, 500); // Aumentando o tamanho da janela
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
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
		frame.setResizable(false);
		
		scrollPane = new JScrollPane();
		//scrollPane.setBounds(26, 44, 315, 152);
		scrollPane.setBounds(26, 44, 540, 300); // Ajustando o tamanho do painel de rolagem

		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			//proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		//evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						//pegar o nome selecionado
						String codigo = (String) table.getValueAt( table.getSelectedRow(), 1);
						String placa = (String) table.getValueAt( table.getSelectedRow(), 2);
						String saida = (String) table.getValueAt( table.getSelectedRow(), 4);
						textField_Saida.setText(saida);
						textField_placa.setText(placa);
						textField_codigo1.setText(codigo);
						label.setText("");
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.YELLOW);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_Apagar = new JButton("Apagar");
		button_Apagar.setToolTipText("remover o bilhete do veiculo");
		button_Apagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_codigo1.getText().isEmpty()) {
						label.setText("codigo vazio");
						return;
					}
					String codigo = textField_codigo1.getText();
					//confirma��o
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null, "Confirma exclus�o do bilhete "+codigo, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if(escolha == 0) {
						Fachada.apagarBilhete(codigo);
						label.setText("exclus�o realizada");
						listagem();
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_Apagar.setBounds(380, 412, 80, 23);
		frame.getContentPane().add(button_Apagar);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(26, 447, 326, 14);
		frame.getContentPane().add(label);

		button_Listar = new JButton("Listar");
		button_Listar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_Listar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Listar.setBounds(204, 10, 112, 23);
		frame.getContentPane().add(button_Listar);

		textField_codigo = new JTextField();
		textField_codigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_codigo.setColumns(10);
		textField_codigo.setBounds(78, 11, 86, 20);
		frame.getContentPane().add(textField_codigo);

		lblPlaca = new JLabel("placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(26, 364, 71, 14);
		frame.getContentPane().add(lblPlaca);

		lblHoraSaida = new JLabel("DataHora Saída:");
		lblHoraSaida.setHorizontalAlignment(SwingConstants.LEFT);
		lblHoraSaida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHoraSaida.setBounds(25, 386, 95, 14);
		frame.getContentPane().add(lblHoraSaida);

		lblCodigo = new JLabel("Codigo:");
		lblCodigo.setBounds(26, 15, 57, 14);
		frame.getContentPane().add(lblCodigo);

		lblSelecioneUmBilhete = new JLabel("selecione um bilhete");
		lblSelecioneUmBilhete.setBounds(26, 343, 336, 14);
		frame.getContentPane().add(lblSelecioneUmBilhete);

		textField_Saida = new JTextField();
		textField_Saida.setColumns(10);
		textField_Saida.setBounds(118, 384, 95, 20);
		frame.getContentPane().add(textField_Saida);

		textField_placa = new JTextField();
		textField_placa.setColumns(10);
		textField_placa.setBounds(58, 362, 95, 20);
		frame.getContentPane().add(textField_placa);

		btnSaída = new JButton("Registrar Saída");
		btnSaída.setToolTipText("adicionar data/hora de saída para um veículo");
		btnSaída.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_Saida.getText().isEmpty()) {
						label.setText("Saída vazia");
						return;
					}
					String saida = textField_Saida.getText();
					String placa = textField_placa.getText();
					Fachada.registrarSaida(placa, LocalDateTime.parse(saida));
					label.setText("Saída registrada");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnSaída.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSaída.setBounds(26, 412, 120, 23);
		frame.getContentPane().add(btnSaída);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setToolTipText("alterar a data/hora da saída");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_Saida.getText().isEmpty()) {
						label.setText("Saída Vazia");
						return;
					}
					String saida = textField_placa.getText();
					String novaSaida = textField_NovaSaida.getText();
					Fachada.alterarBilhete(saida, LocalDateTime.parse(novaSaida));
					label.setText("telefone atualizado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAtualizar.setBounds(156, 412, 80, 23);
		frame.getContentPane().add(btnAtualizar);

		lblNovaDatahoraSaida = new JLabel("Nova DataHora Saída:");
		lblNovaDatahoraSaida.setHorizontalAlignment(SwingConstants.LEFT);
		lblNovaDatahoraSaida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNovaDatahoraSaida.setBounds(239, 387, 123, 14);
		frame.getContentPane().add(lblNovaDatahoraSaida);

		textField_NovaSaida = new JTextField();
		textField_NovaSaida.setColumns(10);
		textField_NovaSaida.setBounds(365, 384, 95, 20);
		frame.getContentPane().add(textField_NovaSaida);
		
		JLabel lblCodigo1 = new JLabel("codigo:");
		lblCodigo1.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigo1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCodigo1.setBounds(306, 364, 46, 14);
		frame.getContentPane().add(lblCodigo1);
		
		textField_codigo1 = new JTextField();
		textField_codigo1.setColumns(10);
		textField_codigo1.setBounds(365, 362, 95, 20);
		frame.getContentPane().add(textField_codigo1);

		frame.setVisible(true);
	}

	public void listagem () {
		try{
			List<Bilhete> lista = Fachada.consultarBilhetes(textField_codigo.getText());
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			
			model.addColumn("Codigo");
			model.addColumn("Placa");
			model.addColumn("Entrada");
			model.addColumn("Saída");

			for(Bilhete b : lista) {
				if(b.getVeiculo() != null)
					model.addRow(new Object[]{b.getCodigoDeBarra(),  b.getVeiculo().getPlaca(), b.getDataHoraInicial() , b.getDataHoraFinal()});
				else
					model.addRow(new Object[]{b.getCodigoDeBarra(),  b.getVeiculo().getPlaca(), b.getDataHoraInicial() , b.getDataHoraFinal()});
			}
			lblSelecioneUmBilhete.setText("resultados: "+lista.size()+ " Bilhetes  - selecione uma linha para editar");
			
			// redimensionar a coluna 0
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(100); // coluna codigo
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita
			
			// Ajustar larguras das colunas
			table.getColumnModel().getColumn(0).setPreferredWidth(100); // Codigo
			table.getColumnModel().getColumn(1).setPreferredWidth(100); // Placa
			table.getColumnModel().getColumn(2).setPreferredWidth(200); // Entrada
			table.getColumnModel().getColumn(3).setPreferredWidth(200); // Saída
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
