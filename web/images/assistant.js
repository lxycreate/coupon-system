var base_url = 'http://localhost:8066';

// 用户名和密码
var username = '';
var password = '';

// 作为获取日志的ajax参数
var log_obj = {};
// 内容主体
var js_main_container;
// 初始化
window.onload = function () {
    initUserAndPsd();
    initContent();
    initLogObj();
    ajaxGetLogList();
}

// 初始化姓名和密码
function initUserAndPsd() {
    var temp = Cookies.getJSON('taoAssistant');
    if (temp != undefined) {
        username = temp.username;
        password = temp.password;
    } else {
        // window.location.href = "login.html";
    }
    //这里应该删除Cookie,开发过程为了方便先不删
}

// 初始化内容
function initContent() {
    js_main_container = new Vue({
        el: '.js_main_container',
        data: {
            // 左侧按钮   start
            btns: [{
                name: '数据管理',
                is_select: true,
                icon_class: {
                    'icon-statsbars2': true
                }
            }, {
                name: '数据查看',
                is_select: false,
                icon_class: {
                    'icon-list2': true
                }
            }, {
                name: '数据分析',
                is_select: false,
                icon_class: {
                    'icon-database': true
                }
            }],
            // 左侧按钮   end
            // "数据管理"下的按钮   start
            filter_btns: [{
                    name: '全部',
                    index: 0,
                    value: 'all',
                    is_select: true
                },
                {
                    name: "更新日志",
                    index: 1,
                    value: 'update',
                    is_select: false
                },
                {
                    name: "清理日志",
                    index: 2,
                    value: 'clean',
                    is_select: false
                }
            ],
            // "数据管理"下的按钮   end
            is_log_asc: true, // 当前日志排序方式是升序
            // 更新任务弹窗   start
            update_origin: [{
                    name: '全部',
                    index: 0,
                    is_select: false
                }, {
                    name: '淘客助手',
                    index: 1,
                    is_select: false
                },
                {
                    name: '大淘客联盟',
                    index: 2,
                    is_select: false
                }
            ],
            is_clean: false,
            is_show_update_box: false,
            // 更新任务弹窗   end
            //  清理任务弹窗  start
            clean_origin: [{
                    name: '全部',
                    index: 0,
                    is_select: false
                }, {
                    name: '淘客助手',
                    index: 1,
                    is_select: false
                },
                {
                    name: '大淘客联盟',
                    index: 2,
                    is_select: false
                }
            ],
            is_update: false,
            is_show_clean_box: false,
            //  清理任务弹窗  end
            log_list: [], //日志列表
            log_page_num: 0, //第几页日志
            log_page_count: '?', //日志总数
            is_first_log_page: false, // 是否是第一页日志
            is_last_log_page: false, // 是否是最后一页日志
            log_page_input: ''
        },
        created: function () {
            this.log_page_num = 1;
        },
        watch: {
            log_page_num: function () {
                if (this.log_page_num == 1) {
                    this.is_first_log_page = true;
                } else {
                    this.is_first_log_page = false;
                }
                if (this.log_page_num == this.log_page_count) {
                    this.is_last_log_page = true;
                } else {
                    this.is_last_log_page = false;
                }
            },
            log_page_count: function () {
                if (this.log_page_num == this.log_page_count) {
                    this.is_last_log_page = true;
                } else {
                    this.is_last_log_page = false;
                }
            }
        },
        // data  end
        methods: {
            init: function () {
                this.resetLogType();
                this.changeLogSortWay(true);
            },
            // 更新弹窗 start
            showUpdateBox: function () {
                this.cancelClean();
                if (!this.is_show_update_box) {
                    this.is_show_update_box = true;
                }
            },
            hideUpdateBox: function () {
                if (this.is_show_update_box) {
                    this.is_show_update_box = false;
                }
            },
            cancelUpdate: function () {
                this.hideUpdateBox();
                this.is_clean = false;
                for (var i = 0; i < this.update_origin.length; ++i) {
                    this.update_origin[i].is_select = false;
                }
            },
            selectUpdateItem: function (index) {
                this.selectItem(this.update_origin, index);
            },
            selectItem: function (temp, index) {
                if (index == 0) {
                    if (temp[0].is_select) {
                        for (var i = 0; i < temp.length; ++i) {
                            temp[i].is_select = false;
                        }
                    } else {
                        for (var i = 0; i < temp.length; ++i) {
                            temp[i].is_select = true;
                        }
                    }
                } else {
                    if (temp[index].is_select) {
                        temp[index].is_select = false;
                        temp[0].is_select = false;
                    } else {
                        temp[index].is_select = true;
                        var flag = true;
                        for (var i = 1; i < temp.length; ++i) {
                            if (!temp[i].is_select) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            temp[0].is_select = true;
                        }
                    }
                }
            },
            cleanBeforeUpdate: function () {
                this.is_clean = !this.is_clean;
            },
            // 更新弹窗 end
            // 清理弹窗 start
            showCleanBox: function () {
                this.cancelUpdate();
                if (!this.is_show_clean_box) {
                    this.is_show_clean_box = true;
                }
            },
            hideCleanBox: function () {
                if (this.is_show_clean_box) {
                    this.is_show_clean_box = false;
                }
            },
            cancelClean: function () {
                this.hideCleanBox();
                this.is_update = false;
                for (var i = 0; i < this.clean_origin.length; ++i) {
                    this.clean_origin[i].is_select = false;
                }
            },
            selectCleanItem: function (index) {
                this.selectItem(this.clean_origin, index);
            },
            updateAfterClean: function () {
                this.is_update = !this.is_update;
            },
            // 清理弹窗 end
            // 筛选日志类型
            filterLogType: function (index) {
                log_obj['page_num'] = 1;
                for (var i = 0; i < this.filter_btns.length; ++i) {
                    this.filter_btns[i].is_select = false;
                }
                this.filter_btns[index].is_select = true;
                addToLogObj('type', this.filter_btns[index].value);
            },
            // 重置筛选日志
            resetLogType: function () {
                for (var i = 0; i < this.filter_btns.length; ++i) {
                    this.filter_btns[i].is_select = false;
                }
                this.filter_btns[0].is_select = true;
            },
            // 改变日志排序方式
            changeLogSortWay: function (flag) {
                Velocity(this.$refs.js_sort_btn, 'stop');
                if (flag) {
                    Velocity(this.$refs.js_sort_btn, {
                        'margin-top': '8px',
                        rotateZ: '0deg'
                    });
                    addToLogObj('order', 'create_time asc');
                } else {
                    Velocity(this.$refs.js_sort_btn, {
                        'margin-top': '13px',
                        rotateZ: '180deg'
                    });
                    addToLogObj('order', 'create_time desc');
                }
                this.is_log_asc = flag;
            },
            // 首页
            firstLogPage: function () {
                addToLogObj('page_num', 1);
            },
            // 尾页
            lastLogPage: function () {
                addToLogObj('page_num', this.log_page_count);
            },
            // 下一页
            nextLogPage: function () {
                if (this.log_page_num < this.log_page_count) {
                    addToLogObj('page_num', this.log_page_num + 1);
                }
            },
            // 上一页
            preLogPage: function () {
                if (this.log_page_num > 1) {
                    addToLogObj('page_num', this.log_page_num - 1);
                }
            },
            // 日志跳转
            jumpLog: function () {
                if (this.log_page_input != '') {
                    var e = parseInt(this.log_page_input);
                    if (e <= this.log_page_count) {
                        addToLogObj('page_num', e);
                    }
                }
            }
        }
        // 
    });
}

// 初始化log_obj
function initLogObj() {
    log_obj = {};
    log_obj['username'] = "admin";
    log_obj['password'] = "6323d5f91d07bb414a29c813c35c3660";
    log_obj['page_num'] = 1;
    log_obj['page_size'] = 10;
    log_obj['order'] = 'create_time asc';
}

// 添加参数
function addToLogObj(name, value) {
    log_obj[name] = value;
    ajaxGetLogList();
}

// 获取日志
function ajaxGetLogList() {
    console.log(log_obj);
    axios({
        url: base_url + '/getLogList',
        method: 'post',
        params: log_obj
    }).then(function (response) {
        if (response != null && response.data.success) {
            parseLogList(response.data);
            console.log(response);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

// 解析日志列表
function parseLogList(data) {
    if (data.page_count != null) {
        js_main_container.log_page_count = data.page_count;
    }
    if (data.page_num != null) {
        js_main_container.log_page_num = data.page_num;
    }
    if (data.log_list != null) {
        var temp_list = [];
        for (var i = 0; i < data.log_list.length; ++i) {
            var temp_log = data.log_list[i];
            if (temp_log.status == "wait") {
                temp_log.status = "待执行";
            }
            if (temp_log.status == "running") {
                temp_log.status = "正在执行";
            }
            if (temp_log.status == "success") {
                temp_log.status = "成功";
            }
            if (temp_log.type == "update") {
                temp_log.type = "更新";
            }
            if (temp_log.type == "clean") {
                temp_log.type = "清理";
            }
            if (temp_log.obj == "tkzs") {
                temp_log.obj = "淘客助手";
            }
            if (temp_log.obj == "dtklm") {
                temp_log.obj = "大淘客联盟";
            }
            temp_list.push(temp_log);
        }
        js_main_container.log_list = temp_list;
    }
}

// 限制跳转输入框
function limitInput(event) {
    event.value = event.value.replace(/\D/g, '')
}

function testUpdate() {
    var temp = {};
    temp['page_size'] = 10;
    temp['page_num'] = 1;
    axios({
        url: base_url + '/getLogList',
        method: 'post',
        params: temp
    }).then(function (response) {

        console.log(response);
    }).catch(function (error) {
        console.log(error);
    });
}

// testUpdate();