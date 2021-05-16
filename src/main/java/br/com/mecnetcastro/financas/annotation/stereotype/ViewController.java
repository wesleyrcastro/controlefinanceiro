package br.com.mecnetcastro.financas.annotation.stereotype;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
@Stereotype
@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface ViewController {

}
