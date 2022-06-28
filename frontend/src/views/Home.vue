<script>
import CategoryDataService from "../services/CategoryDataService";

export default {
  data() {
    return {
      categories: [],
    }
  },

  mounted() {
    this._refreshCategories()
  },

  methods: {
    _refreshCategories() {
      console.debug("Fetching categories")
      CategoryDataService.getAll().then(response => {
        this.categories = response.data._embedded.categories
      })
    }
  }
}
</script>

<template>
  <h1>Home</h1>
  <h2>Categories</h2>
  <ul>
    <li v-for="category in categories" :key="category.id">
      {{ category.name }} ({{ category.code }})
    </li>
  </ul>

  <DataTable :value="categories" class="p-datatable-sm" striped-rows>
    <Column :sortable="true" field="code" header="Code"></Column>
    <Column field="name" header="Name"></Column>
    <Column field="created" header="Created"></Column>
  </DataTable>

</template>