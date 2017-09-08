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

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public String save(String sessionId, PessoaDto pessoaDto) {
        return null;
    }
    
    @Override
    public PessoaDto findOne(String sessionId, Long id) {
        return null;
    }
    
    @Override
    public String update(String sessionId, PessoaDto pessoaDto) {
        return null;
    }
    
    @Override
    public String delete(String sessionId, PessoaDto pessoa) {
        return null;
    }
    
    @Override
    public List<PessoaDto> findAll(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        if (!sessionRepository.containsValue(sessionId)) {
            return null;
        }
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
                break;
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
        }
        return pessoasDto;
    }
    
}