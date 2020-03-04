$(document).ready(function() {

    $(document).on('click', ".deleteFromClass", function (event) {
        event.preventDefault();
        var delUserId = $(this).attr('id');
        $('#' + delUserId).on(delete_user_submit(delUserId));
    });

    function delete_user_submit(id) {

        $.ajax({
            type: "DELETE",
            url: "/admin/" + id,
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                console.log("SUCCESS : ", result);
                refresh_user_table();
            },
            error: function (e) {
                console.log("ERROR : ", e);
                refresh_user_table();
            }
        });
    }

});