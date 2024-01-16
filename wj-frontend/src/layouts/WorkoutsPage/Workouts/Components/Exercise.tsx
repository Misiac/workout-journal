import test from "../../../../resources/test.png";

export const Exercise = () => {

    return (
        <div className="w-1/2 px-2">
            <a href="#"
               className="flex flex-col items-center bg-white border border-gray-200 rounded-lg shadow md:flex-row md:max-w-xl hover:bg-gray-100">
                <img className="object-cover rounded-t-lg h-28 md:w-48 md:rounded-none md:rounded-s-lg w-1/3"
                     src={test}/>
                <div className="flex flex-col justify-between p-4 leading-normal w-2/3">
                    <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">Lat Raise</h5>
                </div>
            </a>
        </div>
    );
}
export default Exercise;