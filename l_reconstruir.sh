#!/bin/bash

make singlehtml
make latex
cd _build/latex ; pdflatex ServiciosProcesos.tex ; pdflatex ServiciosProcesos.tex
cd ..
cd ..

