window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_turno');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {
            paciente: {
                id: document.querySelector('#paciente_id').value,
            },
            odontologo: {
                id: document.querySelector('#odontologo_id').value
            },
            fecha: document.querySelector('#fecha').value
        };

        const url = '/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 let successAlert =
                 '<div class="alert alert-success alert-dismissible">' +
                     'Turno agregado.' +
                     '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                 '</div>'


                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {

            let errorAlert =
            '<div class="alert alert-danger alert-dismissible">' +
                '<strong>Error intente nuevamente.</strong>' +
                '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>'+
            '</div>'

                 document.querySelector('#response').innerHTML = errorAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();})
    });

    function resetUploadForm() {
        document.querySelector('#paciente_id').value = "";
        document.querySelector('#odontologo_id').value = "";
        document.querySelector('#fecha').value = "";
    }

// Creo que esto es para marcar cual navbar esta activa. Lo hice directo en el html
//    (function(){
//        let pathname = window.location.pathname;
//        if(pathname === "/"){
//            document.querySelector(".nav .nav-item a:first").addClass("active");
//        } else if (pathname == "/post_turnos.html") {
//            document.querySelector(".nav .nav-item a:last").addClass("active");
//        }
//    })();
});