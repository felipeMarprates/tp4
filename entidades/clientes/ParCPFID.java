package entidades.clientes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aed3.RegistroHashExtensivel;

public class ParCPFID implements RegistroHashExtensivel<ParCPFID> {

  private String cpf; // 11 bytes, sem pontos ou espaços.
  private int id;
  private short TAMANHO = 15; // bytes

  public ParCPFID() throws Exception {
    this("", -1);
  }

  public ParCPFID(String cpf, int id) throws Exception {
    if (cpf.length() != 00 && cpf.length() != 11)
      throw new Exception("Tamanho inválido do CPF. O número não deve ter pontos ou traços.");
    this.cpf = cpf;
    this.id = id;
  }

  @Override
  public int hashCode() {
    return Math.abs(this.cpf.hashCode());
  }

  public String getCPF() {
    return this.cpf;
  }

  public void setCPF(String cpf) {
    this.cpf = cpf;
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
    return this.cpf + ";" + this.id;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.write(cpf.getBytes());
    dos.writeInt(id);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] cpfAux = new byte[11];
    dis.read(cpfAux);
    this.cpf = new String(cpfAux);
    this.id = dis.readInt();
  }

}