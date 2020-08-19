var tabBoxHelper = {
    createNew: function (tabHolderID, tabContentHolderID) {
        var hlp = {};

        hlp.tabHolderID = tabHolderID;
        hlp.tabContentHolderID = tabContentHolderID;
        hlp.tabHolder = $("#"+tabHolderID);
        hlp.tabContentHolder = $("#"+tabContentHolderID);

        hlp.getTabHtml = function (tabName, tabContentID) {
            var htmlstr = "<li class=\"\"><a data-toggle=\"tab\" href=\"#" + tabContentID + "\" onclick='return false;' >" + tabName + "</a></li>";
            return htmlstr;
        };

        hlp.getTabContentHtml = function (tabContentID, tabContent) {
            var htmlstr = "<div id=\"" + tabContentID + "\" class=\"tab-pane\">" + tabContent + "</div>";
            return htmlstr;
        };

        hlp.init = function (tabNameArr, tabContentIDArr, tabContentArr) {
            var htmlTabstr = "";
            var htmlContentStr = "";
            for (var i = 0; i < tabNameArr.length; i++) {
                var tabContentID = tabContentIDArr[i];
                var tabName = tabNameArr[i];
                var tabContent = tabContentArr[i];
                htmlTabstr += hlp.getTabHtml(tabName, tabContentID);
                htmlContentStr += hlp.getTabContentHtml(tabContentID, tabContent);
            }
            hlp.tabHolder.append(htmlTabstr);
            hlp.tabContentHolder.append(htmlContentStr);

            hlp.addTabClick(hlp.tabHolderID, hlp.tabContentHolderID);
        };

        hlp.addTab = function (tabName, tabContentID, tabContent) {
            var htmlstr = "";
            htmlstr = hlp.getTabHtml(tabName, tabContentID);
            hlp.tabHolder.append(htmlstr);

            htmlstr = hlp.getTabContentHtml(tabContentID, tabContent);
            hlp.tabContentHolder.append(htmlstr);

            hlp.addTabClick(hlp.tabHolderID, hlp.tabContentHolderID);
        };

        hlp.delTab = function (tabContentID) {
            $("a[href=#" + tabContentID + "]").remove();
            $("#"+tabContentID).remove();
        };



/////////////////////////////////////////////////////

        hlp.addTabClick = function (tabHolderID, tabContentHolderID) {
            $("#" + tabHolderID + " li").unbind();
            $("#" + tabHolderID + " li").on("click", function (e) {
                $("#" + tabHolderID + " li").each(function () {
                    $(this).removeClass("active");
                });
                $("#" + tabContentHolderID+" div").each(function () {
                    $(this).removeClass("active");
                });

                var target = $(this).find("a").attr("href");
                $(this).addClass("active");
                $(target).addClass("active");
            });
        };

        return hlp;
    }


    ///////////////////////////////////////////////////////////
    //sample
    /*
    function tabBoxInit() {
        var tabBox = tabBoxHelper.createNew("tabHolder", "tabContentHolder");
        var tabs = ["tab1", "tab2", "tab3"];
        var tabContentID = ["tab1", "tab2", "tab3"];
        var tabContent = ["this is tab1", "this is tab2", "this is tab3"];
        tabBox.init(tabs, tabContentID, tabContent);
 
        tabBox.addTab("tab4", "tab4", "this is tab4");
        tabBox.delTab("tab2");
    }
    */

    /*
    html:
            <ul class="nav nav-tabs" id="flowTabs">
                <li class="active"><a data-toggle="tab" href="#flowongoing" onclick='return false;' >进行中</a></li>
                <li><a data-toggle="tab" href="#flowdone" onclick='return false;' >已完成</a></li>
                <li><a data-toggle="tab" href="#flowall" onclick='return false;' >全部</a></li>
            </ul>

    js:
            var tabBox = tabBoxHelper.createNew("flowTabs", "flowContent");
            tabBox.addTabClick(tabBox.tabHolderID, tabBox.tabContentHolderID);


    */
};
