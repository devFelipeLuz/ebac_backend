import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner r = new Scanner(System.in);
        Group grupo = new Group();

        for (int i = 0; i < 4; i++) {
            System.out.print("Digite seu nome: ");
            String name = r.nextLine();
            System.out.print("Agora digite o seu gênero: ");
            String gender = r.nextLine();
            Pessoa p = new Pessoa(name, gender);
            grupo.addPerson(p);
        }
        grupo.printGroup();
    }
}
