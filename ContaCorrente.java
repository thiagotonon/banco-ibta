public class ContaCorrente extends Conta {

	public double limite = 0;

	public ContaCorrente(int codigo, double limite) throws IllegalArgumentException {
		super(codigo); // invoca o construto do pai
		if (limite < 0) {
			throw new IllegalArgumentException("Limite não pode ser negativo: " + limite);
		}
		this.limite = limite;
	}

	public ContaCorrente(int codigo) throws IllegalArgumentException {
		// invoca o construtor que possui esta assinatura
		this(codigo, (double) 100);
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
