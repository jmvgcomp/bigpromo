console.log("teste")
$("#linkPromocao").on("change", function (){
    let url = $(this).val();
    if(url.length > 7){
        $.ajax({
            method: "POST",
            url: "/meta/info?url="+url,
            cache: false,
            beforeSend: function (){
                $("#alert").removeClass("alert alert-danger").text("");
                $("#titulo").val("");
                // $("#site").text(""));
                $("#imagem").attr("src", "img/logo.svg");
            },
            success: function(data){
                console.log(data)
                $("#titulo").val(data.title);
               // $("#site").text(data.site.replace("@", ""));
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