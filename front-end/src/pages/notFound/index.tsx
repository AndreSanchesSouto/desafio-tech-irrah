import { useNavigate } from "react-router-dom"

export default function NotFound() {
    const navigate = useNavigate();

    return(
        <div className="flex flex-col absolute items-center justify-center w-screen h-screen">
            <h1 className="font-bold text-3xl">Página não encontrada...</h1>
            <button 
                onClick={ () => navigate(-1)}
                className='text-violet-400'>Voltar</button>
        </div>
    )
}