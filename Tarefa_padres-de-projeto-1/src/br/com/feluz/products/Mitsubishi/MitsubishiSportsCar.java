package br.com.feluz.products.Mitsubishi;

import br.com.feluz.products.ISports;

public class MitsubishiSportsCar implements ISports {
    @Override
    public void makeSound() {
        System.out.println("Mitsubishi Esporte: vruuuuuuuuum!");
    }
}
