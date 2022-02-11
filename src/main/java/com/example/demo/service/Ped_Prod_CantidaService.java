package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Ped_Prod_Cantida;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;


public interface Ped_Prod_CantidaService {

	public void init(List<Producto>catalogo,Pedido p1, Pedido p2, Pedido p3);
	public Ped_Prod_Cantida add(Ped_Prod_Cantida pedidoProd);
	public Ped_Prod_Cantida findById(long id);
	public List<Ped_Prod_Cantida> findAll();
	public Ped_Prod_Cantida edit(Ped_Prod_Cantida pedidoProd);
	//public List<Ped_Prod_Cantida> findByPedido(Pedido ped);
	public void delete(long id);
	//public List<Ped_Prod_Cantida> findForPedido(long idPed);
	public int precioTotal(List<Ped_Prod_Cantida> listaProdYCantidad, List<Producto> productosListado);
}
