import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "./src/_mantine";`,
      },
    },
  },
  server: {
    host: 'localhost',
    port: 5173,
    open: '/home',
    proxy: {
      // '/home': 'http://localhost:5173/home',
      // open: 'home',
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      // open: 'home'
    }
  }
})
