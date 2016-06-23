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

      Statement stmt = conn.createStatement();
      String sql = "INSERT INTO conta (saldo, tipoconta_id, limite, data_criacao) VALUES ("
           + conta.getSaldo() + ", "
           + tipoConta + ", "
           + (tipoConta == 1 ? ((ContaCorrente) conta).getLimite() : 0) + ", "
           + "'20160616')";
      System.out.println(sql);
      stmt.execute(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void atualiza(Conta conta) {
  }

  public void apagar(int id) {

  }

  public Conta findBy(int id) {
    return null;
  }

  public List<Conta> list() {
    return new ArrayList<Conta>();
  }

}
