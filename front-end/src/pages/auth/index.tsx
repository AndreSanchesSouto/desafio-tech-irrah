import { IdCard, Loader2, User } from "lucide-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import api from "../../utils/api";
import { API_URL, getHeaders } from "../../utils/configs";
import { useHookFormMask } from "use-mask-input";
import type { Auth } from "../../utils/interfaces";
import toast, { Toaster } from "react-hot-toast";
import useUserStore from "../../utils/store/store";
import { Role } from "../../utils/enums";
import BCB_LOGO from "../../../public/BCB.png"

export default function Auth() {
    const { register, getValues, handleSubmit } = useForm<Auth>();
    const [showLoad, setShowLoad] = useState(false);
    const mask = useHookFormMask(register);
    const [login, setLogin] = useState(true)
    const setUser = useUserStore(state => state.setUser);

    async function handleSubmitLogin() {
        if (showLoad) return;
        setShowLoad(true);
        login ? handleLogin() : handleRegister();
    }

    function handleLogin() {
        const data = {
            document: getValues('document')
        }

        const postLogin = api.post(`${API_URL}/auth/login`, data, {
            headers: { 'Content-Type': 'application/json' },
            withCredentials: true
        });

        toast.promise(
            postLogin.then((response) => {
                localStorage.setItem('access_token_api', response.data.token)
                localStorage.setItem('actual_user', JSON.stringify(response.data.client));
                whereToGo(response.data.client.role)
            }),
            {
                loading: "Entrando...",
                success: "Sucesso",
                error: (e) => {
                    setLogin(false)
                    setShowLoad(false)
                    return `Erro: ${e.response.data.message}`
                }
            }
        );
    }

    function whereToGo(role: string) {
        console.log(role === Role.ADMINER)
        if (role === Role.ADMINER) window.location.href = '/chats';
        if (role === Role.COMMON) {
            api.get(`${API_URL}/chats/common-user`, getHeaders())
                .then((chat) => {
                    window.location.href = `chats/${chat.data.id}`;
                }).finally(() => setShowLoad(false));
        }
    }

    function handleRegister() {
        const data = {
            name: getValues('name'),
            document: getValues('document'),
        }

        const postRegister = api.post(`${API_URL}/auth/register`, data, {
            headers: { 'Content-Type': 'application/json' }
        });

        toast.promise(
            postRegister.then(() => {
                setLogin(true);
            })
                .finally(() => setShowLoad(false)),
            {
                loading: "Entrando...",
                success: "Sucesso",
                error: (e) => `Erro: ${e.response.data.message}`
            }
        );
    }

    return (
        <>
            <div className='w-full h-screen flex flex-col justify-center items-center bg-gray-100 text-sm font-cabin'>
                <div className='text-left flex flex-col gap-5 w-full justify-center items-center'>
                    <div className='max-w-[25rem]'>
                        <div className="h-auto w-32">
                            <img src={BCB_LOGO} loading="lazy" />
                        </div>
                    </div>
                    <form id='login'
                        className='flex flex-col gap-3 justify-center items-center bg-violet-500 w-full max-w-[30rem] rounded-lg px-5 py-5 border-2 border-violet-800'
                    >
                        {!login &&
                            <div className="space-y-3 w-full ">
                                <p className="px-3 font-medium text-base text-violet-700 bg-violet-100 w-max rounded-md">Digite seu nome: </p>
                                <div className="flex items-center text-violet-400 w-full">
                                    <label htmlFor="email" className="absolute flex items-center gap-1 px-2">
                                        <User size={20} strokeWidth={2} />
                                    </label>
                                    <input
                                        className='w-full py-3 text-base font-medium pl-10 rounded-md bg-neutral-100 placeholder:text-neutral-400 text-violet-900'
                                        placeholder='Digite seu nome'
                                        type="text"
                                        {...register('name', {
                                            required: true
                                        })}
                                    />
                                </div>
                            </div>
                        }
                        <div className="space-y-3 w-full ">
                            <p className="px-3 font-medium text-base text-violet-700 bg-violet-100 w-max rounded-md">Digite seu CPF / CNPJ: </p>
                            <div className="flex items-center text-violet-400 w-full">
                                <label htmlFor="email" className="absolute flex items-center gap-1 px-2">
                                    <IdCard size={20} strokeWidth={2} />
                                </label>
                                <input
                                    className='w-full py-3 text-base font-medium pl-10 rounded-md bg-neutral-100 placeholder:text-neutral-400 text-violet-900'
                                    placeholder='Digite seu documento'
                                    type="text"
                                    {...mask('document', ["###-###-###-##", '##.###.###/####-##'], {
                                        required: true
                                    })}
                                    onInput={(e: any) => {
                                        e.target.value = e.target.value.replace(/\D/g, '');
                                    }}
                                />
                            </div>
                        </div>

                        <button form='login' className='bg-violet-900 text-white font-bold w-24 h-8 rounded-md hover:bg-violet-700 hover:scale-105 hover:duration-200 duration-200'
                            onClick={handleSubmit(handleSubmitLogin)}
                        >
                            <div className='flex justify-center'>
                                {
                                    !showLoad ? !login ? "Registrar" : "Login" :
                                        <div className='animate-spin'>
                                            <Loader2 strokeWidth={2.3} color={"#FFFF"} width={20} />
                                        </div>
                                }
                            </div>
                        </button>
                    </form>
                    <div className="flex relative">
                        <button
                            onClick={() => setLogin(!login)}
                            className='font-bold text-neutral-400 flex m-auto hover:text-violet-500 transition-all px-10'>
                            {login ? "Registrar" : "Login"}
                        </button>
                    </div>
                </div>
            </div>
            <Toaster
                position="bottom-center"
                gutter={12}
                containerClassName="mt-16 mr-4"
                toastOptions={{
                    duration: 4000,
                    className: "!rounded-md",
                    success: {
                        className: '!text-emerald-600 !font-bold !text-sm',
                        iconTheme: {
                            primary: '#059669',
                            secondary: 'white'
                        }
                    },
                    error: {
                        className: '!text-red-600 !font-bold !text-sm',
                        iconTheme: {
                            primary: '#dc2626',
                            secondary: 'white'
                        }
                    },
                    loading: {
                        className: '!text-cyan-800 !font-bold !text-sm',
                        iconTheme: {
                            primary: '#155e75',
                            secondary: 'white'
                        }
                    }
                }}
            />
        </>
    );
}