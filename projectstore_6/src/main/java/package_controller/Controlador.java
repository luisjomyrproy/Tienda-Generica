package package_controller;

// IMPORTACION DE ELEMENTOS
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import package_dao.dao_customer;
// IMPORTACION DE MODELOS
import package_modelo.modelo_user;
import package_modelo.modelo_supplier;
// IMPORTACION DE DAO PROCESOS
import package_dao.dao_user;
import package_dao.dao_supplier;
import package_modelo.modelo_customer;

// CUERPO DE PROCESOS
public class Controlador extends HttpServlet {

    // IMPORTACION DE MODELOS
    modelo_user usuario = new modelo_user();
    modelo_supplier proveedor = new modelo_supplier();
    modelo_customer cliente = new modelo_customer();

    // IMPORTACION DE DAO
    dao_user usuarioDao = new dao_user();
    dao_supplier proveedorDao = new dao_supplier();
    dao_customer clienteDao = new dao_customer();

    // CRUD DE USUARIO
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DEFINICION DE ACCIONES PARA LA BARRA DE MENU
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");
        String mensaje = "";

        // ESTE ELEMENTO DEFINE LLA SUB-PANTALLA DE ACCIONES, CORRESPONDE A LAS ACCIONES DE LA BARRA DE MENU
        // REALIZA ACCION DE ENTRADA DE VISTA usuario.jsp
        if (menu.equals("Usuarios")) {
            switch (accion) {

                case "Listar":
                    String tipos[] = {"Administrador", "Cliente"};
                    request.setAttribute("usuarios", usuarioDao.getUsuarios());
                    request.setAttribute("tipos", tipos);
                    request.setAttribute("usuarioEdit", new modelo_user());
                    break;

                case "Buscar":
                    int CedulaB = Integer.valueOf(request.getParameter("txtId"));
                    modelo_user busu = new modelo_user();
                    String[] categoriasb = {"Administrador", "Cliente"};
                    busu = usuarioDao.getUsuarioCedula(CedulaB);
                    request.setAttribute("usuarioEdit", busu);
                    request.setAttribute("categorias", categoriasb);
                    request.setAttribute("cedula", busu.getCedulaUsuario()); // **** LUIS, ESTE ELEMENTO ES NUEVO
                    break;

                case "Agregar":
                    int CdUsuario = Integer.parseInt(request.getParameter("txtId"));
                    String clave = request.getParameter("txtClave");
                    String nombreUsuario = request.getParameter("txtNombre");
                    String correo = request.getParameter("txtCorreo");
                    String tipoUsuario = request.getParameter("txtTipo");
                    usuario.setCedulaUsuario(CdUsuario);
                    usuario.setClave(clave);
                    usuario.setCorreo(correo);
                    usuario.setNombreUsuario(nombreUsuario);
                    usuario.setTipoUsuario(tipoUsuario);
                    boolean creado = usuarioDao.agregarUsuario(usuario);
                    if (creado) {
                        mensaje = "Usuario Creado";
                    } else {
                        mensaje = "Faltan Datos del Usuario";
                    }
                    request.setAttribute("mensaje", mensaje);
                    request.getRequestDispatcher("Controlador?menu=Usuarios&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    int CdU = Integer.valueOf(request.getParameter("id"));
                    modelo_user usu = new modelo_user();
                    String[] categorias = {"Administrador", "Cliente"};
                    usu = usuarioDao.getUsuarioCedula(CdU);
                    request.setAttribute("usuarioEdit", usu);
                    request.setAttribute("categorias", categorias);
                    break;

                case "Actualizar":
                    int cdUsuarioa = Integer.parseInt(request.getParameter("txtId"));
                    String clavea = request.getParameter("txtClave");
                    String nombreUsuarioa = request.getParameter("txtNombre");
                    String correoa = request.getParameter("txtCorreo");
                    String tipoUsuarioa = request.getParameter("txtTipo");
                    usuario.setCedulaUsuario(cdUsuarioa);
                    usuario.setClave(clavea);
                    usuario.setCorreo(correoa);
                    usuario.setNombreUsuario(nombreUsuarioa);
                    usuario.setTipoUsuario(tipoUsuarioa);
                    usuarioDao.actualizarUsuario(usuario);
                    boolean editado = usuarioDao.actualizarUsuario(usuario);
                    if (editado) {
                        mensaje = "Usuario Actualizado";
                    } else {
                        mensaje = "Faltan datos";
                    }
                    request.setAttribute("mensaje: ", mensaje);
                    request.getRequestDispatcher("Controlador?menu=Usuarios&accion=Listar").forward(request, response);
                    break;

                case "Eliminar":
                    int cdUsuarioa2 = Integer.valueOf(request.getParameter("id"));
                    usuarioDao.eliminarUsuario(cdUsuarioa2);
                    request.getRequestDispatcher("Controlador?menu=Usuarios&accion=Listar").forward(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("jsp/usuarios.jsp").forward(request, response);
        } // if (menu.equals("Usuarios")) {}
        // REALIZA ACCION DE ENTRADA DE VISTA proveedor.jsp
        else if (menu.equals("Proveedores")) {
            int nit;
            String nombre_proveedor;
            String direccion_proveedor;
            int telefono_proveedor;
            String ciudad_proveedor;

            switch (accion) {

                case "Listar":
                    request.setAttribute("proveedor", proveedorDao.getProveedor());
                    request.setAttribute("proveedorEdit", new modelo_supplier());
                    break;

                case "Buscar":
                    modelo_supplier pro = new modelo_supplier();

                    nit = Integer.valueOf(request.getParameter("txtIdp"));
                    pro = proveedorDao.getProveedorId(nit);
                    request.setAttribute("proveedorEdit", pro);
                    request.setAttribute("nit", pro.getSupplierNit());
                    break;

                case "Agregar":

                    // IMORTANTE, ESTE CAMBIO CAMBIA DRASTICAMENTE, SE DEBERA GUARDAR EL ORDEN SEGUN LA TABLA DE BASE DE DATOS
                    // REVISAR LA DEFINICION DE TIPOS DE VARIABLES
                    // TENER EN CUENTA LOS NOMBRES DE LAS ENTRADAS SI ES QUE TAMBIEN SE CAMIA LOS BONOTES EN EL HTML O VISTA PROVEEDORES
                    nit = Integer.parseInt(request.getParameter("txtIdp"));
                    nombre_proveedor = request.getParameter("txtNombrep");
                    direccion_proveedor = request.getParameter("txtDireccionp");
                    telefono_proveedor = Integer.parseInt(request.getParameter("txtTelefonop"));
                    ciudad_proveedor = request.getParameter("txtCiudadp");
                    proveedor.setSupplierNit(nit);
                    proveedor.setSupplierName(nombre_proveedor);
                    proveedor.setSupplierAddress(direccion_proveedor);
                    proveedor.setSupplierPhone(telefono_proveedor);
                    proveedor.setSupplierCity(ciudad_proveedor);
                    boolean creado = proveedorDao.agregarProveedor(proveedor);
                    if (creado) {
                        mensaje = "Usuario Creado";
                    } else {
                        mensaje = "Faltan Datos del Proveedor";
                    }
                    request.setAttribute("mensaje", mensaje);
                    request.getRequestDispatcher("Controlador?menu=Proveedores&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    nit = Integer.valueOf(request.getParameter("txtIdp"));
                    request.setAttribute("proveedorEdit", proveedorDao.getProveedorId(nit));
                    break;

                case "Actualizar":
                    nit = Integer.parseInt(request.getParameter("txtIdp"));
                    nombre_proveedor = request.getParameter("txtNombrep");
                    direccion_proveedor = request.getParameter("txtDireccionp");
                    telefono_proveedor = Integer.parseInt(request.getParameter("txtTelefonop"));
                    ciudad_proveedor = request.getParameter("txtCiudadp");
                    proveedor.setSupplierNit(nit);
                    proveedor.setSupplierName(nombre_proveedor);
                    proveedor.setSupplierAddress(direccion_proveedor);
                    proveedor.setSupplierPhone(telefono_proveedor);
                    proveedor.setSupplierCity(ciudad_proveedor);
                    proveedorDao.actualizarProveedor(proveedor);
                    request.getRequestDispatcher("Controlador?menu=Proveedores&accion=Listar").forward(request, response);
                    break;

                case "Eliminar":
                    nit = Integer.valueOf(request.getParameter("txtIdp"));
                    proveedorDao.eliminarProveedor(nit);
                    request.getRequestDispatcher("Controlador?menu=Proveedores&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("jsp/proveedor.jsp").forward(request, response);
        } // else if (menu.equals("Proveedor")) {}
        
        
        // REALIZA ACCION DE ENTRADA DE VISTA clientes.jsp
        else if (menu.equals("Clientes")) {
            int cedula_cliente;
            String nombre_cliente;
            String direccion_cliente;
            int telefono_cliente;
            String email_cliente;

            switch (accion) {

                case "Listar":
                    request.setAttribute("cliente", clienteDao.getCliente());
                    request.setAttribute("clienteEdit", new modelo_customer());
                    break;

                case "Buscar":
                    modelo_customer cli = new modelo_customer();
                    cedula_cliente = Integer.valueOf(request.getParameter("txtIdc"));
                    cli = clienteDao.getClienteId(cedula_cliente);
                    request.setAttribute("clienteEdit", cli);
                    request.setAttribute("cedula_cliente", cli.getCustomerId());
                    break;

                case "Agregar":

                    // IMORTANTE, ESTE CAMBIO CAMBIA DRASTICAMENTE, SE DEBERA GUARDAR EL ORDEN SEGUN LA TABLA DE BASE DE DATOS
                    // REVISAR LA DEFINICION DE TIPOS DE VARIABLES
                    // TENER EN CUENTA LOS NOMBRES DE LAS ENTRADAS SI ES QUE TAMBIEN SE CAMIA LOS BONOTES EN EL HTML O VISTA PROVEEDORES
                    cedula_cliente = Integer.parseInt(request.getParameter("txtIdc"));
                    nombre_cliente = request.getParameter("txtNombrec");
                    direccion_cliente = request.getParameter("txtDireccionc");
                    telefono_cliente = Integer.parseInt(request.getParameter("txtTelefonoc"));
                    email_cliente = request.getParameter("txtEmailc");
                    cliente.setCustomerId(cedula_cliente);
                    cliente.setCustomerNameFull(nombre_cliente);
                    cliente.setCustomerAddress(direccion_cliente);
                    cliente.setCustomerPhone(telefono_cliente);
                    cliente.setCustomerEmail(email_cliente);
                    boolean creado = clienteDao.agregarCliente(cliente);
                    if (creado) {
                        mensaje = "Cliente Creado";
                    } else {
                        mensaje = "Faltan Datos del Cliente";
                    }
                    request.setAttribute("mensaje", mensaje);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    cedula_cliente = Integer.valueOf(request.getParameter("txtIdc"));
                    request.setAttribute("clienteEdit", clienteDao.getClienteId(cedula_cliente));
                    break;

                case "Actualizar":
                    cedula_cliente = Integer.parseInt(request.getParameter("txtIdc"));
                    nombre_cliente = request.getParameter("txtNombrec");
                    direccion_cliente = request.getParameter("txtDireccionc");
                    telefono_cliente = Integer.parseInt(request.getParameter("txtTelefonoc"));
                    email_cliente = request.getParameter("txtEmailc");
                    cliente.setCustomerId(cedula_cliente);
                    cliente.setCustomerNameFull(nombre_cliente);
                    cliente.setCustomerAddress(direccion_cliente);
                    cliente.setCustomerPhone(telefono_cliente);
                    cliente.setCustomerEmail(email_cliente);
                    clienteDao.actualizarCliente(cliente);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;

                case "Eliminar":
                    cedula_cliente = Integer.valueOf(request.getParameter("txtIdc"));
                    clienteDao.eliminarCliente(cedula_cliente);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            } // switch (accion){}
            request.getRequestDispatcher("jsp/cliente.jsp").forward(request, response);
        } // else if (menu.equals("clientes")) {}
        
        // RALIZAR ACCION A VISTA VireSles.jsp
        else if (menu.equals("AccionVentas")) {
            System.out.println("\n\n>> >> >> CONTROLADOR / ACCION VENTAS");
            int BuscarCedulaCliente;
            
            switch (accion) {

                case "BuscarCliente":
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / INICIO");
                    BuscarCedulaCliente = Integer.parseInt(request.getParameter("InputVentaCedula"));
                    modelo_customer ModeloClientesVentas = new modelo_customer();
                    ModeloClientesVentas.setCustomerId(BuscarCedulaCliente);
                    System.out.println("\n>> >> >> CONTROLADOR / CLIENTE: "+ModeloClientesVentas.toString());
                    request.setAttribute("clienteFactura", BuscarCedulaCliente);
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / FIN");
                    break;

                case "Listar":
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / INICIO");

                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / FIN");
                    break;

                case "Agregar":
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / AGREGAR / INICIO");

                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / AGREGAR / FIN");
                    break;

                case "Editar":
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / EDITAR / INICIO");

                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / EDITAR / FIN");
                    break;

                case "Eliminar":
                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / INICIO");

                    System.out.println("\n>> >> >> CONTROLADOR / ACCION VENTAS / LISTA / FIN");
                    break;

                default:
                    throw new AssertionError();
            } // switch (accion){}
            request.getRequestDispatcher("jsp/ViewSales.jsp").forward(request, response);
        } // else if (menu.equals("AccionVentas")) {}

    } // protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
