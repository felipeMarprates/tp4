package entidades.clientes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import aed3.Registro;
import aed3.Cripto;

public class Cliente implements Registro {

  private int ID;
  private String nome;
  private String cpf;
  private String email;

  public Cliente() throws Exception {
    this(-1, "", "", "");
  }

  public Cliente(String n, String c, String e) throws Exception {
    this(-1, n, c, e);
  }

  public Cliente(int i, String n, String c, String e) throws Exception {
    if (c.length() != 0 && c.length() != 11)
      throw new Exception("Tamanho inválido do CPF. O número não deve ter pontos ou traços.");
    this.ID = i;
    this.nome = n;
    this.cpf = c;
    this.email = e;
  }

  public int getID() {
    return this.ID;
  }

  public void setID(int i) {
    this.ID = i;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String s) {
    this.nome = s;
  }

  public String getCPF() {
    return this.cpf;
  }

  public void setCPF(String c) throws Exception {
    if (cpf.length() != 11)
      throw new Exception("Tamanho inválido do CPF. O número não deve ter pontos ou traços.");
    this.cpf = c;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String s) {
    this.email = s;
  }

  public String toString() {
    return "\nID: " + this.ID +
        "\nNome: " + this.nome +
        "\nCPF: " + this.cpf +
        "\nEmail: " + this.email;
  }

  public byte[] toByteArray() throws Exception {
    ByteArrayOutputStream ba = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(ba);
    dos.writeInt(this.ID);
    dos.writeUTF(this.nome);
    dos.write(this.cpf.getBytes());
    dos.writeUTF(this.email);
    byte[] dados = ba.toByteArray();
    Cripto cr = new Cripto(dados);
    byte[] dadosCifrados = cr.cifrar();

    return dadosCifrados;
  }

  public void fromByteArray(byte[] vb) throws Exception {
    Cripto dcr = new Cripto(vb);
    byte[] dadosDecifrados = dcr.decifrar();
    ByteArrayInputStream ba = new ByteArrayInputStream(dadosDecifrados);
    DataInputStream dis = new DataInputStream(ba);

    this.ID = dis.readInt();
    this.nome = dis.readUTF();
    byte[] cpfAux = new byte[11];
    dis.read(cpfAux);
    this.cpf = new String(cpfAux);
    this.email = dis.readUTF();
  }

}
