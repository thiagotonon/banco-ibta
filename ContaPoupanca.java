public class ContaPoupanca extends Conta implements Investimento {
	public void sacar(double valor) throws IllegalArgumentException {
		if (valor > saldo) {
			throw new IllegalArgumentException("Saldo insuficiente: " + saldo);
		} else if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		} else {
			this.saldo -= valor;
		}
	}

	public void transferir(Conta conta, double valor) throws IllegalArgumentException {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		} else if (saldo < valor) {
			throw new IllegalArgumentException("Saldo insuficiente: " + valor);
		}

		conta.saldo += valor;
		this.saldo -= valor;
	}

	public void reajustar() {
		this.saldo *= (1 + getRendimento());
	}

	public double getRendimento() {
		return 0.02;
	}

}
