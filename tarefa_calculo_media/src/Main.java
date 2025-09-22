import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalculaMedia media = new CalculaMedia();
        Scanner reader = new Scanner(System.in);

        System.out.println("Digite a nota 1: ");
        double nota1 = reader.nextDouble();
        media.setNota1(nota1);

        System.out.println("Digite a nota 2: ");
        double nota2 = reader.nextDouble();
        media.setNota2(nota2);

        System.out.println("Digite a nota 3: ");
        double nota3 = reader.nextDouble();
        media.setNota3(nota1);

        System.out.println("Digite a nota 4: ");
        double nota4 = reader.nextDouble();
        media.setNota4(nota4);

        media.calculaMedia();
        media.printMedia();
    }
}