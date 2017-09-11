package br.com.joaops.site.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author João Paulo
 */
public class PessoaDto implements Serializable {
    
    private Long id;
    
    @NotEmpty
    @Length(max = 255)
    private String nome;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nascimento;
    
    public PessoaDto() {
        this(0L, "", new Date());
    }
    
    public PessoaDto(Long id, String nome, Date nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Date getNascimento() {
        return nascimento;
    }
    
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.nascimento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaDto other = (PessoaDto) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nascimento, other.nascimento)) {
            return false;
        }
        return true;
    }
    
}