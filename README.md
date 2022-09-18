# Parte Practica de Sistemas Distribuidos
Estudiante: Jose Elias Gonzalez Valdez

## Enunciado

Escriba un programa servidor y un programa cliente TCP o UDP en lenguaje Java que reciba datos de la cotización del dólar de diversos bancos/financieras y los imprima en consola. Los datos de la cotización debe estar en formato JSON. Se asume que cada banco/financiera es un cliente y tiene un identificador único.


## Pre configuración

Para poder levantar es necesario tener maven, de modo a poder instalar las dependencias indicadas en el archivo pom.xml
Luego  sigue la ejecución 
- Del  archivo serverUDP.java para correr el servidor.
- Del archivo clientUDP.java para correr un cliente



## Description de código utilizado

Para realizar esta tarea reutilice el código del laboratorio de **sockets**, la cual modifique para adaptarlo a los requerido.

# Principales cambios
## Paquete py.una.entidad
*  **Banco.java**
	Originalmente era la entidad persona, y era la clase que se usaba para manipular dicho objeto. Para este trabajo modique la clase de modo a que contenga 3 atributos
	* ID 
	* Nombre 
	* Cotizacion
La clase persona tenia 
	* cedula
	* nombre
	* apellido
Cada uno con sus respectivos cambios de tipos de datos.
* **BancoJSON.java**
Originalmente era la clase que proveia los metodos para poder convertir el objeto persona a su representacion en Json y viceversa
El cambio respecto a este archivo tiene que ver con lo tipos de datos y atributos, debido a que banco tiene atributos distintos a persona.
Ademas de que agregue dos metodos llamados response y read_response. 
	* response es llamado por el servidor para responder al cliente con un mensaje.
	* read_responde es llamado por el cliente para leer la respuesta del servidor.
## Paquete py.una.bd
* **BancoCRUD.java**
	Originalmente era el archivo que servia de punto de interaccion con la base de datos y este originalmente incluia los metodos Insertar,Seleccionar, Modificar,Borrar.
	No obstante, para este trabajo, no requeria los metodo de crear, modificar y eliminar. Solo modifique el metodo **seleccionarPorCedular** para que pase a ser el metodo **seleccionarPorID** que retorna el Banco con el Id que se le pase como parametro.


		

 * **Bd.java**
 Este archivo no presenta ningun cambio en su logica, pero hay que fijarse bien en indicar  correctamente:
	 * La url
	 * El usuario
	 * La contraseña
* **Observacion:** 
	* Es necesario crear desde PgAdmin la base de datos y la tabla banco.
	* La insercion de datos  en la base de datos se hace de manera manual por PgAdmin. Dejare el codigo SQL a continuacion.
	
			CREATE TABLE public.banco (
		                id INTEGER NOT NULL,
		                nombre VARCHAR NOT NULL,
		                cotizacion INTEGER,
		                CONSTRAINT id PRIMARY KEY (id)
		                );

		
## Paquete py.una.server.udp
* **UDPServer.java**
 La estructura del codigo técnicamente es la misma, pero los cambios principales son:
	* La accion que hacia el servidor en el laboratorio era que insertaba un dato persona a la base de datos, en este caso realiza una consulta a la misma con el uso del id del cliente. 
		 *	Si este efectivamente se encuentra imprime los datos  de ese banco mas la cotizacion del dolar. Y el servidor le responde con un mensaje de agradecimiento por proveer enviar la cotizacion.
		 *	Si no se encuentra en la base de datos le responde con un mensaje que no se reconce su id.

*  **UDPClient.java**
Al igual que UDPServer,java, tecnicamente estamos ante el mismo codigo, no obstante los cambios realizados son.
	* Se solicita que el operador del cliente ingrese el identificador del banco 
	* Agregue un metodo llamado **generarfluctuacion** que genera la cotizacion de 1 dolar en guaranies.




