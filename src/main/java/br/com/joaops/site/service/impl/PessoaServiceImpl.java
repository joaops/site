package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.model.domain.Pessoa;
import br.com.joaops.site.model.dao.PessoaRepository;
import br.com.joaops.site.service.PessoaService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João Paulo
 */
@Service("PessoaService")
public class PessoaServiceImpl implements PessoaService {
    
    @Autowired
    private Mapper mapper;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Override
    public PessoaDto newDto() {
        return new PessoaDto();
    }
    
    @Override
    public void save(PessoaDto pessoaDto) {
        Pessoa pessoa = null;
        if (pessoaDto != null) {
            pessoa = new Pessoa();
            mapper.map(pessoaDto, pessoa);
        }
        if (pessoa != null) {
            pessoaRepository.save(pessoa);
        }
    }
    
    @Override
    public void delete(PessoaDto pessoa) {
        pessoaRepository.delete(pessoa.getId());
    }
    
    @Override
    public PessoaDto findOne(Long id) {
        Pessoa pessoa = pessoaRepository.findOne(id);
        PessoaDto pessoaDto = null;
        if (pessoa != null) {
            pessoaDto = mapper.map(pessoa, PessoaDto.class);
        }
        return pessoaDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<PessoaDto> findAll() {
        List<PessoaDto> pessoasDto = new ArrayList<>();
        Iterable<Pessoa> pessoas = pessoaRepository.findAll();
        pessoas.forEach(pessoa -> {
            PessoaDto pessoaDto = mapper.map(pessoa, PessoaDto.class);
            pessoasDto.add(pessoaDto);
        });
        return pessoasDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<PessoaDto> findAll(Pageable p) {
        List<PessoaDto> listDto = new ArrayList<>();
        Page<Pessoa> pessoas = pessoaRepository.findAll(p);
        for (Pessoa pessoa : pessoas) {
            PessoaDto pessoaDto = new PessoaDto();
            mapper.map(pessoa, pessoaDto);
            listDto.add(pessoaDto);
        }
        Page<PessoaDto> page;
        if (listDto.isEmpty()) {
            page = new PageImpl<>(new ArrayList<>(), p, 0);
        } else {
            page = new PageImpl<>(listDto, p, pessoas.getTotalElements());
        }
        return page;
    }
    
}