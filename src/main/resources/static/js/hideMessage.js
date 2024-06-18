window.onload = function() {
    setTimeout(function() {
        const successMsg = document.querySelector('.alert-success', );
        const errorMsg = document.querySelector('.alert-danger');
        if (successMsg) {
            successMsg.style.opacity = '0';
            setTimeout(function() {
                successMsg.style.display = 'none';
            }, 1000);
        }
        if (errorMsg) {
            errorMsg.style.opacity = '0';
            setTimeout(function() {
                errorMsg.style.display = 'none';
            }, 1000);
        }
    }, 5000);
};