var userLoginStatus=false;
function readUserInfo() {
    $.post("/getCurrentUser",
        {
        },
        function (data, status) {
            if (data && status == 'success') {
                var appendHtml = '';
                if(data.id!=0){
                    userLoginStatus=true;
                    $("#logInOrLogout").text("Logout");
                    appendHtml=appendHtml+'<li class="nav-item"><img class="rounded" src="'+data.image+'" style="height: 30px;width: 30px;"/></li>';
                }else{
                    userLoginStatus=false;
                    $("#logInOrLogout").text("Login");
                    $("#GoToWritePage").hide();
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
$("#GoToRegisterFromLogin").click(function () {
    $("#exampleModalCenter").modal('hide');
    $("#RegisterModal").modal('show');
});
$("#LoginModalEmail").bind("input propertychange",function () {
    if(isValidEMail($("#LoginModalEmail").val())){
        if($("#LoginModalEmail").hasClass("is-invalid")){
            $("#LoginModalEmail").removeClass("is-invalid");
        }
    }else{
        if(!$("#LoginModalEmail").hasClass("is-invalid")){
            $("#LoginModalEmail").addClass("is-invalid");
        }
    }
});
$("#LoginModalPassword").bind("input propertychange",function () {
    if($('#LoginModalPassword').val().length>6){
        if($("#LoginModalPassword").hasClass("is-invalid")){
            $("#LoginModalPassword").removeClass("is-invalid");
        }
    }else{
        if(!$("#LoginModalPassword").hasClass("is-invalid")){
            $("#LoginModalPassword").addClass("is-invalid");
        }
    }
});
$("#LoginInButton").click(function () {
    if(!isValidEMail($("#LoginModalEmail").val())){
        if(!$("#LoginModalEmail").hasClass("is-invalid")){
            $("#LoginModalEmail").addClass("is-invalid");
        }
    }else if($('#LoginModalPassword').val().length<=6){
        if(!$("#LoginModalPassword").hasClass("is-invalid")){
            $("#LoginModalPassword").addClass("is-invalid");
        }
    } else {
        if ($('#LoginModalEmail').val() != "" && $('#LoginModalPassword').val() != "") {
            $.post("/user/login",
                {
                    email: $("#LoginModalEmail").val(),
                    password: $("#LoginModalPassword").val()
                },
                function (data, status) {
                    if (data.state && data.state == 1) {
                        $("#LoginInFormMessage").hide();
                        window.location.reload();
                    } else {
                        $("#LoginInFormMessage").show();
                        $("#LoginInFormMessage").text("Password or email error");
                    }
                });
        }
    }
});

function isValidEMail(email){
    if(email){
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(!reg.test(email)){
            return false;
        }else{
            return true;
        }
    }else{
        return false;
    }

}
//检测Email是否输入正确
$("#RegisterModalEmail").bind("input propertychange",function () {
    if(isValidEMail($("#RegisterModalEmail").val())){
        if($("#RegisterModalEmail").hasClass("is-invalid")){
            $("#RegisterModalEmail").removeClass("is-invalid");
        }
    }else{
        if(!$("#RegisterModalEmail").hasClass("is-invalid")){
            $("#RegisterModalEmail").addClass("is-invalid");
        }
    }
});
//检测用户名长度是否大于10
$("#RegisterModalUserName").bind("input propertychange",function () {
    if($("#RegisterModalUserName").val().length<=10){
        if($("#RegisterModalUserName").hasClass("is-invalid")){
            $("#RegisterModalUserName").removeClass("is-invalid");
        }
    }else{
        if(!$("#RegisterModalUserName").hasClass("is-invalid")){
            $("#RegisterModalUserName").addClass("is-invalid");
        }
    }
});
$("#RegisterModalPassword").bind("input propertychange",function () {
    if($("#RegisterModalPassword").val().length>6){
        if($("#RegisterModalPassword").hasClass("is-invalid")){
            $("#RegisterModalPassword").removeClass("is-invalid");
        }
    }else{
        if(!$("#RegisterModalPassword").hasClass("is-invalid")){
            $("#RegisterModalPassword").addClass("is-invalid");
        }
    }
});
//检测重复密码是否正确
$("#RegisterModalRepeatPassword").bind("input propertychange",function () {
    if($("#RegisterModalRepeatPassword").val()==$("#RegisterModalPassword").val()){
        if($("#RegisterModalRepeatPassword").hasClass("is-invalid")){
            $("#RegisterModalRepeatPassword").removeClass("is-invalid");
        }
    }else{
        if(!$("#RegisterModalRepeatPassword").hasClass("is-invalid")){
            $("#RegisterModalRepeatPassword").addClass("is-invalid");
        }
    }
});
$("#RegisterModalAuthCode").bind("input propertychange",function () {
    if($("#RegisterModalAuthCode").val()>=100000&&$("#RegisterModalAuthCode").val()<=999999){
        if($("#RegisterModalAuthCode").hasClass("is-invalid")){
            $("#RegisterModalAuthCode").removeClass("is-invalid");
        }
    }else{
        if(!$("#RegisterModalAuthCode").hasClass("is-invalid")){
            $("#RegisterModalAuthCode").addClass("is-invalid");
        }
    }
});
//获取验证码
$("#RegisterModalGetAuthCode").click(function () {
    if(isValidEMail($("#RegisterModalEmail").val())) {
        $.post("/user/getAuthCode",{
            to:$("#RegisterModalEmail").val()
        },function (data,status) {
            if(data&&data.state){
                if(data.state=="-1"){
                    //-1说明已经被注册了
                    if(!$("#RegisterModalEmail").hasClass("is-invalid")){
                        $("#RegisterModalEmail").addClass("is-invalid");
                    }
                }else{
                    $("#RegisterModalGetAuthCode").attr("disable","true");
                }
            }
        })
    }
});
$("#RegisterModalButton").click(function () {
   //先需要判断所有的输入是否正确
   if($("#RegisterModalAuthCode").val()<100000||$("#RegisterModalAuthCode").val()>999999){
       if(!$("#RegisterModalAuthCode").hasClass("is-invalid")){
           $("#RegisterModalAuthCode").addClass("is-invalid");
       }
   }else if(!isValidEMail($("#RegisterModalEmail").val())){
       if(!$("#RegisterModalEmail").hasClass("is-invalid")){
           $("#RegisterModalEmail").addClass("is-invalid");
       }
   }else if($("#RegisterModalUserName").val().length>10){
       if(!$("#RegisterModalUserName").hasClass("is-invalid")){
           $("#RegisterModalUserName").addClass("is-invalid");
       }
   }else if($("#RegisterModalPassword").val().length<=6){
       if(!$("#RegisterModalPassword").hasClass("is-invalid")){
           $("#RegisterModalPassword").addClass("is-invalid");
       }
   }else if($("#RegisterModalRepeatPassword").val()!=$("#RegisterModalPassword").val()){
       if(!$("#RegisterModalRepeatPassword").hasClass("is-invalid")){
           $("#RegisterModalRepeatPassword").addClass("is-invalid");
       }
   }else{
       $.post("/createUser",{
           username:$("#RegisterModalUserName").val(),
           password:$("#RegisterModalPassword").val(),
           email:$("#RegisterModalEmail").val(),
           authCode:$("#RegisterModalAuthCode").val()
       },function (data,status) {
           console.log(data);
           if(data&&data.state){
               if(data.state==1){
                   $("#RegisterModal").modal('hide');
                   $("#exampleModalCenter").modal('show');
               }
           }
       })
   }
});


