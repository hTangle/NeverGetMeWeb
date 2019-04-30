function readUserInfo() {
    $.post("/getCurrentUser",
        {
        },
        function (data, status) {
            if (data && status == 'success') {
                var appendHtml = '';
                if(data.id!=0){
                    appendHtml=appendHtml+'<li class="nav-item">\n' +
                        '                <a class="nav-link" href="/logout">Logout</a>\n' +
                        '            </li>';
                    appendHtml=appendHtml+'<li class="nav-item"><img class="rounded" src="'+data.image+'" style="height: 30px;width: 30px;"/></li>';
                }else{
                    appendHtml=appendHtml+'<li class="nav-item">\n' +
                        '                <a class="nav-link" href="/login">Login</a>\n' +
                        '            </li>';
                }
                $("#footerUser").append(appendHtml);
            }
            console.log(status);
        });
}