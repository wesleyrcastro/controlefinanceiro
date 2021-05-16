package br.com.mecnetcastro.financas.model.enumeration;

public enum TipoMovimentacao {

	ENTRADA("Entrada"), SAIDA("Saida");

	private String descricao;

	TipoMovimentacao(String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
