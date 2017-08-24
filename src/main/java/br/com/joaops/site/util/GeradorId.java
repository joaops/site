/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.util;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public class GeradorId {
    
    private static Long ID = 1l;
    
    public static String getNextId() {
        return String.valueOf(ID++);
    }
    
}