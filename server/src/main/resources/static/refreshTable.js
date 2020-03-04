function refresh_user_table() {
    $.ajax({
        url: '/admin/all',
        type: 'GET',
        dataType: "json",
        success: function (response) {
            var data = '';
            $.each(response, function (index, user) {
                data += '<tr>';
                data += '<td>' + user.id + '</td>';
                data += '<td>' + user.firstName + '</td>';
                data += '<td>' + user.lastName + '</td>';
                data += '<td>' + user.login + '</td>';
                data += '<td>' + user.phoneNumber + '</td>';
                data += '<td>' + user.role.name + '</td>';
                data += '<td>' +
                    '                                                <form class="deleteFromClass" action="/admin" method="delete" id="' + user.id + '">\n' +
                    '                                                    <button class="btn btn-danger btn-xs" type="button" name="id" value="' + user.id + '" id="delId"><span class="glyphicon glyphicon-trash"></span></button>\n' +
                    '                                                </form>\n' +
                    '                                            </td>\n' +
                    '                                            <td>\n' +
                    '                                                <form class="editFromClass" id="' + user.id + '">\n' +
                    '                                                    <button href="#modal-warning" type="button" class="btn btn-primary btn-xs" data-toggle="modal" id="' + user.id + '">edit</button>\n' +
                    '            <!--                            Modal window-->\n' +
                    '                                                    <div class="modal modal-warning fade in" data-backdrop="false" id="modal-warning">\n' +
                    '                                                        <div class="modal-dialog">\n' +
                    '                                                            <div class="modal-content">\n' +
                    '                                                                <div class="modal-header">\n' +
                    '                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
                    '                                                                        <span aria-hidden="true">×</span></button>\n' +
                    '                                                                    <h4 class="modal-title">Edit user</h4>\n' +
                    '                                                                </div>\n' +
                    '                                                                <div class="modal-body">\n' +
                    '                                                                    <div id="modalContent"></div>\n' +
                    '                                                                </div>\n' +
                    '                                                                <div class="modal-footer">\n' +
                    '                                                                    <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>\n' +
                    '                                                                    <button type="button" class="btn btn-outline editFromClassBut">Change</button>\n' +
                    '                                                                </div>;\n' +
                    '                                                            </div>\n' +
                    '                                                        </div>\n' +
                    '                                                    </div>\n' +
                    '                                                </form>\n' +
                    '                                            </td>\n' +
                    '                                        </tr>\n';

            });
            $('#user_table').html(data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$(document).ready(function() {
    refresh_user_table();
});