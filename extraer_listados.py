#!/usr/bin/python3

from subprocess import call
import os

DIRECTORIO_EXTRACCION_LISTADOS="codigo_extraido"
DIRECTORIO_WORKSPACE="workspace"
DIRECTORIO_CODIGO_FUENTE="src"
EXTRAER_CLASE = "class"
EXTRAER_METODO= "method"
lista_extractor=["java", "-jar", "Extractor.jar"]



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
			DIRECTORIO_WORKSPACE,"Multiproceso_Vocales", DIRECTORIO_CODIGO_FUENTE,
			"com", "ies","ProcesadorFichero.java"
		]),
		"hacerRecuento", EXTRAER_METODO
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
