import java.sql.*;
import java.util.*;

public class ContaDAO {

  private final Connection conn;

  private ClienteDAO clienteDao = null;

  public ContaDAO(Connection connection) {
    this.conn = connection;
  }

  public void inserir(Conta conta) {
    try {
      int tipoConta = conta instanceof ContaCorrente ? 1 : 2;
      String sql = "INSERT INTO conta (saldo, tipoconta_id, limite, data_criacao) VALUES (?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setDouble(1, conta.getSaldo());
      stmt.setInt(2, tipoConta);
      stmt.setDouble(3, (tipoConta == 1 ? ((ContaCorrente) conta).getLimite() : 0));
      stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

      System.out.println(sql);
      stmt.execute();

      ResultSet rs = stmt.getGeneratedKeys();

      if (rs.next()) {
        int id = rs.getInt(1);
        String sql2 = "INSERT INTO cliente_conta (cliente_id, conta_id) VALUES (?, ?)";
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        stmt2.setInt(1, conta.getCliente().getCodigo());
        stmt2.setInt(2, id);

        System.out.println(sql2);
        stmt2.execute();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void atualiza(Conta conta) {
    try {
      int tipoConta = conta instanceof ContaCorrente ? 1 : 2;
      String sql = "UPDATE conta SET saldo = ?, limite = ? WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setDouble(1, conta.getSaldo());
      stmt.setDouble(2, (tipoConta == 1 ? ((ContaCorrente) conta).getLimite() : 0));
      stmt.setInt(3, conta.getCodigo());

      System.out.println(sql);
      stmt.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void apagar(int id) {
    try {
      String sql = "DELETE FROM conta WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);

      System.out.println(sql);
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Conta findBy(int id) {
    try {
      String sql = "SELECT saldo, tipoconta_id, limite, data_criacao FROM conta WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setDouble(1, id);

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        int tipoConta = rs.getInt("tipoconta_id");
        Conta c = null;

        if (tipoConta == 1) {
          ContaCorrente corrente = new ContaCorrente(rs.getDouble("limite"));
          c = corrente;
        } else {
          ContaPoupanca poupanca = new ContaPoupanca();
          c = poupanca;
        }

        c.setCodigo(id);
        c.setSaldo(rs.getDouble("saldo"));
        c.setDataCriacao(rs.getDate("data_criacao"));
        return c;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Conta> list() {
    List<Conta> lista = new ArrayList<Conta>();
    try {
      String sql = "SELECT id, saldo, tipoconta_id, limite, data_criacao FROM conta ORDER BY id";
      PreparedStatement stmt = conn.prepareStatement(sql);

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        int tipoConta = rs.getInt("tipoconta_id");
        Conta c = null;

        if (tipoConta == 1) {
          ContaCorrente corrente = new ContaCorrente(rs.getDouble("limite"));
          c = corrente;
        } else {
          ContaPoupanca poupanca = new ContaPoupanca();
          c = poupanca;
        }

        c.setCodigo(rs.getInt("id"));
        c.setSaldo(rs.getDouble("saldo"));
        c.setDataCriacao(rs.getDate("data_criacao"));
        lista.add(c);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lista;
  }

  public List<Conta> list(Cliente cliente) {
    List<Conta> lista = new ArrayList<Conta>();
    try {
      String sql = "SELECT * FROM conta c INNER JOIN cliente_conta cc ON c.id = cc.conta_id WHERE cc.cliente_id = ? ORDER BY cc.id";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, cliente.getCodigo());

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int tipoConta = rs.getInt("tipoconta_id");
        Conta c = null;

        if (tipoConta == 1) {
          ContaCorrente corrente = new ContaCorrente(rs.getDouble("limite"));
          c = corrente;
        } else {
          ContaPoupanca poupanca = new ContaPoupanca();
          c = poupanca;
        }

        c.setCliente(cliente);
        c.setCodigo(rs.getInt("id"));
        c.setSaldo(rs.getDouble("saldo"));
        c.setDataCriacao(rs.getDate("data_criacao"));
        lista.add(c);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lista;
  }

}
