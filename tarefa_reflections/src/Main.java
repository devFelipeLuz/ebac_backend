public class Main {

    public static void main(String[] args) {
        TabelaComAnnotation t = new TabelaComAnnotation();

        if (t.getClass().isAnnotationPresent(Tabela.class)) {
            Tabela tabela = t.getClass().getAnnotation(Tabela.class);

            System.out.println("Nome da tabela: " + tabela.nome());
        }
    }
}
