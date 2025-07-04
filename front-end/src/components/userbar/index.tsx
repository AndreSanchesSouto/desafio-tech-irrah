import { UserPlus2 } from "lucide-react";

export default function Userbar() {
    return (
        <div className="bg-violet-400 w-full px-2">
            <div className="text-white font-semibold text-sm w-full py-1 flex justify-end border-b border-violet-900">
                <div className="rounded-2xl border flex gap-2 py-[1px] w-max px-3 items-center">
                    Nome do usuário
                    <UserPlus2 />
                </div>
            </div>
        </div>
    )
}