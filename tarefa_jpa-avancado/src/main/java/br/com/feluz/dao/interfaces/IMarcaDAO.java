package br.com.feluz.dao.interfaces;

import br.com.feluz.Marca;

import java.util.List;

public interface IMarcaDAO {

    Boolean register(Marca marca);

    Marca find(Long id);

    void update(Marca marca);

    void remove(Marca marca);

    List<Marca> findAll();
}
