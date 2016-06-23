public class ContaListItem {

	private final Conta conta;

	public ContaListItem(Conta conta) {
		this.conta = conta;
	}

	public Conta getConta() {
		return conta;
	}

	public String toString() {
		return "Conta: " + conta.getCodigo() + "; Saldo: " + conta.getSaldo();
	}

}
