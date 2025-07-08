import useSWR from "swr";
import { API_URL } from "../../utils/configs";
import { getUser } from "../../utils/getUser";
import useAxiosConfiguration from "../../utils/axiosFetcher";
import type { Payment } from "../../utils/interfaces";
import { setPlanType } from "../../utils/enums";
import { parseBrl } from "../../utils/parseBrl";

export default function Payments() {
    const fetcher = useAxiosConfiguration();
    const { data: payments } = useSWR<Payment[]>(
        `${API_URL}/payments/${getUser()?.id}`,
        fetcher
    );


    return (
        <div className='flex flex-col h-full bg-neutral-50'>
            <div className='bg-neutral-200 border-neutral-300 py-2 border-b-2 w-full px-5'>
                <h1 className='font-bold text-3xl'>Pagamentos</h1>
            </div>

            <div className="p-5 flex flex-col gap-3 h-full items-center">
                <div className='text-sm font-bold flex h-full flex-col max-w-[65rem] w-full bg-neutral-100 shadow-[0_0_8px_rgba(0,0,0,0.3)] rounded-md'>
                    <div className='flex flex-col w-full px-3 h-full pb-3'>
                        <div className="text-violet-800 font-str text-2xl rounded-t-md py-2">
                            <p>Histórico:</p>
                        </div>
                        <div className=' flex flex-col gap-5 w-full border border-neutral-400 h-full rounded-md p-3 overflow-y-auto'>
                            {payments?.map((payment) => (
                                <div>
                                    <h2>Plano selecionado - {setPlanType(payment.type)}:</h2>
                                    <ul className="font-normal list-disc px-5">
                                        <li>Valor: {parseBrl(payment.price)}</li>
                                        <li>Dia: {payment.createdAt}</li>
                                    </ul>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}