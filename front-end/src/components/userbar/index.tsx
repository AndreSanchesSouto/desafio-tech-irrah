import { User } from "lucide-react";
import { getUser } from "../../utils/getUser";

export default function Userbar() {
    return (
        <div className="bg-violet-500 w-full px-2 ">
            <div className="text-white font-semibold text-sm w-full py-1 flex justify-end ">
                <div className="rounded-2xl border flex gap-2 py-[1px] w-max px-3 items-center">
                    {getUser()?.name}
                    <User />
                </div>
            </div>
        </div>
    )
}