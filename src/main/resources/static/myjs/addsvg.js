function addUser(username,id) {
    return '<a class="badge badge-secondary" href="/my/'+id+'">author:'+username+'</a><span class="badge badge-light"> </span>';
}
function addDate(publishdate) {
    return '<span class="badge badge-success">date:' + publishdate+'</span><span class="badge badge-light"> </span>';
}
function addLikes(likes){
    return '<span class="badge badge-danger">likes:' + likes+"</span><span class=\"badge badge-light\"> </span>";
}
function addEyes(eyes) {
    return '<span class="badge badge-info">eyes:'+eyes+'</span><span class="badge badge-light"> </span>';
}

function modifyTitleAndIdToATag(title,id) {
    return '<a href="/article/showArticle/'+id+'">'+title+'</a>';
}
function addMessageNotify() {
    return '<span class="badge badge-danger">  </span>'
}
function changeMessageNotify() {
    $("#HaveMessageFlag").removeClass('badge-light');
    $("#HaveMessageFlag").addClass('badge-danger');
}
function changeMessageIcon() {
    $("#MessageIcon").attr('src','/icon/notice.svg');
}