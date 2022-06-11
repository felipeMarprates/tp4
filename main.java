class main {
    public static void main(String[] args) {
        int len = 25;
        byte[] ent = new byte[len];
        System.out.println("\noriginal\n");
        for (int i = 0; i < len; i++) {
            ent[i] = (byte) i;
            System.out.print("\t" + ent[i] + ",");
        }

        Cripto cr = new Cripto(ent);
        ent = cr.cifrar();
        Cripto dcr = new Cripto(ent);
        ent = dcr.decifrar();

    }
}
