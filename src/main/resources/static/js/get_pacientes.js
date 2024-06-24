window.addEventListener('load', function () {

      const url = '/pacientes';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        let tableBody = document.getElementById("pacienteTableBody");
         for(paciente of data){

            let pacienteRow = tableBody.insertRow();
            let tr_id = 'tr_'+paciente.id;
            pacienteRow.id = tr_id;


            let deleteButton =
                '<button id=' + '\"' + 'btn_delete_' + paciente.id + '\"' +
                    ' type="button" onclick="deleteBy('+paciente.id+')" class="btn btn-danger btn_delete btn-sm">' +
                        '<i class="fa-solid fa-trash"></i>' +
                '</button>';


            let updateButton =
            '<button' +
                'id=' + '\"' + 'btn_id_' + paciente.id + '\"' +
                ' type="button" onclick="findBy('+paciente.id+')" class="btn btn-info btn_id btn-sm">' +
                    '<i class="fa-solid fa-pencil"></i>' +
            '</button>';


            pacienteRow.innerHTML =
                    '<td class=\"td_id align-middle text-center\">' + paciente.id + '</td>' +
                    '<td class=\"td_nombre align-middle\">' + paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellido align-middle\">' + paciente.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_cedula align-middle\">' + paciente.cedula.toUpperCase() + '</td>' +
                    '<td class=\"td_fechaIngreso align-middle\">' + paciente.fechaIngreso + '</td>' +
                    '<td class=\"td_calle align-middle\">' + paciente.domicilio.calle.toUpperCase() + '</td>' +
                    '<td class=\"td_email align-middle\">' + paciente.email.toUpperCase() + '</td>' +
                    '<td class="align-middle text-center">' + updateButton + '</td>' +
                    '<td class="align-middle text-center">' + deleteButton + '</td>';

        };
      })


    // Creo que esto es para marcar cual navbar esta activa. Lo hice directo en el html
    //    (function(){
    //      let pathname = window.location.pathname;
    //      if (pathname == "/get_pacientes.html") {
    //          document.querySelector(".nav .nav-item a:last").addClass("active");
    //      }
    //    })
})
