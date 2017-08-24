/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.response.PessoaResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface PessoaService {
    
    public void salvarPessoaResponse(PessoaResponse pessoaResponse);
    
    public PessoaDto newDto();

    public String save(String sesssionId, PessoaDto pessoaDto);
    
    public PessoaDto findOne(String sesssionId, Long id);
    
    public String update(String sesssionId, PessoaDto pessoaDto);

    public String delete(String sesssionId, PessoaDto pessoa);
    
    public List<PessoaDto> findAll(String sesssionId);
    
}