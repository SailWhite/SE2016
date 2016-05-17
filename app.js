var Login = Vue.extend({
    template: '#login-template'
})

var Signup = Vue.extend({
    template: '#signup-template'
})

var Activities = Vue.extend({
    template: '#activities-template'
})

var Notifications = Vue.extend({
    template: '#notifications-template'
})

var Questions = Vue.extend({
    template: '#questions-template'
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
    '/answers': {
        component: Answers
    }
})

router.start(App, '#app')
