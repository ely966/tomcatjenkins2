package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ped_Prod_Cantida;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.Ped_Prod_CantidaRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.UsuarioRepository;

@Primary
@Service("PedidoServiceDB")
public class PedidoServiceDB implements PedidoService{
	
	@Autowired
	private PedidoRepository repositorioPedido;
	@Autowired
	private UsuarioRepository repositorio;
	@Autowired
	private UsuarioService userServi;
	@Autowired
	private ProductoService serviProd;
	
	@Autowired
	private Ped_Prod_CantidaService repositorioServicePP;
	
	@Autowired
	private Ped_Prod_CantidaRepository repositorioPedProd;
	
	@Override
	public Pedido add (Pedido ped) {
		return repositorioPedido.save(ped);
	}
	@Override
	public List<Pedido> findAll(){
		return repositorioPedido.findAll();
	}
	@Override
	public Pedido findById(long id) {
		return repositorioPedido.findById(id).orElse(null);
	}
	@Override
	public Pedido edit (Pedido ped) {
		
		
		return repositorioPedido.save(ped);
	}
	//@Override
	//public List<Pedido> findByUsuario(Usuario user){
	//	List<Pedido> pedidos = null;
		//pedidos.add(repositorioPedido.findByUsuario(user).orElse(null));
	//	return pedidos;
	//}
	@Override
	public void init() {
		serviProd.init();
		
		//**Creamos los usuario**//
		
		Usuario user1=userServi.findByUserName("maria");
		Usuario user2=userServi.findByUserName("luis");
		
		Pedido pedido1=new Pedido(user1, "Calle Mar n.32");
		Pedido pedido2=new Pedido(user2, "Avenida ");
		Pedido pedido3=new Pedido(user1, "Plazoleta Lunar n.3");
		
		//**Guardar los pedidos en el usuario**//
		user1.getPedidos().add(pedido1);
		user1.getPedidos().add(pedido3);
		user2.getPedidos().add(pedido2);
		//**Guardar pdidos en repositorio user**//
		repositorioPedido.save(pedido1);
		
	
		
		List<Producto>catalogo=serviProd. findAll();
		Producto producto = null;
		
		List<Producto>productosDelPedido=serviProd. findAll();
		
		//**Añadimos el usuario modificado**//
		
		pedido1.setUser(user1);
		pedido3.setUser(user1);
		pedido2.setUser(user2);
		//**Añadimos los usuarios**//
		//userServi.add(user1);
		//userServi.add(user2);
		
		repositorio.save(user1);
		repositorio.save(user2);
		
		
		//**Añadimos los pedidos**//
		repositorioPedido.save(pedido1);
		repositorioPedido.save(pedido2);
		repositorioPedido.save(pedido3);
		repositorioServicePP.init(catalogo, pedido1, pedido3, pedido2);
		
	}
	@Override
	public List<Pedido> findByUsuario(Usuario user) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(long id) {
		repositorioPedido.deleteById(id);
	}
	

	
	public Pedido addUserYDIreccion (Pedido pedido,Usuario usuario, String direccion) {
		pedido.setUser(usuario);
		pedido.setDireccion(usuario.getDireccion());
		return pedido;
	}
	
}
