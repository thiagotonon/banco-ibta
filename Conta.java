import java.util.Date;

public abstract class Conta {

	private int codigo;
	private Cliente cliente;
	protected double saldo = 0;
	private Date dtCriacao = null;

	public Conta() {
		this.dtCriacao = new Date();
	}

	public double getSaldo() {
		return saldo;
	}

	public int getCodigo() {
		return codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getTipoConta() {
		if (this instanceof ContaCorrente) {
			return "Conta Corrente";
		} else if (this instanceof ContaPoupanca) {
			return "Conta Poupanca";
		} else {
			return "Conta Salario";
		}
	}

	public void setDataCriacao(Date data) {
		this.dtCriacao = data;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public void setCodigo(int codigo){
		this.codigo = codigo;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public abstract void sacar(double valor) throws IllegalArgumentException;

	public void depositar(double valor) throws IllegalArgumentException {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor invalido: " + valor);
		}
		this.saldo += valor;
	}

	public abstract void transferir(Conta conta, double valor) throws IllegalArgumentException;

	@Override
	// um exemplo de sobreescrita
	public String toString() {
		return "Conta " + codigo + "; Saldo: " + saldo;
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
