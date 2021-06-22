$(document).ready(function (){
    $("#table-server").DataTable({
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
        ]
    })
})