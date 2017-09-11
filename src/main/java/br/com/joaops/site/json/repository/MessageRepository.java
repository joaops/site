/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.json.repository;

import br.com.joaops.site.json.protocol.Message;
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
public class MessageRepository {
    
    private static final Map<Long, Message> MAP = new HashMap<>();
    
    public void save(Message message) {
        MAP.put(message.getId(), message);
    }
    
    public boolean exist(Long id) {
        return MAP.containsKey(id);
    }
    
    public Message findOne(Long id) {
        return MAP.get(id);
    }
    
    public List<Message> findAll() {
        return new ArrayList<>(MAP.values());
    }
    
    public void remove(Long id) {
        MAP.remove(id);
    }
    
    public void removeAll() {
        MAP.clear();
    }
    
}