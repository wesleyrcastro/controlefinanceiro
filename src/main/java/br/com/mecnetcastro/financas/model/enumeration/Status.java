package br.com.mecnetcastro.financas.model.enumeration;

public enum Status {

	PENDENTE("Pendente"), CONCLUIDO("Concluído");

	private String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
