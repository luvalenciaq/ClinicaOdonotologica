window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo odontólogo
    const formulario = document.querySelector('#add_new_odontologo');

    //Ante un submit del formulario se ejecutará la siguiente función
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
       //creamos un JSON que tendrá los datos del nuevo odontólogo
        const formData = {
            matricula: document.querySelector('#matricula').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value
                    };

        //invocamos utilizando la función fetch la API odontólogos con el método POST que guardará
        //el odontólogo que enviaremos en formato JSON
        const url = '/odontologos';
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
                 //Si no hay ningun error se muestra un mensaje diciendo que el odontólogo
                 //se agrego bien
                 let successAlert =
                 '<div class="alert alert-success alert-dismissible">' +
                     '<i class="fa-solid fa-circle-check me-2"></i>' +
                     'Odontologo agregado.' +
                     '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                 '</div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    //Si hay algún error se muestra un mensaje diciendo que el odontólogo
                    //no se pudo guardar y se intente nuevamente
                    let errorAlert =
                    '<div class="alert alert-danger alert-dismissible">' +
                        '<i class="fa-solid fa-circle-xmark me-2"></i>' +
                        '<strong>Error intente nuevamente.</strong>' +
                        '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                    '</div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     //se dejan todos los campos vacíos por si se quiere ingresar otro odontólogo
                     resetUploadForm();})
    });

function resetUploadForm() {
        matricula: document.querySelector('#matricula').value = "";
        matricula: document.querySelector('#nombre').value = "";
        matricula: document.querySelector('#apellido').value = "";
    }

// Creo que esto es para marcar cual navbar esta activa. Lo hice directo en el html
//    (function(){
//        let pathname = window.location.pathname;
//        if(pathname === "/"){
//            document.querySelector(".nav .nav-item a:first").addClass("active");
//        } else if (pathname == "/post_odontologos.html") {
//            document.querySelector(".nav .nav-item a:last").addClass("active");
//        }
//    })();
});