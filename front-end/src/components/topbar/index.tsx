import { User } from "lucide-react";
import { getUser } from "../../utils/getUser";
import { useState } from "react";

export default function Topbar() {

    const [showButton, setShowButton] = useState(false)

    return (
        <div className="bg-violet-500 w-full px-2 z-10">
            <div className="text-white font-semibold text-sm w-full py-1 flex justify-end">
                <div className="rounded-2xl border flex gap-2 py-[1px] w-max px-3 items-center cursor-pointer relative"
                    onClick={() => setShowButton(!showButton)}
                >
                    {getUser()?.name}
                    <User />
                </div>
            {showButton &&
                <div className="w-32 h-32 absolute right-2 top-11 border-4 border-violet-500 rounded-md">
                    <div className="rounded-md text-violet-500 g-neutral-100 w-full h-full">
                        <p>Configurações</p>
                    </div>
                </div>
            }
            </div>
        </div>
    )
}