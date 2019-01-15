var base_url = 'http://localhost:8088';

// login_box对象
var js_login_box;

// 初始化函数
window.onload = function () {
    initLoginBox();
}

// 初始化login_box
function initLoginBox() {
    js_login_box = new Vue({
        el: '.js_login_box',
        data: {
            user_tip: {
                txt: '用户名不能为空',
                is_show: false
            },
            psd_tip: {
                txt: '密码不能为空',
                is_show: false
            }
        },
        methods: {
            test: function () {
                this.user_tip.is_show = !this.user_tip.is_show;
            }
        }
    })
}