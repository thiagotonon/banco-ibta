public class ClienteListItem {

  private final Cliente cliente;

  public ClienteListItem(Cliente cliente) {
    this.cliente = cliente;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public String toString() {
    return cliente.getNome();
  }

}
