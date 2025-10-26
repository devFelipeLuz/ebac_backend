package br.com.feluz.dao.interfaces;

import br.com.feluz.Carro;

import java.util.List;

public interface ICarroDAO {

    Boolean register(Carro carro);

    Carro find(Long id);

    void update(Carro carro);

    void remove(Carro carro);

    List<Carro> findAll();

}
