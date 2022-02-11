package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

@Entity
public class Producto {
	@Id
	@GeneratedValue()
	private long id;
	private String nombre;
	private Integer precio;
	@Min(0) 
	private Integer cantidad;
	//@Column(nullable=false)
	@OneToMany(mappedBy="produtoo", targetEntity = Ped_Prod_Cantida.class )
	private List<Ped_Prod_Cantida>listaPedidop;
	
	
	
	public Producto( String nombre, Integer precio, @Min(0) Integer cantidad) {
		super();
		
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.listaPedidop= new ArrayList<>();
		
	}
	public Producto( String nombre, Integer precio, @Min(0) Integer cantidad,List<Pedido>listaPedido) {
		super();
		
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.listaPedidop= new ArrayList<>();
		
	}

	
	public Producto(long id, String nombre, Integer precio, @Min(0) Integer cantidad) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.listaPedidop= new ArrayList<>();
	}


	public Producto() {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.listaPedidop= new ArrayList<>();
	}

	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	public List<Ped_Prod_Cantida> getListaPedido() {
		return listaPedidop;
	}
	public void setListaPedido(List<Ped_Prod_Cantida> listaPedido) {
		this.listaPedidop = listaPedido;
	}
	public long getId() {
		return id;
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
		Producto other = (Producto) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio +  "]";
	}
	
}

