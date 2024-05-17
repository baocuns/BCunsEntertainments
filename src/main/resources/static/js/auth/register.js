$(document).ready(function () {
    let timeout = null;
    // check password and confirm password
    $('#password, #confirm-password').on('keyup', function () {
        const password = $('#password');
        const confirmPassword = $('#confirm-password');
        if (password.val() === confirmPassword.val()) {
            confirmPassword.addClass('border-green-500');
            confirmPassword.removeClass('border-red-500');
            password.addClass('border-green-500');
            password.removeClass('border-red-500');
        } else {
            confirmPassword.addClass('border-red-500');
            confirmPassword.removeClass('border-green-500');
            password.addClass('border-red-500');
            password.removeClass('border-green-500');
        }
    });
    // check username exist
    $('#username').on('keyup', function () {
        const username = $('#username');
        if (username.val().length < 6) {
            username.addClass('border-red-500');
            username.removeClass('border-green-500');
            return;
        }
        if (timeout) clearTimeout(timeout);
        // set timeout
        timeout = setTimeout(function () {
            $.post({
                url: '/api/users/check-username-exist',
                data: {
                    username: username.val()
                },
                success: function (data) {
                    if (data === true) {
                        username.addClass('border-red-500');
                        username.removeClass('border-green-500');
                    } else {
                        username.addClass('border-green-500');
                        username.removeClass('border-red-500');
                    }
                }
            });
            clearTimeout(timeout);
        }, 1000);
    });
    // change username Text to slug
    $('#username').on('keyup', function () {
        const username = $('#username');
        const slug = handleTextToSlug(username.val(), "");
        username.val(slug);
    });
    // check email exist
    $('#email').on('keyup', function () {
        const email = $('#email');
        if (timeout) clearTimeout(timeout);
        // set timeout
        timeout = setTimeout(function () {
            $.post({
                url: '/api/users/check-email-exist',
                data: {
                    email: email.val()
                },
                success: function (data) {
                    if (data === true) {
                        email.addClass('border-red-500');
                        email.removeClass('border-green-500');
                    } else {
                        email.addClass('border-green-500');
                        email.removeClass('border-red-500');
                    }
                }
            });
            clearTimeout(timeout);
        }, 1000);
    });
});