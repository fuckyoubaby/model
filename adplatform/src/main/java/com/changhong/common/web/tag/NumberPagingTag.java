package com.changhong.common.web.tag;

import com.changhong.common.web.paging.Paging;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: pengjie
 * Date: 16-1-4
 * Time: 下午4:38
 */
public class NumberPagingTag extends TagSupport {

    private Paging paging;

    private String urlMapping;

    private int maxPageNumber = 7;

    private String previous = "&lt";

    private String next = "&gt";

    private String goToSpecificPage = "跳转到";

    private String currentPageNumberParameter = "current";

    private String delimiter = "";

    private boolean showGoTo = Boolean.TRUE;

    private boolean showPageInformation = Boolean.TRUE;

    private String function = "";

    @Override
    public int doStartTag() throws JspException {
        String pagingLinks;
        if (paging == null || paging.getTotalPages() <= 0) {
            pagingLinks = "";
        } else {
            pagingLinks = createPagingLinks();
        }
        try {
            writeMessage(pagingLinks);
        } catch (IOException e) {

        }
        return EVAL_BODY_INCLUDE;
    }

    private String createPagingLinks() {
        StringBuilder builder = new StringBuilder();

        builder.append(createPreviousPageLink());
        builder.append(createNumberPageLinks());
        builder.append(createNextPageLink());
        builder.append(showGoTo ? createGoToSpecificPageLink() : "");
        builder.append(showPageInformation ? createPageInformation() : "");

        return builder.toString();
    }

    private String createPreviousPageLink() {
        String prevLink = "";
        if (paging.hasPreviousPage()) {
            prevLink = "<li><a href=\"" + createHref(paging.getPreviousPageNumber()) + "\"" + createLinkClick(paging.getPreviousPageNumber()) + " >" + previous + "</a></li>\n";
        } else {
            prevLink = "<li><a style=\"background:#BDBDBD;\" href=\"javascript:void(0);\">" + previous + "</a></li>\n";
        }
        return prevLink + delimiter;
    }

    private String createNumberPageLinks() {
        String numberPageLinks = "";
        int currentPage = paging.getCurrentPageNumber();
        int totalPages = paging.getTotalPages();

        if (maxPageNumber >= totalPages) {
            numberPageLinks = getNumberPageLinks(1, currentPage, totalPages);
        } else {
            if ((currentPage - maxPageNumber / 2 - 1) <= 0) {
                numberPageLinks = getNumberPageLinks(1, currentPage, maxPageNumber);
            } else if ((currentPage + maxPageNumber / 2 - 1) >= totalPages) {
                numberPageLinks = getNumberPageLinks(totalPages - maxPageNumber + 1, currentPage, totalPages);
            } else {
                numberPageLinks = getNumberPageLinks(currentPage - (maxPageNumber / 2), currentPage, currentPage + (maxPageNumber / 2) - ((maxPageNumber % 2) == 0 ? 1 : 0));
            }
        }

        return numberPageLinks;
    }

    private String getNumberPageLinks(int startPage, int currentPage, int endPage) {
        StringBuilder builder = new StringBuilder();

        for (int i = startPage; i <= endPage; i++) {
            if (i != currentPage) {
                builder.append(createNumberPageLink(i, false));
            } else {
                builder.append(createNumberPageLink(i, true));
            }
        }

        return builder.toString();
    }

    private String createNumberPageLink(int pageNum, boolean currentPage) {
        String linkStr = "";

        if (currentPage) {
            linkStr = "<li class=\"cur\"><a href=\"" + createHref(pageNum) + "\"" + createLinkClick(pageNum) + ">" + pageNum + "</a></li>\n";
        } else {
            linkStr = "<li><a href=\"" + createHref(pageNum) + "\"" + createLinkClick(pageNum) + ">" + pageNum + "</a></li>\n";
        }

        return linkStr;
    }

    private String createNextPageLink() {
        String nextLink = "";
        if (paging.hasNextPage()) {
            nextLink = "<li><a href=\"" + createHref(paging.getNextPageNumber()) + "\"" + createLinkClick(paging.getNextPageNumber()) + " >" + next + "</a></li>\n";
        } else {
            nextLink = "<li><a style=\"background:#BDBDBD;\" href=\"javascript:void(0);\">" + next + "</a></li>\n";
        }
        return nextLink + delimiter;
    }

    private String createPageInformation() {
        return "<li>" + delimiter + "<span style=\"border-style: none;\">(" + paging.getCurrentPageNumber() + " / " + paging.getTotalPages() + ")</span>" + delimiter + "</li>\n";
    }

    private String createGoToSpecificPageLink() {
        String goToSpecificPageLink = "<li><span style=\"border-style: none;\">" + goToSpecificPage + "</span></li>\n" +
                "<li><input style=\"width:40px;height:19px;\" id=\"specificPageNumber\" type=\"text\" value=\"" + paging.getCurrentPageNumber() + "\"/></li>\n<li><a href=\"javascript:void(0);\" onclick=\"" + createGotoClick() + "\">Go</a></li>\n";
        return goToSpecificPageLink;
    }

    private String createGotoClick() {
        if (StringUtils.hasText(function)) {
            return function + "('" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value)" + "+'" + getParameterValues() + "'";
        } else {
            String defaultAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value" + "+'" + getParameterValues() + "'";
            String errorhandleAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + 1 + getParameterValues() + "'";
            return "if(document.getElementById('specificPageNumber').value.match(/\\D+/) != null || " +
                    " document.getElementById('specificPageNumber').value == '' || " +
                    "document.getElementById('specificPageNumber').value == 0 || " +
                    "document.getElementById('specificPageNumber').value > " + paging.getTotalPages() + "){" + errorhandleAction + " ;return ;}"
                    + defaultAction;
        }
    }

    private String createUrl(int pageNumber) {
        return urlMapping + getCurrentPageNumberParameter() + pageNumber + getParameterValues();
    }

    private String createHref(int pageNumber) {
        return StringUtils.hasText(function) ? "javascript:void(0);" : createUrl(pageNumber);
    }

    private String createLinkClick(int pageNumber) {
        return StringUtils.hasText(function) ? " onclick=\"" + function + "('" + createUrl(pageNumber) + "');\"" : "";
    }

    private String getCurrentPageNumberParameter() {
        if (urlMapping.contains("?")) {
            return "&" + currentPageNumberParameter + "=";
        } else {
            return "?" + currentPageNumberParameter + "=";
        }
    }

    public String getParameterValues() {
        return paging.getParameterValues();
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    public void setMaxPageNumber(int maxPageNumber) {
        this.maxPageNumber = maxPageNumber;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setGoToSpecificPage(String goToSpecificPage) {
        this.goToSpecificPage = goToSpecificPage;
    }

    public void setCurrentPageNumberParameter(String currentPageNumberParameter) {
        this.currentPageNumberParameter = currentPageNumberParameter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setShowGoTo(boolean showGoTo) {
        this.showGoTo = showGoTo;
    }

    public void setShowPageInformation(boolean showPageInformation) {
        this.showPageInformation = showPageInformation;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
