package br.com.mecnetcastro.financas.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("financasPU");

	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
