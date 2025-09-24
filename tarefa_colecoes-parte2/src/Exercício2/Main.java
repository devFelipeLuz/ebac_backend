package Exercício2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner r = new Scanner(System.in);
        List<String> masculino = new ArrayList<>();
        List<String> feminino = new ArrayList<>();

        System.out.println("Digite nomes - gênero seguidos de vírigula. Exemplo: Ricardo - M, Luana - F, etc...");
        String res = r.nextLine();
        String[] pessoas = res.split(",");
        Arrays.sort(pessoas);

        for (String p : pessoas) {
            p = p.trim();
            if (p.contains(" - M")) {
                masculino.add(p);
            } else if (p.contains(" - F")) {
                feminino.add(p);
            }
        }

        System.out.println("*** Grupo masculino: ");
        masculino.forEach(n -> System.out.println(n));
        System.out.println("*** Grupo feminino: ");
        feminino.forEach(n -> System.out.println(n));
    }
}
