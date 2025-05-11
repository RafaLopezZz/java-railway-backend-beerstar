Repositorio en producción a través de railway para pruebas con la API REST beerstar.

Utiliza peticiones:

*** LOGIN Y REGISTRO USUARIOS PÚBLICOS(CLIENTE/PROVEEDOR) ***
POST /beerstar/auth/login               → Autenticación y generación de token JWT
POST /beerstar/auth/registro/cliente    → Registro exclusivo para clientes
POST /beerstar/auth/registro/proveedor  → Registro exclusivo para proveedores

*** PROVEEDORES ***
GET  /beerstar/usuarios/proveedores                 → Listar todos los proveedores
GET  /beerstar/usuarios/proveedores/{idUsuario}     → Obtener datos de proveedor por ID de usuario
PUT  /beerstar/usuarios/proveedores/{idProveedor}   → Actualizar datos de proveedor por ID de proveedor

*** CLIENTES ***
GET  /beerstar/usuarios/clientes              → Listar todos los clientes
GET  /beerstar/usuarios/clientes/{idUsuario}  → Obtener datos de cliente por ID de usuario
PUT  /beerstar/usuarios/clientes/{idCliente}  → Actualizar datos de cliente por ID de cliente

*** USUARIOS (Sólo para administradores) ***
POST   /beerstar/usuarios/admin         → Registrar nuevo usuario (requiere rol ADMIN)
GET    /beerstar/usuarios/{idUsuario}   → Obtener usuario por ID
GET    /beerstar/usuarios               → Listar todos los usuarios
PUT    /beerstar/usuarios/{idUsuario}   → Actualizar usuario existente
DELETE /beerstar/usuarios/{idUsuario}   → Eliminar usuario por ID

*** CATEGORÍAS ***
POST   /beerstar/categorias        → Crear categoría
GET    /beerstar/categorias/{id}   → Obtener categoría por ID
GET    /beerstar/categorias        → Listar categorías
PUT    /beerstar/categorias/{id}   → Actualizar categoría
DELETE /beerstar/categorias/{id}   → Eliminar categoría

*** ARTÍCULOS ***
POST   /beerstar/articulos                   → Crear un nuevo artículo
GET    /beerstar/articulos/{idArticulo}      → Obtener artículo por ID
GET    /beerstar/articulos                   → Listar todos los artículos
PUT    /beerstar/articulos/{idArticulo}      → Actualizar artículo por ID
DELETE /beerstar/articulos/{idArticulo}      → Eliminar artículo por ID

*** LOTES ***
POST   beerstar/lotes             -> Crea un nuevo lote
GET    beerstar/lotes/{idLote}    -> Obtiene un lote por su ID
GET    beerstar/lotes             -> Retorna todos los lotes registrados
PUT    beerstar/lotes/{idLote}    -> Actualiza un lote existente por su ID
DELETE beerstar/lotes/{idLote}    -> Elimina un lote por su ID

                  
