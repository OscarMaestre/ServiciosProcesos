#!/usr/bin/python3
from subprocess import call
import os, glob

DIRECTORIO_EXTRACCION_LISTADOS="listados"
DIRECTORIO_LISTADOS_RST="listados_a_incluir"
INDENTADOR = "astyle --mode=java --max_code_length=60"
ficheros_java=glob.glob(DIRECTORIO_EXTRACCION_LISTADOS + os.sep + "*.java")
for tupla in ficheros_java:
	print (tupla)
