function getReplies(int level, long id) {
    $.ajax({
        url: '/replies?id=' + id,
        dataType: 'JSON',
        type: 'post',
        //contentType: 'application/x-www-form-urlencoded',
        //data: $(this).serialize(),
        success: function( data, textStatus, jQxhr ){
            $('#response pre').html( data );
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });

}