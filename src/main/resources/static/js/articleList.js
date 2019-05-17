function updateArticleList(articleList) {
    $("#articleTables").empty();
    var appendHtml = '';
    for (var i in articleList) {
        var article = articleList[i];
        var isStickHtml='';
        if(article.isStick==1){
            isStickHtml='<h6 style="display: inline;"><span style="display: inline;vertical-align:text-top" class="badge badge-danger">置顶</span></h6>';
        }
        var coverBegin='';
        var coverEnd='';
        if(article.cover){
            coverBegin='<div class="row no-gutters"><div class="col-md-3"><img src="'+article.cover+'" class="card-img img-fluid" alt="Responsive image"></div><div class="col-md-9">';
            coverEnd='</div></div>';
        }
        appendHtml=appendHtml+'<div class="card mb-3 shadow-lg p-3 bg-white rounded">'+coverBegin+'<div class="card-body">';
        appendHtml = appendHtml + '\n' +
            '                \n' +
            '<div class="card-title"><h3 style="display: inline;"><a target="_blank"  href="/article/showArticle/' + article.id + '"\n' +
            'style="display: inline;color: #000000">' + article.title+isStickHtml + '</a>'+'</h3>' +
            '<div class="text-muted">\n' +
            '<span class="badge badge-secondary">author:'+article.author.username+
            '</span><span class="badge badge-light"> </span><span class="badge badge-success">date:' + article.publishDate +
            '</span><span class="badge badge-light"> </span><span class="badge badge-info">eyes:'+article.visitTimes+
            '  </span></div>' +
            '</div>' +
            '<div class="card-text">' + article.shortcut + '<a href="/article/showArticle/'+article.id+'">阅读全文</a>\n' +
            '</div>\n';
        var tagsList=article.tagsList;
        appendHtml=appendHtml+'<div class="card-text text-muted">'
        for(var j in tagsList){
            appendHtml=appendHtml+'<span class="badge badge-warning">'+tagsList[j].value+'</span><span class="badge badge-light"> </span>';
        }
        appendHtml=appendHtml+'</div></div></div>'+coverEnd;
    }
    $("#articleTables").append(appendHtml);
}
function updateFooter(data){
    if(data>=5){//fixed-bottom sticky-bottom
        //不小于5的时候应该sticky-bottom
        if($("#footer").hasClass("fixed-bottom")){
            $("#footer").removeClass("fixed-bottom");
        }
        if(!$("#footer").hasClass("sticky-bottom")){
            $("#footer").addClass("sticky-bottom");
        }
    }else{
        if(!$("#footer").hasClass("fixed-bottom")){
            $("#footer").addClass("fixed-bottom");
        }
        if($("#footer").hasClass("sticky-bottom")){
            $("#footer").removeClass("sticky-bottom");
        }
    }
}
function updateArticle(pageInfos) {
    $("#pageInfoNav").empty();
    if (pageInfos.pageNum == 1 && pageInfos.pages == 1) {
        return;
    }
    var appendHtml = ""
    if (pageInfos.pageNum != 1) {
        appendHtml = appendHtml + '<li class="page-item">\n' +
            '            <a class="page-link" onclick="readCurrentPage(currentPage-1)" aria-label="Previous" tabindex="-1"><span aria-hidden="true">&laquo;</span></a>\n' +
            '        </li>';
    }
    for (var i = 1; i <= pageInfos.pages; i++) {
        if (i == pageInfos.pageNum) {
            appendHtml = appendHtml + '<li class="page-item active">\n' +
                '            <a class="page-link" onclick="readCurrentPage(' + i + ')">' + i + '</a>\n' +
                '        </li>';
        } else {
            appendHtml = appendHtml + '<li class="page-item">\n' +
                '            <a class="page-link" onclick="readCurrentPage(' + i + ')">' + i + '</a>\n' +
                '        </li>';
        }
    }
    if (pageInfos.pageNum != pageInfos.pages) {
        appendHtml = appendHtml + '<li class="page-item">\n' +
            '            <a class="page-link" aria-label="Next" onclick="readCurrentPage(currentPage+1)"><span aria-hidden="true">&raquo;</span></a>\n' +
            '        </li>';
    }
    $("#pageInfoNav").append(appendHtml);
}