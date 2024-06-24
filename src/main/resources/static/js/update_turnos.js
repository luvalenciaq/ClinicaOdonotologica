window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
    event.preventDefault();
        let odontologoId = document.querySelector('#turno_id').value;


        const formData = {
            id: document.querySelector('#turno_id').value,
            paciente: {id: document.querySelector('#paciente_id').value },
            odontologo: {id:document.querySelector('#odontologo_id').value},
            fecha: document.querySelector('#fecha').value,

        };

        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => {// alerta
                                                                                 const toastSeleccionada = document.getElementById('toast-success-actualizar');
                                                                                 const toast = new bootstrap.Toast(toastSeleccionada);
                                                                                 toast.show();
                                                                                 setTimeout(function(){
                                                                                     location.reload();
                                                                                 }, 1500);})
                                                                                 .catch(error => {
                                                                                                        // alerta
                                                                                                                                        const toastSeleccionada = document.getElementById('toast-error-actualizar');
                                                                                                                                        const toast = new bootstrap.Toast(toastSeleccionada);
                                                                                                                                        toast.show();
                                                                                                        console.error('Error:', error)});

    })
 })


    function findBy(id) {
          const url = '/turnos/' + id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turno = data;
              document.querySelector('#turno_id').value = turno.id;
              document.querySelector('#paciente_id').value = turno.paciente.id;
              document.querySelector('#odontologo_id').value = turno.odontologo.id;
              document.querySelector('#fecha').value = turno.fecha;

              document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }