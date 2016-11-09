#!/usr/bin/python3

from subprocess import call
import os

DIRECTORIO_EXTRACCION_LISTADOS="codigo_extraido"
DIRECTORIO_WORKSPACE="workspace"
DIRECTORIO_CODIGO_FUENTE="src"
EXTRAER_CLASE = "class"
EXTRAER_METODO= "method"
lista_extractor=["java", "-jar", "Extractor.jar"]


class Extraccion(object):
	def __init__(self, clase_cualificada, dir_proyecto):
		self.clase			=	clase_cualificada
		self.nombre_clase	=	self.clase.split(".")[-1]
		self.archivo_salida_clase = self.clase.replace(".", "_")+".java"
		print(self.nombre_clase)
		self.dir_proyecto	=	dir_proyecto
		self.archivo_clase=self.clase.replace(".", os.sep) + ".java"
	def get_ruta(self):
		lista=[DIRECTORIO_WORKSPACE, self.dir_proyecto,
			   DIRECTORIO_CODIGO_FUENTE, self.archivo_clase]
		return os.sep.join(lista)
	def extraer_clase(self):
		ruta_clase=self.get_ruta()
		lista_lanzamiento = lista_extractor + [ruta_clase, self.nombre_clase, EXTRAER_CLASE ]
		fich_salida = DIRECTORIO_EXTRACCION_LISTADOS + os.sep + "Clase_"+self.archivo_salida_clase
		with open(fich_salida, "w") as descriptor_salida:
			print ("Ejecutando "+ " ".join(lista_lanzamiento)+ " > " + fich_salida)
			call (lista_lanzamiento, stdout=descriptor_salida)
		print("Clase extraida")
		
		
		
		
e1=Extraccion("com.utilidades.UtilidadesFicheros", "Utilidades")
e1.extraer_clase()

extracciones=[
	("LanzaHilos.java", "LanzaHilos", EXTRAER_CLASE),
	(
		os.sep.join([
			DIRECTORIO_WORKSPACE,"Utilidades", DIRECTORIO_CODIGO_FUENTE,
			"com", "utilidades","UtilidadesFicheros.java"
		]),
		"getBufferedReader", EXTRAER_METODO
	),
	(
		os.sep.join([
			DIRECTORIO_WORKSPACE,"Utilidades", DIRECTORIO_CODIGO_FUENTE,
			"com", "utilidades","UtilidadesFicheros.java"
		]),
		"getPrintWriter", EXTRAER_METODO
	),
	(
		os.sep.join([
			DIRECTORIO_WORKSPACE,"Utilidades", DIRECTORIO_CODIGO_FUENTE,
			"com", "utilidades","UtilidadesFicheros.java"
		]),
		"getLineasFichero", EXTRAER_METODO
	),
	(
		os.sep.join([
			DIRECTORIO_WORKSPACE,"Multiproceso_Vocales", DIRECTORIO_CODIGO_FUENTE,
			"com", "ies","ProcesadorFichero.java"
		]),
		"hacerRecuento", EXTRAER_METODO
	),
	(
		os.sep.join([
			DIRECTORIO_WORKSPACE,"Multiproceso_Vocales", DIRECTORIO_CODIGO_FUENTE,
			"com", "ies","ProcesadorFichero.java"
		]),
		"main", EXTRAER_METODO
	)
]

for e in extracciones:
	lista_lanzamiento=lista_extractor[:]
	if e[2]==EXTRAER_CLASE:
		fich_salida = DIRECTORIO_EXTRACCION_LISTADOS + os.sep + "Clase_"+e[1]+".java"
	else:
		fich_salida = DIRECTORIO_EXTRACCION_LISTADOS + os.sep + "Metodo_"+e[1]+".java"
	lista_lanzamiento = lista_lanzamiento +  [e[0], e[1], e[2] ]
	print ("Ejecutando "+ " ".join(lista_lanzamiento))
	with open(fich_salida, "w") as descriptor_salida:
		call (lista_lanzamiento, stdout=descriptor_salida)
