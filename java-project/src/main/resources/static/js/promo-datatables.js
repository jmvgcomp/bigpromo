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
            var id = getPromoId();
            $.ajax({
                method: "GET",
                url: "/edit/"+id,
                beforeSend: function (){
                    $("#modal-form").modal('show');
                },
                success: function (data){
                    console.log('sucesso')
                    $("#edt_id").val(data.id);
                    $("#edt_titulo").val(data.titulo);
                    $("#edt_descricao").val(data.descricao);
                    $("#edt_preco").val(data.preco.toLocaleString('pt-BR', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    }));
                    $("#edt_categoria").val(data.categoria.id);
                    $("#edt_linkImagem").val(data.linkImagem);
                    $("#edt_imagem").attr('src', data.linkImagem);
                },
                error: function (){
                    alert("ocorreu um erro!")
                }
            })
        }
    })
    $('#btn-excluir').on('click', function (){
        if(isSelectedRow()){
            $("#modal-delete").modal('show');
        }
    })

    $("#btn-edit-modal").on("click", function (){
        var promo = {};
        promo.descricao = $("#edt_descricao").val();
        promo.preco = $("#edt_preco").val();
        promo.titulo = $("#edt_titulo").val();
        promo.categoria = $("#edt_categoria").val();
        promo.linkImagem = $("#edt_linkImagem").val();
        promo.id = $("#edt_id").val();

        $.ajax({
            method: "POST",
            url: "/edit",
            data: promo,
            success: function (){
                $("#modal-form").modal('hide');
                table.ajax.reload();
            },
            statusCode: {
                422: function (xhr){
                    console.log('status error ', xhr.status);
                    let errors = $.parseJSON(xhr.responseText);
                    $.each(errors, function (key, val){
                        $("#error-"+key).addClass('error-validacao').text(val);
                    })
                }
            },
        })


    })

    $("#edt_linkImagem").on('change', function (){
        let link = $(this).val();
        $("edt_imagem").attr("src", link)
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