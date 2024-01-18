import Exercise from "./Exercise";

export const WorkoutExplorer = () => {
    return (
        <>
            <div className="w-full overflow-y-auto">
                <div className="grid grid-cols-2 px-4 gap-y-4 ">
                    <Exercise test={'1'}/>
                    <Exercise test={'2'}/>
                    <Exercise test={'3'}/>
                    <Exercise test={'4'}/>
                    <Exercise test={'5'}/>
                    <Exercise test={'6'}/>
                    <Exercise test={'7'}/>
                    <Exercise test={'8'}/>
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