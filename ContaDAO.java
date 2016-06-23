import java.sql.*;
import java.util.*;

public class ContaDAO {

  private final Connection conn;

  public ContaDAO(Connection connection) {
    this.conn = connection;
  }

  public void inserir(Conta conta) {
    try {
      int tipoConta = conta instanceof ContaCorrente ? 1 : 2;
      String sql = "INSERT INTO conta (saldo, tipoconta_id, limite, data_criacao) VALUES (?, ?, ?, ?)";
      PreparedStatement stmt = conn.preparedStatement(sql);
      stmt.setDouble(1, conta.getSaldo());
      stmt.setInt(2, tipoConta);
      stmt.setDouble(3, (tipoConta == 1 ? ((ContaCorrente) conta).getLimite() : 0));
      stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

      stmt.execute();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void atualiza(Conta conta) {
    try {
      int tipoConta = conta instanceof ContaCorrente ? 1 : 2;
      String sql = "UPDATE conta SET saldo = ?, limite = ? WHERE id = ?";
      PreparedStatement stmt = conn.preparedStatement(sql);
      stmt.setDouble(1, conta.getSaldo());
      stmt.setDouble(2, (tipoConta == 1 ? ((ContaCorrente) conta).getLimite() : 0));
      stmt.setInt(3, conta.getCodigo());

      stmt.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void apagar(int id) {
    try {
      String sql = "DELETE FROM conta WHERE id = ?";
      PreparedStatement stmt = conn.preparedStatement(sql);
      stmt.setInt(1, id);
      stmt.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Conta findBy(int id) {
    try {
      String sql = "SELECT saldo, tipoconta_id, limite, data_criacao FROM conta WHERE id = ?";
      PreparedStatement stmt = conn.preparedStatement(sql);
      stmt.setDouble(1, id);

      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        int tipoConta = rs.getInt("tipoconta_id");
        Conta c = null;

        if tipoConta == 1 {
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
      String sql = "SELECT saldo, tipoconta_id, limite, data_criacao FROM conta ORDER BY id";
      PreparedStatement stmt = conn.preparedStatement(sql);

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int tipoConta = rs.getInt("tipoconta_id");
        Conta c = null;

        if tipoConta == 1 {
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

}
