package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ped_Prod_Cantida;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.Ped_Prod_CantidaRepository;
import com.example.demo.repository.PedidoRepository;

@Service("Ped_Prod_CantidaServiceMemory")
public class Ped_Prod_CantidaServiceMemory implements Ped_Prod_CantidaService{
	
	private List<Ped_Prod_Cantida> repositorioServicePP=new ArrayList<>();

	

	
	@Autowired
	private PedidoRepository repositorioPedido;
	@Autowired
	private ProductoService serviProd;
	@Autowired
	private PedidoService serviPedi;
	
	
	
	
	
	
	@Override
	public Ped_Prod_Cantida add(Ped_Prod_Cantida pedidoProd) {
		repositorioServicePP.add(pedidoProd);
		 return pedidoProd;
	}
	@Override
	public Ped_Prod_Cantida findById(long id) {
		Ped_Prod_Cantida proPed=null;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < repositorioServicePP.size()) {
			if (repositorioServicePP.get(i).getId() == id) {
				encontrado = true;
				proPed=repositorioServicePP.get(i);
			} else {
				i++;
			}
		}
		return proPed;
	}
	@Override
	public List<Ped_Prod_Cantida> findAll(){
		
		return repositorioServicePP;
	}
	@Override
	public Ped_Prod_Cantida edit(Ped_Prod_Cantida pedidoProd) {
		Ped_Prod_Cantida proPed=null;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < repositorioServicePP.size()) {
			if (repositorioServicePP.get(i).getId() == pedidoProd.getId()) {
				encontrado = true;
				repositorioServicePP.remove(i);
				repositorioServicePP.add(i,pedidoProd);
			} else {
				i++;
			}
		}
		 repositorioServicePP.add(pedidoProd);
		 return pedidoProd;
	}
	
	@Override
	public void init(List<Producto>catalogo,Pedido p1, Pedido p2, Pedido p3) {
		//**Añadir las uniones**//
				for (int i=0; i< catalogo.size(); i++) {
					Producto producto=catalogo.get(i);
					
					//**De cada Producto, lo unimos a cada pedido**//
					Ped_Prod_Cantida pedProd= new Ped_Prod_Cantida(p1,catalogo.get(i),0);
					Ped_Prod_Cantida pedProd1= new Ped_Prod_Cantida(p2,catalogo.get(i),0);
					Ped_Prod_Cantida pedProd2= new Ped_Prod_Cantida(p3,catalogo.get(i),0);
					repositorioServicePP.add(pedProd);
					repositorioServicePP.add(pedProd1);
					repositorioServicePP.add(pedProd2);
					
				}
	}
	
	@Override
	public void delete(long id) {
		Boolean encontrado =false;
		int i=0;
		while ( i<repositorioServicePP.size() && !encontrado) {
			if(repositorioServicePP.get(i).getId() == id) {
				repositorioServicePP.remove(i); 
				encontrado =true;
			}
			else {
				i=i+1;
			}
		}
	}

	public int precioTotal(List<Ped_Prod_Cantida> listaProdYCantidad, List<Producto> productosListado) {
		//**Precio total de todo el pedido se recogera en esta variable**//
		int precioTotal=0;
		//**Precio de cada cantidad de cada producto**//
		int precioProducto=0;
		Producto productoParaPedido=null;
		for (int i=0; i< productosListado.size();i++) {
			productoParaPedido=productosListado.get(i);//**Recogemos el producto que toca en orden**//
			//**Multiplicamos el precio dl producto por la cantidad seleccionada**//
			precioProducto=productoParaPedido.getPrecio()*listaProdYCantidad.get(i).getCantidad();
			//**Lo vamos sumando al precio total**//
			precioTotal=precioTotal+precioProducto;
			}
		return precioTotal;
	}
	
}