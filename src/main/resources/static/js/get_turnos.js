window.addEventListener('load', function () {


      const url = '/turnos';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
         for(turno of data){
            let table = document.getElementById("turnoTableBody");
            let turnoRow =table.insertRow();
            let tr_id = 'tr_'+turno.id;
            turnoRow.id = tr_id;

            let deleteButton =
                            '<button id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                                ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete btn-sm">' +
                                    '<i class="fa-solid fa-trash"></i>' +
                            '</button>';


                        let updateButton =
                        '<button' +
                            'id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                            ' type="button" onclick="findBy('+turno.id+')" class="btn btn-info btn_id btn-sm">' +
                                '<i class="fa-solid fa-pencil"></i>' +
                        '</button>';


            turnoRow.innerHTML = '<td class="text-center align-middle">' + turno.id + '</td>' +
                    '<td class=\"td_paciente align-middle"\">' + turno.paciente.id + '</td>' +
                    '<td class=\"td_odontologo align-middle"\">' + turno.odontologo.id + '</td>' +
                    '<td class=\"td_fecha align-middle"\">' + turno.fecha + '</td>' +
                    '<td class="text-center align-middle">' + updateButton + '</td>' +
                    '<td class="text-center align-middle">' + deleteButton + '</td>';

        };
      })

//    (function(){
//      let pathname = window.location.pathname;
//      if (pathname == "/get_turnos.html") {
//          document.querySelector(".nav .nav-item a:last").addClass("active");
//      }
//    })
})