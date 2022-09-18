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
            
            System.out.print("SOlicitamos que ingrese sus credenciales");
            System.out.print("Ingrese el número el identificador (debe ser numérico): ");
            
            String str_identificador = inFromUser.readLine();
            Integer id = 0;
            
            try {
            	
            	id = Integer.parseInt(str_identificador);
            }catch(Exception e1) {
            	
            }

            System.out.print("Ingrese el nombre del banco: ");
            String nombre = inFromUser.readLine();

            Banco p = new Banco(id,nombre,0);
            String datoPaquete = BancoJSON.objetoString(p); 
            sendData = datoPaquete.getBytes();            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Esperamos si viene la respuesta.");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            try {
                // ESPERAMOS LA RESPUESTA, BLOQUENTE
                clientSocket.receive(receivePacket);
                String respuesta = new String(receivePacket.getData());
                Banco presp= BancoJSON.stringObjeto(respuesta.trim());
                System.out.println("El precio del dolar(en guaranies es) : "+ presp.getCotizacion()+" gs");

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
} 

