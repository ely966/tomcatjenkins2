package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Ped_Prod_Cantida;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.Ped_Prod_CantidaRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.Ped_Prod_CantidaService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	private Ped_Prod_CantidaRepository repositorioPedProd;
	@Autowired
	private Ped_Prod_CantidaService repositorioServicePP;
	
	@Autowired
	private UsuarioService userServi;
	
	@Autowired
	private ProductoService serviProd;
	
	@Autowired
	private ProductoRepository repositorioProducto;
	
	@Autowired
	//private PedidoService servipedi;
	private PedidoRepository repositorioPedido;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PedidoService serviPedi;
	
	private int inicio=0;
	
//**Inicio**//
	//**Creamos unu usuario que se rellena con lsod atos del formulario que se escribira en el formulario de la prtada**//
	@GetMapping({"/","/portada"})
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		//serviPedi.init();
		return "portada";
	}
	
	//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/"})
	public String loginPost(Model model) {
		model.addAttribute("usuario", new Usuario());
		//servicioProducto.init();
		//serviPedi.init();
		return "portada";
	}
	
//**Comprueba Usario**//
	//**Ha llegado los datos del usuario del formulario de la portada,**//
	//**Ahora comprobamos que el usuario, aver si existe**//
	//**Desde el formulario me llega un objeto de tipo usuario, recogemos el usuario y comprobamos que exista//
	@GetMapping({"/usuario/comprobar"})
	public String comprobarUser (@Valid @ModelAttribute("usuario") Usuario usuario,
		BindingResult bindingResult) {
		//**Si hay algun error, redirija a la portada**//
		if(bindingResult.hasErrors()) { 
			//response.sendRedirect("/");
			return "portada";
		}
		//**Aqui comprobamos que el nombre de usuario exista. **//
		if(userServi.findByUserName(usuario.getUserName()) != null) {//**Si entra es que coincide el username, asi que es el usuario**//
			//**Si todo bien, recogemos el usuario que coincida el username(el cual es unico))**//
			Usuario usuarioExtraidoPorUsuario=userServi.findByUserName(usuario.getUserName());
			//**Si existe ,pero antes compronbamos si su contraseña coincida**//
			if(usuario.getPass().equals(usuarioExtraidoPorUsuario.getPass())) {//**Si entra aqui es que el usaurio y la apss es correcta**//
				
				//**Esto es para que se inicie el init solo la primera vez**//
				if(inicio == 0) {
					serviPedi.init();
					inicio=inicio+1;
					}
				//**añadimo el Nickname/nombreUser lo añadimos en la session**//
				String usuarioDelUser=usuario.getUserName();
				session.setAttribute("userSesion",usuarioDelUser);
				//**Ahora nos dirigimos a la pantalla session**//
				//**le daremos la opcion de que muetsre las opciones de crear pedido o mostrar el listado**//
				return "seleccion";	 
			}
		}
	return "portada";
	}
	
	//**Su version post para evitar el pantallazo error, si entra sin usuario, y como sera modo get , evitamos pantallazo**//
	@PostMapping({"/usuario/comprobar"})
	public String comprobarUser2(Model model) {
		//servicioProducto.init();
		return "portada";
	}
		//**Volver desde lista de pedido a seleccion**//
	@GetMapping({"/seleccion"})
	public String volverSelec (Model model) {
		if(session.getAttribute("userSesion") != null) {
			return "seleccion";
		}else {
			return "/portada";
		}
		}
		
//**------------------------------------------------------------------------------------------------------------**//
	
	
	
	//**--Desde Seleccion:--**//
		
//**--Desde Seleccion: Listar pedidos del usuario--**//
	@GetMapping({"/servicio/listarporUser"})
	public String listarPedPorUser (Model model, ServletResponse res) throws IOException {
			//**Recogemos la respuesta para comprobar, si no existe el usuario o session, redirijiremos a la portada**//
		HttpServletResponse response = (HttpServletResponse) res;
					
	//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
			//**Recogemos el username de la session**//
			String userName = (String)session.getAttribute("userSesion");
		//**Desde el username recojo el usuario**//
			Usuario userr=userServi.findByUserName(userName);
			model.addAttribute("listaPedido", userr.getPedidos());
			return "listaPedidos";
		}
	}
	//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/servicio/listarporUser"})
	public String listarPedPorUser2 (Model model) {
		return "portada";
		}
//**Fin listado de pedidos**//

	
	
	
	
	
	
	
//**----------------------------------------------------------------------------------------------**//
		
//**----------------Seleccionar el pedido a editar-------------------------------**//
//**Editar pedido**//
	//**Una vez seleccionado el pedido de la lista de pedidos, se mostrara la informacion del pedido y permitir editar cantidades y direccion**//
	@GetMapping({"/pedido/edit/{id}"})
	public String recogerPedidoParaEditar(@PathVariable int id,Model model,ServletResponse res) throws IOException {
		//**Recogemos la respuesta para comprobar, si no existe el usuario o session, redirijiremos a la portada**//
		HttpServletResponse response = (HttpServletResponse) res;
		//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
			//**Si el usuario existe, recogeremos el usuario de la session**//
			String userName = (String)session.getAttribute("userSesion");
			//**Buscaremos el pedido por su id**//
			Pedido pedConcreto= serviPedi.findById(id);
			model.addAttribute("pedido", pedConcreto);
			
			List<Ped_Prod_Cantida>listaUniondelpedido=new ArrayList();
			List<Ped_Prod_Cantida>listaUnion=repositorioServicePP.findAll();
		
			 
			//**Recogemos las unicones del pedido**//
			for (int i=0; i< listaUnion.size();i++) {
				if(listaUnion.get(i).getPedido().getId() ==pedConcreto.getId() ) {
					listaUniondelpedido.add(listaUnion.get(i));
				}
			}
			
			//**crear una lista con producto y otra lista con las  cantidad**//
			List<Producto> productos=new ArrayList<>();
			List<Integer> cantidades=new ArrayList<>();
			//**De cada unionPedidoProducto sacamos lso productos y lo guardamos en una lista de productos y por otro lado recogemso la cantidad y añadida en la lista de cabtidades en orden**//
			for (int i=0; i< listaUniondelpedido.size();i++) {
						productos.add(listaUniondelpedido.get(i).getProdutoo());
						cantidades.add(listaUniondelpedido.get(i).getCantidad());
				}
			model.addAttribute("listaProductos",productos);
			model.addAttribute("listaCantidad",cantidades);
			
			return "editPedido";
		}
	}
	
	//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/pedido/edit/{id}"})
	public String editarPedido2 (Model model) {
		return "portada";
				}
	
//**Segunda aprte de editar**//
		//**Editar pedidos**//
	@PostMapping({"/pedido/edit/submit"})
	public String editarPedidoNuevo(@RequestParam(required=false,name=("cantidad"))Integer[] listaCantidad,@Valid @ModelAttribute("pedido") Pedido pedido , @RequestParam(required=false,name=("id"))Integer idd,Model model, BindingResult result) throws IOException   {
		//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {//**SI no existe**//
			return "portada";
		}else {
			//**Si el usuario y session existe**//
			//**comprobamos que no haya error**//
			if(result.hasErrors()) {
				return "redirect:/servicio/seleccion";
			}
			
			//**EN esta lista añadimos el catalogo**//
			List<Producto> productosListado=repositorioProducto.findAll();
			//**Lista de todas las Uniones de productos y pedidos **//
			List<Ped_Prod_Cantida> pedidosProductos= repositorioServicePP.findAll();
			//**Recorremos todas las uniones ProductosPedidos **//
			for (int a=0; a< pedidosProductos.size();a++) {
				//*creamos uno que ira recogiendo la union**//
				Ped_Prod_Cantida pedidoProductoUnion=pedidosProductos.get(a);//**La union**//
				//**Recorremos añadiendo cada producto del catalogo **//
				for (int i=0; i< productosListado.size();i++) {
					//*Si coincide el pedido con el editado y el producto**//
					if(pedidosProductos.get(a).getPedido().getId() ==pedido.getId() && pedidosProductos.get(a).getProdutoo().getId()==productosListado.get(i).getId()) {
						//**Modificamos la cantidad**//
						pedidoProductoUnion.setCantidad(listaCantidad[i]);
						//**Agregamos la nueva union con la nueva cantidad**//
						repositorioServicePP.edit(pedidoProductoUnion);
					}
				}	
			}		
			//**Recogeremos el usuario de la session**//
			String user = (String)session.getAttribute("userSesion");
			Usuario usuario =userServi.findByUserName(user);
			//**Agregamos los datos al pedido**//
			pedido.setUser(usuario);
			//**En esta edicion borraremos el antiguo, y añadiremos el mismo pero con los dtaos cambiado**//
			serviPedi.edit(pedido);
			//**Redirige a la lista de usuario**//
			return "redirect:/servicio/listarporUser";
			}
		}
	//**Tiene su get para corregir errores, es decir, si la session esta vacia , para corregurla necesitamos su version get, para que no salte error y podamos redirigirlo**//
	@GetMapping({"/pedido/edit/submit"})
	public String editarPedidoNuevo(Model model, ServletRequest req,ServletResponse res) throws IOException   {
		//**Recogemos la respuesta. tambien recogi la peticion request por si acaso.
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//comprobamos si la session existe. Si no existe
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
		return "seleccion";
		}
	}
	
		
		
//**Borrar pedido**//
		
		
		//**Borrar pedido**//
	@GetMapping({"/pedido/deletePedido/{id}"})
	public String recogerPedidoParaBorrar(@PathVariable long id,Model model,ServletResponse res) throws IOException {
		//**Recogemos la respuesta para comprobar, si no existe el usuario o session, redirijiremos a la portada**//
		HttpServletResponse response = (HttpServletResponse) res;
		//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
			//**Buscaremos el pedido por su id**//
			Pedido pedConcreto= serviPedi.findById(id);
			//**Lista de uniones**//
			List<Ped_Prod_Cantida>listaUnion=repositorioServicePP.findAll();
			//**Recogemos las uniones del pedido**//
			for (int i=0; i< listaUnion.size();i++) {
				//**SI la union tiene tiene el pedido seleccionado en la pagina annterior loe liminamos***//
				if(listaUnion.get(i).getPedido().getId() ==pedConcreto.getId() ) {
					repositorioPedProd.delete(listaUnion.get(i));
				}
			}
			//**Una vez eliminada las uniones**//
			//**Eliminamos el pedido**//
			repositorioPedido.deleteById(id);
			
			return "seleccion";
		}
	}
	
	//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/pedido/deletePedido/{id}"})
	public String recogerPedidoParaBorrar2 (Model model) {
		return "portada";
				}	
						
						
						
//**-----------------------------------------------------------------------------------------------------**//
//**---------------Desde Seleccion:----------------------------------**//
		
//**------------Catalogo------------------**//
	//**Si se desea crear un pedido, tendra primero que ver el catalogo**//
	//**Para ello mostraeros la lista de productos**//
	@GetMapping({"/servicio/mostrarCatalogo"})
	public String mostrarCatalogo (Model model,ServletResponse res) throws IOException {
		//**Recogemos la respuesta para comprobar, si no existe el usuario o session, redirijiremos a la portada**//
		HttpServletResponse response = (HttpServletResponse) res;
		//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
		    return null;
		}else {
			//**Si el usuario existe**//
			//**creamos un nuevo producto**//
			Producto productoo= new Producto();
			model.addAttribute("catalogo", repositorioProducto.findAll());
			model.addAttribute("productoo", productoo );
			return "catalogo";
			}
		}
		
	//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/servicio/mostrarCatalogo"})
	public String mostrarCatalogo2 (Model model) {
		//servicioProducto.init();
		return "portada";
		}
			
//**---------------------------------------------------------**//
		
//**Añadir pedido**//
		
//**Añadir un nuevo pedido1. Primera parte que viene del catalogo**//
//**Verison get por si intenta entrar sin usuario ni peticion post**//
	@GetMapping({"/pedido/add/subm"})
	public String addNew(Model model, ServletRequest req, ServletResponse res) throws IOException {
		//**Recogemos la respuesta. tambien recogi la peticion request por si acaso.
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//**comprobamos si la session existe. Si no existe**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
			}else {
				//**Si todo esta correcto, iremos a comprobar el pedido**//
				model.addAttribute("pedido", new Pedido());
				return "comprobarPedido";
			}
		}
		
//**Siguiente parte de añadir un nuevo pedido**//
		
	@PostMapping({"/pedido/add/subm"})
	public String addNew( @RequestParam(required=false,name=("cantidad"))Integer[] listaCantidad , Model model, ServletRequest req, ServletResponse res) {
	//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {//**SI no existe**//
			return "portada";
		}else {
			//**Si el usuario y session existe**//
			//**EN esta lista añadimos el catalogo**//
			List<Producto> productosListado=repositorioProducto.findAll();
			Producto productoParaPedido=null;
			//**Creamos el nuevo pedido**//
			Pedido pedido=new Pedido();

			//**Creamos una lista de productos y una de cantidades, que servira para la siguiente pagina**//
			List<Producto> productos=new ArrayList<>();
			List<Integer> cantidades=new ArrayList<>();
			//**Precio total de todo el pedido se recogera en esta variable**//
			int precioTotal=0;
			//**crear la union de cada producto con su pedido**//
			List<Ped_Prod_Cantida> listaProdYCantidad= new ArrayList();
			for (int i=0; i< productosListado.size();i++) {
				productoParaPedido=productosListado.get(i);//**Recogemos el producto que toca en orden**//
				Ped_Prod_Cantida pedPro= new Ped_Prod_Cantida(pedido,productosListado.get(i),listaCantidad[i]);//**Creamos union de pedido y producto con su cantidad**//
				listaProdYCantidad.add(pedPro);
				//**Añadimos los productos a una lista de producto**//
				productos.add(listaProdYCantidad.get(i).getProdutoo());
				//**Añadimos las cantidades a una lista de cantidades**//
				cantidades.add(listaProdYCantidad.get(i).getCantidad());
				}
			//**Recogeremos el usuario de la session**//
			String user = (String)session.getAttribute("userSesion");
			Usuario usuario =userServi.findByUserName(user);
			//**Agregamos los datos al pedido**//
			pedido.setUser(usuario);
			pedido.setDireccion(usuario.getDireccion());
			precioTotal=repositorioServicePP.precioTotal(listaProdYCantidad,productosListado);
			//**Recogemos los datos para mandarlo la siguiente aprte que es la comprobacion del pedido antes de guardarlo**//
			model.addAttribute("pedidoo",pedido);
			model.addAttribute("listaProductos",productos);
			model.addAttribute("listaCantidad",cantidades);
			model.addAttribute("precioTotal",precioTotal);
			return "comprobarPedido";
			}
		}

	//**Una vez comprobado y confirmado el pedido  lo aadiremso ene l repositorio **""
	@PostMapping({"/pedido/addList"})
	public String addNewAlaListapedidos(@RequestParam(required=false,name=("cantidad"))Integer[] listaCantidad ,@Valid @ModelAttribute("pedido") Pedido pedido, BindingResult binding, ServletRequest req, ServletResponse res) {
		//**Recogemos la respuesta. tambien recogi la peticion request por si acaso.
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//**comprobamos si la session existe. Si no existe**//
		if(session.getAttribute("userSesion") == null) {  //**Sino existe**//
			return "redirect:/servicio/seleccion";
		}else {
			Integer[] listDeCantidades =listaCantidad;//**Primero creamos una lista de integer, donde recogeremos al lista de integer recogida de las cantidades seleccionada del formulario**//
			List<Producto> productosListado=repositorioProducto.findAll();//**EN esta lista añadimos el catalogo**//					
			String user = (String)session.getAttribute("userSesion");//**Recojo el usuaario**//
			Usuario usuario =userServi.findByUserName(user);
			pedido.setUser(usuario);
			//**Si la direccion esta vacia**//
			if (pedido.getDireccion() ==null) {pedido.setDireccion(usuario.getDireccion());}
				serviPedi.add(pedido);//**Guardo el nuevo pedido**//
			//**Recojo lso productos**//
			List<Pedido> li=serviPedi.findAll();
			//**Creo uniones de cada producto con el pedido**Recojo el ultimo pedido añadiendo en el repositorio de pedidos, que es el ultimo añadido**//
			//**Porque le cambia el id al añadir el pedido**//
			Pedido pedidonuevo=li.get(li.size()-1);
			//**Recorremos los productos**//
			for (int i=0; i< productosListado.size();i++) {
				Producto p= productosListado.get(i);
				int cantida=listaCantidad[i];
				//**Recojo cada union añadiendole el pedido ,producto,y cantdiad del producto**//
				Ped_Prod_Cantida pedPro= new Ped_Prod_Cantida(pedidonuevo,p,cantida);
				repositorioServicePP.add(pedPro);//**Lo vamos guardando en el repositorio**//
			}
		return "redirect:/servicio/seleccion";
		}
	}
	
//**Verison get por si intenta entrar sin usuario ni peticion post**//
	@GetMapping({"/pedido/addList"})
	public String addPedidoAListaPedidosGet(Model model, ServletRequest req, ServletResponse res) throws IOException {
		//**Recogemos la respuesta. tambien recogi la peticion request por si acaso.
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//**comprobamos si la session existe. Si no existe**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
			//**Si todo esta correcto, iremos a comprobar el pedido**//
			model.addAttribute("pedido", new Pedido());
			return "comprobarPedido";
		}
	}
				
				
				
//**-------------Dirigir a la seleccion------------------**//
 //**Desde seleccion se dirige a crear pedido. Mostrar productos.**//
	@GetMapping({"/servicio/seleccion"})
	public String seleccion (Model model,ServletResponse res) throws IOException {
		//**Recogemos la respuesta para comprobar, si no existe el usuario o session, redirijiremos a la portada**//
		HttpServletResponse response = (HttpServletResponse) res;
		//**Comprobamos que la session y el usuario exista**//
		if(session.getAttribute("userSesion") == null) {
			response.sendRedirect("/");
			return null;
		}else {
			//**Si el usuario existe, redirige a la seleccion**//
			return "seleccion";
			}
		}
//**Su verison post para evitar el pantallazo error**//
	@PostMapping({"/servicio/seleccion"})
	public String seleccion2 (Model model) {
		return "portada";
	}
		
	
}

