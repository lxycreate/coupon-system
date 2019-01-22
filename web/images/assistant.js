var base_url = 'http://localhost:8066';

// 用户名和密码
var username = '';
var password = '';

// 商品滚动条
var goods_scroll;
// 更新或清理
var task_obj = {};
// 作为获取日志的ajax参数
var log_obj = {};
// 获取商品数据的ajax参数
var goods_obj = {};
//  数据分析参数
var data_obj = {};
// 图表
var mychart;
// 内容主体
var js_main_container;
// 初始化
window.onload = function () {
    initUserAndPsd();
    initContent();
    js_main_container.initDataManage();
}

// 初始化姓名和密码
function initUserAndPsd() {
    var temp = Cookies.getJSON('taoAssistant');
    if (temp != undefined) {
        username = temp.username;
        password = temp.password;
        // Cookies.remove('taoAssistant');
    } else {
        window.location.href = "login.html";
    }
    //这里应该删除Cookie,开发过程为了方便先不删
}

// 初始化内容
function initContent() {
    js_main_container = new Vue({
        el: '.js_main_container',
        data: {
            // ==================================  日志区域   start  ==================================  //
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
                    value: 'all',
                    is_select: true
                },
                {
                    name: "更新日志",
                    value: 'update',
                    is_select: false
                },
                {
                    name: "清理日志",
                    value: 'clean',
                    is_select: false
                }
            ],
            // "数据管理"下的按钮   end
            is_log_asc: true, // 当前日志排序方式是升序
            // 更新任务弹窗   start
            update_origin: [{
                    name: '全部',
                    is_select: false
                }, {
                    name: '淘客助手',
                    is_select: false
                },
                {
                    name: '大淘客联盟',
                    is_select: false
                }
            ],
            is_clean: false,
            is_show_update_box: false,
            // 更新任务弹窗   end
            //  清理任务弹窗  start
            clean_origin: [{
                    name: '全部',
                    is_select: false
                }, {
                    name: '淘客助手',
                    is_select: false
                },
                {
                    name: '大淘客联盟',
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
            log_page_input: '',
            // ==================================  日志区域   end  ==================================  //
            // ==================================  商品区域   start  ==================================  //
            goods_filter_btns: [{
                name: '全部',
                value: '0',
                is_select: true
            }, {
                name: '淘客助手',
                value: '1',
                is_select: false
            }, {
                name: '大淘客联盟',
                value: '2',
                is_select: false
            }],
            goods_list: [], //日志列表
            goods_page_num: 0, //第几页日志
            goods_page_count: '?', //日志总数
            is_first_goods_page: false, // 是否是第一页日志
            is_last_goods_page: false, // 是否是最后一页日志
            goods_page_input: '',
            // ==================================  商品区域   end  ==================================  //
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
            },
            goods_page_num: function () {
                if (this.goods_page_num == 1) {
                    this.is_first_goods_page = true;
                } else {
                    this.is_first_goods_page = false;
                }
                if (this.goods_page_num == this.goods_page_count) {
                    this.is_last_goods_page = true;
                } else {
                    this.is_last_goods_page = false;
                }
            },
            goods_page_count: function () {
                if (this.goods_page_num == this.goods_page_count) {
                    this.is_last_goods_page = true;
                } else {
                    this.is_last_goods_page = false;
                }
            }
        },
        // data  end
        methods: {
            clickLeftBtn: function (index) {
                for (var i = 0; i < this.btns.length; ++i) {
                    this.btns[i].is_select = false;
                }
                this.btns[index].is_select = true;
                if (index == 0) {
                    this.initDataManage();
                }
                if (index == 1) {
                    this.initGoodsArea();
                }
                if (index == 2) {
                    this.initData();
                }
            },
            //  ======================================= 日志区域 =================================//
            // 初始化数据管理页
            initDataManage: function () {
                this.resetLogType();
                this.changeLogSortWay(true);
                initLogObj();
                ajaxGetLogList();
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
                initTaskObj();
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
            confirmUpdate: function () {
                var flag = false;
                if (this.update_origin[1].is_select) {
                    task_obj['is_update_tkzs'] = true;
                    if (this.is_clean) {
                        task_obj['is_clean_tkzs'] = true;
                    }
                    flag = true;
                }
                if (this.update_origin[2].is_select) {
                    task_obj['is_update_dtklm'] = true;
                    if (this.is_clean) {
                        task_obj['is_clean_dtklm'] = true;
                    }
                    flag = true;
                }
                if (flag) {
                    task_obj['page_size'] = 10;
                    ajaxCleanOrUpdate(task_obj);
                    this.resetLogType();
                    this.changeLogSortWay(true);
                }
                this.cancelUpdate();
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
                initTaskObj();
            },
            selectCleanItem: function (index) {
                this.selectItem(this.clean_origin, index);
            },
            updateAfterClean: function () {
                this.is_update = !this.is_update;
            },
            confirmClean: function () {
                var flag = false;
                if (this.clean_origin[1].is_select) {
                    task_obj['is_clean_tkzs'] = true;
                    if (this.is_update) {
                        task_obj['is_update_tkzs'] = true;
                    }
                    flag = true;
                }
                if (this.clean_origin[2].is_select) {
                    task_obj['is_clean_dtklm'] = true;
                    if (this.is_update) {
                        task_obj['is_update_dtklm'] = true;
                    }
                    flag = true;
                }
                if (flag) {
                    task_obj['page_size'] = 10;
                    ajaxCleanOrUpdate(task_obj);
                    this.resetLogType();
                    this.changeLogSortWay(true);
                }
                this.cancelClean();
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
                log_obj['page_num'] = 1;
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
            },
            //  ======================================= 日志区域 =================================//
            //  ======================================= 商品区域 =================================//
            // 初始化商品区域
            initGoodsArea: function () {
                this.resetPlatform();
                initGoodsObj();
                ajaxGetGoodsList();
            },
            // 重置platform
            resetPlatform: function () {
                for (var i = 0; i < this.goods_filter_btns.length; ++i) {
                    this.goods_filter_btns[i].is_select = false;
                }
                this.goods_filter_btns[0].is_select = true;
            },
            // 切换数据来源
            changePlatform: function (index) {
                goods_obj['page_num'] = 1;
                for (var i = 0; i < this.goods_filter_btns.length; ++i) {
                    this.goods_filter_btns[i].is_select = false;
                }
                this.goods_filter_btns[index].is_select = true;
                addToGoodsObj('platform_id', this.goods_filter_btns[index].value);
            },
            // 上一页
            preGoodsPage: function () {
                if (this.goods_page_num > 1) {
                    addToGoodsObj('page_num', this.goods_page_num - 1);
                }
            },
            // 下一页
            nextGoodsPage: function () {
                if (this.goods_page_num < this.goods_page_count) {
                    addToGoodsObj('page_num', this.goods_page_num + 1);
                }
            },
            // 首页
            firstGoodsPage: function () {
                addToGoodsObj('page_num', 1);
            },
            // 尾页
            lastGoodsPage: function () {
                addToGoodsObj('page_num', this.goods_page_count);
            },
            // 跳转
            jumpGoods: function () {
                if (this.goods_page_input != '') {
                    var e = parseInt(this.goods_page_input);
                    if (e <= this.goods_page_count) {
                        addToGoodsObj('page_num', e);
                    }
                }
            },
            //  ======================================= 商品区域 =================================//
            initData: function () {
                initDataObj();
                ajaxGetData();
            }
        }
        // 
    });
}

// 
function initTaskObj() {
    var flag = true;
    task_obj = {};
    if (username == undefined || username == '' || password == undefined || password == '') {
        flag = false;
    }
    if (flag) {
        task_obj['username'] = username;
        task_obj['password'] = password;
    } else {
        window.location.href = "login.html";
    }
}

// 初始化log_obj
function initLogObj() {
    var flag = true;
    log_obj = {};
    if (username == undefined || username == '' || password == undefined || password == '') {
        flag = false;
    }
    if (flag) {
        log_obj['username'] = username;
        log_obj['password'] = password;
    } else {
        window.location.href = "login.html";
    }
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
    axios({
        url: base_url + '/getLogList',
        method: 'post',
        params: log_obj
    }).then(function (response) {
        if (response != null && response.data != null && response.data.success) {
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
            temp_log.is_wait = false;
            temp_log.is_running = false;
            temp_log.is_success = false;
            if (temp_log.status == "wait") {
                temp_log.status = "待执行";
                temp_log.is_wait = true;
            }
            if (temp_log.status == "running") {
                temp_log.status = "正在执行";
                temp_log.is_running = true;
            }
            if (temp_log.status == "success") {
                temp_log.status = "成功";
                temp_log.is_success = true;
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

// 初始化goods_obj
function initGoodsObj() {
    var flag = true;
    goods_obj = {};
    if (username == undefined || username == '' || password == undefined || password == '') {
        flag = false;
    }
    if (flag) {
        goods_obj['username'] = username;
        goods_obj['password'] = password;
    } else {
        window.location.href = "login.html";
    }
    goods_obj['page_num'] = 1;
    goods_obj['page_size'] = 10;
}

// 向goods_obj中添加参数
function addToGoodsObj(name, value) {
    goods_obj[name] = value;
    ajaxGetGoodsList();
}

// 获取商品列表
function ajaxGetGoodsList() {
    axios({
        url: base_url + '/getGoodsList',
        method: 'post',
        params: goods_obj
    }).then(function (response) {
        if (response != null && response.data != null && response.data.success) {
            parseGoodsList(response.data);
            console.log(response);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

// 解析商品列表
function parseGoodsList(data) {
    if (data.page_count != null) {
        js_main_container.goods_page_count = data.page_count;
    }
    if (data.page_num != null) {
        js_main_container.goods_page_num = data.page_num;
    }
    if (data.goods_list != null) {
        var temp_list = [];
        for (var i = 0; i < data.goods_list.length; ++i) {
            var temp = data.goods_list[i];
            if (temp.platform_id == '1') {
                temp.platform_id = '淘客助手';
            }
            if (temp.platform_id == '2') {
                temp.platform_id = '大淘客联盟';
            }
            temp_list.push(temp);
        }
        js_main_container.goods_list = temp_list;
    }
    initGoodsScroll();
}

// 加载滚动条
function initGoodsScroll() {
    if (goods_scroll == undefined) {
        goods_scroll = new BScroll('.js_goods_container', {
            scrollY: true,
            click: true,
            scrollbar: {
                fade: true,
                interactive: false // 1.8.0 新增
            },
            mouseWheel: true
        });
    } else {
        goods_scroll.refresh();
    }
}

//
function initDataObj() {
    data_obj = {};
    var flag = true;
    if (username == undefined || username == '' || password == undefined || password == '') {
        flag = false;
    }
    if (flag) {
        data_obj['username'] = username;
        data_obj['password'] = password;
    } else {
        window.location.href = "login.html";
    }
}

// 
function ajaxGetData() {
    axios({
        url: base_url + '/getData',
        method: 'post',
        params: data_obj
    }).then(function (response) {
        if (response != null && response.data != null && response.data.success) {
            parseData(response.data);
        }
        // console.log(response);
    }).catch(function (error) {
        console.log(error);
    });
}

// 解析数据
function parseData(data) {
    console.log(data);
    if (data.platform != null) {
        var temp = {};
        var labels = [];
        var temp_data = [];
        for (var i = 0; i < data.platform.length; ++i) {
            labels.push(data.platform[i].name);
            temp_data.push(data.platform[i].value);
        }
        temp.labels = labels;
        var obj = {};
        obj.data = temp_data;
        obj.backgroundColor = ["#3f5683", "#3f98d0"];
        temp.datasets = [];
        temp.datasets.push(obj);
        initChart(temp);
    }
}

// 加载图表
function initChart(data) {
    // if (mychart == undefined) {
    mychart = new Chart(js_main_container.$refs.js_data_chart, {
        type: "pie",
        data: data,
        options: {
            title: {
                display: true,
                text: '商品数据分析图',
                position: 'bottom',
                fontSize: 16,
                padding: 20
            },
            legend: {
                display: true,
                labels: {
                    fontColor: '#4c62bd',
                    padding: 30
                },
                position: 'bottom'
            }
        }
    });
    // } else {
    //     console.log('update data');
    //     mychart.data.datasets = data.datasets;
    // }
}

// 更新清理事件
function ajaxCleanOrUpdate(temp) {
    console.log(temp);
    axios({
        url: base_url + '/manage/data',
        method: 'post',
        params: temp
    }).then(function (response) {
        if (response != null && response.data != null && response.data.success) {
            parseLogList(response.data);
            console.log(response);
        }
    }).catch(function (error) {
        console.log(error);
    });
}