package entidades.livros;

import aed3.Arquivo;
import aed3.HashExtensivel;

public class ArquivoLivros extends Arquivo<Livro> {

  private HashExtensivel<ParISBNID> indiceIndiretoISBN;

  public ArquivoLivros(String nomePasta) throws Exception {
    super(nomePasta, Livro.class.getConstructor());
    indiceIndiretoISBN = new HashExtensivel<>(
        ParISBNID.class.getConstructor(),
        4,
        nomePasta + "/indiceISBN.1.db",
        nomePasta + "/indiceISBN.2.db");
  }

  // ---------------------
  // CREATE
  // ---------------------
  @Override
  public int create(Livro livro) throws Exception {
    int id = super.create(livro);
    indiceIndiretoISBN.create(
        new ParISBNID(livro.getISBN(), id));
    return id;
  }

  // ---------------------
  // READ
  // ---------------------
  public Livro read(String isbn) throws Exception {
    ParISBNID par = indiceIndiretoISBN.read(Math.abs(isbn.hashCode()));
    if (par == null)
      return null;
    return super.read(par.getID());
  }

  // ---------------------
  // UPDATE
  // ---------------------
  @Override
  public boolean update(Livro novoLivro) throws Exception {
    Livro livroAntigo = super.read(novoLivro.getID());
    if (livroAntigo != null) {
      // Testa se mudou o ISBN. Como o ID nunca é alterado,
      // o método update() do índice não resolve a alteração de ISBN.
      if (livroAntigo.getISBN().compareTo(novoLivro.getISBN()) != 0) {
        indiceIndiretoISBN.delete(Math.abs(livroAntigo.getISBN().hashCode()));
        indiceIndiretoISBN.create(new ParISBNID(novoLivro.getISBN(), novoLivro.getID()));
      }
      // Atualiza o livro no arquivo de dados. Não há mudança de ID.
      return super.update(novoLivro);
    }
    return false;
  }

  // ---------------------
  // DELETE
  // ---------------------
  @Override
  public boolean delete(int id) throws Exception {
    Livro livro = super.read(id);
    if (livro != null)
      if (indiceIndiretoISBN.delete(Math.abs(livro.getISBN().hashCode())))
        return super.delete(id);
    return false;
  }
}
