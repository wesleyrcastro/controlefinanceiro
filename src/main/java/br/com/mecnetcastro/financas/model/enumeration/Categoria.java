package br.com.mecnetcastro.financas.model.enumeration;

public enum Categoria {

	GASTO_FIXO("Gasto Fixo"), GASTO_VARIAVEL("Gasto Variável");

	private String descricao;

	Categoria(String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
