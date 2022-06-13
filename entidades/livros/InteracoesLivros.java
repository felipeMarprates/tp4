package entidades.livros;

import java.io.File;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Scanner;

public class InteracoesLivros {

  private static Scanner console = new Scanner(System.in);
  private ArquivoLivros arqLivros;

  public InteracoesLivros() {
    try {
      File d;
      d = new File("dados");
      if (!d.exists())
        d.mkdir();
      d = new File("dados/livros");
      if (!d.exists())
        d.mkdir();
      arqLivros = new ArquivoLivros("dados/livros");
    } catch (Exception e) {
      System.out.println("Arquivo não pode ser aberto ou criado.");
      e.printStackTrace();
    }
  }

  // ---------------------
  // LE_LIVRO
  // ---------------------
  public Livro leLivro() throws Exception {
    System.out.print("\nISBN: ");
    String isbn = console.nextLine();
    System.out.print("Título: ");
    String titulo = console.nextLine();
    System.out.print("Autor: ");
    String autor = console.nextLine();
    System.out.print("Preço: R$ ");
    float preco;
    String sPreco = console.nextLine();
    if (sPreco.length() > 0)
      preco = Float.parseFloat(sPreco);
    else
      preco = -1F;
    System.out.print("Data de lançamento (dd/mm/aaaa): ");
    Calendar lancamento = Calendar.getInstance();
    String sData = console.nextLine();
    if (sData.length() > 0) {
      String[] partesData = sData.split("/");
      lancamento.set(
          Integer.parseInt(partesData[2]),
          Integer.parseInt(partesData[1]) - 1,
          Integer.parseInt(partesData[0]), 0, 0, 0);
    } else {
      lancamento.setTimeInMillis(0);
    }
    Livro l = new Livro(isbn, titulo, autor, preco, lancamento.getTime());
    return l;
  }

  // ---------------------
  // MOSTRA_LIVRO
  // ---------------------
  public void mostraLivro(Livro l) {
    System.out.println(
        "\nISBN: " + l.getISBN() +
            "\nTítulo: " + l.getTitulo() +
            "\nAutor: " + l.getAutor() +
            "\nPreço: " + NumberFormat.getCurrencyInstance().format(l.getPreco()) +
            "\nData de lançamento: " + l.getLancamento());
  }

  // ---------------------
  // MENU_LIVROS
  // ---------------------
  public void menuLivros() {
    int opcao;
    do {
      System.out.println("\nMENU DE LIVROS");
      System.out.println("\n1) Incluir livro");
      System.out.println("2) Buscar livro por ID");
      System.out.println("3) Buscar livro por ISBN");
      System.out.println("4) Alterar livro");
      System.out.println("5) Excluir livro");
      System.out.println("\n0) Retornar ao menu anterior");

      System.out.print("\nOpção: ");
      try {
        opcao = Integer.valueOf(console.nextLine());
      } catch (NumberFormatException e) {
        opcao = -1;
      }

      switch (opcao) {
        case 1:
          incluirLivro();
          break;
        case 2:
          buscarLivroPorID();
          break;
        case 3:
          buscarLivroPorISBN();
          break;
        case 4:
          alterarLivro();
          break;
        case 5:
          excluirLivro();
          break;
        case 0:
          break;
        default:
          System.out.println("Opção inválida");
      }
    } while (opcao != 0);
  }

  // ---------------------
  // INCLUIR_LIVRO
  // ---------------------
  public void incluirLivro() {
    Livro novoLivro;
    try {
      boolean dadosCompletos = false;
      do {
        novoLivro = leLivro();
        if (novoLivro.getISBN().length() == 0 || novoLivro.getTitulo().length() == 0
            || novoLivro.getAutor().length() == 0 || novoLivro.getPreco() < 0
            || novoLivro.getLancamento().getTime() == 0)
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
      id = arqLivros.create(novoLivro);
    } catch (Exception e) {
      System.out.println("Livro não pode ser criado");
      e.printStackTrace();
      return;
    }

    System.out.println("\nLivro criado com o ID " + id);

  }

  // ---------------------
  // BUSCAR_LIVRO_POR_ID
  // ---------------------
  public void buscarLivroPorID() {
    int id;
    System.out.print("\nID do Livro: ");
    try {
      id = Integer.valueOf(console.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("ID inválido.");
      return;
    }

    try {
      Livro l = arqLivros.read(id);
      if (l == null) {
        System.out.println("Livro não encontrado.");
        return;
      }
      mostraLivro(l);
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // BUSCAR_LIVRO_POR_ISBN
  // ---------------------
  public void buscarLivroPorISBN() {
    String isbn;
    System.out.print("\nISBN do Livro: ");
    isbn = console.nextLine();

    try {
      Livro l = arqLivros.read(isbn);
      if (l == null) {
        System.out.println("Livro não encontrado.");
        return;
      }
      mostraLivro(l);
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // ALTERAR_LIVRO
  // ---------------------
  public void alterarLivro() {
    System.out.print("\nISBN do Livro: ");
    String isbn = console.nextLine();

    try {
      Livro livro = arqLivros.read(isbn);
      if (livro == null) {
        System.out.println("Livro não encontrado.");
        return;
      }
      mostraLivro(livro);

      System.out.println("\nDigite os novos dados.\nDeixe em branco os que não forem alterados.");
      Livro livro2;
      try {
        livro2 = leLivro();
      } catch (Exception e) {
        System.out.println("Dados inválidos");
        return;
      }
      if (livro2.getISBN().length() > 0)
        livro.setISBN(livro2.getISBN());
      if (livro2.getTitulo().length() > 0)
        livro.setTitulo(livro2.getTitulo());
      if (livro2.getAutor().length() > 0)
        livro.setAutor(livro2.getAutor());
      if (livro2.getPreco() > 0)
        livro.setPreco(livro2.getPreco());
      if (livro2.getLancamento().getTime() > 0)
        livro.setLancamento(livro2.getLancamento().getTime());

      System.out.print("Confirma alteração do livro (S/N)? ");
      char resp = console.nextLine().charAt(0);
      if (resp == 'S' || resp == 's') {
        if (arqLivros.update(livro))
          System.out.println("Livro alterado!");
        else
          System.out.println("Erro na alteração do livro!");
      } else
        System.out.println("Alteração cancelada!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

  // ---------------------
  // EXCLUIR_LIVRO
  // ---------------------
  public void excluirLivro() {
    System.out.print("\nISBN do Livro: ");
    String isbn = console.nextLine();

    try {
      Livro livro = arqLivros.read(isbn);
      if (livro == null) {
        System.out.println("Livro não encontrado.");
        return;
      }
      mostraLivro(livro);

      System.out.print("Confirma exclusão do livro (S/N)? ");
      char resp = console.nextLine().charAt(0);
      if (resp == 'S' || resp == 's') {
        if (arqLivros.delete(livro.getID()))
          System.out.println("Livro excluído!");
        else
          System.out.println("Erro na exclusão do livro!");
      } else
        System.out.println("Exclusão cancelada!");
    } catch (Exception e) {
      System.out.println("Erro no acesso ao arquivo");
      e.printStackTrace();
    }
  }

}
