package br.com.mecnetcastro.financas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import br.com.mecnetcastro.financas.model.enumeration.Cargo;

@Entity
@Audited
@AuditTable(value = "USUARIO_AUD")
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotBlank(message = "Nome obrigatório")
	@Length(min = 3, max = 45, message = "O nome deve ter entre {min} e {max} caracteres")
	@Column(name = "NOME", length = 45, nullable = false)
	private String nome;

	@NotEmpty
	@Email
	@Length(max = 45, message = "O e-mail deve ter no máximo {max} caracteres")
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;

	@NotBlank(message = "Login obrigatório")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Informe um login valido!")
	@Column(name = "LOGIN", length = 50, nullable = false)
	private String login;

	@NotBlank(message = "Senha obrigatório")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Informe uma senha valida!")
	@Column(name = "SENHA", length = 200, nullable = false)
	private String senha;

	@NotNull(message = "Cargo obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "ID_CARGO", length = 15, nullable = false)
	private Cargo cargo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + "]";
	}

}
