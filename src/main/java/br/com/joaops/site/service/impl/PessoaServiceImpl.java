package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.json.domain.PessoaJson;
import br.com.joaops.site.json.repository.PessoaResponseRepository;
import br.com.joaops.site.json.repository.SessionRepository;
import br.com.joaops.site.json.request.PessoaRequest;
import br.com.joaops.site.json.response.PessoaResponse;
import br.com.joaops.site.service.PessoaService;
import br.com.joaops.site.util.CONSTANTES;
import br.com.joaops.site.util.GeradorId;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private PessoaResponseRepository pessoaResponseRepository;
    
    @Override
    public void salvarPessoaResponse(PessoaResponse pessoaResponse) {
        pessoaResponseRepository.save(pessoaResponse);
    }
    
    @Override
    public PessoaDto newDto() {
        return new PessoaDto();
    }
    
    @Override
    public PessoaDto save(PessoaDto pessoaDto) throws Exception {
        String sessionId = getSessionId();
        PessoaRequest request = new PessoaRequest();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.SALVAR_PESSOA);
        List<PessoaJson> aux = new ArrayList<>();
        PessoaJson json = new PessoaJson();
        mapper.map(pessoaDto, json);
        aux.add(json);
        request.setPessoas(aux);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.PESSOA, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!pessoaResponseRepository.exist(request.getId())) {
            if (cont >= 600) { // 600 = 1 minuto, 6000 = 10 minutos
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
        PessoaResponse response = pessoaResponseRepository.findOne(request.getId());
        PessoaDto dto = null;
        if (response != null) {
            List<PessoaJson> pessoasJson = response.getPessoas();
            if (pessoasJson.size() > 0) {
                dto = new PessoaDto();
                PessoaJson pessoaJson = pessoasJson.get(0);
                mapper.map(pessoaJson, dto);
            }
            pessoaResponseRepository.remove(request.getId());
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
        return dto;
    }
    
    @Override
    public PessoaDto findOne(Long id) throws Exception {
        String sessionId = getSessionId();
        PessoaRequest request = new PessoaRequest();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.CONSULTAR_PESSOA_ID);
        List<PessoaJson> aux = new ArrayList<>();
        PessoaJson aux2 = new PessoaJson();
        aux2.setId(id);
        aux.add(aux2);
        request.setPessoas(aux);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.PESSOA, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!pessoaResponseRepository.exist(request.getId())) {
            if (cont >= 600) {
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
        PessoaResponse response = pessoaResponseRepository.findOne(request.getId());
        PessoaDto pessoaDto = null;
        if (response != null) {
            List<PessoaJson> pessoasJson = response.getPessoas();
            if (pessoasJson.size() > 0) {
                pessoaDto = new PessoaDto();
                PessoaJson pessoaJson = pessoasJson.get(0);
                mapper.map(pessoaJson, pessoaDto);
            }
            pessoaResponseRepository.remove(request.getId());
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
        return pessoaDto;
    }
    
    @Override
    public void delete(PessoaDto pessoaDto) throws Exception {
        String sessionId = getSessionId();
        PessoaRequest request = new PessoaRequest();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.DELETAR_PESSOA);
        PessoaJson pessoaJson = new PessoaJson();
        mapper.map(pessoaDto, pessoaJson);
        request.setPessoas(Arrays.asList(pessoaJson));
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.PESSOA, request, headerAccessor.getMessageHeaders());
    }
    
    @Override
    public List<PessoaDto> findAll() throws Exception {
        String sessionId = getSessionId();
        PessoaRequest request = new PessoaRequest();
        request.setId(GeradorId.getNextId());
        request.setOperacao(CONSTANTES.COMANDOS.CONSULTAR_PESSOAS);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.PESSOA, request, headerAccessor.getMessageHeaders());
        int cont = 0;
        while (!pessoaResponseRepository.exist(request.getId())) {
            if (cont >= 6000) {
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
        PessoaResponse response = pessoaResponseRepository.findOne(request.getId());
        List<PessoaDto> pessoasDto = new ArrayList<>();
        if (response != null) {
            List<PessoaJson> pessoasJson = response.getPessoas();
            PessoaDto pessoaDto;
            for (PessoaJson pessoaJson : pessoasJson) {
                pessoaDto = new PessoaDto();
                mapper.map(pessoaJson, pessoaDto);
                pessoasDto.add(pessoaDto);
            }
            pessoaResponseRepository.remove(request.getId());
        } else {
            throw new UnsupportedOperationException("Operação Sem Resposta.");
        }
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