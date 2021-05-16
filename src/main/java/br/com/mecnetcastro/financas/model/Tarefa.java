package br.com.mecnetcastro.financas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.mecnetcastro.financas.model.enumeration.Cargo;
import br.com.mecnetcastro.financas.model.enumeration.Status;

@Entity
@Audited
@AuditTable(value = "TAREFA_AUD")
@Table(name = "TAREFA")
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INCLUSAO", nullable = false)
	private Date dataInclusao;

	@NotNull(message = "Data vencimento obrigatório")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_VENCIMENTO", nullable = false)
	private Date dataVencimento;

	@Lob
	@NotEmpty(message = "Descrição obrigatório")
	@Type(type = "org.hibernate.type.StringClobType")
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@NotNull(message = "Valor obrigatório")
	@DecimalMin(value = "0.01")
	@Column(name = "VALOR", nullable = false, columnDefinition = "numeric(10,2)")
	private BigDecimal valor;

	@NotNull(message = "Status obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "ID_STATUS", length = 15, nullable = false)
	private Status status;

	@NotNull(message = "Conta obrigatório")
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", referencedColumnName = "ID", nullable = false)
	private Conta conta;

	@NotNull(message = "Usuário obrigatório")
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false)
	private Usuario usuario;

	@PrePersist
	private void preSave() {
		this.dataInclusao = new Date();
		this.status = Status.PENDENTE;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean podeSerExecutadaPor(Usuario usuario) {
		return usuario.getCargo() == Cargo.GERENTE || usuario.getId() == this.usuario.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
