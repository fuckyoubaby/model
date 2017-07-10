var tableRows = 0;
var tableMaxPage = 7;
var refreshFun;

function generatePagingHtml(currentPage, totalPage, maxPageNumber) {
    var html = '';
    if (totalPage != 0) {
        html += createNumberPageLinks(currentPage, totalPage, maxPageNumber);
        html += createGoToLink(currentPage, totalPage);
        html += createPageInformation(currentPage, totalPage);
    }
    return html;
}

function createNumberPageLinks(currentPage, totalPage, maxPageNumber) {
    var numberPageLinks;
    if (maxPageNumber >= totalPage) {
        numberPageLinks = getNumberPageLinks(1, currentPage, totalPage, totalPage);
    } else {
        if ((currentPage - parseInt(maxPageNumber / 2) - 1) <= 0) {
            numberPageLinks = getNumberPageLinks(1, currentPage, maxPageNumber, totalPage);
        } else if ((currentPage + parseInt(maxPageNumber / 2) - 1) >= totalPage) {
            numberPageLinks = getNumberPageLinks(totalPage - maxPageNumber + 1, currentPage, totalPage, totalPage);
        } else {
            numberPageLinks = getNumberPageLinks(currentPage - parseInt(maxPageNumber / 2), currentPage, currentPage + parseInt(maxPageNumber / 2) - ((maxPageNumber % 2) == 0 ? 1 : 0), totalPage);
        }
    }
    return numberPageLinks;
}

function createGoToLink(currentPage, totalPage) {
    var goToHtml = '<li style="margin:1px;"><span style="border-style: none;">跳转到</span></li>';
    goToHtml += '<li style="margin:1px;"><input style="width:40px;height:19px;" id="specificPageNumber" type="text" value="' + currentPage + '"/></li>';
    goToHtml += '<li style="margin:1px;"><a href="javascript:void(0);" onclick="gotoClick(' + totalPage + ')">Go</a></li>';
    return goToHtml;
}

function createPageInformation(currentPage, totalPage) {
    var pageInfoHtml = '<li style="margin:1px;"><span style="border-style: none;">(' + currentPage + ' / ' + totalPage + ')</span></li>';
    return pageInfoHtml;
}

function getNumberPageLinks(startPage, currentPage, endPage, totalPage) {
    var pagingHtml = '';
    if (currentPage != 1) {
        pagingHtml += '<li style="margin:1px;"><a href="javascript:void(0);" onclick="pagingClick(' + (currentPage - 1) + ',' + totalPage + ')">&lt</a></li>';
    } else {
        pagingHtml += '<li style="margin:1px;"><a style="background:#BDBDBD;" href="javascript:void(0);">&lt</a></li>';
    }

    for (var i = startPage; i <= endPage; i++) {
        if (i != currentPage) {
            pagingHtml += createNumberPageLink(i, false, totalPage);
        } else {
            pagingHtml += createNumberPageLink(i, true, totalPage);
        }
    }

    if (currentPage != endPage) {
        pagingHtml += '<li style="margin:1px;"><a href="javascript:void(0);" onclick="pagingClick(' + (currentPage + 1) + ',' + totalPage + ')">&gt</a></li>';
    } else {
        pagingHtml += '<li style="margin:1px;"><a style="background:#BDBDBD;" href="javascript:void(0);">&gt</a></li>';
    }

    return pagingHtml;
}

function createNumberPageLink(pageNum, currentPage, totalPage){
    var html;
    if (currentPage) {
        html = '<li class="cur" style="margin:1px;"><a href="javascript:void(0);" onclick="pagingClick(' + pageNum + ',' + totalPage + ');">' + pageNum + '</a></li>';
    } else {
        html = '<li style="margin:1px;"><a href="javascript:void(0);" onclick="pagingClick(' + pageNum + ',' + totalPage + ');">' + pageNum + '</a></li>';
    }
    return html;
}

function gotoClick(totalPage){
    var gotoPage = jQuery("#specificPageNumber").val();
    if (gotoPage == null || gotoPage == '' || isNaN(gotoPage)) {
    } else {
        pagingClick(parseInt(gotoPage), totalPage);
    }
}

function pagingClick(pageNum, totalPage) {
    if (pageNum <= totalPage) {
        refreshFun(pageNum, tableRows);
    } else {
        refreshFun(1, tableRows);
    }
}

function init_numberPaging(rows, maxPage, fun) {
    tableRows = rows;
    refreshFun = fun;
    tableMaxPage = maxPage;
}

function getTableRows() {
    return tableRows;
}
