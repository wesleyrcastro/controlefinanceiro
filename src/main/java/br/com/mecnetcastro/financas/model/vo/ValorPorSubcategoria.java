package br.com.mecnetcastro.financas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.mecnetcastro.financas.model.Subcategoria;

public class ValorPorSubcategoria implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal valor;
	private Subcategoria subcategoria;
	private Date data;

	public ValorPorSubcategoria(BigDecimal valor, Subcategoria subcategoria, Date data) {
		this.valor = valor;
		this.subcategoria = subcategoria;
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public Date getData() {
		return data;
	}

}