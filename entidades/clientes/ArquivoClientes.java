package entidades.clientes;

import aed3.Arquivo;
import aed3.HashExtensivel;

public class ArquivoClientes extends Arquivo<Cliente> {

  HashExtensivel<ParEmailID> indiceIndiretoEmail;
  HashExtensivel<ParCPFID> indiceIndiretoCPF;

  public ArquivoClientes(String nomePasta) throws Exception {
    super(nomePasta, Cliente.class.getConstructor());
    indiceIndiretoCPF = new HashExtensivel<>(
        ParCPFID.class.getConstructor(),
        4,
        nomePasta + "/indiceCPF.1.db",
        nomePasta + "/indiceCPF.2.db");
    indiceIndiretoEmail = new HashExtensivel<>(
        ParEmailID.class.getConstructor(),
        4,
        nomePasta + "/indiceEmail.1.db",
        nomePasta + "/indiceEmail.2.db");
  }

  // ---------------------
  // CREATE
  // ---------------------
  @Override
  public int create(Cliente cliente) throws Exception {
    int id = super.create(cliente);
    cliente.setID(id);
    indiceIndiretoCPF.create(
        new ParCPFID(cliente.getCPF(), id));
    indiceIndiretoEmail.create(
        new ParEmailID(cliente.getEmail(), id));
    return id;
  }

  // ---------------------
  // READ (campo, valor)
  // ---------------------
  public Cliente read(String campo, String valor) throws Exception {
    switch (campo) {
      case "CPF":
      case "cpf":
        ParCPFID p1 = indiceIndiretoCPF.read(Math.abs(valor.hashCode()));
        if (p1 == null)
          return null;
        return read(p1.getID()); // método da superclasse
      case "Email":
      case "email":
      case "EMAIL":
        ParEmailID p2 = indiceIndiretoEmail.read(Math.abs(valor.hashCode()));
        if (p2 == null)
          return null;
        return read(p2.getID()); // método da superclasse
    }
    return null;
  }

  // ---------------------
  // UPDATE
  // ---------------------
  public boolean update(Cliente novoCliente) throws Exception {
    Cliente clienteAntigo = super.read(novoCliente.getID());
    if (clienteAntigo != null) {
      // Testa se mudou o CPF. Como o ID nunca é alterado,
      // o método update() do índice não resolve a alteração de CPFs e Emails.
      if (clienteAntigo.getCPF().compareTo(novoCliente.getCPF()) != 0) {
        indiceIndiretoCPF.delete(Math.abs(clienteAntigo.getCPF().hashCode()));
        indiceIndiretoCPF.create(new ParCPFID(novoCliente.getCPF(), novoCliente.getID()));
      }
      // Testa se mudou o email.
      if (clienteAntigo.getEmail().compareTo(novoCliente.getEmail()) != 0) {
        indiceIndiretoEmail.delete(Math.abs(clienteAntigo.getEmail().hashCode()));
        indiceIndiretoEmail.create(new ParEmailID(novoCliente.getEmail(), novoCliente.getID()));
      }
      // Atualiza o cliente no arquivo de dados. Não há mudança de ID.
      return super.update(novoCliente);
    }
    return false;
  }

  // ---------------------
  // DELETE
  // ---------------------
  public boolean delete(int id) throws Exception {
    Cliente cliente = super.read(id);
    if (cliente != null)
      if (indiceIndiretoCPF.delete(Math.abs(cliente.getCPF().hashCode()))
          && indiceIndiretoEmail.delete(Math.abs(cliente.getEmail().hashCode())))
        return super.delete(id);
    return false;
  }

}
