window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
    event.preventDefault();
        let pacienteId = document.querySelector('#paciente_id').value;

        const formData = {
            id: pacienteId,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: document.getElementById('calle').value,
                numero: document.getElementById('numero').value,
                localidad: document.getElementById('localidad').value,
                provincia: document.getElementById('provincia').value
            },
            email: document.querySelector('#email').value
        };

        const url = '/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
            .then(response => {
                if (response.ok) {
                    console.log('Paciente actualizado con éxito:');
                                    // alerta
                                                    const toastSeleccionada = document.getElementById('toast-success-actualizar');
                                                    const toast = new bootstrap.Toast(toastSeleccionada);
                                                    toast.show();
                                                    setTimeout(function(){
                                                        location.reload();
                                                    }, 1500);

                } else {
                    throw new Error('Error al actualizar el paciente');
                    // alerta
                                                                const toastSeleccionada = document.getElementById('toast-error-actualizar');
                                                                const toast = new bootstrap.Toast(toastSeleccionada);
                                                                toast.show();
                }
            })
            .catch(error => {
            // alerta
                                            const toastSeleccionada = document.getElementById('toast-error-actualizar');
                                            const toast = new bootstrap.Toast(toastSeleccionada);
                                            toast.show();
            console.error('Error:', error)});
    });


    window.findBy = function (id) {
        const url = '/pacientes/' + id;
        const settings = {
            method: 'GET'
        };
        fetch(url, settings)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Error al buscar el paciente');
                }
            })
            .then(data => {
                let paciente = data;
                document.querySelector('#paciente_id').value = paciente.id;
                document.querySelector('#nombre').value = paciente.nombre;
                document.querySelector('#apellido').value = paciente.apellido;
                document.querySelector('#cedula').value = paciente.cedula;
                document.querySelector('#fechaIngreso').value = paciente.fechaIngreso;
                if (paciente.domicilio) {  // Verifica si el domicilio no es null
                    document.querySelector('#calle').value = paciente.domicilio.calle;
                    document.querySelector('#numero').value = paciente.domicilio.numero;
                    document.querySelector('#localidad').value = paciente.domicilio.localidad;
                    document.querySelector('#provincia').value = paciente.domicilio.provincia;
                }
                document.querySelector('#email').value = paciente.email;

                    document.querySelector('#div_paciente_updating').style.display = "block";
            })
            .catch(error => {
                alert("Error: " + error);
            });
    };
});