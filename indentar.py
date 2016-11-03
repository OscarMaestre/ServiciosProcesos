#!/usr/bin/python3
from subprocess import call
import os, glob

DIRECTORIO_EXTRACCION_LISTADOS="listados"
DIRECTORIO_LISTADOS_RST="listados_a_incluir"
INDENTADOR = ["astyle",  "--max-code-length=60"]
ficheros_java=glob.glob(DIRECTORIO_EXTRACCION_LISTADOS + os.sep + "*.java")
for f in ficheros_java:
	print ("Indentando "+f)
	lista_lanzamiento=INDENTADOR[:]
	fich_entrada = open ( f , "r")
	trozos=f.split(os.sep)
	print(trozos[-1])
	fich_salida = open (DIRECTORIO_LISTADOS_RST + os.sep + trozos[-1], "w")
	call (lista_lanzamiento, stdin=fich_entrada, stdout=fich_salida)
