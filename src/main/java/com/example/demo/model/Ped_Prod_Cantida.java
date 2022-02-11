package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Value;
@Entity
public class Ped_Prod_Cantida {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	@JoinColumn(name="producto_id")
	@ManyToOne(targetEntity= Producto.class) 
	private Producto produtoo;
	@Min(value=0)
	private int cantidad;
	
	
	public Ped_Prod_Cantida() {
		super();
	}

	public Ped_Prod_Cantida(long id, @NotEmpty Pedido pedido, @NotEmpty Producto produto, int cantidad) {
		super();
		this.id = id;
		this.pedido = pedido;
		this.produtoo = produto;
		this.cantidad = cantidad;
	}
	
	public Ped_Prod_Cantida(@NotEmpty Pedido pedido, @NotEmpty Producto produto, int cantidad) {
		super();
		
		this.pedido = pedido;
		this.produtoo = produto;
		this.cantidad = cantidad;
	}
	public Ped_Prod_Cantida(@NotEmpty Pedido pedido, @NotEmpty Producto produto) {
		super();
		
		this.pedido = pedido;
		this.produtoo = produto;
		
	}
	
	public Ped_Prod_Cantida(int cantidad) {
		super();
		this.id = id;
		
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProdutoo() {
		return produtoo;
	}

	public void setProdutoo(Producto produtoo) {
		this.produtoo = produtoo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ped_Prod_Cantida other = (Ped_Prod_Cantida) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Ped_Prod_Cantida [id=" + id + ", pedido=" + pedido + ", produto=" + produtoo + ", cantidad=" + cantidad
				+ "]";
	}
	
	
	
}
