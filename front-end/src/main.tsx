import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './pages/styles/style.css'
import { RouterProvider } from 'react-router-dom'
import { routes } from "./routes"

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={routes}/>
  </StrictMode>,
)
