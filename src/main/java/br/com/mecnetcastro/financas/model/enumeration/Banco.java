package br.com.mecnetcastro.financas.model.enumeration;

public enum Banco {

	BANCO_DO_BRASIL("Banco do Brasil - 001"), CAIXA_ECONOMICA("Caixa Econômica - 104"), HSBC("HSBC - 399"), BRB(
			"BRB - 070"), ITAU("Itaú - 341"), SANTANDER("Santander - 033"), BRADESCO("Bradesco - 237"), UNICRED("Unicred - 091-4");

	private String descricao;

	Banco(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
