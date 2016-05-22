var API_BASE_URL = 'http://localhost:3000'

var Welcome = Vue.extend({
    template: '#welcome-template'
})

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
    template: '#activities-template',
    data: function () {
        return {
            activities: []
        }
    },
    ready: function () {
        this.$http.get(API_BASE_URL + '/activities',
                       function (response) {
                           console.log(response)
                           this.activities = response["activities"]
                       })
    }
})

var CreateActivity = Vue.extend({
    template: '#create-activity-template',
    data: function () {
        return {
            name: null,
            description: null,
            schedule: null,
        }
    },
    methods: {
        create_activity: function () {
            activity_data = {
                activity: this.$data
            }
            this.$http.post(API_BASE_URL + '/activities/create',
                            activity_data,
                            function (response) {
                                // TODO: Complete this success function
                                console.log(response)
                            })
        }
    }
})

var Notifications = Vue.extend({
    template: '#notifications-template',
    data: function () {
        return {
            notices: null
        }
    },
    ready: function () {
        this.$http.get(API_BASE_URL + '/notices',
                       function (response) {
                           this.notices = response["notices"]
                       })
    }
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

var AnswerQuestion = Vue.extend({
    template: '#answer-question-template',
    data: function () {
        return {
            question: null,
            content: null
        }
    },
    ready: function () {
        this.$http.get(API_BASE_URL + '/questions/' + this.$route.params["question_id"],
                       function (response) {
                           this.question = response["question"]
                       })
    },
    methods: {
        create_answer: function () {
            answer_data = {
                answer: {
                    question_id: this.$route.params["question_id"],
                    content: this.content
                }
            }
            this.$http.post(API_BASE_URL + '/answers',
                            answer_data,
                            function (response) {
                                console.log(response)
                                router.go('/questions/' + this.$route.params["question_id"])
                            })
        }
    }
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
    '/': {
        component: Welcome
    },
    '/login': {
        component: Login
    },
    '/signup': {
        component: Signup
    },
    '/activities/create': {
        component: CreateActivity
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
    '/questions/:question_id/answer': {
        component: AnswerQuestion
    }
})

router.start(App, '#app')
