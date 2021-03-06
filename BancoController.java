import java.util.*;
import java.sql.*;

public class BancoController {

	private String nome;

	private ContaDAO contaDao = null;
	private ClienteDAO clienteDao = null;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/bancoibta";

	public String getNome() {
		return nome;
	}

	public BancoController(String nome) {
		this.nome = nome;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			System.out.println("Conexao com o banco de dados aberta");

			this.contaDao = new ContaDAO(conn);
			this.clienteDao = new ClienteDAO(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void criaConta(Cliente cliente, int tipoConta, double... limite) throws Exception {
		Conta c = null;
		switch (tipoConta) {
			case 1:
				c = new ContaCorrente(limite[0]);
				break;
			case 2:
				c = new ContaPoupanca();
				break;
			case 3:
				c = new ContaSalario();
				break;
			default:
				throw new BancoException("Tipo invalido");
		}
		c.setCliente(cliente);
		contaDao.inserir(c);
	}

	public void sacar(Conta conta, double valor) throws Exception {
		conta.sacar(valor);
		contaDao.atualiza(conta);
	}

	public void depositar(Conta conta, double valor) throws Exception {
		conta.depositar(valor);
		contaDao.atualiza(conta);
	}

	public void transferir(Conta conta1, Conta conta2, double valor) throws Exception {
		conta1.transferir(conta2, valor);
		contaDao.atualiza(conta1);
		contaDao.atualiza(conta2);
	}

	public void criaCliente(String nome) throws Exception {
		Cliente cliente = new Cliente(nome);
		clienteDao.inserir(cliente);
	}

	public Conta buscar(int codigo) throws Exception {
		return contaDao.findBy(codigo);
	}

	public List<Conta> listaContas() {
		return contaDao.list();
	}

	public List listaContas(Class classe) {
		List<Conta> lista = contaDao.list();
		List<Conta> saida = new ArrayList<Conta>();
		for (Conta c : lista) {
			if (classe.isInstance(c)) {
				saida.add(c);
			}
		}
		return saida;
	}

	public Cliente buscaCliente(String nome) {
		return clienteDao.findBy(nome);
	}

	public List<Conta> listaContas(Cliente cliente) {
		return contaDao.list(cliente);
	}

	public List<Cliente> listaClientes() {
		return clienteDao.list();
	}

	public void reajustarInvestimentos(Conta c) throws Exception {
		Investimento invest = (Investimento) c;
		invest.reajustar();
		contaDao.atualiza(c);
	}

}
