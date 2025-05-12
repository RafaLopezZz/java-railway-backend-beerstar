Repositorio en producción a través de railway para pruebas con la API REST beerstar.

Utiliza peticiones:

**_ LOGIN Y REGISTRO USUARIOS PÚBLICOS(CLIENTE/PROVEEDOR) _**
POST /beerstar/auth/login → Autenticación y generación de token JWT
POST /beerstar/auth/registro/cliente → Registro exclusivo para clientes
POST /beerstar/auth/registro/proveedor → Registro exclusivo para proveedores

- JSON login
  {
  "email": "pruebacliente00@cliente.es",
  "password": "12345678"
  }

- JSON registro Cliente y response
  {
  "email": "pruebacliente02@cliente.es",
  "password": "12345678"
  }

{
"idUsuario": 20,
"email": "pruebacliente02@cliente.es",
"rol": "USER",
"tipoUsuario": "CLIENTE"
}

- JSON registro Proveedor y response
  {
  "email": "pruebaproveedor00@proveedor.es",
  "password": "12345678"
  }

{
"idUsuario": 21,
"email": "pruebaproveedor00@proveedor.es",
"rol": "USER",
"tipoUsuario": "PROVEEDOR"
}

**_ PROVEEDORES _**
GET /beerstar/usuarios/proveedores → Listar todos los proveedores
GET /beerstar/usuarios/proveedores/{idUsuario} → Obtener datos de proveedor por ID de usuario
PUT /beerstar/usuarios/proveedores/{idProveedor} → Actualizar datos de proveedor por ID de proveedor

- JSON listar Proveedor
  {
  "idProveedor": 1,
  "usuario": {
  "idUsuario": 2,
  "email": "pruebacliente01@cliente.es",
  "rol": "USER",
  "tipoUsuario": "PROVEEDOR"
  },
  "nombre": "rafa",
  "direccion": "C/ Tal, 69",
  "telefono": "666223344",
  "fechaRegistro": "2025-03-31T20:02:04.604443",
  "url": null
  }

- JSON actualizar Proveedor
  {
  "nombre": "rafa",
  "direccion": "C/ Tal, 69",
  "telefono": 666223344,
  "url": null
  }

**_ CLIENTES _**
GET /beerstar/usuarios/clientes → Listar todos los clientes
GET /beerstar/usuarios/clientes/{idUsuario} → Obtener datos de cliente por ID de usuario
PUT /beerstar/usuarios/clientes/{idCliente} → Actualizar datos de cliente por ID de cliente

- JSON listar Clientes
  {
  "idCliente": 1,
  "usuario": {
  "idUsuario": 1,
  "email": "pruebacliente00@cliente.es",
  "rol": "USER",
  "tipoUsuario": "CLIENTE"
  },
  "nombre": "rafa",
  "direccion": "C/ falsa, 123",
  "telefono": "666222222",
  "fechaRegistro": "2025-03-30T17:14:02.463288"
  }

- JSON actualizar cliente
  {
  "nombre": "rafa",
  "direccion": "C/ Tal, 69",
  "telefono": 666223344,
  "url": null
  }

**_ USUARIOS (Sólo para administradores) _**
POST /beerstar/usuarios/admin → Registrar nuevo usuario (requiere rol ADMIN)
GET /beerstar/usuarios/{idUsuario} → Obtener usuario por ID
GET /beerstar/usuarios → Listar todos los usuarios
PUT /beerstar/usuarios/{idUsuario} → Actualizar usuario existente
DELETE /beerstar/usuarios/{idUsuario} → Eliminar usuario por ID

**_ CATEGORÍAS _**
POST /beerstar/categorias → Crear categoría
GET /beerstar/categorias/{idCategoria} → Obtener categoría por ID
GET /beerstar/categorias → Listar categorías
PUT /beerstar/categorias/{idCategoria} → Actualizar categoría
DELETE /beerstar/categorias/{idCategoria} → Eliminar categoría

- JSON listar Categorías
  {
  "idCategoria": 1,
  "nombre": "Amber Ale",
  "descripcion": "Cervezas de color ámbar, con sabores maltosos y notas a caramelo, ligeramente tostadas."
  }

- JSON crear/actualizar Categorías
  {
  "nombre": "Amber Ale",
  "descripcion": "Cervezas de color ámbar, con sabores maltosos y notas a caramelo, ligeramente tostadas."
  }

**_ ARTÍCULOS _**
POST /beerstar/articulos → Crear un nuevo artículo
GET /beerstar/articulos/{idArticulo} → Obtener artículo por ID
GET /beerstar/articulos → Listar todos los artículos
PUT /beerstar/articulos/{idArticulo} → Actualizar artículo por ID
DELETE /beerstar/articulos/{idArticulo} → Eliminar artículo por ID

- JSON listar Artículos  
   {
  "idArticulo": 4,
  "nombre": "Alhambra Baltic Porter",
  "descripcion": "Cervezas oscuras y densas, con sabores a café, chocolate y maltas muy tostadas.",
  "precio": 6.36,
  "stock": 76,
  "idCategoria": 5,
  "nombreCategoria": "Stout",
  "idProveedor": 1,
  "nombreProveedor": null,
  "graduacion": 7.8,
  "url": "https://firebasestorage.googleapis.com/v0/b/imagenes-fb98d.firebasestorage.app/o/alhambra-baltic.png?alt=media&token=1ab6e299-053f-4352-991b-3863cb7c7b08"
  }

- JSON crear/actualizar Artículos
  {
  "nombre": "Orval",
  "descripcion": "Este es un tipo de cerveza de fermentación alta, que incluye subestilos como Pale Ale, India Pale Ale (IPA), Stout, Porter, y Belgian Ale.",
  "precio": 2.95,
  "stock": 10,
  "idCategoria": 1,
  "nombreCategoria": "Ale",
  "idProveedor": 1,
  "nombreProveedor": "Proveedor Genérico",
  "graduacion": 6.2,
  "url": null
  }

**_ LOTES _**
POST beerstar/lotes -> Crea un nuevo lote
GET beerstar/lotes/{idLote} -> Obtiene un lote por su ID
GET beerstar/lotes -> Retorna todos los lotes registrados
PUT beerstar/lotes/{idLote} -> Actualiza un lote existente por su ID
DELETE beerstar/lotes/{idLote} -> Elimina un lote por su ID

- JSON listar Lotes
  {
  "idLote": 16,
  "nombreLote": "Lote-Beck's",
  "descripcion": "24 LATAS DE 0.5L BECKS GOLD 4.9%",
  "idProveedor": 8,
  "nombreProveedor": "Beck's",
  "precio": 29.95,
  "url": "https://firebasestorage.googleapis.com/v0/b/imagenes-fb98d.firebasestorage.app/o/1LoteBecks.png?alt=media&token=817db10e-109b-4f69-8d12-491eab0dcbeb"
  }

- JSON crear/actualizar Lotes
  {
  "nombreLote": "Lote - Test - Beck's",
  "descripcion": "12 LATAS DE 0.5L BECKS GOLD",
  "idProveedor": 8,
  "nombreProveedor": "Beck's",
  "precio": 29.95,
  "url": "https://firebasestorage.googleapis.com/v0/b/imagenes-fb98d.firebasestorage.app/o/1LoteBecks.png?alt=media&token=817db10e-109b-4f69-8d12-491eab0dcbeb"
  }
