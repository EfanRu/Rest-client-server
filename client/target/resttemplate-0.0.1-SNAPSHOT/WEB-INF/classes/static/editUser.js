$(document).ready(function() {

    $(document).on('click', '.editFromClassBut', function (event) {
        event.preventDefault();
        edit_user_submit();
    });

    function edit_user_submit() {

        var id = $('#editUser').val();

        var editUser = {}
        editUser["id"] = id;
        editUser["firstName"] = $('#editFirstName').val();
        editUser["lastName"] = $('#editLastName').val();
        editUser["phoneNumber"] = $('#editPhoneNumber').val();
        editUser["role"] = $('#editRole').val();
        editUser["login"] = $('#editLogin').val();
        editUser["password"] = $('#editPassword').val();
        if (editUser["login"] === "") {
            $('#errorInEditUser').show();
            throw new DOMException("User login is null");
        }

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/admin/" + id,
            data: JSON.stringify(editUser),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                refresh_user_table();
                $('#errorInEditUser').hide();
                $('#modal-warning').close();
            },
            error: function (e) {
                console.log("ERROR : ", e);
                $('#errorInEditUser').show();
            }
        });

    }

});