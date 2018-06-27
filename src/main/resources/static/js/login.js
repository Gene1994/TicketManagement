var authorization = null;

function login() {
    $.ajax({
        url: 'http://localhost:8080/login',
        type: 'POST',
        data: {
            username: $('#username').val(),
            password: $('#password').val()
        },
        success: function (xhr, status) {
            authorization = xhr.getResponseHeader('Authorization'
            $.ajax({
                url: 'http://localhost:8080/customer/success',
                type: 'GET',
                headers: {
                    "Authorization": authorization
                }
            })
        }
    })
}