package entidades.livros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.NumberFormat;
import java.util.Date;

import aed3.Registro;

public class Livro implements Registro { // Classe == TIPO DE ENTIDADE

  private int ID;
  private String isbn;
  private String titulo;
  private String autor;
  private float preco;
  private Date lancamento;

  public Livro() {
    this(-1, "", "", "", 0F, new Date());
  }

  public Livro(String b, String t, String a, float p, Date d) {
    this(-1, b, t, a, p, d);
  }

  public Livro(int i, String b, String t, String a, float p, Date d) {
    this.ID = i;
    this.isbn = b;
    this.titulo = t;
    this.autor = a;
    this.preco = p;
    this.lancamento = d;
  }

  public int getID() {
    return this.ID;
  }

  public void setID(int i) {
    this.ID = i;
  }

  public String getISBN() {
    return this.isbn;
  }

  public void setISBN(String b) {
    this.isbn = b;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String t) {
    this.titulo = t;
  }

  public String getAutor() {
    return this.autor;
  }

  public void setAutor(String a) {
    this.autor = a;
  }

  public float getPreco() {
    return this.preco;
  }

  public void setPreco(float p) {
    this.preco = p;
  }

  public Date getLancamento() {
    return this.lancamento;
  }

  public void setLancamento(long d) {
    this.lancamento = new Date(d);
  }

  public String toString() {
    return "\nID: " + this.ID +
        "\nISBN: " + this.isbn +
        "\nTítulo: " + this.titulo +
        "\nAutor: " + this.autor +
        "\nPreço: " + NumberFormat.getCurrencyInstance().format(this.preco) +
        "\nData de lançamento: " + this.lancamento;
  }

  public byte[] toByteArray() throws Exception {
    ByteArrayOutputStream ba = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(ba);
    dos.writeInt(this.ID);
    dos.writeUTF(this.isbn);
    dos.writeUTF(this.titulo);
    dos.writeUTF(this.autor);
    dos.writeFloat(this.preco);
    dos.writeLong(this.lancamento.getTime());
    return ba.toByteArray();
  }

  public void fromByteArray(byte[] vb) throws Exception {
    ByteArrayInputStream ba = new ByteArrayInputStream(vb);
    DataInputStream dis = new DataInputStream(ba);
    this.ID = dis.readInt();
    this.isbn = dis.readUTF();
    this.titulo = dis.readUTF();
    this.autor = dis.readUTF();
    this.preco = dis.readFloat();
    this.lancamento = new Date(dis.readLong());
  }

}
