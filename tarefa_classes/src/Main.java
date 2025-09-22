/**
 * @author Felipe Luz
 */
public class Main {

    /**
     * Metodo criado para pular uma linha
     */
    public void breakLine() {
        System.out.println();
    }

    public static void main(String[] args) {
        /**
         * Instanciando o objeto Pessoa
         */
        Pessoa pessoa = new Pessoa();

        pessoa.setName("Teste Testado da Silva Teste");
        pessoa.setAge(82);
        pessoa.setCpf("123.456.789-10");
        pessoa.setOccupation("Java Developer");

        Main br = new Main();
        br.breakLine();

        pessoa.printId();
        pessoa.printName();
        pessoa.printAge();
        pessoa.printCpf();
        pessoa.printOccupation();
    }
}