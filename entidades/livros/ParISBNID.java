package entidades.livros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aed3.RegistroHashExtensivel;

public class ParISBNID implements RegistroHashExtensivel<ParISBNID> {

  private String isbn; // tamanho fixo 13 bytes
  private int id;
  private short TAMANHO = 17; // bytes

  public ParISBNID() throws Exception {
    this("", -1);
  }

  public ParISBNID(String isbn, int id) throws Exception {
    if (isbn.length() > 0 && isbn.length() != 13)
      throw new Exception("ISBN inválido");
    this.isbn = isbn;
    this.id = id;
  }

  @Override
  public int hashCode() {
    return Math.abs(this.isbn.hashCode());
  }

  public String getISBN() {
    return this.isbn;
  }

  public void setISBN(String isbn) {
    this.isbn = isbn;
  }

  public int getID() {
    return this.id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public short size() {
    return this.TAMANHO;
  }

  public String toString() {
    return this.isbn + ";" + this.id;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.write(isbn.getBytes());
    dos.writeInt(id);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] aux = new byte[13];
    dis.read(aux);
    this.isbn = new String(aux);
    this.id = dis.readInt();
  }

}