import {createApp} from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import Home from './views/Home.vue'
import PrimeVue from "primevue/config";
import DataTable from "primevue/datatable";
import Column from "primevue/column";

// styles
import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

// Define some routes
// Each route should map to a component.
// We'll talk about nested routes later.
const routes = [
  {path: '/', name: 'Home', component: Home},
]

// Create the router instance and pass the `routes` option
// You can pass in additional options here, but let's
// keep it simple for now.
const router = createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  history: createWebHistory(),
  routes, // short for `routes: routes`
})

// Create and mount the root instance.
const app = createApp({})
// Make sure to _use_ the router instance to make the
// whole app router-aware.
app.use(router)
app.use(PrimeVue)

// register components
app.component('DataTable', DataTable);
app.component('Column', Column);

app.mount('#app')

// Now the app has started!