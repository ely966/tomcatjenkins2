package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//**strategy=GenerationType.AUTO**//
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="usuario_id")
	private Usuario user;
	private String direccion;
	//@ManyToMany
	//private List<Producto> listaProductos;
	@OneToMany(mappedBy="pedido")
	private List<Ped_Prod_Cantida>listaProductosPed;
	
	public Pedido(Usuario user) {
		super();
		
		this.user = user;
		this.listaProductosPed = new ArrayList<>();
		this.direccion=direccion;
		
	}

	public Pedido() {
		super();
		
		this.user = user;
		this.direccion=direccion;
		this.listaProductosPed = new ArrayList<>();
		
	}
	public Pedido(Usuario user,String direccion,List<Producto> listaProductosPed) {
		super();
		
		this.user = user;
		this.direccion=direccion;
		this.listaProductosPed = new ArrayList<>();
		
	}
	public Pedido(long id,Usuario user,String direccion,List<Producto> listaProductosPed) {
		super();
		this.id=id;
		this.user = user;
		this.direccion=direccion;
		this.listaProductosPed = new ArrayList<>();
		
	}
	
	
	public Pedido(Usuario user, String direccion) {
		super();
		
		this.user = user;
		this.direccion = direccion;
		this.listaProductosPed = new ArrayList<>();
	}

	

	public long getId() {
		return id;
	}
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	
	
	public void setId(long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	public List<Ped_Prod_Cantida> getListaProductosPed() {
		return listaProductosPed;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductosPed = listaProductosPed;
	}
	
	public void addProductoaLista(Ped_Prod_Cantida produped) {
		listaProductosPed.add(produped);
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
		Pedido other = (Pedido) obj;
		return id== other.id;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", user=" + user + ", listaProducto=" + listaProductosPed + "]";
	}
}

