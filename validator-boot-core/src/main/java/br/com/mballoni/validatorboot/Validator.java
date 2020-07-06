package br.com.mballoni.validatorboot;

public interface Validator {
  <T> void validate(T entity);

  <T> void validate(T entity, Class<?>... groups);
}
