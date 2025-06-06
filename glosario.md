# Glosario NettyTcpServer

---

### **Netty**
Framework de Java para construir aplicaciones de red de alto rendimiento y baja latencia. Permite manejar conexiones TCP, UDP y otros protocolos de manera eficiente y asíncrona.

---

### **ServerBootstrap**
Clase principal de Netty para configurar y arrancar un servidor. Permite definir los grupos de hilos, el tipo de canal, las opciones de red y el pipeline de handlers para cada conexión.

---

### **EventLoopGroup**
Grupo de hilos que maneja los eventos de red, como aceptar conexiones, leer y escribir datos. Netty separa los grupos en uno para aceptar conexiones (`bossGroup`) y otro para manejar el tráfico de las conexiones (`workerGroup`).

---

### **NioEventLoopGroup**
Implementación de EventLoopGroup basada en NIO (Non-blocking I/O) de Java. Permite manejar múltiples conexiones de manera eficiente usando pocos hilos.

---

### **Channel**
Abstracción de una conexión de red (por ejemplo, un socket TCP). Cada cliente conectado tiene su propio Channel, a través del cual se envían y reciben datos.

---

### **ChannelInitializer**
Clase que permite definir cómo se inicializa el pipeline de cada nueva conexión. Aquí se agregan los handlers que procesarán los eventos y datos de esa conexión.

---

### **SocketChannel**
Tipo de Channel que representa una conexión TCP individual entre el servidor y un cliente.

---

### **NioServerSocketChannel**
Implementación de Channel para servidores TCP basada en NIO. Es el canal principal que acepta nuevas conexiones.

---

### **ChannelPipeline**
Cadena (pipeline) de handlers asociados a un Channel. Cada handler puede procesar, transformar o reaccionar a los datos y eventos que pasan por la conexión.

---

### **LoggingHandler**
Handler especial de Netty que registra en los logs los eventos de red (conexiones, desconexiones, mensajes, errores). Facilita el monitoreo y la depuración del servidor.

---

### **LogLevel.INFO**
Nivel de detalle de los logs generado por LoggingHandler. INFO muestra eventos importantes como conexiones y errores, pero omite detalles de depuración.

---

### **SimpleChannelInboundHandler**
Handler de Netty para procesar mensajes entrantes de un tipo específico (en este caso, ByteBuf). Permite definir cómo se manejan los datos recibidos.

---

### **ByteBuf**
Buffer de bytes de Netty, usado para leer y escribir datos binarios de manera eficiente. Es la estructura principal para manejar datos en Netty.

---

### **ChannelHandlerContext**
Objeto que proporciona contexto a los handlers, permitiendo interactuar con el pipeline, el canal y otros handlers.

---

### **@Slf4j**
Anotación de Lombok que genera automáticamente un logger llamado `log` en la clase, facilitando la escritura de mensajes de log.

---

### **@Component**
Anotación de Spring que marca la clase como un componente inyectable, permitiendo que Spring la gestione como un bean.

---

### **@PreDestroy**
Anotación que indica que el método debe ejecutarse antes de destruir el bean, útil para liberar recursos al cerrar la aplicación.

---

### **shutdownGracefully()**
Método de Netty para cerrar los EventLoopGroups de forma ordenada, esperando que terminen las tareas pendientes antes de liberar los recursos.

---

### **bind(PORT)**
Método que liga el servidor al puerto especificado, permitiendo aceptar conexiones entrantes en ese puerto.

---

### **closeFuture().sync()**
Bloquea el hilo actual hasta que el canal principal se cierra, manteniendo el servidor en ejecución hasta que se detenga explícitamente.

---

### **exceptionCaught**
Método de los handlers que se ejecuta cuando ocurre una excepción en el pipeline. Permite manejar errores y cerrar la conexión si es necesario.

---

### **channelRead0**
Método de SimpleChannelInboundHandler que se ejecuta cada vez que se recibe un mensaje. Aquí se define la lógica para procesar los datos entrantes.

---

### **SO_BACKLOG**
Opción de red que define el tamaño de la cola de conexiones pendientes que el sistema operativo puede mantener para el servidor.

---

### **SO_KEEPALIVE**
Opción de red que mantiene las conexiones activas enviando mensajes periódicos, ayudando a detectar conexiones muertas.

---