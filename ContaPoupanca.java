public class ContaPoupanca extends Conta implements Investimento {
	public void sacar(double valor) {
		if (valor > saldo) {
			System.err.println("Saldo insuficiente: " + saldo);
		} else {
			this.saldo -= valor;
		}
	}

	public void reajustar() {
		this.saldo *= (1 + getRendimento());
	}

	public double getRendimento() {
		return 0.02;
	}

}
