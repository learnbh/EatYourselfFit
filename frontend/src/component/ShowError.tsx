
type Props = {
    message:string
}

export default function ShowError(props: Readonly<Props>){

    return(
        <>
            <span className="text-red-800">{ props.message }</span>
        </>
    )
}