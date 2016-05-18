var API_BASE_URL = 'http://localhost:3000'

var Login = Vue.extend({
    template: '#login-template'
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

var Answers = Vue.extend({
    template: '#answers-template'
})

var App = Vue.extend({})

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
    '/answers': {
        component: Answers
    }
})

router.start(App, '#app')
