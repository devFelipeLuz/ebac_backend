import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Media m = new Media();

        System.out.print("Digite a primeira nota: ");
        float nota1 = s.nextFloat();
        m.setNota1(nota1);

        System.out.print("Digite a segunda nota: ");
        float nota2 = s.nextFloat();
        m.setNota2(nota2);

        System.out.print("Digite a terceira nota: ");
        float nota3 = s.nextFloat();
        m.setNota3(nota3);

        System.out.print("Digite a quarta nota: ");
        float nota4 = s.nextFloat();
        m.setNota4(nota4);

        m.calculaMedia();
        m.printMedia();
        m.printStatus();
    }
}
