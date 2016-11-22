#!/bin/bash
./extraer_listados.py
./indentar.py
make singlehtml
make latex
cd _build/latex ; pdflatex ServiciosProcesos.tex ; pdflatex ServiciosProcesos.tex
cd ..
cd ..
./copiar.py
cd ..
pwd
cd oscarmaestre.github.io
pwd
git commit -a --allow-empty-message -m ''
git push

