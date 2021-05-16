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

import br.com.mecnetcastro.financas.model.enumeration.Categoria;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;

@Entity
@Audited
@AuditTable(value = "MOVIMENTACAO_AUD")
@Table(name = "MOVIMENTACAO")
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA", nullable = false)
	private Date data;

	@NotNull(message = "Valor obrigatório")
	@DecimalMin(value = "0.01")
	@Column(name = "VALOR", nullable = false, columnDefinition = "numeric(10,2)")
	private BigDecimal valor;

	@Lob
	@Type(type = "org.hibernate.type.StringClobType")
	@Column(name = "DESCRICAO")
	private String descricao;

	@NotNull(message = "Tipo de movimentação obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "ID_TIPO_MOVIMENTACAO", length = 25, nullable = false)
	private TipoMovimentacao tipoMovimentacao;

	@NotNull(message = "Categoria obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "ID_CATEGORIA", length = 25, nullable = false)
	private Categoria categoria;

	@NotNull(message = "Subcategoria obrigatório")
	@ManyToOne
	@JoinColumn(name = "ID_SUBCATEGORIA", referencedColumnName = "ID", nullable = false)
	private Subcategoria subcategoria;

	@NotNull(message = "Conta obrigatório")
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", referencedColumnName = "ID", nullable = false)
	private Conta conta;

	@PrePersist
	private void preSave() {
		this.data = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
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
		Movimentacao other = (Movimentacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
