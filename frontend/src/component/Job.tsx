
import { IoPlay } from "react-icons/io5";

type Props = {
    task: string
    description:string,
    onClick: () => void,
    answer:string
}

export default function  Job(props:Readonly<Props>){
    function handleClick(){
        props.onClick();
    }
    return(
        <>
            <tr className= "">
                <td className= "bordertable p-2">{props.task}</td>
                <td className= "bordertable p-2">{props.description}</td>
                <td className= "bordertable">
                    <button className="p-2" onClick={handleClick}
                    >
                        <IoPlay className="text-green-600"/>
                    </button>
                </td>
                <td className= "bordertable p-2">{props.answer}</td>
            </tr>
        </>
    )
}