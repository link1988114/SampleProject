$(function () {
    var currMenu = "";
    var lastMenu = "";
    pageinit();

    function pageinit() {
        $.ajax({
            url: "../logined.do",
            method: "get",
            cache: false,
            success: function (re) {
                if (re != "success") {
                    window.location.href = "login.html";
                }
                else {
                    $.get("../user/menu.do", function (re) {
                        var json = new Function("return" + re)();
                        if (json.result == "error") {
                            alert(json.msg);
                        }
                        else {
                            var menuJson = new Function("return" + json.msg)();
                            createMenu(menuJson.data);
                        }
                    });
                }
            },
            error: function (e) {
                window.location.href = "login.html";
            }
        });
    }


    function createMenu(dataArr) {
        var menuList = dataArr;
        var menuObj = '';

        for (var i = 0; i < menuList.length; i++) {
            if (menuList[i].children.length > 0) {
                menuObj = menuObj + "<li class=\"submenu\"> <a href=\"#\"><i class=\"icon "+menuList[i].icon+"\"></i> <span>"+menuList[i].title+"</span> </a><ul>";
                for (var j = 0; j < menuList[i].children.length; j++) {
                    menuObj = menuObj + "<li > <a href=\""+menuList[i].children[j].url+"\" target=\"homeif\"><i class=\"icon "+menuList[i].children[j].icon+"\"></i> <span>"+menuList[i].children[j].title+"</span></a> </li>";
                }
                menuObj = menuObj + "</ul></li>";

            } else {
                menuObj = menuObj + "<li > <a href=\""+menuList[i].url+"\" target=\"homeif\"><i class=\"icon "+menuList[i].icon+"\"></i> <span>"+menuList[i].title+"</span></a> </li>";
            }
        }
        $("#sidebarItems").html(menuObj);

        addMenuClick();
    }

    function addMenuClick() {
        initSideBar();

        $("#sidebarItems li").click(function (e) {
            if ($(this).hasClass("submenu")) {
            }
            else {
                if (lastMenu == "") {
                    currMenu = this;
                    lastMenu = this;
                    this.className = this.className + " active";
                }
                else {
                    lastMenu = currMenu;
                    currMenu = this;
                    setActive(lastMenu, currMenu);
                }
                setBreadcrumb(this.innerHTML);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////
    // init side bar

    function initSideBar() {
        //matrix.js init  sidebar
        // === Sidebar navigation === //

        $('.submenu > a').click(function (e) {
            e.preventDefault();
            var submenu = $(this).siblings('ul');
            var li = $(this).parents('li');
            var submenus = $('#sidebar li.submenu ul');
            var submenus_parents = $('#sidebar li.submenu');
            if (li.hasClass('open')) {
                if (($(window).width() > 768) || ($(window).width() < 479)) {
                    submenu.slideUp();
                } else {
                    submenu.fadeOut(250);
                }
                li.removeClass('open');
            } else {
                if (($(window).width() > 768) || ($(window).width() < 479)) {
                    submenus.slideUp();
                    submenu.slideDown();
                } else {
                    submenus.fadeOut(250);
                    submenu.fadeIn(250);
                }
                submenus_parents.removeClass('open');
                li.addClass('open');
            }
        });

        var ul = $('#sidebar > ul');

        $('#sidebar > a').click(function (e) {
            e.preventDefault();
            var sidebar = $('#sidebar');
            if (sidebar.hasClass('open')) {
                sidebar.removeClass('open');
                ul.slideUp(250);
            } else {
                sidebar.addClass('open');
                ul.slideDown(250);
            }
        });

        // === Resize window related === //
        $(window).resize(function () {
            if ($(window).width() > 479) {
                ul.css({ 'display': 'block' });
                $('#content-header .btn-group').css({ width: 'auto' });
            }
            if ($(window).width() < 479) {
                ul.css({ 'display': 'none' });
                fix_position();
            }
            if ($(window).width() > 768) {
                $('#user-nav > ul').css({ width: 'auto', margin: '0' });
                $('#content-header .btn-group').css({ width: 'auto' });
            }
        });

        if ($(window).width() < 468) {
            ul.css({ 'display': 'none' });
            fix_position();
        }

        if ($(window).width() > 479) {
            $('#content-header .btn-group').css({ width: 'auto' });
            ul.css({ 'display': 'block' });
        }

        // === Tooltips === //
        /*
        $('.tip').tooltip();	
        $('.tip-left').tooltip({ placement: 'left' });	
        $('.tip-right').tooltip({ placement: 'right' });	
        $('.tip-top').tooltip({ placement: 'top' });	
        $('.tip-bottom').tooltip({ placement: 'bottom' });	
        */

        // === Search input typeahead === //
        /*
        $('#search input[type=text]').typeahead({
            source: ['Dashboard','Form elements','Common Elements','Validation','Wizard','Buttons','Icons','Interface elements','Support','Calendar','Gallery','Reports','Charts','Graphs','Widgets'],
            items: 4
        });
        */

        // === Fixes the position of buttons group in content header and top user navigation === //
        function fix_position() {
            var uwidth = $('#user-nav > ul').width();
            $('#user-nav > ul').css({ width: uwidth, 'margin-left': '-' + uwidth / 2 + 'px' });

            var cwidth = $('#content-header .btn-group').width();
            $('#content-header .btn-group').css({ width: cwidth, 'margin-left': '-' + uwidth / 2 + 'px' });
        }

        // === Style switcher === //
        $('#style-switcher i').click(function () {
            if ($(this).hasClass('open')) {
                $(this).parent().animate({ marginRight: '-=190' });
                $(this).removeClass('open');
            } else {
                $(this).parent().animate({ marginRight: '+=190' });
                $(this).addClass('open');
            }
            $(this).toggleClass('icon-arrow-left');
            $(this).toggleClass('icon-arrow-right');
        });

        $('#style-switcher a').click(function () {
            var style = $(this).attr('href').replace('#', '');
            $('.skin-color').attr('href', 'css/maruti.' + style + '.css');
            $(this).siblings('a').css({ 'border-color': 'transparent' });
            $(this).css({ 'border-color': '#aaaaaa' });
        });

        $('.lightbox_trigger').click(function (e) {

            e.preventDefault();

            var image_href = $(this).attr("href");

            if ($('#lightbox').length > 0) {

                $('#imgbox').html('<img src="' + image_href + '" /><p><i class="icon-remove icon-white"></i></p>');

                $('#lightbox').slideDown(500);
            }

            else {
                var lightbox =
                '<div id="lightbox" style="display:none;">' +
                    '<div id="imgbox"><img src="' + image_href + '" />' +
                        '<p><i class="icon-remove icon-white"></i></p>' +
                    '</div>' +
                '</div>';

                $('body').append(lightbox);
                $('#lightbox').slideDown(500);
            }

        });

        /*
        $('#lightbox').live('click', function() { 
            $('#lightbox').hide(200);
        });
        */
    }



    //////////////////////////////////////////////////////////////////////////////////


    function changeFrameHeight() {
        var ifm = document.getElementById("homeframe");
        ifm.height = document.documentElement.clientHeight;

    }

    window.onresize = function () {
        changeFrameHeight();
    }


    $("#homeframe").load(function () {
        changeFrameHeight();
    });


    function setActive(last, current) {
        $(last).removeClass("active");
        current.className = current.className + " active";
    }

    function setBreadcrumb(htmlstr) {
        $("#breadcrumb").html(htmlstr);
    }
});
