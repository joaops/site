package br.com.joaops.site.model.dao;

import br.com.joaops.site.model.Pessoa;
import java.time.LocalDate;
import java.time.Month;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

/**
 *
 * @author João Paulo
 */
@Repository
public class PessoaRepository {
    
    private static Long seq_pessoa = 0L;
    private static final Map<Long, Pessoa> MAP = new HashMap<>();
    
    @PostConstruct
    private void init() {
        MAP.put(++seq_pessoa, new Pessoa(seq_pessoa, "Fulano", LocalDate.of(1993, Month.NOVEMBER, 14)));
        MAP.put(++seq_pessoa, new Pessoa(seq_pessoa, "Ciclano", LocalDate.of(1994, Month.FEBRUARY, 18)));
        MAP.put(++seq_pessoa, new Pessoa(seq_pessoa, "Beltrano", LocalDate.of(1995, Month.JUNE, 9)));
    }
    
    public Pessoa save(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }
        if (pessoa.getId() > 0) {
            return MAP.put(pessoa.getId(), pessoa);
        } else {
            pessoa.setId(++seq_pessoa);
            return MAP.put(seq_pessoa, pessoa);
        }
    }
    
    public Pessoa findOne(Long id) {
        return MAP.get(id);
    }
    
    public boolean exists(Long id) {
        return MAP.containsKey(id);
    }
    
    public void remove(Long id) {
        MAP.remove(id);
    }
    
    public Iterable<Pessoa> findAll() {
        return MAP.values();
    }
    
}