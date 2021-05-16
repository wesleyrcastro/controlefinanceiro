package br.com.mecnetcastro.financas.model.enumeration;

public enum Cargo {

	FUNCIONARIO("Funcionário"), GERENTE("Gerente");

	private String descricao;

	Cargo(String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
