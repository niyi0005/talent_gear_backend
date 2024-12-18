import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import PageRouter from './PageRouter.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <PageRouter />
  </StrictMode>,
)
