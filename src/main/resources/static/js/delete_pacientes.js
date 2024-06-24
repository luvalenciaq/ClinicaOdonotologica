function deleteBy(id) {
    let modalElement = document.querySelector('#modal-eliminar');
    let modal = new bootstrap.Modal(modalElement);

    modal.show();

    let btnSaveChanges = document.querySelector('#btn-eliminar');
    btnSaveChanges.onclick = function() {
        const url = '/pacientes/' + id;
        const settings = {
            method: 'DELETE'
        };

        fetch(url, settings)
            .then(response => {
                if (response.ok) {
                console.log('Paciente eliminado con éxito');

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
                // alerta
                                    const toastSeleccionada = document.getElementById('toast-error-eliminar');
                                    const toast = new bootstrap.Toast(toastSeleccionada);
                                    toast.show();
                    throw new Error('Error al eliminar el paciente');
                }
            })
            .catch(error => {
            // alerta
                                const toastSeleccionada = document.getElementById('toast-error-eliminar');
                                const toast = new bootstrap.Toast(toastSeleccionada);
                                toast.show();
                console.error('Error:', error);
            });

        modal.hide();
    };


    let btnCancel = document.querySelector('.modal-footer .btn-secondary');
    btnCancel.onclick = function() {

        modal.hide();
    };
}