import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [
      tailwindcss(),
      react()
  ],
  server: {
    proxy: {
      '/eyf': {
        target: "http://localhost:8080",
      },
    }
  }
});
