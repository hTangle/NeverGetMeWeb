function readUserInfo() {
    $.post("/getCurrentUser",
        {
        },
        function (data, status) {
            if (data && status == 'success') {
                var appendHtml = '';
                if(data.id!=0){
                    $("#logInOrLogout").text("Logout");
                    appendHtml=appendHtml+'<li class="nav-item"><img class="rounded" src="'+data.image+'" style="height: 30px;width: 30px;"/></li>';
                }else{
                    $("#logInOrLogout").text("Login");
                }
                $("#footerUser").append(appendHtml);
            }
            console.log(status);
        });
}
function userLoginOut(){
    $.post("/user/logout",{},function (data,status) {
        if(data.state&&data.state==1){

        }
        window.location.reload();
    });
}
$("#logInOrLogout").click(function () {
    // console.log("click here");
    console.log($("#logInOrLogout").text());
    if($("#logInOrLogout").text()=="Logout"){
        userLoginOut();
    }else{
        $("#LoginInFormMessage").hide();
        $("#exampleModalCenter").modal('show');
    }

});

$("#LoginInButton").click(function () {
    if($('#loginInFormUserName').val()!=""&&$('#loginInFormPassword').val()!=""){
        $.post("/user/login",
            {
                userName:$("#loginInFormUserName").val(),
                password:$("#loginInFormPassword").val()
            },
            function (data, status) {
                if(data.state&&data.state==1){
                    $("#LoginInFormMessage").hide();
                    window.location.reload();
                }else{
                    $("#LoginInFormMessage").show();
                    $("#LoginInFormMessage").text("Password or Username error");
                }
            });
    }
});

