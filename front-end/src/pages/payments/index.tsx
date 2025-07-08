export default function Payments() {
    return (
        <div className='flex flex-col h-full bg-neutral-50'>
            <div className='bg-neutral-200 border-neutral-300 py-2 border-b-2 w-full px-5'>
                <h1 className='font-bold text-3xl'>Pagamentos</h1>
            </div>

            <div className="p-5 flex flex-col gap-3 h-full items-center">
                <div className='text-sm font-bold flex h-full flex-col max-w-[65rem] w-full bg-neutral-100 shadow-[0_0_8px_rgba(0,0,0,0.3)] rounded-md'>
                    <div className='flex flex-col w-full px-3'>
                        <div className="text-violet-800 font-str text-2xl rounded-t-md py-2">
                            <p>Histórico:</p>
                        </div>
                        <div className='grid grid-cols-1 lg:grid-cols-2 gap-4 w-full justify-end h-full'>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}