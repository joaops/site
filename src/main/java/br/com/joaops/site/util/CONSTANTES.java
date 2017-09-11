package br.com.joaops.site.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
public interface CONSTANTES {
    
    interface COMANDOS {
        public static String CONSULTAR_PESSOA_ID = "consultarPessoaId";
        public static String CONSULTAR_PESSOAS = "consultarPessoas";
        public static String SALVAR_PESSOA = "salvarPessoa";
        public static String DELETAR_PESSOA = "deletarPessoa";
    }
    
    interface TOPICS {
        public static String PING = "/topic/ping";
    }
    
    interface QUEUES {
        public static String PING = "/queue/ping";
        public static String PESSOA = "/queue/PessoaRequest";
    }
    
    interface ENDPOINTS {
        public static String START = "/start";
        public static String PESSOA = "/PessoaResponse";
    }
    
}