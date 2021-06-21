$("#form-add-promo").submit(function (event){
    event.preventDefault();
    var promo = {};
    promo.linkPromocao = $("#linkPromocao").val();
    promo.descricao = $("#descricao").val();
    promo.preco = $("#preco").val();
    promo.titulo = $("#titulo").val();
    promo.categoria = $("#categoria").val();
    promo.linkImagem = $("#imagem").attr("src");
    //promo.site = $("#site").text();

    console.log("Promo >", promo)

    $.ajax({
        method: "POST",
        url: "/save",
        data: promo,
        beforeSend: function (){
            $("#form-add-promo").hide();
            $("#loader-form").addClass("loader").show();
        },
        success: function (){
            $("#form-add-promo").each(function (){
                this.reset();
            })
            $("#imagem").attr("src", "/img/logo.svg")
            $("#site").text("");
            $('#alert').removeClass("alert alert-error").addClass("alert alert-success").text("Promoção cadastrada com sucesso!");
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
        error: function (xhr){
            console.log("Error > ", xhr.responseText);
            $("#alert").addClass("alert alert-error").text("Não foi possível salvar esta promoção");
        },
        complete: function (){
            $("#loader-form").fadeOut(800, function (){
                    $("#form-add-promo").fadeIn(250);
                    $("#loader-form").removeClass("loader");
                })
        }


    })

})

$("#linkPromocao").on("change", function (){
    let url = $(this).val();
    if(url.length > 7){
        $.ajax({
            method: "POST",
            url: "/meta/info?url="+url,
            cache: false,
            beforeSend: function (){
                $("#alert").removeClass("alert alert-danger alert-success").text("");
                $("#titulo").val("");
                //$("#site").text("");
                $("#imagem").attr("src", "/img/logo.svg");
            },
            success: function(data){
                console.log(data)
                $("#titulo").val(data.title);
                //$("#site").text(data.site.replace("@", ""));
                $("#imagem").attr("src", data.image);

            },
            statusCode:{
                404: function (){
                    $("#alert").addClass("alert alert-error").text("Nenhuma informação pode ser recuperada dessa url");
                }

            },
            error: function (){
                $("#alert").addClass("alert alert-error").text("Algo deu errado, verifique o link inserido!");
            }

        })
    }
})