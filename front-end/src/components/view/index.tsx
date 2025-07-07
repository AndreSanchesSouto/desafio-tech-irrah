import Userbar from "../userbar";

interface Props {
    children?: React.ReactNode;
}

export default function View({ children }: Props) {
    return (
        <div className="h-full flex flex-col">
            <Userbar />
            <div className="h-full">{children}</div>
        </div>
    )
}