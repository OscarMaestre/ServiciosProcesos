#!/usr/bin/env python

import socketserver


TAM_MAXIMO_PARAMETROS=64
PUERTO=9876

class GestorConexion(socketserver.BaseRequestHandler):
    
    def leer_cadena(self, LONGITUD):
        cadena=self.request.recv(LONGITUD)
        return cadena.strip()
    
    def convertir_a_cadena(self, bytes):
        return bytes.decode("utf-8")
    
    def calcular_resultado(self, n1, op, n2):
        n1=int(n1)
        n2=int(n2)
        
        op=self.convertir_a_cadena(op)
        if (op=="+"):
            return n1+n2
        if (op=="-"):
            return n1-n2
        return 0
    """Controlador de evento 'NuevaConexion"""
    def handle(self):
        direccion=self.client_address[0]
        operacion   =   self.leer_cadena(2)
        num1        =   self.leer_cadena(3)
        num2        =   self.leer_cadena(3)
        print (direccion+" pregunta:"+str(num1)+" "+str(operacion)+" "+str(num2))
        
        resultado=self.calcular_resultado(num1, operacion, num2)
        print ("Devolviendo a " + direccion+" el resultado "+str(resultado))
        bytes_resultado=bytearray(str(resultado), "utf-8");
        self.request.send(bytes_resultado)
        
        


servidor=socketserver.TCPServer(("10.13.0.20", 9876), GestorConexion)
print ("Servidor en marcha.")
servidor.serve_forever()