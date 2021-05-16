package br.com.mecnetcastro.financas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ValorPorMes implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Integer mes;
	private final Integer ano;
	private final BigDecimal valor;

	public ValorPorMes(Integer mes, Integer ano, BigDecimal valor) {

		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
	}

	public Integer getMes() {
		return mes;
	}

	public Integer getAno() {
		return ano;
	}

	public BigDecimal getValor() {
		return valor;
	}

}