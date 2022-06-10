class main {
    public static void main(String[] args) {
        int len = 16;
        byte[] ent = new byte[len];

        for (int i = 0; i < len; i++) {
            ent[i] = (byte) i;
            System.out.print("\t" + ent[i] + ",");
        }
        System.out.println("\noriginal\n");
        Cripto cr = new Cripto(ent);
        ent = cr.cifrar();

    }
}
