package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.repository.PessoaResponseRepository;
import br.com.joaops.site.json.repository.SessionRepository;
import br.com.joaops.site.json.response.PessoaResponse;
import br.com.joaops.site.service.PessoaService;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author João Paulo
 */
@Service("PessoaService")
public class PessoaServiceImpl implements PessoaService {
    
    @Autowired
    private Mapper mapper;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private PessoaResponseRepository pessoaResponseRepository;
    
    @Override
    public void salvarPessoaResponse(PessoaResponse pessoaResponse) {
        pessoaResponseRepository.save(pessoaResponse);
    }
    
    @Override
    public PessoaDto newDto() {
        return new PessoaDto();
    }
    
    @Override
    public String save(String sesssionId, PessoaDto pessoaDto) {
        return null;
    }

    @Override
    public PessoaDto findOne(String sesssionId, Long id) {
        return null;
    }

    @Override
    public String update(String sesssionId, PessoaDto pessoaDto) {
        return null;
    }

    @Override
    public String delete(String sesssionId, PessoaDto pessoa) {
        return null;
    }

    @Override
    public List<PessoaDto> findAll(String sesssionId) {
        return null;
    }
    
}