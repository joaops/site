package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.model.Pessoa;
import br.com.joaops.site.model.dao.PessoaRepository;
import br.com.joaops.site.service.PessoaService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author João Paulo
 */
@Service("PessoaService")
public class PessoaServiceImpl implements PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Override
    public PessoaDto newDto() {
        return new PessoaDto();
    }
    
    @Override
    public void save(PessoaDto pessoaDto) {
        pessoaRepository.save(mapper(pessoaDto));
    }
    
    @Override
    public List<PessoaDto> findAll() {
        List<PessoaDto> pessoasDto = new ArrayList<>();
        Iterable<Pessoa> pessoas = pessoaRepository.findAll();
        pessoas.forEach(pessoa -> {
            PessoaDto pessoaDto = mapper(pessoa);
            if (pessoaDto != null) {
                pessoasDto.add(pessoaDto);
            }
        });
        return pessoasDto;
    }
    
    private PessoaDto mapper(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }
        return new PessoaDto(pessoa.getId(), pessoa.getNome(), pessoa.getNascimento());
    }
    
    private Pessoa mapper(PessoaDto pessoaDto) {
        if (pessoaDto == null) {
            return null;
        }
        return new Pessoa(pessoaDto.getId(), pessoaDto.getNome(), pessoaDto.getNascimento());
    }
    
}