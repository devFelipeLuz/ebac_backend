package br.com.feluz.dao.interfaces;


import br.com.feluz.Acessorio;

import java.util.List;

public interface IAcessorioDAO {

    Boolean register(Acessorio acessorio);

    Acessorio find(Long id);

    void update(Acessorio acessorio);

    void remove(Acessorio acessorio);

    List<Acessorio> findAll();
}
