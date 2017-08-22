/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.PessoaDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface PessoaService {
    
    public PessoaDto newDto();
    public void save(PessoaDto pessoaDto);
    public void delete(PessoaDto pessoa);
    public PessoaDto findOne(Long id);
    public List<PessoaDto> findAll();
    public Page<PessoaDto> findAll(Pageable p);
    
}