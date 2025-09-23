import java.util.ArrayList;
import java.util.List;

public class Group {
    List<Pessoa> masculino = new ArrayList<Pessoa>();
    List<Pessoa> feminino = new ArrayList<Pessoa>();

    public void addPerson(Pessoa p) {
        if(p.getGender().equalsIgnoreCase("Masculino")) {
            masculino.add(p);
        } else if (p.getGender().equalsIgnoreCase("Feminino")) {
            feminino.add(p);
        } else {
            System.out.println("Desconhecido");
        }
    }

    public void printGroup() {
        System.out.println("************ Grupo masculino ************");
        masculino.forEach(p -> System.out.println(p));

        System.out.println("************ Grupo feminino ************");
        feminino.forEach(p -> System.out.println(p));
    }
}
