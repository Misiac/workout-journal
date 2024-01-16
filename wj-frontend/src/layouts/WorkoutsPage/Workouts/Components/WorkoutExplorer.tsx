import test from "../../../../resources/test.png"
import Exercise from "./Exercise";

export const WorkoutExplorer = () => {
    return (
        <>
            <div className="w-full overflow-y-auto">
                <div className="flex flex-wrap w-full px-4  gap-y-4 items-start justify-items-start ">
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                    <Exercise/>
                     <Exercise/>
                     <Exercise/>
                     <Exercise/>
                     <Exercise/>
                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;


{/*<table className="table-auto w-full border-collapse">*/
}
{/*    <tbody>*/
}
{/*    <tr>*/
}
{/*        <td className=" p-4 text-center">Content 1</td>*/
}
{/*        <td className="border-l border-r p-4 text-center">Content 2</td>*/
}
{/*        <td className=" p-4 text-center">Content 3</td>*/
}
{/*    </tr>*/
}
{/*    </tbody>*/
}
{/*</table>*/
}