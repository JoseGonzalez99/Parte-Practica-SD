package py.una.server.udp;

import java.io.*;
import java.net.*;

import py.una.bd.BancoCRUD;
import py.una.entidad.Banco;
import py.una.entidad.BancoJSON;

public class UDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        BancoCRUD pdao = new BancoCRUD();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			
            System.out.println("Servidor Sistemas Distribuidos - UDP - Proveedor de Cotizacion DOLAR $ ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
           
			
            //3) Servidor siempre esperando
            while (true) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("Esperando a algun cliente... ");

                
                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println(" ");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );

                //Convertimos el json recibido a un objeto para poder manipular
                Banco p = BancoJSON.stringObjeto(datoRecibido);
                
                
                //Almacenamos su IP y el puerto del cliente que hizo la solicitud.
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                Integer fluctuacion=0;
                try {
                        if (pdao.seleccionarPorID(p.getID())==1){
                        	
                        	System.out.println("Banco identificado...");
                        	
                        	fluctuacion= generarfluctuacion();
                        	
                        	System.out.println("Valor de dolar " + fluctuacion.toString());
                        	
                        	p.setCotizacion(fluctuacion);
                        
                        }
                        else{
                        System.out.println("El banco no esta registrado");

                        }
                }catch(Exception e) {
                	System.out.println("Fallo de consulta de Banco a la Base de datos, razón: " + e.getLocalizedMessage());
                }
                

                sendData = BancoJSON.objetoString(p).getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }


    private static Integer generarfluctuacion(){
    	/*
    	 Generar numero aleatorio y luego convertirlo a string
    	  */
    	int Random = (int)(Math.random()*100);
    	int randomOP = (int) (Math.random() * (1-0)) + 0;
    	if(randomOP==1) {
    		return 5800+Random;
    	}else {
    	return 5800-Random;
    	}
    	
    }

}  

