import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Scanner r = new Scanner(System.in);

        System.out.println("Digite o nome da pessoa e seu sexo e separe-as por vírgula");
        System.out.println("Exemplo: Ricardo - Masculino, Fernanda - Feminino");
        String input = r.nextLine();
        String[] inputSplit = input.split(",");
        List<String> list = List.of(inputSplit);

        System.out.println("********** predicate inline **********");
        list.stream()
                .filter(p -> p.toLowerCase().contains("feminino"))
                .map(s -> "Pessoa: " + s)
                .forEach(System.out::println);

        System.out.println("********** Predicate nomeado **********");
        Predicate<String> mulheres = s -> s.toLowerCase().contains("feminino");
        list.stream()
                .filter(mulheres)
                .map(s -> "Pessoa: " + s)
                .forEach(System.out::println);
    }
}
