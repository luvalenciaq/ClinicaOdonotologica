function deleteBy(id) {
    // Obtener referencia al modal
    let modalElement = document.querySelector('.modal');
    let modal = new bootstrap.Modal(modalElement);

    // Mostrar modal
    modal.show();

    // Configurar evento para el botón "Guardar cambios"
    let btnSaveChanges = document.querySelector('.modal-footer .btn-primary');
    btnSaveChanges.onclick = function() {
        // Ocultar modal
        modal.hide();

        // Variables para la URL y la configuración de la solicitud
    const url = '/odontologos/' + id;
    const settings = {
    method: 'DELETE'
    };

        // Procesar eliminación del odontólogo
    fetch(url, settings)
       .then(response => {
           if (response.ok) {
            console.log('Odontólogo eliminado con éxito');

                    // Eliminar la fila de la tabla correspondiente al odontólogo eliminado
                    let row_id = '#tr_' + id;
                    let rowToRemove = document.querySelector(row_id);
                    if (rowToRemove) {
                        rowToRemove.remove();

                    } else {
                        console.error('La fila a eliminar no se encontró en el DOM');
                    }

// alerta
                const toastSeleccionada = document.getElementById('toast-success-eliminar');
                const toast = new bootstrap.Toast(toastSeleccionada);
                toast.show();
                setTimeout(function(){
                                                                        location.reload();
                                                                    }, 1500);

                } else {
                    throw new Error('Error al eliminar el odontólogo');
                    // alerta
                                                        const toastSeleccionada = document.getElementById('toast-error-eliminar');
                                                        const toast = new bootstrap.Toast(toastSeleccionada);
                                                        toast.show();
                }
            })
            .catch(error => {console.error('Error:', error)
            // alerta
                                                const toastSeleccionada = document.getElementById('toast-error-eliminar');
                                                const toast = new bootstrap.Toast(toastSeleccionada);
                                                toast.show();
            });
    };

    // Configurar evento para el botón "Cerrar"
    let btnCancel = document.querySelector('.modal-footer .btn-secondary');
    btnCancel.onclick = function() {
        // Ocultar modal sin realizar ninguna acción
        modal.hide();
    };
}