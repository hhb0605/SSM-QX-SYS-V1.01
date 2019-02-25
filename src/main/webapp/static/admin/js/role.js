//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

const {body} = document;
const WIDTH = 1024;
const RATIO = 3;

//Vue实例
var vm = new Vue({
    el: '#app',
    data() {
        return {
            user: {
                id: '',
                username: '',
                password: '',
                email: ''
            },

            defaultActive: '13',
            editDialog: false,
            token: {name: ''},
            mobileStatus: false, //是否是移动端
            sidebarStatus: true, //侧边栏状态，true：打开，false：关闭
            sidebarFlag: ' openSidebar ', //侧边栏标志
            
            personal:false,
	        log : false,
	        user:false,
	        role:false,
	        permissions:[]
        }
    },
    methods: {
        init() {
           
        },
        handleOpen(key, keyPath) {
        },
        handleClose(key, keyPath) {
        	this.editDialog = false;
        },

        isMobile() {
            const rect = body.getBoundingClientRect();
            return rect.width - RATIO < WIDTH
        },

        handleSidebar() {
            if (this.sidebarStatus) {
                this.sidebarFlag = ' hideSidebar ';
                this.sidebarStatus = false;

            } else {
                this.sidebarFlag = ' openSidebar ';
                this.sidebarStatus = true;
            }
            const isMobile = this.isMobile();
            if (isMobile) {
                this.sidebarFlag += ' mobile ';
                this.mobileStatus = true;
            }
        },
        //蒙版
        drawerClick() {
            this.sidebarStatus = false;
            this.sidebarFlag = ' hideSidebar mobile '
        }

    },
    // 生命周期函数
    created() {
        this.init();
        const isMobile = this.isMobile();
        if (isMobile) {
            //手机访问
            this.sidebarFlag = ' hideSidebar mobile ';
            this.sidebarStatus = false;
            this.mobileStatus = true;
        }
    },
    mounted : function() {
		this.$http.post('/SSM-QX-SYS-V1.01/role/doFindCurrentMenus.do').then(result => {
			this.permissions = result.data.data;
			for(var i =0;i<this.permissions.length;i++){
				switch(this.permissions[i]){
					case 'sys:personal':
						this.personal=true;
						   break;
					case 'sys:log':
						this.log = true;
                        break;
					case 'sys:user':
						this.user = true;
                        break;
					case 'sys:role':
						this.role = true;
                        break;
					case 'sys:root':
						this.role = true;
						this.user = true;
						this.log = true;
						this.personal=true;
                        break;
				}
			}
        });
    }
});