$(document).ready(function() {

    $("#addUser").submit(function (event) {
        event.preventDefault();
        add_new_user_submit();
    });

    function add_new_user_submit() {

        var user = {}
        user["firstName"] = $("#addFirstName").val();
        user["lastName"] = $("#addLastName").val();
        user["phoneNumber"] = $("#addPhoneNumber").val();
        user["role"] = $("#addRole").val();
        user["login"] = $("#addLogin").val();
        user["password"] = $("#addPassword").val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/admin",
            data: JSON.stringify(user),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                refresh_user_table();
                $('#addFirstName').val('');
                $('#addLastName').val('');
                $('#addPhoneNumber').val('');
                $('#addLogin').val('');
                $('#addPassword').val('');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }

});