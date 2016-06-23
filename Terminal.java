import java.util.*;

public class Terminal {

	private Banco banco;

	public static void main(String[] args) {

		Banco banco = new Banco("IBTA");
		Terminal terminal = new Terminal(banco);
		terminal.console();

	}

	public Terminal(Banco banco) {
		this.banco = banco;
	}

	public void console() {
		int opcao = 0;
		do {
			try {
				opcao = menu();
				switch (opcao) {
					case 1: criaConta(); break;
					case 2: listaContas(); break;
					case 3: buscar(); break;
					case 4: depositar(); break;
					case 5: sacar(); break;
					case 6: System.out.println("transferir"); break;
					case 7: reajustarInvestimentos(); break;
					default: throw new BancoException("Opcao invalida: " + opcao);

				}
			} catch(BancoException e) {
				System.err.println("[Erro] " + e.getMessage());
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while (opcao != 0);
		System.out.println("Ate a proxima!!!");
	}

	private int menu() throws Exception {
		System.out.println();
		System.out.println();
		System.out.println("Banco " + banco.getNome());
		System.out.println("1. Criar conta");
		System.out.println("2. Listar contas");
		System.out.println("3. Saldo");
		System.out.println("4. Depositar");
		System.out.println("5. Sacar");
		System.out.println("6. Transferir");
		System.out.println("7. Reajustar investimentos");
		System.out.println("0. Sair");
		System.out.println();
		System.out.print("Digite sua opcao: ");

		return leInteiro();
	}

	private void criaConta() throws Exception {
		System.out.print("Corrente(1) Poupanca(2): ");
		int tipoConta = leInteiro();
		if (tipoConta == 1) {
			System.out.print("Limite: ");
			double limite = leDouble();
			banco.criaConta(tipoConta, limite);
		} else {
			banco.criaConta(tipoConta);
		}
	}

	private void listaContas() throws Exception {
		List<ContaCorrente> contasCorrente = banco.listaContas(ContaCorrente.class);
		System.out.println("Conta Corrente");
		for (ContaCorrente c : contasCorrente) {
			System.out.println(c);
		}
		List<ContaPoupanca> contasPoupanca = banco.listaContas(ContaPoupanca.class);
		System.out.println("Conta Poupanca");
		for (ContaPoupanca c : contasPoupanca) {
			System.out.println(c);
		}
		System.out.println("Total de contas: " + (contasCorrente.size() + contasPoupanca.size()));
	}

	private void depositar() throws Exception {
		Conta c = buscar();
		System.out.print("Valor: ");
		double valor = leDouble();
		c.depositar(valor);
	}

	private void sacar() throws Exception {
		Conta c = buscar();
		System.out.print("Valor: ");
		double valor = leDouble();
		c.sacar(valor);
	}

	private Conta buscar() throws Exception {
		System.out.print("Digite codigo da conta: ");
		int codigo = leInteiro();
		Conta c = banco.buscar(codigo);
		if (c == null) throw new BancoException("Conta nao encontrada: " + codigo);
		return c;
	}

	private void reajustarInvestimentos() throws Exception {
		banco.reajustarInvestimentos();
	}

	private int leInteiro() throws Exception {
		Scanner sc = new Scanner(System.in);
		String numero = sc.nextLine();
		int n = Integer.parseInt(numero);
		return n;
	}

	private double leDouble() throws Exception {
		Scanner sc = new Scanner(System.in);
		String numero = sc.nextLine();
		return Double.parseDouble(numero);
	}

}

