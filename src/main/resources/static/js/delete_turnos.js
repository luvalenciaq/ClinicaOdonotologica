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

                    location.reload();
                } else {
                    throw new Error('Error al eliminar el turno');
                }
            })
            .catch(error => console.error('Error:', error));
    };


    let btnCancel = document.querySelector('.modal-footer .btn-secondary');
    btnCancel.onclick = function() {

        modal.hide();
    };
}