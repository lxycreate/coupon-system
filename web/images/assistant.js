var base_url = 'http://localhost:8066';

// 用户名和密码
var username = '';
var password = '';
// 内容主体
var js_main_container;
// 初始化
window.onload = function () {
    initUserAndPsd();
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
            btns: []
        }
    });
}