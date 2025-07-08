import { CircleX, Loader2, PencilLine, Save } from "lucide-react"
import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import toast from "react-hot-toast"
import useSWR, { useSWRConfig } from "swr"
import { useHookFormMask } from "use-mask-input"
import api from "../../utils/api"
import useAxiosConfiguration from "../../utils/axiosFetcher"
import { API_URL, getHeaders } from "../../utils/configs"
import type { ActualUser } from "../../utils/interfaces"
import { getUser } from "../../utils/getUser"

export function Settings() {
    const { mutate } = useSWRConfig()
    const [showLoad, setShowLoad] = useState(false)
    const fetcher = useAxiosConfiguration()
    const { data: user, isLoading } = useSWR<ActualUser>(`${API_URL}/users/${getUser()?.id}`, fetcher)
    const { register, getValues, setValue, handleSubmit, watch } = useForm<ActualUser>();
    const [activeEdition, setActiveEdition] = useState(false)
    const registerDocument = useHookFormMask(register);

    useEffect(() => {
        if (user) {
            setValue("name", user.name);
            setValue("documentId", user.documentId);
            setValue("planType", user.planType);
            setValue("number", user.number);
            setValue("balance", user.balance);
            setValue("role", user.role);
        }
    }, [user, setValue, activeEdition])

    function handleSubmitUpdate() {
        if (showLoad) return
        setShowLoad(true)

        const data = {
            name: getValues('name'),
            document: getValues('documentId'),
            planType: getValues('planType'),
            number: getValues('number'),
            balance: getValues('balance'),
            status: 'ACTIVE'
        }

        const patchUser = api.patch(
            `${API_URL}/users/${user?.id}`,
            data,
            getHeaders()
        )

        toast.promise(
            patchUser.then(async () => {
                await mutate(`${API_URL}/users/${user?.id}`)
                setActiveEdition(false)
            }),
            {
                loading: "Salvando...",
                success: "Informações salvas",
                error: (e) => `Falha ao salvar: ${e.response.data.message}`
            }
        ).finally(() => setShowLoad(false))
    }

    useEffect(() => {
        const planType = watch('planType');

        if (planType === 'postpaid') {
            setValue('number', user?.number || ''); // seta o valor original vindo do backend
        } else if (planType === 'prepaid') {
            setValue('balance', user?.balance || ''); // seta o valor original vindo do backend
        }
    }, [watch('planType'), user, setValue]);

    function editionSubmitter() {
        if (activeEdition && !isLoading && !showLoad) {
            handleSubmit(handleSubmitUpdate)()
        }

        if (!showLoad) {
            setActiveEdition(true)
        }
    }

    function getButtonClass() {
        if (activeEdition) {
            return showLoad
                ? 'scale-110 flex gap-1 px-3 py-1 rounded-md bg-violet-900 text-neutral-100 duration-150 cursor-default opacity-70'
                : 'hover:scale-110 text-violet-800 bg-neutral-300 flex gap-1 px-3 py-1 rounded-md hover:bg-violet-900 hover:text-neutral-100 duration-150';
        }
        return `${showLoad ? 'cursor-default opacity-70 text-neutral-500 bg-neutral-300 ' : 'cursor-pointer hover:scale-110 duration-150 text-neutral-500 hover:text-violet-800 bg-neutral-300 '} flex gap-1 px-3 py-1 rounded-md`;
    }

    return (
        <div className='flex flex-col h-full bg-neutral-50'>
            <div className='bg-neutral-200 border-neutral-300 py-2 border-b-2 w-full px-5'>
                <h1 className='font-bold text-3xl'>Configurações</h1>
            </div>

            <div className="p-5 flex flex-col gap-3 h-full items-center">
                <form className='text-sm font-bold flex  flex-col max-w-[65rem] w-full bg-neutral-100 shadow-[0_0_8px_rgba(0,0,0,0.3)] rounded-md'>
                    <div className='flex flex-col w-full px-3'>
                        <div className="text-violet-800 font-str text-2xl rounded-t-md py-2">
                            <p>Meus dados:</p>
                        </div>
                        <div className='grid grid-cols-1 lg:grid-cols-2 gap-4 w-full justify-end h-full'>
                            <label className="gap-1 flex flex-col w-full">
                                <p className='px-4 text-sm'>Nome do usuário:</p>
                                <input
                                    {...register('name', { required: true })}
                                    type='text'
                                    placeholder='Nome do usuário'
                                    className={`${activeEdition && !showLoad ?
                                        'text-neutral-600 cursor-text' :
                                        'text-neutral-400 cursor-default'
                                        } 
                                            bg-neutral-100 px-4 py-1 border-2 border-neutral-300 font-medium text-base placeholder:text-neutral-500 focus:outline-none rounded-md`}
                                    disabled={!activeEdition || showLoad}
                                />
                            </label>
                            <label className="gap-1 flex flex-col w-full">
                                <p className='pl-4 pr-2'>Documento (CPF / CNPJ):</p>
                                <input
                                    {...registerDocument("documentId", ['###.###.###-##', '##.###.###/####-##'], { required: false })}
                                    type='text'
                                    onInput={(e: any) => {
                                        e.target.value = e.target.value.replace(/\D/g, '');
                                    }}
                                    placeholder='Digite seu documento'
                                    className={`${activeEdition && !showLoad ?
                                        'text-neutral-600 cursor-text' :
                                        'text-neutral-400 cursor-default'
                                        } 
                                            bg-neutral-100 px-4 py-1 border-2 border-neutral-300 font-medium text-base placeholder:text-neutral-500 focus:outline-none rounded-md`}
                                    disabled={!activeEdition || showLoad}
                                />
                            </label>
                            <label className="gap-1 flex w-full flex-col">
                                <p className='px-4 text-sm'>Plano:</p>
                                <select
                                    {...register('planType', { required: true })}
                                    defaultValue={""}
                                    disabled={!activeEdition || showLoad}
                                    className={`${activeEdition && !showLoad ?
                                        'text-zinc-600 cursor-pointer' :
                                        'text-zinc-400 cursor-not-allowed'
                                        }
                                            bg-neutral-100 px-4 py-1 border-2 border-neutral-300 font-medium text-base placeholder:text-neutral-500 focus:outline-none rounded-md`}
                                >
                                    <option disabled value="">Selecione: </option>
                                    <option value="prepaid">Pré pago</option>
                                    <option value="postpaid">Pós pago</option>
                                </select>
                            </label>
                            {watch('planType') === 'postpaid' ?
                                <label className="gap-1 flex flex-col w-full">
                                    <p className='pl-4 pr-2'>Saldo limite por mês:</p>
                                    <input
                                        {...register("number", { required: true })}
                                        type='text'
                                        placeholder='Digite seu limite mensal'
                                        className={`${activeEdition && !showLoad ?
                                            'text-neutral-600 cursor-text' :
                                            'text-neutral-400 cursor-default'
                                            } 
                                            bg-neutral-100 px-4 py-1 border-2 border-neutral-300 font-medium text-base placeholder:text-neutral-500 focus:outline-none rounded-md`}
                                        disabled={!activeEdition || showLoad}
                                    />
                                </label> :
                                <label className="gap-1 flex flex-col w-full">
                                    <p className='pl-4 pr-2'>Saldo na conta:</p>
                                    <input
                                        {...register("balance", { required: true })}
                                        type='text'
                                        placeholder='Digite seu saldo'
                                        className={`${activeEdition && !showLoad ?
                                            'text-neutral-600 cursor-text' :
                                            'text-neutral-400 cursor-default'
                                            } 
                                            bg-neutral-100 px-4 py-1 border-2 border-neutral-300 font-medium text-base placeholder:text-neutral-500 focus:outline-none rounded-md`}
                                        disabled={!activeEdition || showLoad}
                                    />
                                </label>
                            }
                        </div>
                    </div>
                    <div className="w-full flex justify-end p-3">
                        <div className="w-full flex justify-end">
                            <div className="w-full flex justify-start gap-3">
                                <button
                                    form="infouseInfoDriver"
                                    onClick={editionSubmitter}
                                >
                                    <div className={getButtonClass()}>
                                        {activeEdition ? (
                                            showLoad ? (
                                                <div className="animate-spin">
                                                    <Loader2 strokeWidth={2} color={"#FFFF"} width={20} />
                                                </div>
                                            ) : (
                                                <Save size={20} strokeWidth={2} />
                                            )
                                        ) : (
                                            <PencilLine size={20} strokeWidth={2} />
                                        )}
                                        <p className="font-bold text-base">{activeEdition ? "Salvar" : "Editar"}</p>
                                    </div>
                                </button>

                                {activeEdition && (
                                    <button
                                        className={showLoad ? 'opacity-70 text-zinc-600 px-2 cursor-not-allowed' : 'text-zinc-600 hover:scale-110 duration-100 hover:text-red-500 px-2 rounded-full'}
                                        onClick={() => !showLoad && setActiveEdition(false)}
                                    >
                                        <CircleX />
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}