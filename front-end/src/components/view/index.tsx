import Topbar from "../topbar";

interface Props {
    children?: React.ReactNode;
}

export default function View({ children }: Props) {
    return (
        <div className="h-full flex flex-col relative">
            <Topbar />
            <div className="h-full">{children}</div>
        </div>
    )
}