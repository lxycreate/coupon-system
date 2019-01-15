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
            username: '',
            password: '',
            user_tip: {
                txt: '用户名不能为空',
                is_height_zero: true
            },
            psd_tip: {
                txt: '密码不能为空',
                is_height_zero: true
            },
            is_psd_error: false //用户名或密码错误
        },
        methods: {
            test: function () {
                this.user_tip.is_show = !this.user_tip.is_show;
            },
            checkInputIsEmpty: function () {
                if (this.username == '' || this.username == null) {
                    this.user_tip.txt = '用户名不能为空';
                    this.user_tip.is_height_zero = false;
                    this.showTip(this.$refs.js_user_tip);
                }
                if (this.password == '' || this.password == null) {
                    this.psd_tip.txt = '密码不能为空';
                    this.psd_tip.is_height_zero = false;
                    this.showTip(this.$refs.js_psd_tip);
                }
                if (this.username.length > 0 && this.password.length > 0) {
                    return true;
                }
                return false;
            },
            hideErrorTip: function (str) {
                if (str == 'user') {
                    this.hideTip(this.$refs.js_user_tip);
                    this.user_tip.is_height_zero = true;
                }
                if (str == 'psd') {
                    this.hideTip(this.$refs.js_psd_tip);
                    this.psd_tip.is_height_zero = true;
                }
            },
            // 显示错误提示
            showTip: function (el) {
                Velocity(el, 'stop');
                Velocity(el, {
                    'margin-top': '10px',
                    height: '20px',
                    opacity: '1'
                }, {
                    duration: "fast",
                    easing: 'linear'
                });
            },
            hideTip: function (el) {
                Velocity(el, 'stop');
                Velocity(el, {
                    'margin-top': '0px',
                    height: '0px',
                    opacity: '0'
                }, {
                    duration: "fast",
                    easing: 'linear'
                });
            }
            // 
        }
        // methods   end

    });
    // Vue app    end
}