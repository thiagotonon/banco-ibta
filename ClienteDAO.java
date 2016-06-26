import java.sql.*;
import java.util.*;

public class ClienteDAO {

  private final Connection conn;

  public ClienteDAO(Connection connection) {
    this.conn = connection;
  }

  public void inserir(Cliente cliente) {
    try {
      String sql = "INSERT INTO cliente (nome) VALUES (?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, cliente.getNome());

      System.out.println(sql);
      stmt.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Cliente findBy(int id) {
    try {
      String sql = "SELECT nome FROM cliente WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setDouble(1, id);

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        Cliente c = new Cliente(rs.getString("nome"));
        c.setCodigo(rs.getInt("id"));
        return c;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Cliente findBy(String nome) {
    try {
      String sql = "SELECT id, nome FROM cliente WHERE nome = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, nome);

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Cliente c = new Cliente(rs.getString("nome"));
        c.setCodigo(rs.getInt("id"));
        return c;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Cliente> list() {
    List<Cliente> lista = new ArrayList<Cliente>();
    try {
      String sql = "SELECT id, nome FROM cliente ORDER BY id";
      PreparedStatement stmt = conn.prepareStatement(sql);

      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Cliente c = new Cliente(rs.getString("nome"));
        c.setCodigo(rs.getInt("id"));
        lista.add(c);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lista;
  }

}
