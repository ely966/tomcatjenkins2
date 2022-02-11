package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;

public interface PedidoService {
	public Pedido add (Pedido ped);
	public List<Pedido> findAll();
	public Pedido findById(long id);
	public Pedido edit (Pedido ped);
	public List<Pedido> findByUsuario(Usuario user);
	public void init();
	public void delete(long id);
	

	
}