var base_url = 'http://localhost:8066';

// 用户名和密码
var username = '';
var password = '';
// 内容主体
var js_main_container;
// 初始化
window.onload = function () {
    initUserAndPsd();
    initContent();
}

// 初始化姓名和密码
function initUserAndPsd() {
    var temp = Cookies.getJSON('taoAssistant');
    username = temp.username;
    password = temp.password;
    //这里应该删除Cookie,开发过程为了方便先不删
}

// 初始化内容
function initContent() {
    js_main_container = new Vue({
        el: '.js_main_container',
        data: {
            btns: [{
                name: '数据管理',
                is_select: true,
                icon_class: {
                    'icon-statsbars2': true
                }
            }, {
                name: '更新日志',
                is_select: false,
                icon_class: {
                    'icon-list2': true
                }
            }, {
                name: '数据来源',
                is_select: false,
                icon_class: {
                    'icon-database': true
                }
            }]
        }
    });
}