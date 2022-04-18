package com.gvendas.gestaovendas.entidades;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	@Column(name = "preco_custo")
	private BigDecimal precoCusto;
	
	@Column(name = "preco_venda")
	private BigDecimal precoVenda;
	
	@Column(name = "observacao")
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "codigo_categoria" , referencedColumnName = "codigo")
	private Categoria categoria;

}
