package com.techlab.main;

import com.techlab.productos.Producto;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.excepciones.ProductoNoEncontradoException;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  private static Scanner scanner = new Scanner(System.in);
  private static ProductoService productoService = new ProductoService();
  private static PedidoService pedidoService = new PedidoService(productoService);

  public static void main(String[] args) {
    // Inicializar datos de prueba
    inicializarDatos();

    boolean salir = false;

    do {

      mostrarMenu();

      try {
        System.out.print("Ingrese su opción: ");
        // Intenta leer un entero. Si ingresa texto, lanza NumberFormatException.
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
          case 1:
            agregarProducto();
            break;
          case 2:
            listarProductos();
            break;
          case 3:
            buscarActualizarProducto();
            break;
          case 4:
            eliminarProducto();
            break;
          case 5:
            crearPedido();
            break;
          case 6:
            listarPedidos();
            break;
          case 7:
            salir = true;
            System.out.println("👋 Gracias por usar el sistema!");
            break;
          default:
            System.out.println("❌ Opcion no válida. Intente de nuevo.");
        }

      } catch (NumberFormatException e) {

        System.err.println("❌ Error de entrada: Ingrese un número valido para la opcion.");

      } catch (Exception e) {

        System.err.println("❌ Ocurrio un error inesperado: " + e.getMessage());

      }

      if (!salir) {
        System.out.println("\n--- Presione Enter para continuar ---");
        scanner.nextLine(); // hacemos una "pausa"
      }

    } while (!salir);

    scanner.close();

  }

  // ==========================================================
  // MÉTODOS DE LA INTERFAZ DE USUARIO
  // ==========================================================

  private static void mostrarMenu() {
    System.out.println("\n========= MENÚ PRINCIPAL =========");
    System.out.println("1. Agregar Producto");
    System.out.println("2. Listar Productos");
    System.out.println("3. Buscar/Actualizar Producto");
    System.out.println("4. Eliminar Producto");
    System.out.println("5. Crear un Pedido");
    System.out.println("6. Listar Pedidos");
    System.out.println("7. Salir");
    System.out.println("==================================");
  }

  // --- Lógica del Menú ---

  private static void inicializarDatos() {
    // Ejemplo de Polimorfismo: La lista de Producto almacena tanto Producto como Bebida
    productoService.agregarProducto(new Producto("Laptop IBM", 1200.0, 5));
    productoService.agregarProducto(new Producto("Laptop HP", 1200.0, 5));
    productoService.agregarProducto(new Producto("Teclado Mecanico", 80.0, 15));
    productoService.agregarProducto(new Producto("Teclado Bluethooth", 120.0, 10));
    productoService.agregarProducto(new Producto("Teclado Inalambrico", 80.0, 15));
    System.out.println("✅ Datos iniciales cargados.");
  }

  private static void agregarProducto() {
    System.out.println("\n--- AGREGAR PRODUCTO ---");

    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();

    try {
      System.out.print("Precio: ");
      double precio = Double.parseDouble(scanner.nextLine());

      System.out.print("Stock: ");
      int stock = Integer.parseInt(scanner.nextLine());

      productoService.agregarProducto(new Producto(nombre, precio, stock));

      System.out.println("✅ Producto agregado con exito.");

    } catch (NumberFormatException e) {

      System.err.println("❌ Error: Precio o Stock deben ser nimeros válidos.");

    }
  }

  private static void listarProductos() {
    System.out.println("\n--- LISTA DE PRODUCTOS ---");
    List<Producto> productos = productoService.listarProductos();
    if (productos.isEmpty()) {
      System.out.println("🛒 No hay productos registrados.");
      return;
    }

    for (Producto p : productos) {
      // Se utiliza el método toString() sobrescrito de Producto
      System.out.println(p);
    }
    System.out.println("--------------------------");
  }

  private static void buscarActualizarProducto() {
    System.out.println("\n--- BUSCAR/ACTUALIZAR PRODUCTO ---");
    try {
      System.out.print("Ingrese el ID del producto a buscar: ");
      int idBusqueda = Integer.parseInt(scanner.nextLine());

      Producto productoEncontrado = productoService.buscarProductoPorId(idBusqueda);
      System.out.println("\n✅ Producto encontrado: " + productoEncontrado);

      System.out.println("\n--- ACTUALIZAR (Deje en blanco para no modificar) ---");
      System.out.print("Nuevo Nombre [" + productoEncontrado.getNombre() + "]: ");
      String nuevoNombre = scanner.nextLine();

      double nuevoPrecio = -1;
      System.out.print("Nuevo Precio [" + productoEncontrado.getPrecio() + "]: ");
      String precioStr = scanner.nextLine();
      if (!precioStr.isEmpty()) {
        nuevoPrecio = Double.parseDouble(precioStr);
      }

      int nuevoStock = -1;
      System.out.print("Nuevo Stock [" + productoEncontrado.getStock() + "]: ");
      String stockStr = scanner.nextLine();
      if (!stockStr.isEmpty()) {
        nuevoStock = Integer.parseInt(stockStr);
      }

      // Llama a la funcion de actualizacion
      productoService.actualizarProducto(idBusqueda, nuevoNombre, nuevoPrecio, nuevoStock);

      System.out.println("✅ Producto actualizado con exito: " + productoEncontrado);

    } catch (NumberFormatException e) {

      System.err.println("❌ Error: El ID, Precio o Stock deben ser numeros validos.");

    } catch (ProductoNoEncontradoException e) {

      // Captura la excepcion personalizada
      System.err.println("❌ " + e.getMessage());

    }
  }

  private static void eliminarProducto() {
    System.out.println("\n--- ELIMINAR PRODUCTO ---");

    try {
      System.out.print("Ingrese el ID del producto a eliminar: ");
      int idEliminar = Integer.parseInt(scanner.nextLine());

      // Reutilizacion de la logica del service y manejo de excepcion personalizada
      productoService.eliminarProducto(idEliminar);

      System.out.println("🗑️ Producto con ID " + idEliminar + " eliminado con exito.");

    } catch (NumberFormatException e) {

      System.err.println("❌ Error: El ID debe ser un número valido.");

    } catch (ProductoNoEncontradoException e) {

      // Captura la excepcion personalizada
      System.err.println("❌ " + e.getMessage());

    }
  }

  private static void crearPedido() {
    System.out.println("\n--- CREAR PEDIDO ---");
    listarProductos();

    List<LineaPedido> lineas = new ArrayList<>();
    int productosEnPedido = 0;

    // Manejo de Excepciones para el número de líneas
    try {
      System.out.print("¿Cuántos productos diferentes va a agregar?: ");
      productosEnPedido = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.err.println("❌ Error: Debe ingresar un número para la cantidad de productos.");
      return;
    }

    for (int i = 0; i < productosEnPedido; i++) {
      try {
        System.out.println("\n--- Línea de Pedido #" + (i + 1) + " ---");
        System.out.print("Ingrese ID del producto: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese Cantidad solicitada: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        // Buscar producto: Lanza ProductoNoEncontradoException
        Producto p = productoService.buscarProductoPorId(id);

        // Validación basica de cantidad
        if (cantidad <= 0) {

          System.out.println("⚠️ La cantidad debe ser mayor a cero. Linea omitida.");

          continue;
        }

        // Agrega la línea de pedido a la lista temporal
        lineas.add(new LineaPedido(p, cantidad));

      } catch (NumberFormatException e) {

        System.err.println("❌ Error: ID y Cantidad deben ser numeros. Linea omitida.");

      } catch (ProductoNoEncontradoException e) {

        System.err.println("❌ Error: " + e.getMessage() + ". Linea omitida.");

      }
    }

    if (lineas.isEmpty()) {
      System.out.println("⚠️ Pedido cancelado: No se agregaron lineas validas.");
      return;
    }

    // Llama al servicio de pedido: Lanza StockInsuficienteException
    try {
      Pedido nuevoPedido = pedidoService.crearPedido(lineas);
      System.out.println("\n✅ PEDIDO CREADO CON EXITO:");
      System.out.println(nuevoPedido);

    } catch (StockInsuficienteException e) {

      System.err.println("\n❌ ERROR AL CREAR PEDIDO: " + e.getMessage());

    }
  }

  private static void listarPedidos() {
    System.out.println("\n--- LISTA DE PEDIDOS ---");
    List<Pedido> pedidos = pedidoService.listarPedidos();

    if (pedidos.isEmpty()) {
      System.out.println("📜 No hay pedidos registrados.");
      return;
    }

    for (Pedido p : pedidos) {
      System.out.println(p);
    }
    System.out.println("--------------------------");
  }
}