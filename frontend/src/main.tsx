import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import {RecipeProvider} from "./context/CardRecipeContext.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <RecipeProvider>
          <BrowserRouter>
              <App />
          </BrowserRouter>
      </RecipeProvider>
  </StrictMode>,
)
