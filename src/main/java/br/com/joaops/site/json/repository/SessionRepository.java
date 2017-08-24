/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.json.repository;

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
public class SessionRepository {
    
    private static final Map<String, String> SESSIONS = new HashMap<String, String>();
    
    public void add(String name, String sessionId) {
        SESSIONS.put(name, sessionId);
    }
    
    public void remove(String name) {
        SESSIONS.remove(name);
    }
    
    public boolean containsKey(String name) {
        return SESSIONS.containsKey(name);
    }
    
    public boolean containsValue(String sessionId) {
        return SESSIONS.containsValue(sessionId);
    }
    
    public String getSessionIdByName(String name) {
        return SESSIONS.get(name);
    }
    
    public List<String> getAll() {
        return new ArrayList<>(SESSIONS.values());
    }
    
}