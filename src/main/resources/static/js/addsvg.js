function addUser(username) {
    return '<span class="badge badge-secondary">author:'+username+'</span><span class="badge badge-light"> </span>';
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