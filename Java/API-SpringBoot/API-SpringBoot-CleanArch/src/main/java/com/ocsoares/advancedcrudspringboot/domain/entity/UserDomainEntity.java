package com.ocsoares.advancedcrudspringboot.domain.entity;

// Essa "UserDomainEntity" vai ser usado pela APLICAÇÃO, ou seja, pelas REGRAS de NEGÓCIO, e NÃO pelo
// BANCO de DADOS para mapear a Tabela como id, nome e etc!!!
public record UserDomainEntity(String name, String email, String password) {
}