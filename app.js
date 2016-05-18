var API_BASE_URL = 'http://localhost:3000'

var Login = Vue.extend({
    template: '#login-template',
    data: function () {
        data = {
            email: "",
            password: "",
        }
        return data
    },
    methods: {
        submit: function () {
            login_data = {
                user:{
                    email: this.email,
                    password: this.password
                }
            }
            console.log(this.user)
            this.$http.get(API_BASE_URL + '/users',
                            login_data).then(
                                function (response) {
                                    alert("Login Success")
                                    this.$root.user = response["data"].user
                                    router.go("/")
                                    console.log(response)
                                },
                                function (response) {
                                    console.log(response)
                                })
        },
        reset: function () {
            this.email = ""
            this.password = ""
        }
    }
})

var Signup = Vue.extend({
    template: '#signup-template',
    data: function () {
        data = {
            username: "",
            email: "",
            password: "",
        }
        return data
    },
    methods: {
        submit: function () {
            signup_data = {
                user:{
                    username: this.username,
                    email: this.email,
                    password: this.password
                }
            }
            this.$http.post(API_BASE_URL + '/users',
                            signup_data).then(
                                function (response) {
                                    alert("Signup Success")
                                    router.go("/login")
                                    console.log(response)
                                },
                                function (response) {
                                    console.log(response)
                                })
        },
        reset: function () {
            this.username = ""
            this.email = ""
            this.password = ""
        }
    }
})

var Activities = Vue.extend({
    template: '#activities-template'
})

var Notifications = Vue.extend({
    template: '#notifications-template'
})

var Questions = Vue.extend({
    template: '#questions-template',
    data: function () {
        data = {
            questions: []
        }
        return data
    },
    ready: function () {
        this.$http.get(API_BASE_URL + '/questions',
                       function (response) {
                           this.questions = response["questions"]
                       })
    }
})

var SingleQuestion = Vue.extend({
    template: '#single-question-template',
    data: function () {
        return {
            question: null
        }
    },
    ready: function () {
        this.$http.get(API_BASE_URL + '/questions/' + this.$route.params["question_id"],
                       function (response) {
                           this.question = response["question"]
                       })
    }
})

var QuestionCreate = Vue.extend({
    template: '#question-create-template',
    data: function () {
        return {
            title: "",
            content: ""
        }
    },
    methods: {
        create_question: function() {
            question_data = {
                question: this.$data
            }
            this.$http.post(API_BASE_URL + '/questions',
                            question_data,
                            function (response) {
                                console.log(response)
                                router.go('/questions/' + response["question"]["id"])
                            })
        }
    }
})

var Answers = Vue.extend({
    template: '#answers-template'
})

var App = Vue.extend({
    data: function () {
        return {
            user: null
        }
    }
})

var router = new VueRouter()

router.map({
    '/login': {
        component: Login
    },
    '/signup': {
        component: Signup
    },
    '/activities': {
        component: Activities
    },
    '/notifications': {
        component: Notifications
    },
    '/questions': {
        component: Questions
    },
    '/questions/:question_id': {
        component: SingleQuestion
    },
    '/questions/create': {
        component: QuestionCreate
    },
    '/answers': {
        component: Answers
    }
})

router.start(App, '#app')
