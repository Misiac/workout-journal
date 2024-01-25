import {PlusIcon} from "lucide-react";

export const LogNewSet = () => {

    return (
        <tr className='fade-animation'>
            <td colSpan={3} className='text-center'>
                <div className='flex  w-full  justify-center'>
                    <PlusIcon/>
                    Add new set
                </div>

            </td>
        </tr>
    );
}
export default LogNewSet;