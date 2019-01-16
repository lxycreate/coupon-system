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
                is_show: false,
                // is_height_zero: true
            },
            psd_tip: {
                txt: '密码不能为空',
                is_show: false,
                // is_height_zero: true
            },
            is_psd_error: false //用户名或密码错误
        },
        methods: {
            login: function () {
                console.log(hex_md5("taoAdmin"));
                console.log('login');
            },
            checkInputIsEmpty: function () {
                if (this.username == '' || this.username == null) {
                    this.user_tip.txt = '用户名不能为空';
                    this.user_tip.is_show = true;
                }
                if (this.password == '' || this.password == null) {
                    this.psd_tip.txt = '密码不能为空';
                    this.psd_tip.is_show = true;
                }
                if (this.username.length > 0 && this.password.length > 0) {
                    return true;
                }
                return false;
            },
            hideErrorTip: function (str) {
                if (str == 'user') {
                    this.user_tip.is_show = false;
                }
                if (str == 'psd') {
                    this.psd_tip.is_show = false;
                }
            },
            // 显示错误提示
            showTip: function (el, done) {
                Velocity(el, 'stop');
                Velocity(el, {
                    'margin-top': '10px',
                    height: '20px',
                    opacity: '1'
                }, {
                    duration: "fast",
                    easing: 'linear',
                    complete: done
                });
            },
            hideTip: function (el, done) {
                Velocity(el, 'stop');
                Velocity(el, {
                    'margin-top': '0px',
                    height: '0px',
                    opacity: '0'
                }, {
                    duration: "fast",
                    easing: 'linear',
                    complete: done
                });
            },
            test: function () {
                this.user_tip.is_show = !this.user_tip.is_show;
            }
            // 
        }
        // methods   end

    });
    // Vue app    end
}