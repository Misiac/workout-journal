export const ExerciseDetails = () => {

    return (
        <table className="table-fixed w-full text-center py-5">
            <thead>
            <tr>
                <th className="border-b border-slate-300">Set</th>
                <th className="border-b border-slate-300">Kg</th>
                <th className="border-b border-slate-300">Reps</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td className="border-b border-slate-300">1</td>
                <td className="border-b border-slate-300">10</td>
                <td className="border-b border-slate-300">15</td>
            </tr>
            <tr>
                <td className="border-b border-slate-300">2</td>
                <td className="border-b border-slate-300">9</td>
                <td className="border-b border-slate-300">12</td>
            </tr>
            <tr>
                <td className="border-b border-slate-300">3</td>
                <td className="border-b border-slate-300">8</td>
                <td className="border-b border-slate-300">10</td>
            </tr>
            </tbody>
        </table>
    );
}

export default ExerciseDetails;