/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.response.PessoaResponse;
import java.util.List;

/**
 *
 * @author João Paulo
 */
public interface PessoaService {
    
    public void salvarPessoaResponse(PessoaResponse pessoaResponse);
    
    public PessoaDto newDto();

    public String save(String sessionId, PessoaDto pessoaDto);
    
    public PessoaDto findOne(String sessionId, Long id);
    
    public String update(String sessionId, PessoaDto pessoaDto);

    public String delete(String sessionId, PessoaDto pessoa);
    
    public List<PessoaDto> findAll(String sessionId);
    
}