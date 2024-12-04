import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    children: [
      {
      path: 'product',
      name: 'product',
      component: () => import( '../views/ProductView.vue')
      },
      {
        path: 'current',
        name: 'current',
        component: () => import( '../views/CurrentView.vue')
      },
      {
        path: 'collection',
        name: 'collection',
        component: () => import( '../views/CollectionView.vue')
      },
      {
        path: 'orders',
        name: 'orders',
        component: () => import( '../views/OrdersView.vue')
      },
      {
        path: 'type',
        name: 'type',
        component: () => import( '../views/TypeView.vue')
      },
      {
        path: 'MyProduct',
        name: 'MyProduct',
        component: () => import( '../views/MyProductView.vue')
      },
      {
        path: 'log',
        name: 'log',
        component: () => import( '../views/LogView.vue')
      },
      {
        path: 'table',
        name: 'table',
        component: () => import( '../views/TableView.vue')
      },

    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
