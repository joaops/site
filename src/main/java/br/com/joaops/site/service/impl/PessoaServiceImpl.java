package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.domain.PessoaJson;
import br.com.joaops.site.json.protocol.Message;
import br.com.joaops.site.json.protocol.Status;
import br.com.joaops.site.json.repository.MessageRepository;
import br.com.joaops.site.json.repository.SessionRepository;
import br.com.joaops.site.service.PessoaService;
import br.com.joaops.site.util.CONSTANTES;
import br.com.joaops.site.util.GeradorId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.dozer.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author João Paulo
 */
@Service("PessoaService")
public class PessoaServiceImpl implements PessoaService {
    
    @Autowired
    private Mapper mapper;
    
    @Autowired
    private SimpMessagingTemplate simp;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Override
    public PessoaDto newDto() {
        return new PessoaDto();
    }
    
    @Override
    public PessoaDto save(PessoaDto pessoaDto) throws Exception {
        String sessionId = getSessionId();
        Message request = new Message();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.SALVAR_PESSOA);
        PessoaJson json = new PessoaJson();
        mapper.map(pessoaDto, json);
        request.setParam("pessoa", json);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.MESSAGE, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!messageRepository.exist(request.getId())) {
            if (cont >= 600) { // 1 minuto para o TimeOut
                throw new TimeoutException("Limite de Tempo Excedido.");
            }
            if (!sessionRepository.containsValue(sessionId)) {
                throw new SessionAuthenticationException("Sessão Não Conectada.");
            }
            cont++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {}
        }
        Message response = messageRepository.findOne(request.getId());
        PessoaDto dto = null;
        if (response != null) {
            messageRepository.remove(request.getId());
            if (response.getStatus() == Status.OK) {
                if (!response.containsParam("pessoa")) {
                    throw new UnsupportedOperationException("A Resposta Não Contém o Parâmetro da Consulta: " + "pessoa");
                }
                PessoaJson pessoaJson = new ObjectMapper().convertValue(response.getParam("pessoa"), PessoaJson.class);
                dto = new PessoaDto();
                mapper.map(pessoaJson, dto);
            } else {
                if (response.getStatus() == Status.ERRO) {
                    throw new UnsupportedOperationException("A Consulta Retornou Com ERRO: " + response.getParam("erro"));
                }
                throw new UnsupportedOperationException("O Status da Resposta é Inválido: " + response.getStatus().name());
            }
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
        return dto;
    }
    
    @Override
    public PessoaDto findOne(Long id) throws Exception {
        String sessionId = getSessionId();
        Message request = new Message();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.CONSULTAR_PESSOA_ID);
        request.setParam("id", id);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.MESSAGE, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!messageRepository.exist(request.getId())) {
            if (cont >= 600) { // 1 minuto para o TimeOut
                throw new TimeoutException("Limite de Tempo Excedido.");
            }
            if (!sessionRepository.containsValue(sessionId)) {
                throw new SessionAuthenticationException("Sessão Não Conectada.");
            }
            cont++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {}
        }
        Message response = messageRepository.findOne(request.getId());
        PessoaDto pessoaDto = null;
        if (response != null) {
            messageRepository.remove(request.getId());
            if (response.getStatus() == Status.OK) {
                if (!response.containsParam("pessoa")) {
                    throw new UnsupportedOperationException("A Resposta Não Contém o Parâmetro da Consulta: " + "pessoa");
                }
                // Fazer Assim Para Converter Tipos Complexos
                PessoaJson pessoaJson = new ObjectMapper().convertValue(response.getParam("pessoa"), PessoaJson.class);
                pessoaDto = new PessoaDto();
                mapper.map(pessoaJson, pessoaDto);
            } else {
                if (response.getStatus() == Status.ERRO) {
                    throw new UnsupportedOperationException("A Consulta Retornou Com ERRO: " + response.getParam("erro"));
                }
                throw new UnsupportedOperationException("O Status da Resposta é Inválido: " + response.getStatus().name());
            }
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
        return pessoaDto;
    }
    
    @Override
    public void delete(Long id) throws Exception {
        String sessionId = getSessionId();
        Message request = new Message();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.DELETAR_PESSOA);
        request.setParam("id", id);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.MESSAGE, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!messageRepository.exist(request.getId())) {
            if (cont >= 600) { // 1 minuto para o TimeOut
                throw new TimeoutException("Limite de Tempo Excedido.");
            }
            if (!sessionRepository.containsValue(sessionId)) {
                throw new SessionAuthenticationException("Sessão Não Conectada.");
            }
            cont++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {}
        }
        Message response = messageRepository.findOne(request.getId());
        if (response != null) {
            messageRepository.remove(request.getId());
            if (response.getStatus() != Status.OK) {
                throw new UnsupportedOperationException("A Operação Retornou Com ERRO: " + response.getParam("erro"));
            }
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
    }
    
    @Override
    public List<PessoaDto> findAll() throws Exception {
        // Pego o SessionId do Usuário Logado
        String sessionId = getSessionId();
        // Crio a Mensagem de Consulta
        Message request = new Message();
        // Seto Um Id único para a mensagem
        request.setId(GeradorId.getNextId());
        // Seto o Comando
        request.setOperacao(CONSTANTES.COMANDOS.CONSULTAR_PESSOAS);
        // Essa Consulta não Possui Parâmetros
        // Crio o Cabeçalho
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        // Envio o Objeto de Consulta
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.MESSAGE, request, headerAccessor.getMessageHeaders());
        // Aguardo a Resposta
        int cont = 0;
        while (!messageRepository.exist(request.getId())) {
            // Verifico o TimeOut
            if (cont >= 3000) {
                throw new TimeoutException("Limite de Tempo Excedido.");
            }
            // Verifico se a Sessão está Conectada
            if (!sessionRepository.containsValue(sessionId)) {
                throw new SessionAuthenticationException("Sessão Não Conectada.");
            }
            // Aguardo um Tempo
            cont++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {}
        }
        // Pego ou Não uma Resposta
        Message response = messageRepository.findOne(request.getId());
        // Crio o Objeto que Será Retornado
        List<PessoaDto> pessoasDto = null;
        if (response != null) {
            // Verifico o Status da Resposta
            if (response.getStatus() == Status.OK) {
                // Verifico se a Resposta Contem os Objetos Consultados
                if (!response.containsParam("pessoas")) {
                    throw new UnsupportedOperationException("A Resposta Não Contém o Parâmetro da Consulta: " + "pessoas");
                }
                // Pego Todos os parâmetros
                Map<String, Object> params = response.getParams();
                // Crio um Objeto para Converter a Lista de Pessoas, é Necessário por ser uma Lista
                ObjectMapper objectMapper = new ObjectMapper();
                // Converte o Objeto do Map em um List<PessoaJson>, foi necessário usar o TypeReference
                List<PessoaJson> pessoasJson = objectMapper.convertValue(params.get("pessoas"), new TypeReference<List<PessoaJson>>() { });
                // Instancio o Objeto de Retorno
                pessoasDto = new ArrayList<>();
                // Converto e Adiciono ao Objeto de Retorno
                PessoaDto pessoaDto;
                for (PessoaJson pessoaJson : pessoasJson) {
                    pessoaDto = new PessoaDto();
                    mapper.map(pessoaJson, pessoaDto);
                    pessoasDto.add(pessoaDto);
                }
            } else {
                if (response.getStatus() == Status.ERRO) {
                    throw new UnsupportedOperationException("A Consulta Retornou Com ERRO: " + response.getParam("erro"));
                }
                // Erro de Status
                throw new UnsupportedOperationException("O Status da Resposta é Inválido: " + response.getStatus().name());
            }
            // Removo a Mensagem do Repositório
            messageRepository.remove(request.getId());
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
        // Retorno a Consulta
        return pessoasDto;
    }
    
    private String getSessionId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (StringUtils.isEmpty(email)) {
            throw new UserPrincipalNotFoundException("Usuário Não Encontrado.");
        }
        String sessionId = sessionRepository.getSessionIdByName(email);
        if (StringUtils.isEmpty(sessionId)) {
            throw new SessionAuthenticationException("Sessão Não Encontrada.");
        }
        return sessionId;
    }
    
}