import java.sql.*;
import java.util.*;

public class ClienteDAO {

  private final Connection conn;

  public ClienteDAO(Connection connection) {
    this.conn = connection;
  }

  public void inserir(Cliente cliente) {
    try {
      Statement stmt = conn.createStatement();
      String sql = "";
      System.out.println(sql);
      stmt.execute(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Cliente findBy(int id) {
    return null;
  }

  public List<Cliente> list() {
    return new ArrayList<Cliente>();
  }

}
