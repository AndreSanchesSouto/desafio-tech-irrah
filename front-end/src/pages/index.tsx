import { Outlet } from "react-router-dom";
import View from "../components/view";

export default function BCB() {
    return (
        <View>
            <Outlet />
        </View>
    )
}