import Userbar from "../userbar"

interface Props {
    children?: React.ReactNode
}

export default function View({ children }: Props) {
    return (
        <div>
            <Userbar />
            <div>{children}</div>
        </div>
    )
}