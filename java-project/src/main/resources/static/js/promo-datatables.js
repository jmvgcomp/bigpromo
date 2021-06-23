$(document).ready(function (){
    var table = $("#table-server").DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        lengthMenu: [10, 15, 20, 25],
        ajax: {
            url: "/datatables/server",
            data: "data"
        },
        columns: [
            {data: 'id'},
            {data: 'titulo'},
            {data: 'linkPromocao'},
            {data: 'descricao'},
            {data: 'preco'},
            {data: 'likes'},
            {data: 'dataCadastro'},
            {data: 'categoria.nome'}
        ],
        dom: 'Bfrtip',
        buttons: [
            {
                text: 'Editar',
                attr: {
                    id: 'btn-editar',
                    type: 'button'
                },
                enable: false,
            },

            {
                text: 'Excluir',
                attr: {
                   id: 'btn-excluir',
                    type: 'button'
                },
                enable: false,
            }
        ]
    })


    $("#table-server thead").on("click", 'tr', function (){
        table.buttons.disable();
    })


    $("#table-server tbody").on("click", 'tr', function (){
        if($(this).hasClass('selected')){
            $(this).removeClass('selected');
            table.buttons.disable();
        }else{
            $('tr.selected').removeClass('selected')
            $(this).addClass('selected')
            table.buttons.enable();

        }
    })

    $('#btn-editar').on('click', function (){
        if(isSelectedRow()){
            $("#modal-form").modal('show');
            let id = getPromoId();
        }
    })
    $('#btn-excluir').on('click', function (){
        if(isSelectedRow()){
            $("#modal-delete").modal('show');
        }

    })

    $("#btn-del-modal").on('click', function (){
        let id = getPromoId();
        $.ajax({
            method: "GET",
            url: "/delete/"+id,
            success: function (){
                $("#modal-delete").modal('hide');
                table.ajax.reload();
            },
            error: function (){
                alert("Ocorreu um erro!")
            }
        })
    })

    function getPromoId(){
        return table.row(table.$('tr.selected')).data().id;
    }

    function isSelectedRow(){
        var trow = table.row(table.$('tr.selected'));
        return trow.data() !== undefined;
    }
})