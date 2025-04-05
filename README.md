Repositorio en producción a través de railway para pruebas con la API REST beerstar.

Utiliza peticiones:

*** USUARIOS ***
GET    -> /beerstar/usuarios/listarUsuarios
          /beerstar/usuarios/obtenerUsuario
          /beerstar/usuarios/clientes/{usuarioId}
          /beerstar/usuarios/proveedores/{usuarioId}

POST   -> /beerstar/usuarios/registro

PUT    -> /beerstar/usuarios/actualizarUsuario/{id}
          /beerstar/usuarios/clientes/{clienteId}
          /beerstar/usuarios/proveedores/{proveedorId}
          
DELETE -> /beerstar/usuarios/eliminarUsuario/{id}

*** CATEGORÍAS ***
GET    -> /beerstar/categorias/listarCategorias
          /beerstar/categorias/obtenerCategoria/{categoriaId}

POST   -> /beerstar/categorias/crearCategoria

PUT    -> /beerstar/categorias/actualizarCategoria/{categoriaId}
          
DELETE -> /beerstar/categorias/eliminarCategoria/{categoriaId}

*** ARTÍCULOS ***
GET    -> /beerstar/articulos/listarArticulos
          beerstar/articulos/obtenerArticulo/{articuloId}

POST   -> /beerstar/articulos/crearArticulo

PUT    -> /beerstar/articulos/actualizarArticulo/{articuloId}
          
DELETE -> /beerstar/articulos/eliminarArticulo/{articuloId}
                  
