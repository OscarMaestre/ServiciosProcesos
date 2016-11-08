extraer_listados.py
call make singlehtml
call make latex
cd _build\latex
call pdflatex ServiciosProcesos.tex
call pdflatex ServiciosProcesos.tex
cd ..
cd ..
call git commit -a --allow-empty-message -m ''
call git push