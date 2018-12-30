function getReplies(level, id) {
    //level is integer value showing calling node's depth in tree,
    // id is calling div's id (which is contained tweet's id)
    $.ajax({
        url: '/replies?id=' + id,
        dataType: 'JSON',
        type: 'post',
        //contentType: 'application/x-www-form-urlencoded',
        //data: $(this).serialize(),
        success: function( data, textStatus, jQxhr ){
            $(".deneme").append("<p>Hello World!</p>");
            $('#response pre').html( data );
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}