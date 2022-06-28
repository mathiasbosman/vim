import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      vue: 'vue/dist/vue.esm-bundler.js'
    }
  },
  server: {
    proxy: {
      '/rest': {
        target: 'http://localhost:8081',
        secure: false,
        changeOrigin: true, ws: true
      }
    }
  }
})
