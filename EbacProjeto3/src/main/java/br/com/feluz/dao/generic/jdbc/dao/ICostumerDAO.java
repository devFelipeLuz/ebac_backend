package br.com.feluz.dao.generic.jdbc.dao;

import java.util.List;

import br.com.feluz.domain.Costumer;


/**
 * @author Felipe Luz
 */
public interface ICostumerDAO {
	
	public Integer register(Costumer costumer) throws Exception;
	
	public Integer update(Costumer costumer) throws Exception;
	
	public Costumer find(String code) throws Exception;
	
	public List<Costumer> findAll() throws Exception;
	
	public Integer remove(Costumer costumer) throws Exception;

}
