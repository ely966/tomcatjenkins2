package com.example.demo.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Usuario {
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long id;
		@NotEmpty
		private String userName;
		@NotEmpty
		private String pass;
		private String nombre;
		private String direccion;
		private Integer telefono;
		//public Pedido listadeProductos;
		@Column(nullable=false)
		@OneToMany(mappedBy="user")
		private List<Pedido>pedidos;
		
		
		public Usuario() {}

		public Usuario(@NotEmpty String userName, @NotEmpty String pass) {
			super();
			this.userName = userName;
			this.pass = pass;
			this.pedidos=new ArrayList<>();
			
		}
		public Usuario(long id,@NotEmpty String userName, @NotEmpty String pass) {
			super();
			this.id=id;
			this.userName = userName;
			this.pass = pass;
			this.pedidos=new ArrayList<>();
		}
		public Usuario(@NotEmpty String userName, @NotEmpty String pass, String nombre,
				 String direccion, Integer telefono) {
			super();
			this.userName = userName;
			this.pass = pass;
			this.nombre = nombre;
			this.direccion = direccion;
			this.telefono=telefono;
			this.pedidos=new ArrayList<>();
		}


		public String getUserName() {
			return userName;
		}

		
		
		public Integer getTelefono() {
			return telefono;
		}

		public void setTelefono(Integer telefono) {
			this.telefono = telefono;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getPass() {
			return pass;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		
		public List<Pedido> getPedidos() {
			return pedidos;
		}

		public void setPedidos(List<Pedido> pedidos) {
			this.pedidos = pedidos;
		}
		

		public long getId() {
			return id;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}


		public String getNombre() {
			return nombre;
		}


		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getDireccion() {
			return direccion;
		}


		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}


		@Override
		public int hashCode() {
			return Objects.hash(userName);
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
			return Objects.equals(userName, other.userName);
		}


		@Override
		public String toString() {
			return "Usuario [userName=" + userName + ", pass=" + pass + ", nombre=" + nombre + ", direccion=" + direccion
					+ "]";
		}
		
		


		
		
		

}
