import java.util.*;
import java.sql.*;

public class BancoController {

	private String nome;

	private ContaDAO contaDao = null;
	private ClienteDAO clienteDao = null;

	private Map<Integer, Conta> mapaContas = null;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/bancoibta";

	public BancoController(String nome) {
		this.nome = nome;
		this.mapaContas = new HashMap<Integer, Conta>();

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

	public String getNome() {
		return nome;
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
			default:
				throw new BancoException("Tipo invalido");
		}
		c.setCliente(cliente);
		contaDao.inserir(c);
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
		System.out.println(nome);
		return clienteDao.findBy(nome);
	}

	public List<Conta> listaContas(Cliente cliente) {
		return contaDao.list(cliente);
	}

	public List<Cliente> listaClientes() {
		return clienteDao.list();
	}

	public void reajustarInvestimentos() throws Exception {
		List<Conta> contas = contaDao.list();
		for (Conta c : contas) {
			if (c instanceof Investimento) {
				Investimento invest = (Investimento) c;
				invest.reajustar();
				contaDao.atualiza(c);
			}
		}
	}

}
