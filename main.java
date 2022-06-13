
import java.util.Scanner;

public class Main {

  private static Scanner console = new Scanner(System.in);

  public static void main(String[] args) {

    int opcao;
    do {
      System.out.println("\nMENU PRINCIPAL");
      System.out.println("\n1) Livros");
      System.out.println("2) Clientes");
      System.out.println("\n0) Sair");

      System.out.print("\nOpção: ");
      try {
        opcao = Integer.valueOf(console.nextLine());
      } catch (NumberFormatException e) {
        opcao = -1;
      }

      switch (opcao) {
        case 1:
          (new entidades.livros.InteracoesLivros()).menuLivros();
          break;
        case 2:
          (new entidades.clientes.InteracoesClientes()).menuClientes();
          break;
        case 0:
          break;
        default:
          System.out.println("Opção inválida");
      }

    } while (opcao != 0);

  }

}