package entidades.clientes;

import java.io.File;
import java.util.Scanner;

public class InteracoesClientes {

  private static Scanner console = new Scanner(System.in);
  private ArquivoClientes arqClientes;

  public InteracoesClientes() {
    try {
      File d;
      d = new File("dados");
      if (!d.exists())
        d.mkdir();
      d = new File("dados/clientes");
      if (!d.exists())
        d.mkdir();
      arqClientes = new ArquivoClientes("dados/clientes");
    } catch (Exception e) {
      System.out.println("Arquivo não pode ser aberto ou criado.");
      e.printStackTrace();
    }
  }

  // ---------------------
  // LE_CLIENTE
  // ---------------------
  public Cliente leCliente() throws Exception {
    System.out.print("\nNome..: ");
    String nome = console.nextLine();
    System.out.print("CPF...: ");
    String cpf = console.nextLine();
    System.out.print("E-Mail: ");
    String email = console.nextLine();
    Cliente l = new Cliente(nome, cpf, email);
    return l;
  }

  // ---------------------
  // MOSTRA_CLIENTE
  // ---------------------
  public void mostraCliente(Cliente l) {
    System.out.println(
        "\nNome..: " + l.getNome() +
            "\nCPF...: " + l.getCPF() +
            "\nE-mail: " + l.getEmail());
  }

  // ---------------------
  // MENU_CLIENTES
  // ---------------------
  public void menuClientes() {
    int opcao;
    do {
      System.out.println("\nMENU DE CLIENTES");
      System.out.println("\n1) Incluir cliente");
      System.out.println("2) Buscar cliente por ID");
      System.out.println("3) Buscar cliente por CPF");
      System.out.println("4) Buscar cliente por E-mail");
      System.out.println("5) Alterar cliente");
      System.out.println("6) Excluir cliente");
      System.out.println("\n0) Retornar ao menu anterior");

      System.out.print("\nOpção: ");
      try {
        opcao = Integer.valueOf(console.nextLine());
      } catch (NumberFormatException e) {
        opcao = -1;
      }

      switch (opcao) {
        case 1:
          incluirCliente();
          break;
        case 2:
          buscarClientePorID();
          break;
        case 3:
          buscarClientePorCPF();
          break;
        case 4:
          buscarClientePorEmail();
          break;
        case 5:
          alterarCliente();
          break;
        case 6:
          excluirCliente();
          break;
        case 0:
          break;
        default:
          System.out.println("Opção inválida");
      }
    } while (opcao != 0);
  }

  // ---------------------
  // INCLUIR_CLIENTE
  // ---------------------
  public void incluirCliente() {
    Cliente novoCliente;
    try {
      boolean dadosCompletos = false;
      do {
        novoCliente = leCliente();
        if (novoCliente.getNome().length() == 0 || novoCliente.getCPF().length() == 0
            || novoCliente.getEmail().length() == 0)
          System.out.println("Dados incompletos. Preencha todos os campos.");
        else
          dadosCompletos = true;
      } while (!dadosCompletos);
    } catch (Exception e) {
      System.out.println("Dados inválidos");
      return;
    }

    int id;
    try {
      id = arqClientes.create(novoCliente);
    } catch (Exception e) {
      System.out.println("Cliente não pode ser criado");
      e.printStackTrace();
      return;
    }

    System.out.println("\nCliente criado com o ID " + id);

  }

  // ---------------------
  // BUSCAR_CLIENTE_POR_ID
  // ---------------------
  public void buscarClientePorID() {
    int id;
    System.out.print("\nID do Cliente: ");
    try {
      id = Integer.valueOf(console.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("ID inválido.");
      return;
    }

    try {
      Cliente cliente = arqClientes.read(id);
      if (cliente != null)
        mostraCliente(cliente);
      else
        System.out.println("Cliente não encontrado!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }

  }

  // ---------------------
  // BUSCAR_CLIENTE_POR_CPF
  // ---------------------
  public void buscarClientePorCPF() {
    String cpf;
    System.out.print("\nCPF do Cliente: ");
    cpf = console.nextLine();

    try {
      Cliente cliente = arqClientes.read("CPF", cpf);
      if (cliente != null)
        mostraCliente(cliente);
      else
        System.out.println("Cliente não encontrado!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // BUSCAR_CLIENTE_POR_EMAIL
  // ---------------------
  public void buscarClientePorEmail() {
    String email;
    System.out.print("\nE-mail do Cliente: ");
    email = console.nextLine();

    try {
      Cliente cliente = arqClientes.read("email", email);
      if (cliente != null)
        mostraCliente(cliente);
      else
        System.out.println("Cliente não encontrado!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // ALTERAR_CLIENTE
  // ---------------------
  public void alterarCliente() {
    String cpf;
    System.out.print("\nCPF do Cliente: ");
    cpf = console.nextLine();

    try {
      Cliente cliente = arqClientes.read("CPF", cpf);
      if (cliente == null) {
        System.out.println("Cliente não encontrado.");
        return;
      }
      mostraCliente(cliente);

      System.out.println("\nDigite os novos dados.\nDeixe em branco os que não forem alterados.");
      Cliente cliente2;
      try {
        cliente2 = leCliente();
      } catch (Exception e) {
        System.out.println("Dados inválidos");
        return;
      }
      if (cliente2.getNome().length() > 0)
        cliente.setNome(cliente2.getNome());
      if (cliente2.getCPF().length() > 0)
        cliente.setCPF(cliente2.getCPF());
      if (cliente2.getEmail().length() > 0)
        cliente.setEmail(cliente2.getEmail());

      System.out.print("Confirma alteração do cliente (S/N)? ");
      char resp = console.nextLine().charAt(0);
      if (resp == 'S' || resp == 's') {
        if (arqClientes.update(cliente))
          System.out.println("Cliente alterado!");
        else
          System.out.println("Erro na alteração do cliente!");
      } else
        System.out.println("Alteração cancelada!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // EXCLUIR_CLIENTE
  // ---------------------
  public void excluirCliente() {
    String cpf;
    System.out.print("\nCPF do Cliente: ");
    cpf = console.nextLine();

    try {
      Cliente cliente = arqClientes.read("CPF", cpf);
      if (cliente == null) {
        System.out.println("Cliente não encontrado.");
        return;
      }
      mostraCliente(cliente);

      System.out.print("Confirma exclusão do cliente (S/N)? ");
      char resp = console.nextLine().charAt(0);
      if (resp == 'S' || resp == 's') {
        if (arqClientes.delete(cliente.getID()))
          System.out.println("Cliente excluído!");
        else
          System.out.println("Erro na exclusão do cliente!");
      } else
        System.out.println("Exclusão cancelada!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

}
