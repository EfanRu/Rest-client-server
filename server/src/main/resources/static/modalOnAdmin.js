$(document).ready(function() {

    $(document).on('click', 'button[data-toggle=modal]', function() {
        var id = $(this).attr('id');

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/admin/" + id,
            // url: "/admin/all",
            dataType: 'json',
            success: function (user) {
                console.log("SUCCESS : ", user);
                var data = '';

                data += '\n' +
                    '                                            <form class="editFromClass" action="/admin" method="put" id="' + user.id + '">\n' +
                    '                                                <div class="form-group text-center">\n' +
                    '                                                    <b>Id:</b><br>\n' +
                    '                                                    <input class="input-lg" type="text" name="id" value="' + user.id + '" id="editUser"><br>\n' +
                    '                                                    <b>First name:</b><br>\n' +
                    '                                                    <input class="input-lg" type="text" name="firstName" value="' + user.firstName + '" id="editFirstName"><br>\n' +
                    '                                                    <b>Last name:</b><br>\n' +
                    '                                                    <input class="input-lg" type="text" name="lastName" value="' + user.lastName + '" id="editLastName"><br>\n' +
                    '                                                    <b>Phone number:</b><br>\n' +
                    '                                                    <input class="input-lg" type="text" name="phoneNumber" value="' + user.phoneNumber + '" id="editPhoneNumber"><br>\n' +
                    '                                                </div>\n' +
                    '                                                <div class="text-center"><b>Role</b>\n' +
                    '                                                </div>\n' +
                    '                                                <select class="form-control" style="position: relative; left: 50%; transform: translate(-50%,0); margin-bottom: 20px; width: 265px; height: 55px;" name = "role" id="editRole">\n' +
                    '                                                    <option value="admin">admin</option>\n' +
                    '                                                    <option value="user">user</option>\n' +
                    '                                                </select>\n' +
                    '                                                <br>\n' +
                    '                                                <div class="form-group text-center">\n' +
                    '                                                    <b>Login:</b><br>\n' +
                    '                                                    <input class="input-lg" type="text" name="login" value="' + user.login + '" id="editLogin"><br>\n' +
                    '                                                    <b>Password:</b><br>\n' +
                    '                                                    <input class="input-lg" type="password" name="password" id="editPassword">\n' +
                    '                                                    <br>' +
                    '                                                 </div>' +
                    '                                              </form>';

                $('#modal-warning').show();
                $('#modalContent').html(data);
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

    });

});