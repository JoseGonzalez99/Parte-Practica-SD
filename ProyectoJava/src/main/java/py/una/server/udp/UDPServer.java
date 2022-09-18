package py.una.server.udp;

import java.io.*;
import java.net.*;
import java.util.List;

import py.una.bd.BancoCRUD;
import py.una.entidad.Banco;
import py.una.entidad.BancoJSON;

public class UDPServer {
	
	
    public static void main(String[] a){
        
        int puertoServidor = 9876;
        BancoCRUD pdao = new BancoCRUD();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			
     
            System.out.println("Bienvenido al servidor UDP - Jose Elias Gonzalez Valdez \n");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
           
			
            //3) Servidor siempre esperando
            while (true) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("Esperando un cliente... ");

                
                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println(" ");
                
                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
           

                //Convertimos el json recibido a un objeto para poder manipular
                Banco p = BancoJSON.stringObjeto(datoRecibido);
                
                
                //Almacenamos su IP y el puerto del cliente que hizo la solicitud.
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                
                System.out.println("Estado de identificacion: ");
                try {
                		
                	
                		List<Banco> lista = pdao.seleccionarPorID(p.getID());
                        if (lista.size()==1){
                        	Integer cotization=p.getCotizacion();
                        	p= lista.get(0);
                        	p.setCotizacion(cotization);
                        	
                        	//fluctuacion= generarfluctuacion();
                        	System.out.println("Banco identificado \n Nombre: " + p.getNombre() + "\n Cotizacion recibida : 1$ = "+ p.getCotizacion()+ "Gs \n");
                        	
                        	//System.out.println("Valor de dolar " + fluctuacion.toString());
                        	String msj="Gracias por su cotizacion "+p.getNombre();
                        	sendData = BancoJSON.response(msj).getBytes();
                        	
                        	//p.setCotizacion(fluctuacion);
                            System.out.println("Finalizando conexion \n");

                        
                        }
                        else{
                        	sendData = BancoJSON.response("Id no identificado ").getBytes();
                        System.out.println("No identificado \n");
                        	

                        }
                }catch(Exception e) {
                	System.out.println("Fallo de consulta de Banco a la Base de datos, razón: " + e.getLocalizedMessage());
                }
                
                /*
                 orignal
                sendData = BancoJSON.objetoString(p).getBytes();
*/
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }



}  

