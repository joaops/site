package br.com.joaops.site.model.dao;

import br.com.joaops.site.model.Pessoa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author João Paulo
 */
@Repository
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {
    
}