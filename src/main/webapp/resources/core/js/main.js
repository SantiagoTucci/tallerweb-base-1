//progreso home
document.addEventListener("DOMContentLoaded", function() {
    var tipoRendimiento = document.querySelector(".progreso-container span").innerText;
    var barraProgreso = document.querySelector(".barra-progreso");
    if (tipoRendimiento === "ALTO") {
        barraProgreso.style.backgroundColor = "#2ecc71"; // Verde
    } else if (tipoRendimiento === "NORMAL") {
        barraProgreso.style.backgroundColor = "#f1c40f"; // Amarillo
    } else if (tipoRendimiento === "BAJO") {
        barraProgreso.style.backgroundColor = "#e74c3c"; // Rojo
    }
});

//que coloque un color segun el tipoRendimiento
document.addEventListener('DOMContentLoaded', function() {
    var tipoRendimientoCells = document.querySelectorAll('.tipo-rendimiento');

    tipoRendimientoCells.forEach(function(cell) {
        var tipoRendimiento = cell.textContent.trim().toLowerCase();
        switch (tipoRendimiento) {
            case 'alto':
                cell.classList.add('tipo-alto');
                break;
            case 'normal':
                cell.classList.add('tipo-normal');
                break;
            case 'bajo':
                cell.classList.add('tipo-bajo');
                break;
            case 'descanso':
                cell.classList.add('tipo-descanso');
                break;
            default:
                break;
        }
    });
});

    document.addEventListener('DOMContentLoaded', function() {
        const retoId = document.querySelector('input[name="retoId"]').value;
        const enProceso = document.querySelector('input[name="enProceso"]').value === 'true';

        if (enProceso) {
            // Si el reto está en proceso, mostrar el botón "Terminado"
            document.getElementById('btn-empezar').style.display = 'none';
            document.getElementById('btn-terminado').style.display = 'block';
        } else {
            // Si el reto no está en proceso, mostrar el botón "Empezar"
            document.getElementById('btn-empezar').style.display = 'block';
            document.getElementById('btn-terminado').style.display = 'none';
        }


    });

document.addEventListener('DOMContentLoaded', (event) => {
    const usuarioDataDiv = document.getElementById('usuario-data');
    const rachaDeRetos = usuarioDataDiv ? usuarioDataDiv.getAttribute('data-racha') : 0;

    // Actualizar el texto de la racha
    const rachaTextoDiv = document.getElementById('racha-texto');
    if (rachaTextoDiv) {
        rachaTextoDiv.textContent = `Racha Retos: ${rachaDeRetos}`;
    }
});







