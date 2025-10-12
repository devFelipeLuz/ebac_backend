package br.com.feluz.services;

import br.com.feluz.domain.Costumer;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

public interface ICostumerService {
    Boolean register(Costumer costumer) throws TipoChaveNaoEncontradaException;

    Costumer find(Long cpf);

    void remove(Long cpf);

    void update(Costumer costumer) throws TipoChaveNaoEncontradaException;
}
