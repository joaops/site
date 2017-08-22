package br.com.joaops.site.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author João Paulo
 */
@Entity
@Table(schema = "public",
        name = "pessoa",
        indexes = {
            @Index(name = "idx_id_pessoa", columnList = "id_pessoa")
        }
)
@SequenceGenerator(name = "PessoaIdGenerator", sequenceName = "seq_pessoa", initialValue = 1, allocationSize = 1)
public class Pessoa implements Serializable {
    
    @Id
    @Column(name = "id_pessoa", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PessoaIdGenerator")
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date nascimento;
    
    public Pessoa() {
        this.id = 0L;
        this.nome = "";
        this.nascimento = new Date();
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
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + Objects.hashCode(this.nascimento);
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
        final Pessoa other = (Pessoa) obj;
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