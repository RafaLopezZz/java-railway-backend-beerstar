-- Tabla Usuarios: Información de login y roles para Clientes y Proveedores
CREATE TABLE Usuarios (
    id_usuario SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('USER', 'ADMIN', 'SUPERADMIN')),
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('CLIENTE', 'PROVEEDOR'))
);

-- Tabla Clientes
CREATE TABLE Clientes (
    id_cliente SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE,  -- Cada cliente se vincula a un usuario
    nombre VARCHAR(100) NOT NULL,
    direccion TEXT,            -- Se sugiere quitar el acento
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabla Proveedores
CREATE TABLE Proveedores (
    id_proveedor SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE,  -- Relación uno a uno con Usuarios
    nombre VARCHAR(100) NOT NULL,
    direccion TEXT,            -- Se sugiere quitar el acento
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabla Categorias
CREATE TABLE Categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT          -- Se sugiere quitar el acento
);

-- Tabla Productos
CREATE TABLE Productos (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,         -- Se sugiere quitar el acento
    precio NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL CHECK (stock >= 0),
    id_categoria INTEGER,
    graduacion NUMERIC(3,1),
    imagen VARCHAR(255),
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria) ON DELETE SET NULL
);

-- Tabla Carrito (un carrito por cliente)
CREATE TABLE Carrito (
    id_carrito SERIAL PRIMARY KEY,
    id_cliente INTEGER UNIQUE, -- Restricción de unicidad
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Se quita el acento
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente) ON DELETE CASCADE
);

-- Tabla Detalles_Carrito
CREATE TABLE Detalles_Carrito (
    id_detalle_carrito SERIAL PRIMARY KEY,
    id_carrito INTEGER,
    id_producto INTEGER,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    FOREIGN KEY (id_carrito) REFERENCES Carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto) ON DELETE CASCADE
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INTEGER,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'enviado', 'entregado', 'cancelado')),
    total NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente) ON DELETE SET NULL
);

-- Tabla Detalles_Pedido
CREATE TABLE Detalles_Pedido (
    id_detalle SERIAL PRIMARY KEY,
    id_pedido INTEGER,
    id_producto INTEGER,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto) ON DELETE SET NULL
);

-- Tabla Pagos
CREATE TABLE Pagos (
    id_pago SERIAL PRIMARY KEY,
    id_pedido INTEGER UNIQUE, -- Un pago por pedido
    monto NUMERIC(10,2) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL CHECK (metodo_pago IN ('tarjeta', 'paypal', 'transferencia', 'efectivo')),
    estado_pago VARCHAR(20),  -- Agregada la coma faltante a continuación
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaccion_id VARCHAR(100),  -- Se sugiere quitar el acento
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE
);
