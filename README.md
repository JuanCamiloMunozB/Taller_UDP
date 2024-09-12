# UDP Chat Application

Grupo: Jose Manuel Cardona y Juan Camilo Muñoz

Esta aplicación es una implementación simple de un chat entre dos computadores conectados a una misma red utilizando el protocolo UDP. Los usuarios pueden enviar y recibir mensajes a través de una interfaz gráfica construida con JavaFX.

## Requisitos previos

Antes de ejecutar la aplicación, asegúrate de tener los siguientes requisitos:

- **Java 8 o superior** instalado en tu máquina.
- **JavaFX** configurado correctamente en tu entorno de desarrollo (JDK con soporte para JavaFX o añadidos manualmente).
- Ambos computadores deben estar conectados a la misma red local (LAN).

## Configuración del proyecto

1. Clona o descarga este repositorio en tu máquina local.
2. Asegúrate de que tu entorno de desarrollo esté configurado para incluir las librerías de **JavaFX**.
3. Compila el proyecto y asegúrate de que no haya errores de configuración.

## Cómo ejecutar la aplicación

### Paso 1: Configurar un nodo (receptor o emisor)

Ambos nodos (computadoras) necesitan ejecutar la aplicación.

### Paso 2: Ejecutar la aplicación

1. Navega hasta la clase `PeerA` o `PeerB` y ejecútala. Esto abrirá la interfaz gráfica de la aplicación.
2. Se abrirá una ventana con varias opciones para enviar y recibir mensajes.
3. Como la implementacion representa una red peer to peer, ambos nodos deben ejecutar la aplicación y configurar los puertos.

### Paso 3: Recibir un mensaje

1. **Configuración de recepción**: El receptor debe ingresar el puerto en el que escuchará los mensajes en el campo "Enter Port". Asegúrate de que este puerto no esté siendo usado por otra aplicación. Al hacer esto, la interfaz mostrata la dirección IP de la máquina en la que se está ejecutando.
2. **Iniciar la recepción**: Haz clic en "Start Receiving" para empezar a escuchar mensajes en el puerto especificado.
3. **Ver los mensajes**: Los mensajes recibidos aparecerán en el área de texto en la parte inferior de la ventana.

### Paso 4: Enviar un mensaje

1. **Escribe un mensaje**: En el campo de texto que dice "Enter message to send", introduce el mensaje que deseas enviar.
2. **Dirección IP del destino**: Introduce la dirección IP del dispositivo receptor en el campo "Enter IP Address". Puedes obtener esta dirección IP del dispositivo receptor (si no la conoces, puedes ejecutarlo en la máquina receptora para mostrar su IP en la interfaz).
3. **Puerto del destino**: Introduce el puerto en el que el receptor está escuchando los mensajes. Este debe ser el mismo puerto que el receptor configuró para escuchar.
4. **Enviar el mensaje**: Haz clic en el botón "Send Message". Esto enviará el mensaje al receptor especificado.

### Paso 5: Detener la escucha

Si deseas dejar de recibir mensajes, haz clic en "Stop Listening". Esto cerrará el socket y detendrá la recepción de mensajes.

## Descripción de la interfaz

- **Message**: Escribe el mensaje que deseas enviar en este campo de texto.
- **IP Address**: Introduce la dirección IP del receptor.
- **Port**: Especifica el puerto del receptor al enviar mensajes o el puerto local en el que escucharás mensajes.
- **Send Message**: Envía el mensaje ingresado al receptor especificado.
- **Start Receiving**: Empieza a escuchar mensajes en el puerto especificado.
- **Stop Listening**: Detiene la escucha de mensajes en el puerto configurado.
- **Message Area**: Muestra todos los mensajes enviados y recibidos.

## Consideraciones

- Ambos nodos deben estar en la misma red local para poder intercambiar mensajes.
- El puerto que utilices para escuchar mensajes debe estar libre y no ser bloqueado por un firewall.
- Asegúrate de que las direcciones IP y puertos coincidan al enviar y recibir mensajes.
