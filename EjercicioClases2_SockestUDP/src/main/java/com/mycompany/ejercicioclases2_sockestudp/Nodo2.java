/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicioclases2_sockestudp;


import java.net.*;
import java.io.*;

/**
 *
 * @author cdk04
 */
public class Nodo2 {
     public static void main(String[] args) {

        int puertoNodo2 = 5002;
        int puertoNodo3 = 5003;

        try {
            DatagramSocket socketUDP =
                new DatagramSocket(puertoNodo2);

            System.out.println(
                "Nodo 2 escuchando en el puerto " + puertoNodo2
            );

            while (true) {

                byte[] bufer = new byte[2000];

                DatagramPacket peticion =
                    new DatagramPacket(bufer, bufer.length);

                socketUDP.receive(peticion);

                String recibido = new String(
                    peticion.getData(),
                    0,
                    peticion.getLength()
                );

                // Recibe: texto|cantidadCaracteres
                String[] partes = recibido.split("\\|", 2);

                String texto = partes[0];
                int cantidadCaracteres =
                    Integer.parseInt(partes[1]);

                int cantidadPalabras;

                if (texto.trim().isEmpty()) {
                    cantidadPalabras = 0;
                } else {
                    cantidadPalabras =
                        texto.trim().split("\\s+").length;
                }

                String tipo;

                if (cantidadCaracteres % 2 == 0) {
                    tipo = "PAR";
                } else {
                    tipo = "IMPAR";
                }

                // Envía: texto|caracteres|palabras|tipo
                String datos = texto + "|" +
                               cantidadCaracteres + "|" +
                               cantidadPalabras + "|" +
                               tipo;

                byte[] mensaje = datos.getBytes();

                InetAddress ipNodo3 =
                    InetAddress.getByName("localhost");

                DatagramPacket respuesta =
                    new DatagramPacket(
                        mensaje,
                        mensaje.length,
                        ipNodo3,
                        puertoNodo3
                    );

                socketUDP.send(respuesta);

                System.out.println(
                    "Nodo 2 procesó y envio la información al Nodo 3."
                );
            }

        } catch (IOException e) {
            System.out.println("Error Nodo 2: " + e.getMessage());
        }
    }
}
