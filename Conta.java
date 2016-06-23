import java.util.Date;

public abstract class Conta {

	private int codigo;
	protected double saldo = 0;
	private Date dtCriacao = null;

	public Conta(int codigo, Date dtCriacao) {
		this.codigo = codigo;
		this.dtCriacao = new Date();
	}

	public double getSaldo() {
		return saldo;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getTipoConta() {
		if (this instanceof ContaCorrente) {
			return "Conta Corrente";
		} else {
			return "Conta Poupanca";
		}
	}

	public void setCodigo(int codigo){
		this.codigo = codigo;
	}

	public abstract void sacar(double valor) throws IllegalArgumentException;

	public void depositar(double valor) throws IllegalArgumentException {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		}
		this.saldo += valor;
	}

	@Override
	// um exemplo de sobreescrita
	public String toString() {
		String out = "";
		out += "Codigo: " + codigo;
		out += "\nSaldo.: " + saldo;
		out += "\nInicio: " + dtCriacao;
		return out;
	}

	@Override
	// obrigatorio para o hashset
	public int hashCode() {
		return codigo;
	}

	@Override
	// obrigatorio para o hashset
	public boolean equals(Object o) {
		return o != null
		    && o instanceof Conta
                    && ((Conta) o).getCodigo() == codigo;
	}

}
