package py.una.server.udp;


import java.io.*;
import java.net.*;

import py.una.entidad.Banco;
import py.una.entidad.BancoJSON;

class UDPClient {

    public static void main(String a[]) throws Exception {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(direccionServidor);

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            System.out.println("Bienvenido al Cliente UDP - Jose Elias Gonzalez Valdez \n");
            System.out.println("\n");
            
            
            System.out.print("Indique el id de algun banco registrado: ");
            
            
            String str_identificador = inFromUser.readLine();
            Integer id = 0;
            
            try {
            	
            	id = Integer.parseInt(str_identificador);
            }catch(Exception e1) {
            	
            }

            //System.out.print("Ingrese el nombre del banco: ");
            //String nombre = inFromUser.readLine();
            Integer cotizacion= 0;
            
            cotizacion=	generarfluctuacion();

            Banco p = new Banco(id,"",cotizacion);
        
            String datoPaquete = BancoJSON.objetoString(p); 
            
            sendData = datoPaquete.getBytes();            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Cotizacion : 1$ = "+ cotizacion + "Gs");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            try {
                // ESPERAMOS LA RESPUESTA, BLOQUENTE
                clientSocket.receive(receivePacket);
                String respuesta = new String(receivePacket.getData());
                String msj= BancoJSON.read_response(respuesta.trim());
                System.out.println("Respuesta del servidor : "+ msj );
                
                
                //System.out.println("La cotizacion fue enviada con exito!!");
            } catch (SocketTimeoutException ste) {
                System.out.println("TimeOut: El paquete udp se asume perdido.");
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    private static Integer generarfluctuacion(){
    	/*
    	 Generar numero aleatorio y luego convertirlo a string
    	  */
    	int Random = (int)(Math.random()*100);
    	int randomOP = (int) (Math.random() * (1-0)) + 0;
    	if(randomOP==1) {
    		return 6850+Random;
    	}else {
    	return 6850-Random;
    	}
    }
    
} 

