public class Main {
    public static void main(String[] args) {
        GenericList<Carro> cars = new GenericList<>();

        //Teste de adicionar um carro
        System.out.println("\n******* Teste adicionando carros *******");
        Toyota corolla = new Toyota(1, "Corolla", "Azul", 2022);
        Honda civic = new Honda(2, "Civic", "Azul Marinho", 2008);
        Mitsubishi lancer = new Mitsubishi(3, "Lancer Evo IX", "Vermelho", 2007);

        cars.add(corolla);
        cars.add(civic);
        cars.add(lancer);

        cars.findAll().forEach(car -> car.printInfo());

        //Teste de update
        System.out.println("\n******* Teste de atualizacao de carro *******");
        Toyota corollaAtualizado = new Toyota(1, "Corolla Altis", "Preto", 2024);
        cars.update(corollaAtualizado);

        //Teste de remocao
        System.out.println("\n******* Teste removendo um carro *******");
        cars.remove(3);

        //gerando a lista atualizada
        System.out.println("\n******* Lista atualizada *******");
        cars.findAll().forEach(car -> car.printInfo());
    }
}
