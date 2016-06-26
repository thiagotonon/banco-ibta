public class Cliente {

  private int codigo;
  private String nome;

  public Cliente(String nome) {
    this.nome = nome;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getCodigo() {
    return codigo;
  }

  public String getNome() {
    return nome;
  }

  @Override
  public String toString() {
    return nome;
  }

}