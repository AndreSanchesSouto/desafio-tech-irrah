import { Outlet } from "react-router-dom";
import { Toaster } from "react-hot-toast";

export default function BCB() {
    return (
        <div className="h-screen">
            <Outlet />
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
        </div>
    )
}