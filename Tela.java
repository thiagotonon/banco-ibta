import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Tela {

	private JList lstContas = new JList();
	private JPanel pnlContaListItem = new JPanel();
	private JFrame frame = null;

	private final Banco banco;
	private final JDialog dialog = new JDialog();
	private final JButton btnSacar = new JButton("Sacar");
	private final JButton btnDepositar = new JButton("Depositar");
	private final JButton btnReajustar = new JButton("Reajustar");

	public Tela(Banco banco) {
		this.banco = banco;
	}

  private void show() {
    //Create and set up the window.
  this.frame = new JFrame(banco.getNome());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	panel.setLayout(null);
	panel.setBackground(Color.GRAY);

	JPanel pnlBotoes = new JPanel();
	pnlBotoes.setLayout(null);
	pnlBotoes.setOpaque(false);
	pnlBotoes.setLocation(15, 22);
	pnlBotoes.setSize(200, 200);

	final JButton btnNovaConta = new JButton("Nova Conta");
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

	btnSacar.setBounds(10, 30, 150, 25);
	btnSacar.setEnabled(false);
	pnlBotoes.add(btnSacar);

	btnDepositar.setBounds(10, 60, 150, 25);
	btnDepositar.setEnabled(false);
	pnlBotoes.add(btnDepositar);

	btnReajustar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			try {
				banco.reajustarInvestimentos();
			} catch (Exception e) {
				showException(e);
			}
		}
	});
	btnReajustar.setBounds(10, 90, 150, 25);
	btnReajustar.setEnabled(false);
	pnlBotoes.add(btnReajustar);

	panel.add(pnlBotoes);

	panel.add(criarListaContasPanel());
	panel.add(criarContaListItemPanel());

	frame.getContentPane().add(panel);

	refreshContas();

    //Display the window.
	frame.setSize(500, 400);
    frame.setVisible(true);
  }

	private void refreshContas() {
		DefaultListModel model = new DefaultListModel();
		java.util.List<Conta> contas = banco.listaContas();
		for (Conta c : contas) {
			model.addElement(new ContaListItem(c));
		}
		lstContas.setModel(model);
	}

	private void showException(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(frame, e.getMessage(), "Deu bomba", JOptionPane.ERROR_MESSAGE);
	}

	private void criarContaDialog(JButton btnNovaConta) {
		Window parentWindow = SwingUtilities.windowForComponent(btnNovaConta);
    dialog.setModal(true);
    dialog.setSize(280, 120);
    dialog.add(criarContaPanel());
    dialog.setVisible(true);
	}

	private JScrollPane criarListaContasPanel() {
		JScrollPane scrContas = new JScrollPane(lstContas);
		scrContas.setBounds(200, 20, 260, 150);

		lstContas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent e) {
      	if (lstContas.getSelectedIndex() == -1) return;
				Conta conta = ((ContaListItem) lstContas.getSelectedValue()).getConta();

				pnlContaListItem.removeAll();
				pnlContaListItem.updateUI();
				pnlContaListItem.repaint();

				btnSacar.setEnabled(true);
				btnDepositar.setEnabled(true);
				btnReajustar.setEnabled(true);

				JLabel lblCodigo = new JLabel("Codigo: " + conta.getCodigo());
				lblCodigo.setVisible(true);
				lblCodigo.setBounds(10, 10, 260, 15);

				JLabel lblTipoConta = new JLabel("Tipo de Conta: " + conta.getTipoConta());
				lblTipoConta.setVisible(true);
				lblTipoConta.setBounds(10, 30, 260, 15);

				JLabel lblSaldo = new JLabel("Saldo: " + conta.getSaldo());
				lblSaldo.setVisible(true);
				lblSaldo.setBounds(10, 50, 260, 15);

				pnlContaListItem.add(lblCodigo);
				pnlContaListItem.add(lblTipoConta);
				pnlContaListItem.add(lblSaldo);
	    }
		});

		return scrContas;
	}

	private JPanel criarContaListItemPanel() {
		pnlContaListItem.setLayout(null);
		pnlContaListItem.setBounds(200, 190, 260, 150);
		return pnlContaListItem;
	}

	private JPanel criarContaPanel() {
		JPanel pnlCriarConta = new JPanel();
		pnlCriarConta.setLayout(null);
		pnlCriarConta.setBounds(10, 210, 280, 100);
		pnlCriarConta.setBackground(Color.CYAN);

		final JLabel lblLimite = new JLabel("Limite");
		lblLimite.setBounds(10, 37, 100, 25);

		final JTextField txtLimite = new JTextField();
		txtLimite.setBounds(115, 37, 100, 25);

		JLabel lblTipoConta = new JLabel("Tipo da Conta");
		lblTipoConta.setBounds(10, 10, 100, 25);

		final JComboBox txtTipoConta = new JComboBox(new String[] {"Conta Corrente", "Conta Poupanca"});
		txtTipoConta.setBounds(115, 10, 150, 25);
		txtTipoConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean limiteVisivel = txtTipoConta.getSelectedIndex() == 0;
				lblLimite.setVisible(limiteVisivel);
				txtLimite.setVisible(limiteVisivel);
			}
		});

		JButton btnCriarContaConfirma = new JButton("Criar");
		btnCriarContaConfirma.setBounds(50, 64, 100, 25);

		btnCriarContaConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String sValor = txtLimite.getText().trim();
					double limite = sValor.length() == 0 ? 0 : Double.valueOf(sValor);
					banco.criaConta(txtTipoConta.getSelectedIndex() + 1, limite);
					dialog.setVisible(false);
					txtLimite.setText("");
					refreshContas();
				} catch (Exception e) {
					showException(e);
				}
			}
		});

		pnlCriarConta.add(lblTipoConta);
		pnlCriarConta.add(txtTipoConta);
		pnlCriarConta.add(lblLimite);
		pnlCriarConta.add(txtLimite);
		pnlCriarConta.add(btnCriarContaConfirma);

		return pnlCriarConta;
	}

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            (new Tela(new Banco("IBTA Banco"))).show();
        }
    });
  }

}
