import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Tela {
	private final BancoController banco;

	private JFrame frame = null;
	private JList lstContas = new JList();
	private JList lstClientes = new JList();
	private JPanel pnlContaListItem = new JPanel();

	private final JDialog criarClienteDialog = new JDialog();
	private final JDialog criarContaDialog = new JDialog();
	private final JDialog novoSaqueDialog = new JDialog();
	private final JDialog novoDepositoDialog = new JDialog();
	private final JButton btnSacar = new JButton("Sacar");
	private final JButton btnDepositar = new JButton("Depositar");
	private final JButton btnReajustar = new JButton("Reajustar");
	private final JButton btnNovaConta = new JButton("Nova Conta");
	private final JButton btnNovoCliente = new JButton("Novo Cliente");
	private final JButton btnTransferir = new JButton("Transferir");

	private static JComboBox comboCliente;

	public Tela(BancoController banco) {
		this.banco = banco;
	}

  private void show() {
  this.frame = new JFrame(banco.getNome());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	panel.setLayout(null);
	panel.setBackground(Color.GRAY);

	JLabel menuPnlTitle = new JLabel("MENU");
	menuPnlTitle.setBounds(30, 15, 100, 25);
	menuPnlTitle.setFont(new Font("Arial", Font.BOLD, 14));
	menuPnlTitle.setForeground(Color.WHITE);

	JLabel clientePnlTitle = new JLabel("CLIENTES");
	clientePnlTitle.setBounds(200, 15, 100, 25);
	clientePnlTitle.setFont(new Font("Arial", Font.BOLD, 14));
	clientePnlTitle.setForeground(Color.WHITE);

	JLabel contaPnlTitle = new JLabel("CONTA");
	contaPnlTitle.setBounds(30, 250, 100, 25);
	contaPnlTitle.setFont(new Font("Arial", Font.BOLD, 14));
	contaPnlTitle.setForeground(Color.WHITE);

	JLabel contaListItemPnlTitle = new JLabel("DETALHES DA CONTA");
	contaListItemPnlTitle.setBounds(200, 250, 160, 25);
	contaListItemPnlTitle.setFont(new Font("Arial", Font.BOLD, 14));
	contaListItemPnlTitle.setForeground(Color.WHITE);

	JPanel pnlBotoes = new JPanel();
	pnlBotoes.setLayout(null);
	pnlBotoes.setOpaque(false);
	pnlBotoes.setLocation(15, 50);
	pnlBotoes.setSize(200, 200);

	btnNovaConta.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				criarContaDialog(btnNovaConta);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	btnNovaConta.setBounds(10, 0, 150, 25);
	pnlBotoes.add(btnNovaConta);

	btnNovoCliente.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				criarClienteDialog(btnNovoCliente);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	btnNovoCliente.setBounds(10, 30, 150, 25);
	pnlBotoes.add(btnNovoCliente);

	btnSacar.setBounds(10, 60, 150, 25);
	btnSacar.setEnabled(false);
	btnSacar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				novoSaqueDialog(btnSacar);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	pnlBotoes.add(btnSacar);

	btnDepositar.setBounds(10, 90, 150, 25);
	btnDepositar.setEnabled(false);
	btnDepositar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				novoDepositoDialog(btnDepositar);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	pnlBotoes.add(btnDepositar);

	btnReajustar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				Conta conta = ((ContaListItem) lstContas.getSelectedValue()).getConta();
				Cliente cliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
				banco.reajustarInvestimentos(conta);
				refreshContas(cliente);
				pnlContaListItem.removeAll();
				pnlContaListItem.updateUI();
				pnlContaListItem.repaint();
				btnSacar.setEnabled(false);
				btnDepositar.setEnabled(false);
				btnReajustar.setEnabled(false);
				btnTransferir.setEnabled(false);
				JOptionPane.showMessageDialog(frame, "Investimento reajustado com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	btnReajustar.setBounds(10, 120, 150, 25);
	btnReajustar.setEnabled(false);
	pnlBotoes.add(btnReajustar);

	btnTransferir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				novaTransferenciaDialog(btnTransferir);
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	btnTransferir.setBounds(10, 150, 150, 25);
	btnTransferir.setEnabled(false);
	pnlBotoes.add(btnTransferir);

	panel.add(menuPnlTitle);
	panel.add(clientePnlTitle);
	panel.add(contaPnlTitle);
	panel.add(contaListItemPnlTitle);
	panel.add(pnlBotoes);
	panel.add(criarListaClientePanel());
	panel.add(criarListaContaPanel());
	panel.add(criarContaListItemPanel());

	frame.getContentPane().add(panel);

	refreshClientes();

    //Display the window.
	frame.setSize(500, 530);
    frame.setVisible(true);
  }

	private void refreshContas(Cliente cliente) {
		DefaultListModel model = new DefaultListModel();
		java.util.List<Conta> contas = banco.listaContas(cliente);
		for (Conta c : contas) {
			model.addElement(new ContaListItem(c));
		}
		lstContas.setModel(model);
	}

	private void refreshClientes() {
		DefaultListModel model = new DefaultListModel();
		java.util.List<Cliente> clientes = banco.listaClientes();
		for (Cliente c : clientes) {
			model.addElement(new ClienteListItem(c));
		}
		lstClientes.setModel(model);
	}

	private void showException(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private void criarContaDialog(JButton btnNovaConta) {
		Window parentWindow = SwingUtilities.windowForComponent(btnNovaConta);
    criarContaDialog.setModal(true);
    criarContaDialog.setSize(280, 150);
    criarContaDialog.add(criarContaPanel());
    criarContaDialog.setVisible(true);
	}

	private void novaTransferenciaDialog(JButton btnTransferir) {
		Window parentWindow = SwingUtilities.windowForComponent(btnTransferir);
    criarContaDialog.setModal(true);
    criarContaDialog.setSize(280, 150);
    criarContaDialog.add(novaTransferenciaPanel());
    criarContaDialog.setVisible(true);
	}

	private void criarClienteDialog(JButton btnNovoCliente) {
		Window parentWindow = SwingUtilities.windowForComponent(btnNovoCliente);
    criarClienteDialog.setModal(true);
    criarClienteDialog.setSize(280, 120);
    criarClienteDialog.add(criarClientePanel());
    criarClienteDialog.setVisible(true);
	}

	private void novoSaqueDialog(JButton btnSacar) {
		Window parentWindow = SwingUtilities.windowForComponent(btnSacar);
    novoSaqueDialog.setModal(true);
    novoSaqueDialog.setSize(280, 120);
    novoSaqueDialog.add(criarSaquePanel());
    novoSaqueDialog.setVisible(true);
	}

	private void novoDepositoDialog(JButton btnDepositar) {
		Window parentWindow = SwingUtilities.windowForComponent(btnDepositar);
    novoDepositoDialog.setModal(true);
    novoDepositoDialog.setSize(280, 120);
    novoDepositoDialog.add(criarDepositoPanel());
    novoDepositoDialog.setVisible(true);
	}

	private JScrollPane criarListaContaPanel() {
		JScrollPane scrContas = new JScrollPane(lstContas);
		scrContas.setBounds(30, 280, 150, 190);

		lstContas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				if (lstContas.getSelectedIndex() == -1) return;
				Conta conta = ((ContaListItem) lstContas.getSelectedValue()).getConta();

				pnlContaListItem.removeAll();
				pnlContaListItem.updateUI();
				pnlContaListItem.repaint();

				btnSacar.setEnabled(true);
				btnDepositar.setEnabled(true);
				btnTransferir.setEnabled(true);
				btnReajustar.setEnabled(conta instanceof ContaPoupanca);

				JLabel lblCodigo = new JLabel("Codigo: " + conta.getCodigo());
				lblCodigo.setVisible(true);
				lblCodigo.setBounds(10, 10, 260, 15);

				JLabel lblTipoConta = new JLabel("Tipo de Conta: " + conta.getTipoConta());
				lblTipoConta.setVisible(true);
				lblTipoConta.setBounds(10, 30, 260, 15);

				JLabel lblSaldo = new JLabel("Saldo: " + conta.getSaldo());
				lblSaldo.setVisible(true);
				lblSaldo.setBounds(10, 50, 260, 15);

				JLabel lblCliente = new JLabel("Cliente: " + conta.getCliente().getNome());
				lblCliente.setVisible(true);
				lblCliente.setBounds(10, 70, 260, 15);

				pnlContaListItem.add(lblCodigo);
				pnlContaListItem.add(lblTipoConta);
				pnlContaListItem.add(lblSaldo);

				if (conta instanceof ContaCorrente) {
					JLabel lblLimite = new JLabel("Limite: " + ((ContaCorrente) conta).getLimite());
					lblLimite.setVisible(true);
					lblLimite.setBounds(10, 70, 260, 15);
					pnlContaListItem.add(lblLimite);
				}
			}
		});

		return scrContas;
	}

	private JScrollPane criarListaClientePanel() {
		JScrollPane scrClientes = new JScrollPane(lstClientes);
		scrClientes.setBounds(200, 50, 260, 190);

		lstClientes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent e) {
      	if (lstClientes.getSelectedIndex() == -1) return;
      	Cliente cliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
      	java.util.List<Conta> contas = banco.listaContas(cliente);

				pnlContaListItem.removeAll();
				pnlContaListItem.updateUI();
				pnlContaListItem.repaint();

				btnSacar.setEnabled(false);
				btnDepositar.setEnabled(false);
				btnReajustar.setEnabled(false);
				btnTransferir.setEnabled(false);

      	DefaultListModel model = new DefaultListModel();
				for (Conta c : contas) {
					model.addElement(new ContaListItem(c));
				}
				lstContas.setModel(model);
	    }
		});

		return scrClientes;
	}

	private JPanel criarContaListItemPanel() {
		pnlContaListItem.setLayout(null);
		pnlContaListItem.setBounds(200, 280, 260, 190);
		return pnlContaListItem;
	}

	private JPanel criarContaPanel() {
		JPanel pnlCriarConta = new JPanel();
		pnlCriarConta.setLayout(null);
		pnlCriarConta.setBounds(10, 210, 280, 130);
		pnlCriarConta.setBackground(Color.CYAN);

		final JLabel lblLimite = new JLabel("Limite");
		lblLimite.setBounds(10, 37, 100, 25);

		final JTextField txtLimite = new JTextField();
		txtLimite.setBounds(115, 37, 100, 25);

		JLabel lblTipoConta = new JLabel("Tipo da Conta");
		lblTipoConta.setBounds(10, 10, 100, 25);

		final JComboBox comboTipoConta = new JComboBox(new String[] {"Conta Corrente", "Conta Poupanca", "Conta Salario"});
		comboTipoConta.setBounds(115, 10, 150, 25);
		comboTipoConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean limiteVisivel = comboTipoConta.getSelectedIndex() == 0;
				lblLimite.setVisible(limiteVisivel);
				txtLimite.setVisible(limiteVisivel);
			}
		});

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 64, 100, 25);

		DefaultComboBoxModel comboBoxCliente = new DefaultComboBoxModel();
		for (Object o : banco.listaClientes().toArray()) {
			comboBoxCliente.addElement(o.toString());
		}

		comboCliente = new JComboBox(comboBoxCliente);
		comboCliente.setBounds(115, 64, 150, 25);

		JButton btnCriarContaConfirma = new JButton("Criar");
		btnCriarContaConfirma.setBounds(50, 91, 100, 25);

		btnCriarContaConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (comboCliente.getSelectedItem() != null) {
						String valor = txtLimite.getText().trim();
						int tipoConta = comboTipoConta.getSelectedIndex() + 1;
						double limite = valor.length() == 0 ? 0 : Double.valueOf(valor);
						Cliente cliente = banco.buscaCliente(comboCliente.getSelectedItem().toString());
						banco.criaConta(cliente, tipoConta, limite);
						criarContaDialog.setVisible(false);
						txtLimite.setText("");

						if (lstClientes.getSelectedValue() != null) {
							Cliente selectedCliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
							refreshContas(selectedCliente);
						}

					}
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlCriarConta.add(lblTipoConta);
		pnlCriarConta.add(comboTipoConta);
		pnlCriarConta.add(lblCliente);
		pnlCriarConta.add(comboCliente);
		pnlCriarConta.add(lblLimite);
		pnlCriarConta.add(txtLimite);
		pnlCriarConta.add(btnCriarContaConfirma);

		return pnlCriarConta;
	}

	private JPanel novaTransferenciaPanel() {
		JPanel pnlNovaTransferencia = new JPanel();
		pnlNovaTransferencia.setLayout(null);
		pnlNovaTransferencia.setBounds(10, 210, 280, 100);
		pnlNovaTransferencia.setBackground(Color.CYAN);

		final JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(10, 10, 100, 25);

		final JTextField txtValor = new JTextField();
		txtValor.setBounds(115, 10, 100, 25);

		JLabel lblConta = new JLabel("Conta");
		lblConta.setBounds(10, 37, 100, 25);

		final JComboBox comboConta = new JComboBox(banco.listaContas().toArray());
		comboConta.setBounds(115, 37, 150, 25);

		JButton btnTransferirConfirma = new JButton("Transferir");
		btnTransferirConfirma.setBounds(50, 75, 100, 25);

		btnTransferirConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					double valor = Double.parseDouble(txtValor.getText().trim());
					int codigo = Integer.parseInt(comboConta.getSelectedItem().toString().split(";")[0].split(" ")[1]);
					Conta conta = banco.buscar(codigo);
					Cliente cliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
					Conta selectedConta = ((ContaListItem) lstContas.getSelectedValue()).getConta();

					pnlContaListItem.removeAll();
					pnlContaListItem.updateUI();
					pnlContaListItem.repaint();

					banco.transferir(selectedConta, conta, valor);
					criarContaDialog.setVisible(false);
					txtValor.setText("");
					refreshContas(cliente);
					btnSacar.setEnabled(false);
					btnDepositar.setEnabled(false);
					btnReajustar.setEnabled(false);
					btnTransferir.setEnabled(false);

					JOptionPane.showMessageDialog(frame, "Transferencia de R$" + valor + " para Conta " + conta.getCodigo() + " realizado com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlNovaTransferencia.add(lblConta);
		pnlNovaTransferencia.add(comboConta);
		pnlNovaTransferencia.add(lblValor);
		pnlNovaTransferencia.add(txtValor);
		pnlNovaTransferencia.add(btnTransferirConfirma);

		return pnlNovaTransferencia;
	}

	private JPanel criarClientePanel(){
		JPanel pnlCriarCliente = new JPanel();
		pnlCriarCliente.setLayout(null);
		pnlCriarCliente.setBounds(10, 210, 280, 100);
		pnlCriarCliente.setBackground(Color.CYAN);

		final JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 20, 100, 25);

		final JTextField txtNome = new JTextField();
		txtNome.setBounds(60, 20, 100, 25);

		JButton btnCriarClienteConfirma = new JButton("Criar");
		btnCriarClienteConfirma.setBounds(0, 50, 100, 25);

		btnCriarClienteConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String nome = txtNome.getText().trim();
					banco.criaCliente(nome);
					criarClienteDialog.setVisible(false);
					txtNome.setText("");
					refreshClientes();
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlCriarCliente.add(lblNome);
		pnlCriarCliente.add(txtNome);
		pnlCriarCliente.add(btnCriarClienteConfirma);

		return pnlCriarCliente;
	}

	private JPanel criarSaquePanel(){
		JPanel pnlNovoSaque = new JPanel();
		pnlNovoSaque.setLayout(null);
		pnlNovoSaque.setBounds(10, 210, 280, 100);
		pnlNovoSaque.setBackground(Color.CYAN);

		final JLabel lblValor = new JLabel("Valor a ser sacado: ");
		lblValor.setBounds(10, 20, 150, 25);

		final JTextField txtValor = new JTextField();
		txtValor.setBounds(150, 20, 100, 25);

		JButton btnSacarConfirma = new JButton("Sacar");
		btnSacarConfirma.setBounds(0, 50, 100, 25);

		btnSacarConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					double valor = Double.parseDouble(txtValor.getText().trim());
					Conta conta = ((ContaListItem) lstContas.getSelectedValue()).getConta();
					Cliente cliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
					banco.sacar(conta, valor);
					pnlContaListItem.removeAll();
					pnlContaListItem.updateUI();
					pnlContaListItem.repaint();
					txtValor.setText("");
					novoSaqueDialog.setVisible(false);
					refreshContas(cliente);
					btnSacar.setEnabled(false);
					btnDepositar.setEnabled(false);
					btnReajustar.setEnabled(false);
					btnTransferir.setEnabled(false);
					JOptionPane.showMessageDialog(frame, "Saque de R$" + valor + " realizado com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlNovoSaque.add(lblValor);
		pnlNovoSaque.add(txtValor);
		pnlNovoSaque.add(btnSacarConfirma);

		return pnlNovoSaque;
	}

	private JPanel criarDepositoPanel(){
		JPanel pnlNovoDeposito = new JPanel();
		pnlNovoDeposito.setLayout(null);
		pnlNovoDeposito.setBounds(10, 210, 280, 100);
		pnlNovoDeposito.setBackground(Color.CYAN);

		final JLabel lblValor = new JLabel("Valor a ser depositado: ");
		lblValor.setBounds(10, 20, 150, 25);

		final JTextField txtValor = new JTextField();
		txtValor.setBounds(160, 20, 100, 25);

		JButton btnDepositarConfirma = new JButton("Depositar");
		btnDepositarConfirma.setBounds(0, 50, 100, 25);

		btnDepositarConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					double valor = Double.parseDouble(txtValor.getText().trim());
					Conta conta = ((ContaListItem) lstContas.getSelectedValue()).getConta();
					Cliente cliente = ((ClienteListItem) lstClientes.getSelectedValue()).getCliente();
					banco.depositar(conta, valor);
					pnlContaListItem.removeAll();
					pnlContaListItem.updateUI();
					pnlContaListItem.repaint();
					txtValor.setText("");
					novoDepositoDialog.setVisible(false);
					refreshContas(cliente);
					btnSacar.setEnabled(false);
					btnDepositar.setEnabled(false);
					btnReajustar.setEnabled(false);
					btnTransferir.setEnabled(false);
					JOptionPane.showMessageDialog(frame, "Deposito de R$" + valor + " realizado com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlNovoDeposito.add(lblValor);
		pnlNovoDeposito.add(txtValor);
		pnlNovoDeposito.add(btnDepositarConfirma);

		return pnlNovoDeposito;
	}

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            (new Tela(new BancoController("IBTA Banco"))).show();
        }
    });
  }

}
