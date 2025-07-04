import { SendHorizonal } from "lucide-react";

export default function UserChat() {
    return (
        <div className="h-screen w-full flex flex-col bg-neutral-50">
            <div>

            </div>
            <div className="mb-3 h-full">
                <div className="w-full flex justify-center items-end h-full px-16 pb-1">
                    <input className="bg-violet-200 text-lg flex w-full px-5 py-1 rounded-lg" type="text" name="" id="" />
                </div>
                <div className="absolute bottom-3 right-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-400 text-neutral-100 hover:cursor-pointer hover:bg-violet-300 hover:text-white">
                    <SendHorizonal />
                </div>
            </div>
        </div>
    )
} 