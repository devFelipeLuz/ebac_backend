import java.util.ArrayList;
import java.util.List;

public class GenericList<T extends Carro> {

    List<T> list = new ArrayList<>();

    public void add(T car) {
        list.add(car);
    }

    public void remove(Integer id) {
        T toRemove = null;

        for (T item : list) {
            if (item.getId().equals(id)) {
                toRemove = item;
                break;
            }
        }

        if (toRemove != null) {
            list.remove(toRemove);
            System.out.println("\nItem " + toRemove + " removido!");
        } else {
            System.out.println("\nItem não encontrado");
        }

    }

    public void update(T car) {
        T toUpdate = null;
        for (T item : list) {
            if (item.getId().equals(car.getId())) {
                toUpdate = item;
            }
        }
        if (toUpdate != null) {
            toUpdate.setModel(car.getModel());
            toUpdate.setColor(car.getColor());
            toUpdate.setYear(car.getYear());
            System.out.println("\nAtributos alterados com sucesso");
            System.out.println("Carro atualizado: " + toUpdate);
        } else {
            System.out.println("\nNão identificado");
        }
    }

    public List<T> findAll() {
        return list;
    }
}
