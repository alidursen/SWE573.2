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
            let i;
            for(i of data){
                $("."+id).append("<div onclick='getReplies("
                    + (level+1) +", &#39;"
                    + i.id_str + "&#39;)' class= '"
                    + (level+1) + " unclicked "
                    + i.id_str +"'><p>"
                    + i.full_text + "<br><a href='https://www.twitter.com/statuses/"
                    + i.id_str + "'>"
                    + i.created_at +"</a></p></div>");
            }

        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });

    if (!e) var e = window.event;
    e.cancelBubble = true;
    if (e.stopPropagation) e.stopPropagation();
}