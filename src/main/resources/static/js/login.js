var authorization = null;
function login1() {
    $.ajax({
        url: 'http://localhost:8080/login',
        type: 'POST',
        data: {
            username: $('#username').val(),
            password: $('#password').val()
        },
        complete: function (xhr, status) {
            authorization = xhr.getResponseHeader('Authorization')
            $.ajax({
                url: 'http://localhost:8080/customer/login',
                type: 'POST',
                headers: {
                    "Authorization" : authorization
                },
                data: {
                    username: $('#username').val(),
                    password: $('#password').val()
                },
                success: function (result) {
                    $('html').html(result)
                }
            })
        }
    })
}