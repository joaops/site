/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.json.repository;

import br.com.joaops.site.json.response.PessoaResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
@Repository
public class PessoaResponseRepository {
    
    private static final Map<String, PessoaResponse> MAP = new HashMap<>();
    
    public void save(PessoaResponse response) {
        MAP.put(response.getId(), response);
    }
    
    public boolean exist(String id) {
        return MAP.containsKey(id);
    }
    
    public PessoaResponse findOne(String id) {
        return MAP.get(id);
    }
    
    public List<PessoaResponse> findAll() {
        return new ArrayList<>(MAP.values());
    }
    
    public void remove(String id) {
        MAP.remove(id);
    }
    
    public void removeAll() {
        MAP.clear();
    }
    
}