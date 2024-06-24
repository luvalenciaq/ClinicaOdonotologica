function deleteBy(id) {
    let modalElement = document.querySelector('.modal');
    let modal = new bootstrap.Modal(modalElement);

    modal.show();

    let btnSaveChanges = document.querySelector('.modal-footer .btn-primary');
    btnSaveChanges.onclick = function() {
        modal.hide();

        const url = '/turnos/' + id;
        const settings = {
            method: 'DELETE'
        };

        fetch(url, settings)
            .then(response => {
                if (response.ok) {
                console.log('Turno eliminado con éxito');

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

                    throw new Error('Error al eliminar el turno');
                }
            })
            .catch(error => {console.error('Error:', error); // alerta
                                                                                                const toastSeleccionada = document.getElementById('toast-error-eliminar');
                                                                                                const toast = new bootstrap.Toast(toastSeleccionada);
                                                                                                toast.show();});
    };


    let btnCancel = document.querySelector('.modal-footer .btn-secondary');
    btnCancel.onclick = function() {

        modal.hide();
    };
}