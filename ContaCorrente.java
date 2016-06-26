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

	public void sacar(double valor) {
		if ((saldo + limite) < valor) {
			System.err.println("Saldo insuficiente: "
			 + saldo + " (" + limite + ")");
		} else {
			this.saldo -= valor;
		}
	}

	public double getLimite() {
		return limite;
	}

	@Override
	public String toString() {
		String out = super.toString();
		out += "\nLimite: " + limite;
		return out;
	}

}
