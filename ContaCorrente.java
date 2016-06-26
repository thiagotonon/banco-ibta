public class ContaCorrente extends Conta {

	private double limite = 0;

	public ContaCorrente(double limite) throws IllegalArgumentException {
		if (limite < 0) {
			throw new IllegalArgumentException("Limite não pode ser negativo: " + limite);
		}
		this.limite = limite;
	}

	public ContaCorrente() throws IllegalArgumentException {
		this((double) 100);
	}

	public void sacar(double valor) throws IllegalArgumentException {
		if ((saldo + limite) < valor) {
			throw new IllegalArgumentException("Saldo insuficiente: " + saldo + " (" + limite + ")");
		} else if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		} else {
			this.saldo -= valor;
		}
	}

	public void transferir(Conta conta, double valor) throws IllegalArgumentException {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		} else if ((saldo + limite) < valor) {
			throw new IllegalArgumentException("Saldo insuficiente: " + valor);
		}

		conta.saldo += valor;
		this.saldo -= valor;
	}

	public double getLimite() {
		return limite;
	}

}
