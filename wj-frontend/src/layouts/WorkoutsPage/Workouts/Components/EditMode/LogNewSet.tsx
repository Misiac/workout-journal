import {PlusIcon} from "lucide-react";
import React from "react";

export const LogNewSet: React.FC<{
    addNewSet: () => void
}> = (props) => {
    return (
        <tr className="transition-colors duration-300 hover:bg-gray-100">
            <td colSpan={3} className="text-center">
                <button onClick={props.addNewSet}>
                    <div className="flex w-full justify-center">
                        <PlusIcon className="mr-2 mt-0.5"/>
                        <span className="text-lg font-semibold">Add new set</span>
                    </div>
                </button>
            </td>
        </tr>
    );
}

export default LogNewSet;