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
                    is_select: true
                },
                {
                    name: "更新日志",
                    is_select: false
                },
                {
                    name: "清理日志",
                    is_select: false
                }
            ],
            // "数据管理"下的按钮   end
            // "数据管理"下的排序按钮   start
            sort_btns: [{
                name: "时间"
            }],
            // "数据管理"下的排序按钮   end
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
            is_show_clean_box: false
            //  清理任务弹窗  end
        },
        // data  end
        methods: {
            test: function (temp, index) {

            },
            // 更新弹窗 start
            showUpdateBox: function () {
                this.cancelClean();
                if (!this.is_show_update_box) {
                    this.is_show_update_box = true;
                }
                console.log('showUpdateBox');
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
            }
            // 清理弹窗 end
        }
        // 
    });
}


function testUpdate() {
    var temp = {};
    temp['is_update_tkzs'] = true;
    temp['is_update_dtklm'] = true;
    axios({
        url: base_url + '/manage/data',
        method: 'post',
        params: temp
    }).then(function (response) {

        console.log(response);
    }).catch(function (error) {
        console.log(error);
    });
}

// testUpdate();