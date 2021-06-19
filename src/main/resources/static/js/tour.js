
function muestraInstancia(tour, max){
    if (document.getElementById){
        var elements = document.getElementsByClassName("contratacion");
        for (let index = 0; index < elements.length; index++) {
            elements[index].style.display = 'none';
        }
        document.getElementById("instancia" + tour).style.display = 'block';
    }
}

function validarFechas()
{
   var fechaIni = document.getElementById("fechaInicial").value;
   var fechaFin = document.getElementById("fechaFinal").value;

   var now = new Date(Date.now());

   if(fechaFin != "" && (Date.parse(now) > Date.parse(fechaIni) || Date.parse(fechaFin) < Date.parse(fechaIni))) {
       document.getElementById("fechaFinal").value = "";
       document.getElementById("divFinal").innerHTML = "La fecha inicial debe ser menor que la fecha final y mayor que la fecha actual";
   }
}

function siguientePaso(){
    document.getElementById("divFinal").style.display = "block";
}

function instanciaNueva(){
    document.getElementById("instanciaNueva").style.display = "block";
}

function anadirEtiqueta(){
    let valor = document.getElementById("etiqueta").value;
    let htmlEtiq = document.getElementById("etiq").innerHTML;
    let valorE = document.getElementById("etiquetas").value;
    document.getElementById("etiq").innerHTML = htmlEtiq + "<button id='" + valor + "' disabled>" + valor + "</button><i class='fas fa-times' title='" + valor + "' onclick='eliminarEtiqueta(this)'></i>"
    document.getElementById("etiquetas").value = valorE + valor + ";";
    document.getElementById("etiqueta").value = "";
}

function eliminarEtiqueta(elemento){
    let valor = elemento.title;
    let valorE = document.getElementById("etiquetas").value;
    elemento.style.display = "none";
    document.getElementById(valor).style.display = "none";
    valorE = valorE.replace(valor + ";", "");
    document.getElementById("etiquetas").value = valorE;
    
}