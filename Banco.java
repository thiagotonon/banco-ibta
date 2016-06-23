import java.util.*;
import java.sql.*;

public class Banco {

	private String nome;

	private ContaDAO contaDao = null;
	// private ClienteDAO clienteDao = null;

	private Map<Integer, Conta> mapaContas = null;

	private int proximoCodigo = 0;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/bancoibta";

	public Banco(String nome) {
		this.nome = nome;
		this.mapaContas = new HashMap<Integer, Conta>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			System.out.println("Conexao com o banco de dados aberta");

			this.contaDao = new ContaDAO(conn);
			// this.clienteDao = new ClienteDAO(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
	}

	public void criaConta(int tipoConta, double... limite) throws Exception {
		Conta c = null;
		switch (tipoConta) {
			// conta corrente
			case 1:
				c = new ContaCorrente(++proximoCodigo, limite[0]);
				break;
			// conta poupanca
			case 2:
				c = new ContaPoupanca(++proximoCodigo);
				break;
			default:
				throw new BancoException("Tipo invalido");
		}
		contaDao.inserir(c);
	}

	public Conta buscar(int codigo) throws Exception {
		return contaDao.findBy(codigo);
	}

	public List<Conta> listaContas() {
		return contaDao.list();
	}

	public List listaContas(Class classe) {
		List lista = new ArrayList<Conta>();
		for (Map.Entry<Integer, Conta> entry : mapaContas.entrySet()) {
			Conta c = entry.getValue();
			if (classe.isInstance(c)) {
				lista.add(c);
			}
		}
		return lista;
	}

	public void reajustarInvestimentos() throws Exception {
		for (Map.Entry<Integer, Conta> entry : mapaContas.entrySet()) {
			Conta c = entry.getValue();
			if (c instanceof Investimento) {
				Investimento invest = (Investimento) c;
				invest.reajustar();
			}
		}
	}

}
