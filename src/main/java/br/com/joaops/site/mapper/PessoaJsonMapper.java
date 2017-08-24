/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.mapper;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.domain.PessoaJson;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class PessoaJsonMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(PessoaJson.class, PessoaDto.class);
    }
    
}